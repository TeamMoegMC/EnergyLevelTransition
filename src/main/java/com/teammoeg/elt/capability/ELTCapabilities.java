package com.teammoeg.elt.capability;

import com.teammoeg.the_seed.api.IResearchProgress;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class ELTCapabilities {
    @CapabilityInject(IResearchProgress.class)
    public static Capability<IResearchProgress> RESEARCHPROGRESS;

}
