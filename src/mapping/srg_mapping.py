#   Copyright (c) 2020. TeamMoeg
#
#   This file is part of Energy Level Transition.
#
#   Energy Level Transition is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, version 3.
#
#   Energy Level Transition is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#
#   You should have received a copy of the GNU General Public License
#   along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.

from typing import Dict, Tuple, Set, Any

from util import utils, mapping_downloader
from util.parser import Parser
from util.sources import SourceMap


def read(mc_version: str) -> Tuple[SourceMap, Dict[str, Dict[Any, Set]]]:
    joined, static_methods, constructors = mapping_downloader.load_mcpconfig(mc_version)

    classes, methods, fields = parse_mcpconfig_joined_tsrg(joined)
    static_methods = parse_mcpconfig_static_methods(static_methods)
    constructors = parse_mcpconfig_constructors(constructors)

    params, indexed_params = generate_srg_params(classes, methods, static_methods, constructors)

    return SourceMap(fields, methods, params, classes), indexed_params


def parse_mcpconfig_joined_tsrg(srg_joined: str) -> Tuple[Dict, Dict, Dict]:
    """
    Parse joined.tsrg into a mapping of classes, methods and fields

    """
    classes = {}
    methods = {}
    fields = {}
    parser = Parser(srg_joined)

    while not parser.eof():
        notch_class = parser.scan_identifier()
        parser.scan(' ')
        srg_class = parser.scan_identifier()
        parser.scan('\n')
        classes[notch_class] = srg_class
        while parser.try_scan('\t'):
            notch_member = parser.scan_identifier()
            parser.scan(' ')
            if parser.next() == '(':
                _, _, desc = parser.scan_java_method_descriptor()
                parser.scan(' ')
                srg_method = parser.scan_identifier()
                methods[(notch_class, notch_member, desc)] = srg_method
            else:
                srg_field = parser.scan_identifier()
                fields[(notch_class, notch_member)] = srg_field
            parser.scan('\n')

    return classes, methods, fields


def parse_mcpconfig_static_methods(srg_static_methods: str) -> Set:
    static_methods = set()
    parser = Parser(srg_static_methods)
    while not parser.eof():
        srg_method = parser.scan_identifier()
        parser.scan('\n')
        static_methods.add(srg_method)

    return static_methods


def parse_mcpconfig_constructors(srg_constructors: str) -> Dict:
    constructors: Dict = {}
    parser = Parser(srg_constructors)
    while not parser.eof():
        srg_id = int(parser.scan_identifier())
        parser.scan(' ')
        srg_class = parser.scan_identifier()
        parser.scan(' ')
        _, _, desc = parser.scan_java_method_descriptor()
        parser.scan('\n')

        constructors[(srg_class, '<init>', desc)] = srg_id

    return constructors


def generate_srg_params(classes: Dict, methods: Dict, static_methods: Set, constructors: Dict) -> Tuple[Dict, Dict[str, Dict[Any, Set]]]:
    """
    Generate srg named parameters by looking through method and constructors

    """
    params = {}
    indexed_params = {}  # class -> method -> params
    for method, srg_method in methods.items():
        group = set()
        notch_class, notch_method, method_desc = method
        _, param_types = Parser.decode_java_method_descriptor(method_desc)

        param_index = 0
        if srg_method not in static_methods:
            param_index += 1

        for param_type in param_types:
            if srg_method.startswith('func_'):
                srg_id = srg_method.split('_')[1]
            else:
                srg_id = srg_method
            srg_param = 'p_' + srg_id + '_' + str(param_index) + '_'
            params[(notch_class, notch_method, method_desc, param_index)] = srg_param
            group.add((srg_param, param_type))
            if param_type in ('J', 'D'):
                param_index += 2
            else:
                param_index += 1

        # Identify if this class is an anon. class by name
        if '$' in notch_class and notch_class.split('$')[-1].isnumeric():
            notch_class_key = notch_class[:notch_class.rindex('$')]  # skip the $[number] at the end, identify by the non-anon. class
            method_key = (notch_class, notch_method, method_desc)
        else:
            notch_class_key = notch_class
            method_key = (None, notch_method, method_desc)
        if notch_class_key not in indexed_params:
            indexed_params[notch_class_key] = {}
        indexed_params[notch_class_key][method_key] = group

    reverse_classes = utils.invert_injective_mapping(classes)
    for method, srg_id in constructors.items():
        group = set()
        srg_class, _, method_desc = method
        if srg_class in reverse_classes:
            notch_class = reverse_classes[srg_class]

            _, param_types = Parser.decode_java_method_descriptor(method_desc)

            param_index = 1
            for param_type in param_types:
                srg_param = 'p_i' + str(srg_id) + '_' + str(param_index) + '_'
                params[(notch_class, '<init>', method_desc, param_index)] = srg_param
                group.add((srg_param, param_type))
                if param_type in ('J', 'D'):
                    param_index += 2
                else:
                    param_index += 1

            # Identify if this class is an anon. class by name
            if '$' in notch_class and notch_class.split('$')[-1].isnumeric():
                notch_class_key = notch_class[:notch_class.rindex('$')]  # skip the $[number] at the end, identify by the non-anon. class
                method_key = (notch_class, '<init>', method_desc)
            else:
                notch_class_key = notch_class
                method_key = (None, '<init>', method_desc)
            if notch_class_key not in indexed_params:
                indexed_params[notch_class_key] = {}
            indexed_params[notch_class_key][method_key] = group

    return params, indexed_params
