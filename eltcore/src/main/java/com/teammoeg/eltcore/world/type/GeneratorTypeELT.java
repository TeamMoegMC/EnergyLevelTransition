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

package com.teammoeg.eltcore.world.type;

import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;

/**
 * @author YueSha
 */
public final class GeneratorTypeELT extends GeneratorType {
    public static final GeneratorType INSTANCE = new GeneratorTypeELT();

    private GeneratorTypeELT() {
        super("elt_realistic");
    }

    public static void register() {
        GeneratorType.VALUES.add(INSTANCE);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomes, Registry<ChunkGeneratorSettings> chunkgens, long seed) {
//        return new NoiseChunkGenerator(new VanillaLayeredBiomeSource(seed, false, false, biomes), seed, new NoiseChunkGenerator());
        return null;
    }
}
