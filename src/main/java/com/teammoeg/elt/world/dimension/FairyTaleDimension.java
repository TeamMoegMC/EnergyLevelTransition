package com.teammoeg.elt.world.dimension;


import com.teammoeg.elt.ELT;
import com.teammoeg.elt.world.biome.dreambiome.FairyTaleBiomeProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.gen.settings.ScalingSettings;
import net.minecraft.world.gen.settings.SlideSettings;

import java.lang.reflect.Constructor;
import java.util.OptionalLong;

public class FairyTaleDimension {
    public static final RegistryKey<DimensionSettings> FAIRYTALE_NOISE_SETTINGS = RegistryKey.create(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY, new ResourceLocation(ELT.MOD_ID, "fairytale"));


    public static void initNoiseSettings() {
        registerNoiseSettings(FAIRYTALE_NOISE_SETTINGS, createNoiseSettings(new DimensionStructuresSettings(false), false, Blocks.SAND.defaultBlockState(), Blocks.WATER.defaultBlockState(), FAIRYTALE_NOISE_SETTINGS.location()));
    }

    public static DimensionSettings createNoiseSettings(DimensionStructuresSettings structureSettingsIn, boolean flag1, BlockState fillerBlockIn, BlockState fluidBlockIn, ResourceLocation settingsLocationIn) {
        try {
            Constructor<DimensionSettings> constructor = DimensionSettings.class.getDeclaredConstructor(DimensionStructuresSettings.class, NoiseSettings.class, BlockState.class, BlockState.class, int.class, int.class, int.class, boolean.class);
            constructor.setAccessible(true);
            return constructor.newInstance(structureSettingsIn, new NoiseSettings(256, new ScalingSettings(0.9999999814507745D, 0.9999999814507745D, 80.0D, 160.0D), new SlideSettings(-10, 3, 0), new SlideSettings(-30, 0, 0), 1, 2, 1.0D, -0.46875D, true, true, false, flag1), fillerBlockIn, fluidBlockIn, -10, 0, 63, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ChunkGenerator createFairyTaleChunkGenerator(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimSettingsRegistry, long seed) {
        return new NoiseChunkGenerator(new FairyTaleBiomeProvider(biomeRegistry, seed), seed, () ->
        {
            return dimSettingsRegistry.getOrThrow(FAIRYTALE_NOISE_SETTINGS);
        });
    }

    public static DimensionSettings registerNoiseSettings(RegistryKey<DimensionSettings> settingsKeyIn, DimensionSettings dimSettingsIn) {
        WorldGenRegistries.register(WorldGenRegistries.NOISE_GENERATOR_SETTINGS, settingsKeyIn.location(), dimSettingsIn);
        return dimSettingsIn;
    }

    public static DimensionType createDimSettings(OptionalLong time, boolean ultrawarm, boolean piglinSafe, ResourceLocation effectsId) {
        return new DimensionType(time, true, false, ultrawarm, true, 1, false, piglinSafe, true, false, false, 256, FuzzedBiomeMagnifier.INSTANCE, BlockTags.INFINIBURN_OVERWORLD.getName(), effectsId, 0.0F) {
        };
    }
}
