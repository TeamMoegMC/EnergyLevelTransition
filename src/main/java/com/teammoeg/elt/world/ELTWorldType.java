package com.teammoeg.elt.world;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraftforge.common.world.ForgeWorldType;


public class ELTWorldType extends ForgeWorldType {
    public ELTWorldType(IChunkGeneratorFactory factory) {
        super(factory);
    }

    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed, String generatorSettings) {
        return super.createChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed, generatorSettings);
    }
}
