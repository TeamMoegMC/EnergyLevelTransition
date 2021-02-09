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

package com.teammoeg.elt.world.biome;

import com.teammoeg.elt.ELT;
import com.teammoeg.elt.world.biome.dreambiome.DeepDreamBiome;
import com.teammoeg.elt.world.biome.dreambiome.FallAsleepBiome;
import com.teammoeg.elt.world.biome.dreambiome.MountainDreamBiome;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class ELTBiomes {
    public static final DeferredRegister<Biome> REGISTER = DeferredRegister.create(ForgeRegistries.BIOMES, ELT.MOD_ID);
    public static RegistryObject<Biome> fallasleepbiome = REGISTER.register("fallasleep_biome", () -> new FallAsleepBiome().getbiome());
    public static RegistryObject<Biome> deepdreambiome = REGISTER.register("deepdream_biome", () -> new DeepDreamBiome().getbiome());
    public static RegistryObject<Biome> mountaindreambiome = REGISTER.register("mountaindream_biome", () -> new MountainDreamBiome().getbiome());

    public static RegistryKey<Biome> getKey(Biome biome) {
        return RegistryKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(biome)));
    }
}
