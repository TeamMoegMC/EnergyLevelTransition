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

from typing import Mapping, MutableMapping, Any, Set, Callable, Tuple

JAVA_KEYWORDS = {'abstract', 'assert', 'boolean', 'break', 'byte', 'case', 'catch', 'char', 'class', 'const', 'continue', 'default', 'do', 'double', 'else', 'enum', 'extends', 'final', 'finally', 'float', 'for', 'goto', 'if', 'implements', 'import', 'instanceof', 'int', 'interface', 'long', 'native', 'new', 'package', 'private', 'protected', 'public', 'return', 'short', 'static', 'strictfp', 'super', 'switch', 'synchronized', 'this', 'throw', 'throws', 'transient', 'try', 'void', 'volatile', 'while'}


def append_mapping(a: MutableMapping, b: Mapping):
    for k, v in b.items():
        if k not in a:
            a[k] = v


def invert_mapping(d: Mapping) -> Mapping[Any, Set]:
    """ Given a mapping a : A -> B, not necessarily injective, returns the map a' : B -> P{A} such that ∀ x ∈ A, x ∈ (a' ∘ a)(x) """
    f = dict()
    for k, v in d.items():
        if v not in f:
            f[v] = {k}
        else:
            f[v].add(k)
    return f


def invert_injective_mapping(d: Mapping) -> Mapping:
    """ Given an injective mapping a : A -> B, returns the map a' : B -> A """
    f = dict()
    for k, v in d.items():
        if v not in f:
            f[v] = k
        else:
            raise ValueError('Tried to injective invert a non-injective mapping')
    return f


def compose_mapping(a: Mapping, b: Mapping) -> Mapping:
    """ Given a : A -> B, and b : B -> C, returns the composition (a ∘ b) : A -> C """
    c = dict()
    for k, v in a.items():
        c[k] = b[v]
    return c


def compose_fuzzy_mapping(a: Mapping[Any, Set], b: Mapping) -> Mapping[Any, Set]:
    """ Given a : A -> P{B} and b : B -> C, returns the composition c : A -> P{C} given by ∀ x ∈ A, c(x) = {b(y) : y ∈ a(x)} """
    c = dict()
    for k, vs in a.items():
        if k not in c:
            ls = set()
            c[k] = ls
        else:
            ls = c[k]
        for v in vs:
            ls.add(b[v])
    return c


def filter_keys(m: Mapping, keys: Set, inverse: bool = False) -> Mapping:
    """ Given m : A -> B, returns the restriction a | keys, or a | keys' if inverse """
    return dict((k, v) for k, v in m.items() if (k in keys) != inverse)


def filter_values(m: Mapping, values: Set, inverse: bool = False) -> Mapping:
    """ Given m : A -> B, returns the restriction on the codomain a | {x ∈ A : m(a) ∉ values }, ' if inverse """
    return dict((k, v) for k, v in m.items() if (v in values) != inverse)


def filter_mapping(m: Mapping, predicate: Callable[[Any, Any], bool]) -> Mapping:
    """ Given m : A -> B, and predicate : A x B -> bool, returns the restriction m' : {x : ∀ x ∈ A, predicate(a, m(a)) } -> B """
    return dict((k, v) for k, v in m.items() if predicate(k, v))


def split_set(s: Set, predicate: Callable[[Any], bool]) -> Tuple[Set, Set]:
    """ Given a set S and predicate P, let T = { s ∈ S : P(s) }. Returns T, T' """
    p = set()
    not_p = set()
    for i in s:
        if predicate(i):
            p.add(i)
        else:
            not_p.add(i)
    return p, not_p
