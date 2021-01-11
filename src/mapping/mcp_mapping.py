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

import csv
import os
import subprocess
import zipfile
from typing import Dict, Tuple, Mapping, Optional

from util import mapping_downloader
from util.sources import SourceMap


def read(mc_version: str, mcp_date: Optional[str] = None) -> Tuple[SourceMap, Dict[str, str], Dict[str, str]]:
    methods, fields, params = mapping_downloader.load_mcp_mappings(mc_version, mcp_date)

    methods, method_comments = parse_mcp(methods)
    fields, fields_comments = parse_mcp(fields)
    params = parse_mcp_params(params)

    return SourceMap(fields, methods, params), method_comments, fields_comments


def write(version: str, source_map: SourceMap, field_comments: Optional[Mapping] = None, method_comments: Optional[Mapping] = None):
    fields_comments = field_comments if field_comments is not None else dict()
    method_comments = method_comments if method_comments is not None else dict()

    fields_txt = write_csv_fields_or_methods(source_map.fields, fields_comments).dump()
    methods_txt = write_csv_fields_or_methods(source_map.methods, method_comments).dump()
    params_txt = write_csv_params(source_map.params).dump()

    file_path = os.path.join(mapping_downloader.get_cache_root(), 'mcp_snapshot-%s.zip' % version)
    with zipfile.ZipFile(file_path, 'w') as f:
        f.writestr('params.csv', params_txt)
        f.writestr('fields.csv', fields_txt)
        f.writestr('methods.csv', methods_txt)


def publish(version: str):
    file_path = os.path.join(mapping_downloader.get_cache_root(), 'mcp_snapshot-%s.zip' % version)
    if not os.path.isfile(file_path):
        raise ValueError('Must first build export before publishing to maven local')

    proc = subprocess.Popen('mvn install:install-file -Dfile=%s -DgroupId=de.oceanlabs.mcp -DartifactId=mcp_snapshot -Dversion=%s -Dpackaging=zip' % (file_path, version), shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    while proc.poll() is None:
        output = proc.stdout.readline().decode('utf-8').replace('\r', '').replace('\n', '')
        if output != '':
            print(output)
    mvn_ret_code = proc.wait()  # catch return code
    if mvn_ret_code != 0:
        raise ValueError('Maven install returned error code %s' % str(mvn_ret_code))


class BufferedWriter:
    """ This exists as an intermediary between csv.writer and zipfile.writestr """

    def __init__(self):
        self.buffer = []

    def write(self, text):
        self.buffer.append(text)

    def dump(self):
        return ''.join(self.buffer)


def parse_mcp(text: str) -> Tuple[Dict[str, str], Dict[str, str]]:
    mappings = {}
    comments = {}

    broken_rows = set()
    broken_mappings = set()

    for row in csv.reader(text.split('\n')[1:]):
        if row:
            if row[0] in mappings:
                broken_mappings.add(row[0])
            mappings[row[0]] = row[1]
            if row[3] != '':
                comments[row[0]] = row[3]
        else:
            broken_rows.add(tuple(row))

    return mappings, comments


def parse_mcp_params(text: str) -> Dict[str, str]:
    mappings = {}

    for row in csv.reader(text.split('\n')[1:]):
        if row:
            mappings[row[0]] = row[1]

    return mappings


def write_csv_fields_or_methods(data: Mapping, comments: Mapping) -> BufferedWriter:
    writer = BufferedWriter()
    csv_writer = csv.writer(writer, lineterminator='\n')
    csv_writer.writerow(['searge', 'name', 'side', 'desc'])
    for srg, named in sorted(data.items()):
        comment = comments[srg] if srg in comments else ''
        csv_writer.writerow([srg, named, '2', comment])  # side is ignored by FG
    return writer


def write_csv_params(params: Mapping) -> BufferedWriter:
    writer = BufferedWriter()
    csv_writer = csv.writer(writer, lineterminator='\n')
    csv_writer.writerow(['param', 'name', 'side'])
    for srg, named in sorted(params.items()):
        csv_writer.writerow([srg, named, '2'])  # side is ignored by FG
    return writer
