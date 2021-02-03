package com.teammoeg.elt.world.dimension;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class ELTDimensions {
    public static final RegistryKey<DimensionType> FAIRYTALETYPE = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation("fairytaletype"));
    public static final RegistryKey<World> FAIRYTALE = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation("fairytale"));

}

