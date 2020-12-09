/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.elt.code;

import java.util.*;

/**
 * @author Gregorius Techneticies
 */
public class HashSetNoNulls<E> extends AbstractSet<E> {
    private transient HashMap<E, Object> map;
    private static final Object OBJECT = new Object();

    public HashSetNoNulls() {
        map = new HashMap<>();
    }

    public HashSetNoNulls(Collection<? extends E> c) {
        map = new HashMap<>(Math.max((int) (c.size()/.75f) + 1, 16));
        addAll(c);
    }

    @SafeVarargs
    public HashSetNoNulls(boolean aDummyParameter, E... aArray) {
        this(Arrays.asList(aArray));
    }

    public HashSetNoNulls(int initialCapacity, float loadFactor) {
        map = new HashMap<>(initialCapacity, loadFactor);
    }

    public HashSetNoNulls(int initialCapacity) {
        map = new HashMap<>(initialCapacity);
    }

    HashSetNoNulls(int initialCapacity, float loadFactor, boolean dummy) {
        map = new LinkedHashMap<>(initialCapacity, loadFactor);
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return e!=null&&map.put(e, OBJECT)==null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o)==OBJECT;
    }

    @Override
    public void clear() {
        map.clear();
    }
}

