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

package com.teammoeg.eltcore;

import com.teammoeg.eltcore.handlers.Handler_Blocks;
import com.teammoeg.eltcore.handlers.Handler_Items;
import com.teammoeg.eltcore.mixin.GeneratorTypeAccessor;
import com.teammoeg.eltcore.tag.ELTTag;
import com.teammoeg.eltcore.world.ELTBiomeSource;
import com.teammoeg.eltcore.world.type.ELTGeneratorType;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class ELT_Core implements ModInitializer {

    public static final String MOD_ID = "eltcore";
    public static final String MOD_NAME = "ELT Core";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);
    public static JLang EN_US = JLang.lang("en_us");
    public static JLang ZH_CN = JLang.lang("zh_cn");
    public static JLang RU_RU = JLang.lang("ru_ru");
    public static final RuntimeResourcePack ELTRESOURCE = RuntimeResourcePack.create(MOD_ID + ":main");

    @Override
    public void onInitialize() {
        log("ELT is Open Source: https://github.com/MoegTech/EnergyLevelTransition");


        Registry.register(Registry.BIOME_SOURCE, "elt", ELTBiomeSource.CODEC);
        GeneratorTypeAccessor.getValues().set(0, ELTGeneratorType.ELT_NORMAL);
        GeneratorTypeAccessor.getValues().add(GeneratorType.DEFAULT);

        // Register all item and blocks
        new Handler_Items();
        new Handler_Blocks();

        // Register all the tags to RRP
        for (Map.Entry<String, ELTTag> entry : ELTTag.TAG_MAP.entrySet()) {
            ELTRESOURCE.addTag(new Identifier("c", "items/" + entry.getKey()), entry.getValue().getTag());
        }

        // Init RRP
        RRPCallback.EVENT.register(a -> a.add(ELTRESOURCE));

        log("ELT Core initialized");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }
}
