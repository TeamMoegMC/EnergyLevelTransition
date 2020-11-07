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

import com.teammoeg.eltcore.handlers.*;
import com.teammoeg.eltcore.world.biome.ELTBiome;
import com.teammoeg.eltcore.world.type.ELTGeneratorType;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import com.teammoeg.eltcore.mixin.GeneratorTypeAccessor;
import com.teammoeg.eltcore.tag.ELTTag;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Map;

import static com.teammoeg.eltcore.tag.ELTTag.createTag;

public class ELTCORE_Main implements ModInitializer {

    public static final String MOD_ID = "eltcore";
    public static final String MOD_NAME = "ELT Core";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);
    public static JLang EN_US = JLang.lang();
    public static JLang ZH_CN = JLang.lang();
    public static final RuntimeResourcePack ELTRESOURCE = RuntimeResourcePack.create(MOD_ID + ":main");
    public static final Handler_ItemGroups ITEM_GROUPS_ELT = new Handler_ItemGroups();

    public static ELTTag PROTON = createTag(1, "protest", "Proton");

    @Override
    public void onInitialize() {

        LOGGER.info("~~~~~~~~~~~~~ ELT Core Created by TeamMoeg ~~~~~~~~~~~~");
        LOGGER.info("~~~~~~~~~ https://github.com/MoegTech/ELTCore ~~~~~~~~~");

        GeneratorTypeAccessor.getValues().add(ELTGeneratorType.ELT_NORMAL);
        ELTBiome ELTBiome = new ELTBiome();
        ELTBiome.setBiomesRidge(new ArrayList<Biome>());

        // Register all item and blocks
        new Handler_Items();
        new Handler_Blocks();

        // Register all the tags to RRP
        for (Map.Entry<String, ELTTag> entry : ELTTag.TAG_MAP.entrySet()) {
            ELTRESOURCE.addTag(new Identifier("c", "items/" + entry.getKey()), entry.getValue().getTag());
        }

        // Init RRP
        RRPCallback.EVENT.register(a -> a.add(ELTRESOURCE));

        LOGGER.info("---Energy Level Transition Initialized!---");
    }

    public void onModPreinit() {}

    public void aModInit() {}

    public void onModPostinit() {}

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}