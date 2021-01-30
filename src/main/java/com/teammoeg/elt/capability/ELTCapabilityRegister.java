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

package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class ELTCapabilityRegister {
    public static void CapabilityRegister() {
        CapabilityManager.INSTANCE.register(IResearchProgress.class, new Capability.IStorage<IResearchProgress>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<IResearchProgress> capability, IResearchProgress instance, Direction side) {
                        return null;
                    }

                    @Override
                    public void readNBT(Capability<IResearchProgress> capability, IResearchProgress instance, Direction side, INBT nbt) {

                    }
                },
                () -> null
        );
    }
}
