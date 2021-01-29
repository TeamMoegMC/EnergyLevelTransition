package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ResearchProgressProvider implements ICapabilityProvider {
    private IResearchProgress ResearchProgres;
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap== ELTCapabilities.RESEARCHPROGRESS){
            return LazyOptional.of(this::getDate).cast();
        }
        return LazyOptional.empty();
    }
    @Nonnull
    public IResearchProgress getDate(){
        if (ResearchProgres==null){
          this.ResearchProgres = new ResearchProgress(0);
        };
        return this.ResearchProgres;
    }
}
