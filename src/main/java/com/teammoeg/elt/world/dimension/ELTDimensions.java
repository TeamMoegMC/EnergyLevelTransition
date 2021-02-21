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

package com.teammoeg.elt.world.dimension;

import com.mojang.serialization.Lifecycle;
import com.teammoeg.elt.ELT;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;

import java.util.OptionalLong;
import java.util.function.Function;
import java.util.function.Supplier;

public class ELTDimensions {
    public static final RegistryKey<Dimension> FAIRYTALEDIM = RegistryKey.create(Registry.LEVEL_STEM_REGISTRY, new ResourceLocation(ELT.MOD_ID, "fairytale"));
    public static final RegistryKey<World> FAIRYTALE = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(ELT.MOD_ID, "fairytale"));

    public static void init(SimpleRegistry<Dimension> simpleRegistry, MutableRegistry<DimensionType> mutableRegistry, MutableRegistry<Biome> biomeRegistry, MutableRegistry<DimensionSettings> dimSettingsRegistry, long seed) {
        Function<RegistryKey<DimensionSettings>, DimensionSettings> dreamSettings = (noiseSettings) -> FairyTaleDimension.createNoiseSettings(new DimensionStructuresSettings(false), false, Blocks.SNOW.defaultBlockState(), Blocks.WATER.defaultBlockState(), FairyTaleDimension.FAIRYTALE_NOISE_SETTINGS.location());
        Function<DimensionSettings, ChunkGenerator> dreamGenerator = (s) -> FairyTaleDimension.createFairyTaleChunkGenerator(biomeRegistry, dimSettingsRegistry, seed);
        Supplier<DimensionType> dreamDimensionType = () -> FairyTaleDimension.createDimSettings(OptionalLong.of(6000L), false, false, new ResourceLocation(ELT.MOD_ID, "fairytale"));

        Dimension dreamDim = new Dimension(dreamDimensionType, dreamGenerator.apply(dreamSettings.apply(FairyTaleDimension.FAIRYTALE_NOISE_SETTINGS)));

        simpleRegistry.register(FAIRYTALEDIM, dreamDim, Lifecycle.stable());

    }

}

