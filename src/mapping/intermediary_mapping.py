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
from typing import Tuple, Dict

from util import mapping_downloader
from util.sources import SourceMap


def read(mc_version: str):
    yarn_intermediary = mapping_downloader.load_yarn_intermediary(mc_version)
    classes, fields, methods = parse_intermediary(yarn_intermediary)

    return SourceMap(fields, methods, classes=classes)


def parse_intermediary(text: str) -> Tuple[Dict, Dict, Dict]:
    classes = {}
    fields = {}
    methods = {}

    for row in csv.reader(text.split('\n')[1:], delimiter='\t'):
        if not row:  # skip empty lines
            continue
        if row[0] == 'CLASS':
            # notch name -> intermediary name
            classes[row[1]] = row[2]
        elif row[0] == 'FIELD':
            # notch class, notch field -> intermediary field
            fields[(row[1], row[3])] = row[4]
        elif row[0] == 'METHOD':
            # notch class, notch method, params -> intermediary method
            methods[(row[1], row[3], row[2])] = row[4]
            # todo: proper param indexes (need to know static / non-static method and long/double types)

    return classes, fields, methods
