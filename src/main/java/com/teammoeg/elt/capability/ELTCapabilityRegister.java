package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class ELTCapabilityRegister {
    public static void CapabilityRegister(){
        CapabilityManager.INSTANCE.register(IResearchProgress.class,new Capability.IStorage<IResearchProgress>() {
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
