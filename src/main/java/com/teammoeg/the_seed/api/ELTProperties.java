package com.teammoeg.the_seed.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;

import java.util.Collection;
import java.util.Optional;

public class ELTProperties {
    public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final PropertyBoolInverted MULTIBLOCKSLAVE = PropertyBoolInverted.create("multiblockslave");
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


