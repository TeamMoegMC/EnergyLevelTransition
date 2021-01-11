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

# A collection of wrappers for mappings
# These encapsulate common mapping values, and allow applying bulk operations on collections, creating comparisons, and operations on multiple mapping sets such as appending or inversion.

from typing import Mapping, Optional, Set, Callable, Any

from util import utils


class SourceMap:
    """ This encapsulates a map form a SourceSet to another SourceSet

    All mappable fields (classes, fields, methods, and parameters) are stored as dictionaries
    This provides convenience methods for manipulating the overall mapping, as well as viewing the domain and codomain of this map

    Due to the nature of this class, it is always a surjective mapping from A -> B. It may or may not be injective, so the act of performing an inverse results in a InverseSourceMap, which can be transformed into a SourceMap if desired
    """

    def __init__(self, fields: Optional[Mapping] = None, methods: Optional[Mapping] = None, params: Optional[Mapping] = None, classes: Optional[Mapping] = None):
        self.fields = fields if fields is not None else dict()
        self.methods = methods if methods is not None else dict()
        self.params = params if params is not None else dict()
        self.classes = classes if classes is not None else dict()

    def keys(self) -> 'SourceSet':
        """ Returns the source set for the domain of this mapping """
        return SourceSet(set(self.fields.keys()), set(self.methods.keys()), set(self.params.keys()), set(self.classes.keys()))

    def values(self) -> 'SourceSet':
        """ Returns the SourceSet for the codomain of this mapping """
        return SourceSet(set(self.fields.values()), set(self.methods.values()), set(self.params.values()), set(self.classes.values()))

    def filter_keys(self, keys: 'SourceSet', inverse: bool = False) -> 'SourceMap':
        """ Returns a new SourceMap which restricts the domain to a subset of the provided SourceSet """
        return SourceMap(
            utils.filter_keys(self.fields, keys.fields, inverse),
            utils.filter_keys(self.methods, keys.methods, inverse),
            utils.filter_keys(self.params, keys.params, inverse),
            utils.filter_keys(self.classes, keys.classes, inverse))

    def filter_values(self, values: 'SourceSet', inverse: bool = False) -> 'SourceMap':
        """ Returns a new SourceMap which restricts the codomain to a subset of the provided SourceSet """
        return SourceMap(
            utils.filter_values(self.fields, values.fields, inverse),
            utils.filter_values(self.methods, values.methods, inverse),
            utils.filter_values(self.params, values.params, inverse),
            utils.filter_values(self.classes, values.classes, inverse))

    def filter(self, predicate: Callable[[Any, Any], bool]):
        return SourceMap(
            utils.filter_mapping(self.fields, predicate),
            utils.filter_mapping(self.methods, predicate),
            utils.filter_mapping(self.params, predicate),
            utils.filter_mapping(self.classes, predicate))

    def inverse(self) -> 'FuzzySourceMap':
        return FuzzySourceMap(
            utils.invert_mapping(self.fields),
            utils.invert_mapping(self.methods),
            utils.invert_mapping(self.params),
            utils.invert_mapping(self.classes))

    def compose(self, other: 'SourceMap') -> 'SourceMap':
        return SourceMap(
            utils.compose_mapping(self.fields, other.fields),
            utils.compose_mapping(self.methods, other.methods),
            utils.compose_mapping(self.params, other.params),
            utils.compose_mapping(self.classes, other.classes))

    def __str__(self):
        return 'SourceMap: Methods = %d, Fields = %d, Params = %d, Classes = %d' % (len(self.methods), len(self.fields), len(self.params), len(self.classes))


class FuzzySourceMap(SourceMap):
    """ This is a SourceMap which has been inverted but may not be invertible.

    For each mappable field, given a source map f : A -> B, this represents a map g : B -> P{A} where P{} is the power set of A
    """

    def __init__(self, fields: Optional[Mapping] = None, methods: Optional[Mapping] = None, params: Optional[Mapping] = None, classes: Optional[Mapping] = None):
        super().__init__(fields, methods, params, classes)

    def inverse(self) -> 'SourceMap':
        raise RuntimeError('Cannot invert a fuzzy mapping as it is not a functional mapping')

    def compose(self, other: 'SourceMap') -> 'FuzzySourceMap':
        return FuzzySourceMap(
            utils.compose_fuzzy_mapping(self.fields, other.fields),
            utils.compose_fuzzy_mapping(self.methods, other.methods),
            utils.compose_fuzzy_mapping(self.params, other.params),
            utils.compose_fuzzy_mapping(self.classes, other.classes))

    def select(self, selector: Optional[Callable[[Set], Any]] = None) -> 'SourceMap':
        """ Using the provided selection strategy, converts this to a functional mapping by only referencing individual values in the codomain. """
        def peek(s: Set) -> Any:
            for x in s:
                return x
        if selector is None:
            selector = peek
        return SourceMap(
            dict((k, selector(v)) for k, v in self.fields.items()),
            dict((k, selector(v)) for k, v in self.methods.items()),
            dict((k, selector(v)) for k, v in self.params.items()),
            dict((k, selector(v)) for k, v in self.classes.items()))

    def invertible(self) -> bool:
        return all((len(x) == 1 for x in self.fields.values())) and all(len(x) == 1 for x in self.methods.values()) and all(len(x) == 1 for x in self.params.values()) and all(len(x) == 1 for x in self.classes.values())


class SourceSet:

    def __init__(self, fields: Optional[Set] = None, methods: Optional[Set] = None, params: Optional[Set] = None, classes: Optional[Set] = None):
        self.fields = fields if fields is not None else set()
        self.methods = methods if methods is not None else set()
        self.params = params if params is not None else set()
        self.classes = classes if classes is not None else set()

    def compare_to(self, other: 'SourceSet') -> 'SourceSetComparison':
        return SourceSetComparison(self, other)

    def is_empty(self) -> bool:
        return not self.fields and not self.methods and not self.params and not self.classes

    def __str__(self):
        return 'SourceSet: Methods = %d, Fields = %d, Params = %d, Classes = %d' % (len(self.methods), len(self.fields), len(self.params), len(self.classes))


class SourceSetComparison:

    def __init__(self, left: 'SourceSet', right: 'SourceSet'):
        self.left: 'SourceSet' = left
        self.right: 'SourceSet' = right

        self.left_only: 'SourceSet' = SourceSet(left.fields - right.fields, left.methods - right.methods, left.params - right.params, left.classes - right.classes)
        self.right_only: 'SourceSet' = SourceSet(right.fields - left.fields, right.methods - left.methods, right.params - left.params, right.classes - left.classes)
        self.union: 'SourceSet' = SourceSet(left.fields | right.fields, left.methods | right.methods, left.params | right.params, left.classes | right.classes)
        self.intersect: 'SourceSet' = SourceSet(left.fields & right.fields, left.methods & right.methods, left.params & right.params, left.classes & right.classes)
