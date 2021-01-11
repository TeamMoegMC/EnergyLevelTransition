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

# This is why we can't have nice things
# Credits to alcatrazEscapee (https://github.com/alcatrazEscapee) for making this script.
# Licensed under MIT.

import argparse
import re
from typing import Dict, Set, Any

from mapping import srg_mapping, mcp_mapping, spreadsheet_mapping, official_mapping
from util import utils, mapping_downloader
from util.parser import Parser
from util.sources import SourceSet, SourceMap, SourceSetComparison

CLI_HELP = """
This is an interface similar to K9 used to reverse engineer mapped names. It will read the mapping log file and can show information about all mapped items, search, and filter based on input commands.
A command consists of a space separated list of elements, which each either produce a set of results, or act upon the previous (current) results. At the end of the statement, the results will be displayed.
Commands:
> help      - print this menu
> exit      - exits the CLI
> nc <name> - lists [n]otch [c]lass names matching <name>
> c <name>  - lists srg [c]lass names matching <name>
> m <name>  - lists named [m]ethods matching <name>
> f <name>  - lists named [f]ields matching <name>
> p <name>  - lists named [p]arams matching <name>
> fc <name> - [f]ilters the results (methods, fields, or params) by the [c]lass <name>
> fm <name> - [f]ilters the results (params) by the [m]ethod <name>
> [ <num>   - only includes a single entry, at index <num> of the previous results
> gm        - [g]ets all [m]ethods from the first class of the previous results
> gp        - [g]ets all [p]arameters from the first method of the previous results
> max <num> - include up to <num> entries in the output (default 10)
"""


def main():
    """ Entry point and argument parser """
    parser = argparse.ArgumentParser(description='The Complete MCP Export')
    parser.add_argument('--cli', action='store_true', dest='cli', help='Run the CLI for mapping reverse engineering.')
    parser.add_argument('--version', type=str, default='complete-20201028-1.16.4', help='The version of the complete mcp export')
    parser.add_argument('--cache', type=str, default='../build/', help='The cache folder, to look for downloaded mappings and other static files')
    parser.add_argument('--stats-only', action='store_true', dest='only_stats', help='Stop after loading and printing statistics for the input mappings.')
    parser.add_argument('--advanced-comments', action='store_true', dest='advanced_comments', help='Add additional comment lines to every field with the srg name and mcp name (if known)')
    parser.add_argument('--include-spreadsheet', type=str, default='none', choices=['none', 'verified', 'all'], help='If the MCP Spreadsheet mappings should be sourced for parameter names? Warning: Including these names (especially unverified names) may cause mapping issues!')

    # Individual versions
    parser.add_argument('--mc-version', type=str, default='1.16.4', help='The Minecraft version used to download official, srg, and spreadsheet mappings')
    parser.add_argument('--mcp-version', type=str, default='1.16.3', help='The Minecraft version used to download mcp mappings')
    parser.add_argument('--mcp-date', type=str, default='20201028', help='The snapshot date for the mcp mappings')

    args = parser.parse_args()

    mapping_downloader.set_cache_root(args.cache)
    if args.cli:
        cli(args.version)
    else:
        make(args.only_stats, args.advanced_comments, args.include_spreadsheet, args.version, args.mc_version, args.mcp_version, args.mcp_date)
        print('Complete MCP Export Done. Version = \'%s\'' % args.version)


