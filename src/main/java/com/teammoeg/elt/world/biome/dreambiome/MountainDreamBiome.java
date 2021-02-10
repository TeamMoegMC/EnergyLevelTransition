package com.teammoeg.elt.world.biome.dreambiome;


import com.teammoeg.elt.world.biome.ELTBiome;
import com.teammoeg.elt.world.surfacebuild.ELTSurfaceBuilders;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;

public class MountainDreamBiome extends ELTBiome {
    public MountainDreamBiome() {
        super(Biome.RainType.RAIN, Biome.Category.EXTREME_HILLS, 1F, 0.55F, 0.2F, 0.4F,
                (new BiomeAmbience.Builder()).waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(calculateSkyColor(0.8F)).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build(),
                new MobSpawnInfo.Builder().addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4)).build(),
                (new BiomeGenerationSettings.Builder()).surfaceBuilder(ELTSurfaceBuilders.ELTGRASS).
                        addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER).build()
        );
    }
}
