package com.teammoeg.elt.world.biome.dreambiome;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.world.biome.ELTBiomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FairyTaleBiomeProvider extends BiomeProvider {
    public static void registerBiomeProvider() {
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(ELT.MOD_ID, "biome_source"), FairyTaleBiomeProvider.CODEC);
    }

    private final long seed;
    private final Registry<Biome> biomes;
    private final Biome fallasleep;
    public static final Codec<FairyTaleBiomeProvider> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter((provider1) -> {
                    return provider1.biomes;
                }),
                Codec.LONG.fieldOf("seed").forGetter((FairyTaleProvider) -> {
                    return FairyTaleProvider.seed;
                })).apply(builder, builder.stable(FairyTaleBiomeProvider::new));
    });

    public FairyTaleBiomeProvider(Registry<Biome> lookupRegistry, long seed) {
        this(lookupRegistry, seed, lookupRegistry.getOrThrow(ELTBiomes.getKey(ELTBiomes.fallasleepbiome.get())));
    }

    protected FairyTaleBiomeProvider(Registry<Biome> lookupRegistry, long seed, Biome fallasleep) {
        super(ImmutableList.of(fallasleep));
        this.biomes = lookupRegistry;
        this.seed = seed;
        this.fallasleep = fallasleep;
    }

    @Override
    protected Codec<? extends BiomeProvider> codec() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public BiomeProvider withSeed(long seed) {
        return new FairyTaleBiomeProvider(this.biomes, seed, this.fallasleep);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.fallasleep;
    }
}
