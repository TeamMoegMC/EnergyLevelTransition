package com.teammoeg.elt.world.biome;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;


public class ELTBiome {
    public Biome biome;

    public ELTBiome(Biome.RainType rainType, Biome.Category category, float depth, float scale, float temperature, float downfall,
                    BiomeAmbience ambience, MobSpawnInfo mobSpawnInfo, BiomeGenerationSettings generationSettings
    ) {
        biome = (new Biome.Builder())
                .precipitation(rainType)
                .biomeCategory(category)
                .depth(depth)
                .scale(scale)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(ambience)
                .mobSpawnSettings(mobSpawnInfo)
                .generationSettings(generationSettings)
                .build();
    }

    public static int calculateSkyColor(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public Biome getbiome() {
        return this.biome;
    }
}
