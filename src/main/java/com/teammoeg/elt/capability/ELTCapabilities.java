/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class ELTCapabilities {
    @CapabilityInject(ITeamCapability.class)
    public static Capability<ITeamCapability> teamCapability;
    @CapabilityInject(IFightCapability.class)
    public static Capability<IFightCapability> fightCapability;

    public static void init() {
        CapabilityManager.INSTANCE.register(ITeamCapability.class, new Capability.IStorage<ITeamCapability>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<ITeamCapability> capability, ITeamCapability instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<ITeamCapability> capability, ITeamCapability instance, Direction side, INBT nbt) {
                    }
                }, () -> null
        );
        CapabilityManager.INSTANCE.register(IFightCapability.class, new Capability.IStorage<IFightCapability>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IFightCapability> capability, IFightCapability instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IFightCapability> capability, IFightCapability instance, Direction side, INBT nbt) {

                    }
                }, () -> null
        );
    }

}
