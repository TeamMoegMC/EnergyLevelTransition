/*
 *  Copyright (c) 2021. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.elt.world.biome.dreambiome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teammoeg.elt.world.biome.ELTBiomes;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FairyTaleBiomeProvider extends BiomeProvider {
    public static final Codec<FairyTaleBiomeProvider> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((provider1) -> {
                    return provider1.biomes;
                }),
                Codec.LONG.fieldOf("seed").forGetter((FairyTaleProvider) -> {
                    return FairyTaleProvider.seed;
                })).apply(builder, builder.stable(FairyTaleBiomeProvider::new));
    });
    private final long seed;
    private final Registry<Biome> biomes;
    private final Biome fallasleep;
    private final Biome deepdream;
    private final Biome mountaindream;
    private final SimplexNoiseGenerator noise;

    public FairyTaleBiomeProvider(Registry<Biome> lookupRegistry, long seed) {
        this(lookupRegistry, seed, lookupRegistry.getOrThrow(ELTBiomes.getKey(ELTBiomes.fallasleepbiome.get())), lookupRegistry.getOrThrow(ELTBiomes.getKey(ELTBiomes.deepdreambiome.get())),
                lookupRegistry.getOrThrow(ELTBiomes.getKey(ELTBiomes.mountaindreambiome.get())));
    }

    protected FairyTaleBiomeProvider(Registry<Biome> lookupRegistry, long seed, Biome fallasleep, Biome deep, Biome mountaindream) {
        super(ImmutableList.of(fallasleep, deep, mountaindream));
        this.biomes = lookupRegistry;
        this.seed = seed;
        this.fallasleep = fallasleep;
        this.deepdream = deep;
        this.mountaindream = mountaindream;
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
        this.noise = new SimplexNoiseGenerator(sharedseedrandom);
    }

    @Override
    protected Codec<? extends BiomeProvider> codec() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BiomeProvider withSeed(long seed) {
        return new FairyTaleBiomeProvider(this.biomes, seed, this.fallasleep, this.deepdream, this.mountaindream);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        int a = x >> 2;
        int b = z >> 2;
        float i = getHeightValue(this.noise, a, b);
        if (i > 50) {
            return this.mountaindream;
        } else if (i > -20) {
            return this.deepdream;
        } else return this.fallasleep;
    }

    public static float getHeightValue(SimplexNoiseGenerator noiseGenerator, int x, int z) {
        int i = x / 2;
        int j = z / 2;
        int k = x % 2;
        int l = z % 2;
        float f = 100.0F - MathHelper.sqrt((float) (x * x + z * z)) * 8.0F;
        f = MathHelper.clamp(f, -20.0F, 80.0F);

        for (int i1 = -12; i1 <= 12; ++i1) {
            for (int j1 = -12; j1 <= 12; ++j1) {
                long k1 = (long) (i + i1);
                long l1 = (long) (j + j1);
                if (noiseGenerator.getValue((double) k1, (double) l1) < (double) -0.9F) {
                    float f1 = (MathHelper.abs((float) k1) * 3439.0F + MathHelper.abs((float) l1) * 147.0F) % 13.0F + 9.0F;
                    float f2 = (float) (k - i1 * 2);
                    float f3 = (float) (l - j1 * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
                    f4 = MathHelper.clamp(f4, -20.0F, 80.0F);
                    f = Math.max(f, f4);
                }
            }
        }

        return f;
    }
}
