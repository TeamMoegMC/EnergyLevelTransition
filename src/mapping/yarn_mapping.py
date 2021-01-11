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

from typing import Tuple, Dict, Optional

from util import mapping_downloader
from util.parser import Parser
from util.sources import SourceMap


def read(mc_version: str, yarn_version: Optional[str]) -> SourceMap:
    yarn_v2 = mapping_downloader.load_yarn_v2(mc_version, yarn_version)
    classes, fields, methods, params = parse_yarn_v2(yarn_v2)

    return SourceMap(fields, methods, params, classes)


def parse_yarn_v2(text: str) -> Tuple[Dict, Dict, Dict, Dict]:
    parser = Parser(text)

    methods = {}
    fields = {}
    classes = {}
    params = {}

    # skip the header line
    parser.scan_until('\n')
    while not parser.eof():
        if parser.try_scan('c'):
            parser.scan('\t')
            intermediary_clazz = parser.scan_identifier()
            parser.scan('\t')
            named_clazz = parser.scan_identifier()
            parser.scan('\n')
            classes[intermediary_clazz] = named_clazz
            intermediary_method = None
            while not parser.eof():
                if parser.try_scan('\tm\t'):
                    params, intermediary_method, named_method = parse_mapping(parser)
                    methods[intermediary_method] = named_method
                elif parser.try_scan('\tf\t'):
                    params, intermediary_field, named_field = parse_mapping(parser)
                    fields[intermediary_field] = named_field
                elif parser.try_scan('\t\tp\t'):
                    index, named_param = parse_param(parser)
                    # noinspection PyUnboundLocalVariable
                    params[intermediary_method + '_' + str(index)] = named_param
                elif parser.try_scan('\tc') or parser.try_scan('\t\tc') or parser.try_scan('\t\t\tc'):
                    # todo: comments at class, method and param level
                    parser.scan_until('\n')
                else:
                    break
        else:
            parser.error('unknown')

    return classes, fields, methods, params


def parse_class(parser: Parser) -> Tuple[str, str, str]:
    parser.scan('\t')
    notch_class = parser.scan_identifier()
    parser.scan('\t')
    intermediary = parser.scan_identifier()
    if parser.try_scan('\t'):
        name = parser.scan_identifier()
    else:
        name = intermediary
    parser.scan('\n')
    return notch_class, intermediary, name


def parse_mapping(parser: Parser) -> Tuple[str, str, str]:
    params = parser.scan_identifier()
    parser.scan('\t')
    intermediary = parser.scan_identifier()
    parser.scan('\t')
    named = parser.scan_identifier()
    parser.scan('\n')
    return params, intermediary, named


def parse_param(parser: Parser) -> Tuple[str, str]:
    index = parser.scan_identifier()
    parser.scan('\t\t')
    named = parser.scan_identifier()
    parser.scan('\n')
    return index, named
