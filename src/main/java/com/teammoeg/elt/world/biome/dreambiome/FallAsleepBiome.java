package com.teammoeg.elt.world.biome.dreambiome;


import com.teammoeg.elt.world.biome.ELTBiome;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;

public class FallAsleepBiome extends ELTBiome {
    public FallAsleepBiome() {
        super(Biome.RainType.RAIN, Biome.Category.ICY, 0.125F, 0.05F, 0.6F, 0.4F,
                (new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.8F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build(),
                new MobSpawnInfo.Builder().build(),
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(ConfiguredSurfaceBuilders.GRASS).build()
        );
    }
}
