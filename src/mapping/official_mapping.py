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

from typing import Tuple, Set

from util import mapping_downloader
from util.parser import Parser
from util.sources import SourceMap


def read(mc_version: str) -> Tuple[SourceMap, Set]:
    # Load / Download official mappings
    client, server = mapping_downloader.load_official(mc_version)

    client, client_lambdas = parse_official(client)
    server, server_lambdas = parse_official(server)

    # Validate, then ditch the server mappings
    validate_official(client, server)

    return client, client_lambdas


def parse_official(text: str) -> Tuple[SourceMap, Set]:
    """
    Parse proguard mappings
    Return the source map consisting of classes, methods, fields, and inferred parameters
    """

    parser = Parser(text)
    sources = SourceMap()
    lambda_methods = set()

    temp_methods = {}  # during parsing these will be stored with mojang names, they need to be converted to notch identifiers

    # Skip comment lines (license text)
    while parser.try_scan('#'):
        parser.scan_until('\n')

    while not parser.eof():
        # Class
        mojmap_class = parser.scan_identifier().replace('.', '/')
        parser.scan(' -> ')
        notch_class = parser.scan_identifier().replace('.', '/')
        parser.scan(':\n')

        sources.classes[notch_class] = mojmap_class

        # Class members
        while parser.try_scan('    '):
            if parser.next() in Parser.NUMERIC_CHARS:
                # Ignore line numbers
                parser.scan_until(':')
                parser.scan_until(':')

            member_type = parser.scan_identifier().replace('.', '/')
            parser.scan(' ')
            mojmap_member = parser.scan_identifier().replace('.', '/')
            parser.scan(' -> ')
            notch_member = parser.scan_identifier().replace('.', '/')
            parser.scan('\n')

            if mojmap_member.endswith(')'):
                # Method
                mojmap_method, params = mojmap_member.split('(')  # Extract the method name and params
                params = params[:-1]  # Remove the last end bracket
                params = params.split(',')  # Split params
                if params == ['']:  # Skip empty param lists from splitting
                    params = []
                params = tuple(params)  # Replace package names with '/'

                # Ignore static initializers and constructors
                if notch_member != '<clinit>' and notch_member != '<init>':
                    temp_methods[(notch_class, notch_member, member_type, tuple(params))] = mojmap_method

            else:
                # Field
                sources.fields[(notch_class, notch_member)] = mojmap_member

    mojmap_to_notch_classes = dict((mojmap, notch) for notch, mojmap in sources.classes.items())

    for method, mojmap_method in temp_methods.items():
        notch_class, notch_method, return_type, params = method
        return_desc = Parser.convert_type_to_descriptor(return_type, mojmap_to_notch_classes)
        params_desc = []
        for p in params:
            params_desc.append(Parser.convert_type_to_descriptor(p, mojmap_to_notch_classes))
        method_desc = '(' + ''.join(params_desc) + ')' + return_desc
        sources.methods[(notch_class, notch_method, method_desc)] = mojmap_method

        if 'lambda$' in mojmap_method:
            lambda_methods.add((notch_class, notch_method, method_desc))

    return sources, lambda_methods


def validate_official(client: SourceMap, server: SourceMap):
    """
    Validates various assumptions about the official mappings
    """

    # Validate official server is a subset of official client (in entirety)
    official_client_v_server = client.keys().compare_to(server.keys())
    assert official_client_v_server.right_only.is_empty()