def make(stats_only: bool, advanced_comments: bool, include_spreadsheet: str, version: str, mc_version: str, mcp_version: str, mcp_date: str):
    print('Reading mappings...')

    mojmap, mojmap_lambdas = official_mapping.read(mc_version)
    srg, srg_indexed_params = srg_mapping.read(mc_version)
    mcp, mcp_method_comments, mcp_field_comments = mcp_mapping.read(mcp_version, mcp_date)

    # Load spreadsheet mappings or defaults
    if include_spreadsheet == 'none':
        ss_root, ss, ss_method_comments, ss_field_comments = SourceSet(), SourceMap(), dict(), dict()
    else:
        ss_root, ss, ss_method_comments, ss_field_comments = spreadsheet_mapping.read(mc_version, include_spreadsheet == 'all')
    manual_mappings = mapping_downloader.load_manual_corrections(mc_version)

    print('Validating mappings...')

    # Validate srg is a subset of official (classes, methods, and fields)
    mojmap_v_srg = mojmap.keys().compare_to(srg.keys())
    assert not mojmap_v_srg.right_only.classes
    assert not mojmap_v_srg.right_only.methods
    assert not mojmap_v_srg.right_only.fields

    # Filter out mcp names which are not used in this mc version
    mcp = mcp.filter_keys(srg.values())
    srg_v_mcp = srg.values().compare_to(mcp.keys())
    assert srg_v_mcp.right_only.is_empty()

    mcp_v_ss = mcp.keys().compare_to(ss.keys())
    srg_v_ss = srg.values().compare_to(ss.keys())
    srg_v_both = srg.values().compare_to(mcp_v_ss.union)

    # Print basic conclusions using official, mojmap and mcp mappings

    print('=== MCP Mappings ===')
    print_compare(srg_v_mcp)
    print('=== MCP Spreadsheet ===')
    print_compare(srg_v_ss)
    print('=== MCP Mappings + Spreadsheet ===')
    print_compare(srg_v_both)

    if stats_only:
        return

    # Generate the result mappings
    temp = SourceMap(fields=srg.fields, methods=srg.methods)  # notch -> srg (methods / fields)
    temp = temp.inverse()  # srg -> notch (fuzzy, methods / fields)
    temp = temp.compose(mojmap)  # srg -> official (fuzzy, methods / fields)
    result = temp.select()  # srg -> official (methods / fields)

    # Procedurally generate the rest of the parameters, watching for conflicts with each other.
    generate_param_names(srg, srg_indexed_params, mcp, ss, manual_mappings, mojmap_lambdas, result)

    print('=== Mojmap + MCP Bot + MCP Spreadsheet + Auto Param ===')
    print_compare(srg.values().compare_to(result.keys()))

    # Comments
    field_comments = {}
    utils.append_mapping(field_comments, mcp_field_comments)
    utils.append_mapping(field_comments, ss_field_comments)
    print('Field Comments = %d' % len(field_comments))

    method_comments = {}
    utils.append_mapping(method_comments, mcp_method_comments)
    utils.append_mapping(method_comments, ss_method_comments)
    print('Method Comments = %d' % len(method_comments))

    # Write reverse lookup log output
    write_reverse_lookup_log(version, srg, result)

    # Generate srg name comments - do this after actual comments are generated
    if advanced_comments:
        generate_advanced_comments(field_comments, result.fields, mcp.fields)
        generate_advanced_comments(method_comments, result.methods, mcp.methods)

    # Write mcp mappings
    mcp_mapping.write(version, result, field_comments, method_comments)
    mcp_mapping.publish(version)

    print('Done')


def print_compare(compare: SourceSetComparison):
    print('Fields: %d / %d (%.2f%%)' % (len(compare.intersect.fields), len(compare.left.fields), 100 * len(compare.intersect.fields) / len(compare.left.fields)))
    print('Methods: %d / %d (%.2f%%)' % (len(compare.intersect.methods), len(compare.left.methods), 100 * len(compare.intersect.methods) / len(compare.left.methods)))
    print('Params: %d / %d (%.2f%%)' % (len(compare.intersect.params), len(compare.left.params), 100 * len(compare.intersect.params) / len(compare.left.params)))


