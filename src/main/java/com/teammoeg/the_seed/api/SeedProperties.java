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

package com.teammoeg.the_seed.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;

import java.util.Collection;
import java.util.Optional;

public class SeedProperties {
    public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final PropertyBoolInverted NOT_MULTI_MAIN = PropertyBoolInverted.create("not_multi_main"); // not_multi_main: 非主方块 用于表示之前的multiblockslave
    public static final PropertyBoolInverted HASBOOK = PropertyBoolInverted.create("hasbook");

    public static class PropertyBoolInverted extends Property<Boolean> {
        private static final ImmutableList<Boolean> ALLOWED_VALUES = ImmutableList.of(false, true);

        protected PropertyBoolInverted(String name) {
            super(name, Boolean.class);
        }

        public static PropertyBoolInverted create(String name) {
            return new PropertyBoolInverted(name);
        }

        @Override
        public Collection<Boolean> getPossibleValues() {
            return ALLOWED_VALUES;
        }

        @Override
        public String getName(Boolean value) {
            return value.toString();
        }

        @Override
        public Optional<Boolean> getValue(String value) {
            return Optional.of(Boolean.parseBoolean(value));
        }
    }
}


