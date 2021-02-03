package com.teammoeg.elt.world.biome;

import com.teammoeg.elt.ELT;
import com.teammoeg.elt.world.biome.dreambiome.FallAsleepBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ELTBiomes {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, ELT.MOD_ID);
    public static RegistryObject<Biome> fallasleepbiome = BIOMES.register("fallasleep_biome", () -> new FallAsleepBiome().getbiome());

    public static RegistryKey<Biome> getKey(Biome biome) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(biome)));
    }
}
