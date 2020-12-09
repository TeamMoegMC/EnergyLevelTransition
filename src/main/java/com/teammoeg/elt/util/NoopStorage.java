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

package com.teammoeg.elt.util;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

/**
 * A no-op implementation of {@link Capability.IStorage} for capabilities that require custom serialize / deserialization logic
 *
 * @param <T> The capability class
 */
public final class NoopStorage<T> implements Capability.IStorage<T>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<T> capability, T instance, Direction side)
    {
        throw new UnsupportedOperationException("This storage is non functional. Do not use it.");
    }

    @Override
    public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt)
    {
        throw new UnsupportedOperationException("This storage is non functional. Do not use it.");
    }
}