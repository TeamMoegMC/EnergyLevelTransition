/*
 * Copyright (c) 2020. TeamMoeg
 *
 * This file is part of Energy Level Transition.
 *
 * Energy Level Transition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Energy Level Transition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.eltcore.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.List;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class ELTBiomeSource extends BiomeSource {
    public static final Codec<ELTBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(Codec.LONG.fieldOf("seed").stable().forGetter((ELTBiomeSource) -> {
            return ELTBiomeSource.seed;
        }), RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((ELTBiomeSource) -> {
            return ELTBiomeSource.biomeRegistry;
        })).apply(instance, instance.stable(ELTBiomeSource::new));
    });
    private final BiomeLayerSampler biomeSampler;
    private static final List<RegistryKey<Biome>> BIOMES;
    private final long seed;
    private final Registry<Biome> biomeRegistry;

    public ELTBiomeSource(long seed, Registry<Biome> biomeRegistry) {
        super(BIOMES.stream().map((registryKey) -> () -> {
            return (Biome)biomeRegistry.getOrThrow(registryKey);
        }));
        this.seed = seed;
        this.biomeRegistry = biomeRegistry;
        this.biomeSampler = BiomeLayers.build(seed, false, 4, 4); // 需要单独做吗？
    }

    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Environment(EnvType.CLIENT)
    public BiomeSource withSeed(long seed) {
        return new ELTBiomeSource(seed, this.biomeRegistry);
    }

    /**
     * Get the Biome for noise generation.
     * Normally we get random sample from a group of Biomes.
     * @param biomeX
     * @param biomeY
     * @param biomeZ
     * @return The Biome where noise generation takes place.
     */
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
//        return BuiltinBiomes.PLAINS;
        return this.biomeSampler.sample(this.biomeRegistry, biomeX, biomeZ);
    }

    static {
        BIOMES = ImmutableList.of(BiomeKeys.DESERT, BiomeKeys.SNOWY_TUNDRA);
    }
}
