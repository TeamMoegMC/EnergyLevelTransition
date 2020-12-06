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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.List;
import java.util.stream.Collectors;

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
     * @return The Biome where noise generation takes place.
     */
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        double avgTemp = Temperature.getAvgTemp(biomeX, biomeY, biomeZ);
//        if (avgTemp < 10) System.out.println("Z " + biomeZ + " Y " + biomeY + " T " + avgTemp);

        if (avgTemp >= 0 && avgTemp < 8) {
            for (Biome biome : biomeRegistry.stream().collect(Collectors.toList())) {
                if (biome.getTemperature() >= 0 && biome.getTemperature() < 0.2) {
                    return biome;
                }
            }
        } else if (avgTemp >= 8 && avgTemp < 16) {
            for (Biome biome : biomeRegistry.stream().collect(Collectors.toList())) {
                if (biome.getTemperature() >= 0.2 && biome.getTemperature() < 0.5) {
                    return biome;
                }
            }
        } else if (avgTemp >= 16 && avgTemp < 24) {
            for (Biome biome : biomeRegistry.stream().collect(Collectors.toList())) {
                if (biome.getTemperature() >= 0.5 && biome.getTemperature() < 0.8) {
                    return biome;
                }
            }
        } else if (avgTemp >= 24 && avgTemp < 32) {
            for (Biome biome : biomeRegistry.stream().collect(Collectors.toList())) {
                if (biome.getTemperature() >= 0.8 && biome.getTemperature() < 2.0) {
                    return biome;
                }
            }
        } else {
            return biomeRegistry.getOrThrow(BiomeKeys.SAVANNA);
        }
        return biomeRegistry.getOrThrow(BiomeKeys.OCEAN);
    }

    private Biome getBiomeGenForCentralIsland(int biomeX, int biomeY, int biomeZ) {
        BlockPos oceanPos = new BlockPos(0, 0, 0);
        int oceanPosX = oceanPos.getX();
        int oceanPosZ = oceanPos.getZ();
        double distance = Math.sqrt((biomeX-oceanPosX)*(biomeX-oceanPosX) + (biomeZ-oceanPosZ)*(biomeZ-oceanPosZ));
        if (distance > 16) {
            return biomeRegistry.getOrThrow(BiomeKeys.OCEAN);
        }
        else {
            return biomeRegistry.getOrThrow(BiomeKeys.ICE_SPIKES);
        }
    }

    private Biome getBiomeForCentralRiver(int biomeX, int biomeY, int biomeZ) {
        int riverWidth = 16;
        if (Math.abs(biomeZ) < riverWidth) {
            return biomeRegistry.getOrThrow(BiomeKeys.OCEAN);
        }
        else {
            return biomeRegistry.getOrThrow(BiomeKeys.PLAINS);
        }
    }

    static {
        BIOMES = ImmutableList.of(BiomeKeys.DESERT, BiomeKeys.SNOWY_TUNDRA);
    }
}