def generate_param_names(srg: SourceMap, srg_indexed_params: Dict[str, Dict[Any, Set]], mcp: SourceMap, ss: SourceMap, manual_params: Dict[str, str], mojmap_lambdas: Set, result: SourceMap):
    # The set of param names which match class names. These are denied ('In' is suffixed) in order to prevent conflicts with local variables
    reserved_class_name_params = set()
    for srg_class in srg.classes.values():
        if '/' in srg_class:  # Remove packages
            srg_class = srg_class.split('/')[-1]
        if '$' in srg_class:  # Ignore inner classes for this rule, as in sources they use '$' in the name
            continue
        srg_class = srg_class.lower()  # lowercase the entire class name
        reserved_class_name_params.add(srg_class)

    unique_srg_pattern = re.compile('p_i?[0-9]+_[0-9]+_')

    # Group indexing by class
    for notch_class, entries in srg_indexed_params.items():

        class_groups = []  # groups that need to be checked for conflicts at a class level
        method_groups = []  # groups belonging to a single method

        for entry, param_group in entries.items():
            anon_class, notch_method, method_desc = entry
            # Exclude param groups which belong to lambda methods
            method_key = (notch_class, notch_method, method_desc)
            if method_key in mojmap_lambdas:
                class_groups.append(param_group)
                continue

            method_groups.append(param_group)

        class_reserved_names = set()
        for group in method_groups:
            reserved_names = set()

            for srg_param, param_type in sorted(group):
                # Ignore srg params that don't have a srg id. These will not be accurate (as they'll only work for the first method of this name found) and cause lots of conflicts
                if not re.match(unique_srg_pattern, srg_param):
                    continue
                # Strict order of precedence of which mapping to apply
                if srg_param in result.params:  # Already mapped, do not try and remap. Add the name for conflict resolution
                    reserved_names.add(result.params[srg_param])
                    continue
                elif srg_param in manual_params:
                    name = manual_params[srg_param]
                elif srg_param in mcp.params:
                    name = mcp.params[srg_param]
                elif srg_param in ss.params:
                    name = ss.params[srg_param]
                else:
                    name = generate_param_name(param_type, srg.classes)

                if name in reserved_class_name_params or name in utils.JAVA_KEYWORDS:
                    name += 'In'  # prevent local variable conflicts, or names mapped to keywords

                if name in reserved_names:
                    name = resolve_name_conflicts(name, reserved_names)

                result.params[srg_param] = name
                reserved_names.add(name)

            class_reserved_names |= reserved_names

        for group in class_groups:
            for srg_param, param_type in sorted(group):
                # Ignore srg params that don't have a srg id. These will not be accurate (as they'll only work for the first method of this name found) and cause lots of conflicts
                if not re.match(unique_srg_pattern, srg_param):
                    continue
                # Strict order of precedence of which mapping to apply
                if srg_param in result.params:  # Already mapped, do not try and remap. Add the name for conflict resolution
                    class_reserved_names.add(result.params[srg_param])
                    continue
                elif srg_param in manual_params:
                    name = manual_params[srg_param]
                elif srg_param in mcp.params:
                    name = mcp.params[srg_param]
                elif srg_param in ss.params:
                    name = ss.params[srg_param]
                else:
                    name = generate_param_name(param_type, srg.classes)

                if name in reserved_class_name_params or name in utils.JAVA_KEYWORDS:
                    name += 'In'  # prevent local variable conflicts, or names mapped to keywords

                if name in class_reserved_names:
                    name = resolve_name_conflicts(name, class_reserved_names)

                result.params[srg_param] = name
                class_reserved_names.add(name)


def generate_param_name(param_type: str, srg_classes: Dict) -> str:
    name, arrays = Parser.convert_descriptor_to_type(param_type, srg_classes)
    if '/' in name:  # Remove packages
        name = name.split('/')[-1]
    if '$' in name:  # Remove inner classes
        name = name.split('$')[-1]
    if len(name) >= 2 and name.startswith('I') and name[1].isupper():  # Remove I prefix on interfaces
        name = name[1:]
    if arrays > 0:  # Add 'Array' for array levels
        name += 'Array' * arrays
    name = name[0].lower() + name[1:]  # lowerCamelCase
    name += '_'  # Signal that this is an automatic name, prevent conflicts with local variables
    return name


def resolve_name_conflicts(name: str, reserved_names: Set) -> str:
    if name in reserved_names:
        if name.endswith('_'):
            proto_name = name[:-1]
            auto = True
        else:
            proto_name = name
            auto = False
        proto_name = proto_name.rstrip('0123456789')  # strip any previous numeric value off the end
        count = 1
        while name in reserved_names:
            name = proto_name + str(count)
            if auto:
                name += '_'
            count += 1
    return name


def generate_advanced_comments(comments: Dict[str, str], mapping: Dict[str, str], alternative_mapping: Dict[str, str]):
    for srg, named in mapping.items():
        adv_comment = 'Mappings: SRG: ' + srg
        if srg in alternative_mapping:
            adv_comment += ', MCP: ' + alternative_mapping[srg]

        if srg in comments:
            comments[srg] = comments[srg] + '\\n' + adv_comment
        else:
            comments[srg] = adv_comment


