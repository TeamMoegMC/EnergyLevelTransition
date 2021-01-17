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

package com.teammoeg.cuckoolib.util.data;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class UnorderedRegistry<K, T> {
	private BiMap<K, T> registry;
	private BiMap<T, K> inversedRegistry;
	private boolean frozen;

	public UnorderedRegistry() {
		this.registry = HashBiMap.create();
		this.inversedRegistry = this.registry.inverse();
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void freeze() {
		if (this.frozen) {
			throw new IllegalStateException("This registry is already forzen");
		}
		this.frozen = true;
	}

	public boolean containsKey(K key) {
		return this.registry.containsKey(key);
	}

	public boolean containsValue(T value) {
		return this.inversedRegistry.containsKey(value);
	}

	public T getValue(K key) {
		return this.registry.get(key);
	}

	public K getKeyForValue(T value) {
		return this.inversedRegistry.get(value);
	}

	public T get(K key) {
		return this.registry.get(key);
	}

	public Set<K> keySet() {
		return this.registry.keySet();
	}

	public Set<Map.Entry<K, T>> entrySet() {
		return this.registry.entrySet();
	}

	public Collection<T> values() {
		return this.registry.values();
	}

	public void register(K key, T value) {
		if (this.frozen) {
			throw new IllegalStateException("This registry is already forzen");
		}
		if (this.registry.containsKey(key)) {
			throw new IllegalStateException("This key has been registered");
		}
		this.registry.put(key, value);
	}

	public void delete(K key) {
		if (this.registry.containsKey(key)) {
			this.registry.remove(key);
		}
	}
}