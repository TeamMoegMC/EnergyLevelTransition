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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.world.gen.GeneratorOptions;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.teammoeg.eltcore.ELTCORE_Main.log;

/**
 * @author: Linguardium
 * Under MIT License
 */
public class WorldTypeRegistry {
    public static HashMap<String, BiFunction<Long, Properties, GeneratorOptions>> levelTypes = new HashMap<>();

    @Environment(EnvType.CLIENT)
    public static <T extends CustomGeneratorType> void registerWorldType(String translationKey, Function<String, T> generatorSupplier) {
        if (GeneratorType.VALUES.stream().noneMatch(value -> value.getTranslationKey().toString().equalsIgnoreCase(translationKey))) {
            GeneratorType.VALUES.add(generatorSupplier.apply(translationKey));
            log(Level.INFO, "Registered World Type: " + translationKey);
        } else {
            log(Level.ERROR, "Attempted to register a duplicate World Type: " + translationKey);

        }
    }

    public static void registerLevelType(String translationKey, BiFunction<Long, Properties, GeneratorOptions> optionSupplier) {
        if (!levelTypes.containsKey(translationKey)) {
            levelTypes.put(translationKey, optionSupplier);
        } else {
            log(Level.WARN, "Attempted to register a duplicate level type: " + translationKey);
        }
    }

}