def write_reverse_lookup_log(version: str, srg: SourceMap, result: SourceMap):
    with open(mapping_downloader.get_cache_root() + '/mcp_snapshot-%s.log' % version, 'w') as f:
        for notch_class, srg_class in srg.classes.items():
            f.write('C\t%s\t%s\n' % (notch_class, srg_class))
        for field, srg_field in srg.fields.items():
            notch_class, notch_field = field
            named_class = srg.classes[notch_class] if notch_class in srg.classes else notch_class
            f.write('F\t%s\t%s\t%s\n' % (named_class, result.fields[srg_field], srg_field))
        for method, srg_method in srg.methods.items():
            notch_class, notch_method, method_desc = method
            named_class = srg.classes[notch_class] if notch_class in srg.classes else notch_class
            f.write('M\t%s\t%s\t%s\t%s\n' % (named_class, result.methods[srg_method], srg_method, method_desc))
        for param, srg_param in srg.params.items():
            notch_class, notch_method, method_desc, param_index = param
            named_class = srg.classes[notch_class] if notch_class in srg.classes else notch_class
            method_key = (notch_class, notch_method, method_desc)
            if method_key in srg.methods:
                srg_method = srg.methods[method_key]
                named_method = result.methods[srg_method]
            else:
                named_method = srg_method = notch_method
            named_param = result.params[srg_param] if srg_param in result.params else srg_param
            f.write('P\t%s\t%s\t%s\t%s\t%s\t%s\n' % (named_class, named_method, param_index, named_param, srg_method, srg_param))


def cli(version: str):
    print('Loading CLI...')

    with open(mapping_downloader.get_cache_root() + 'mcp_snapshot-%s.log' % version) as f:
        log = f.read()

    sources = []
    for line in log.split('\n'):
        if line != '':
            sources.append(tuple(line.split('\t')))
    indexed = {'C': [], 'F': [], 'M': [], 'P': []}
    for s in sources:
        indexed[s[0]].append(s)

    print(CLI_HELP)

    cmd = input('\n>')
    while cmd != 'exit':
        try:
            results = []
            cmd_parts = [c.lower() for c in cmd.split(' ')]
            if cmd_parts == ['help']:
                print(CLI_HELP)
                cmd = input('>')
                continue
            max_show = 10
            index = 0
            while index < len(cmd_parts):
                cmd_part = cmd_parts[index]
                if cmd_part == 'c':  # list classes
                    clazz = cmd_parts[index + 1]
                    results = [i for i in indexed['C'] if clazz in i[2].lower()]
                elif cmd_part == 'nc':  # list notch classes
                    clazz = cmd_parts[index + 1]
                    results = [i for i in indexed['C'] if clazz in i[1].lower()]
                elif cmd_part == 'f':  # list fields
                    field = cmd_parts[index + 1]
                    results = [i for i in indexed['F'] if field in i[2].lower()]
                elif cmd_part == 'm':  # list methods
                    method = cmd_parts[index + 1]
                    results = [i for i in indexed['M'] if method in i[2].lower()]
                elif cmd_part == 'p':  # list params
                    param = cmd_parts[index + 1]
                    results = [i for i in indexed['P'] if param in i[4].lower()]
                elif cmd_part == 'fc':  # filter classes (on a field, method or class search)
                    clazz = cmd_parts[index + 1]
                    results = [r for r in results if clazz in r[1].lower()]
                elif cmd_part == 'fm':  # filter methods (on a param search)
                    method = cmd_parts[index + 1]
                    results = [r for r in results if method in r[2].lower()]
                elif cmd_part == '[':  # picks a single result
                    i = int(cmd_parts[index + 1])
                    results = [results[i]]
                elif cmd_part == 'gp':  # gets parameters for the first method name
                    method = results[0]
                    results = [p for p in indexed['P'] if method[1] == p[1] and method[2] == p[2]]
                    index -= 1
                elif cmd_part == 'gm':  # gets methods for each class
                    clazz = results[0]
                    results = [m for m in indexed['M'] if clazz[2] == m[1]]
                    index -= 1
                elif cmd_part == 'max':  # max results returned
                    max_show = int(cmd_parts[index + 1])

                index += 2

            for r in results[:max_show]:
                print(r)
            if len(results) > max_show:
                print('First %d results shown. Use max # for more' % max_show)
            if not results:
                print('No Results')
        except Exception as e:
            print('Error: ' + str(e))
        cmd = input('\n>')


if __name__ == '__main__':
    main()
