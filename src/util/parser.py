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

# A simple lexical scanner / parser, for simple text based parsing
# Supports many common requirements for all mapping formats

from typing import Optional, Set, Tuple, Dict, List


class Parser:
    IDENTIFIER_CHARS = set('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789/-_$;()[]<>.,')
    ALPHA_CHARS = set('ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz')
    NUMERIC_CHARS = set('0123456789')

    FIELD_DESCRIPTOR_NAMES = {
        'byte': 'B',
        'char': 'C',
        'double': 'D',
        'float': 'F',
        'int': 'I',
        'long': 'J',
        'short': 'S',
        'boolean': 'Z',
        'void': 'V'
    }
    FIELD_DESCRIPTOR_NAMES_INVERSE = dict((v, k) for k, v in FIELD_DESCRIPTOR_NAMES.items())

    def __init__(self, text: str):
        self.text = text
        self.pointer = 0

    def eof(self) -> bool:
        return self.pointer >= len(self.text)

    def next(self) -> str:
        return self.text[self.pointer]

    def scan_until(self, end: str) -> str:
        identifier = ''
        while not self.eof():
            if self.try_scan(end):
                identifier += end
                break
            identifier += self.next()
            self.pointer += 1
        return identifier

    def scan(self, expected: str):
        if not self.try_scan(expected):
            self.error('Unexpected EoF, expected %s' % repr(expected))

    def try_scan(self, expected: str) -> bool:
        size = len(expected)
        if self.pointer + size > len(self.text):
            return False
        elif self.text[self.pointer:self.pointer + size] == expected:
            self.pointer += size
            return True
        else:
            return False

    def scan_identifier(self, chars: Optional[Set[str]] = None) -> str:
        if chars is None:
            chars = Parser.IDENTIFIER_CHARS
        identifier = ''
        while not self.eof():
            c = self.text[self.pointer]
            if c in chars:
                identifier += c
                self.pointer += 1
            else:
                return identifier

    def scan_java_method_descriptor(self) -> Tuple[str, List[str], str]:
        self.scan('(')
        desc = '('
        params = []
        while self.next() != ')':
            param = self.scan_type()
            desc += param
            params.append(param)
        self.scan(')')
        desc += ')'
        ret_type = self.scan_type()
        desc += ret_type
        return ret_type, params, desc

    def scan_type(self) -> str:
        identifier = ''
        while self.next() == '[':
            self.scan('[')
            identifier += '['
        if self.next() == 'L':
            identifier += self.scan_until(';')
        else:
            identifier += self.next()
            self.pointer += 1
        return identifier

    def error(self, error_msg):
        print('Parser Error: %s' % error_msg)
        line_num = self.text[:self.pointer].count('\n')
        print('Around: %s' % repr(self.text[self.pointer - 3:self.pointer + 3]))
        print('Line %d:\n%s' % (line_num, repr(self.text.split('\n')[line_num])))
        raise RuntimeError('Stacktrace')

    @staticmethod
    def convert_type_to_descriptor(name: str, remap: Dict[str, str]):
        desc = ''
        # Array levels
        while len(name) > 2 and name[-2:] == '[]':
            name = name[:-2]
            desc += '['
        if name in Parser.FIELD_DESCRIPTOR_NAMES:
            # Primitive type
            desc += Parser.FIELD_DESCRIPTOR_NAMES[name]
        else:
            # Object
            if name in remap:
                name = remap[name]
            desc += 'L' + name + ';'
        return desc

    @staticmethod
    def convert_descriptor_to_type(desc: str, remap: Dict[str, str]) -> Tuple[str, int]:
        name = desc[:]
        arrays = 0
        while name.startswith('['):
            name = name[1:]
            arrays += 1
        if name in Parser.FIELD_DESCRIPTOR_NAMES_INVERSE:
            return Parser.FIELD_DESCRIPTOR_NAMES_INVERSE[name], arrays
        elif name.startswith('L') and name.endswith(';'):
            name = name[1:-1]
            if name in remap:
                name = remap[name]
            return name, arrays
        else:
            raise ValueError('The provided descriptor %s was not a valid type descriptor' % desc)

    @staticmethod
    def decode_java_method_descriptor(desc: str):
        parser = Parser(desc)
        parser.scan('(')
        params = []
        while parser.next() != ')':
            params.append(parser.scan_type())
        parser.scan(')')
        ret_type = parser.scan_type()
        return ret_type, params
