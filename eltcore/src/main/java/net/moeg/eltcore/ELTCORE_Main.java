/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.eltcore;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.biome.Biome;
import net.moeg.eltcore.code.ArrayListNoNulls;
import net.moeg.eltcore.handlers.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static net.moeg.eltcore.data.CS.F;

public class ELTCORE_Main implements ModInitializer {

    public static final String MOD_ID = "eltcore";
    public static final String MOD_NAME = "ELT Core";
    public static final Logger LOGGER = LogManager.getFormatterLogger(MOD_NAME);
    public static JLang EN_US = JLang.lang();
    public static JLang ZH_CN = JLang.lang();
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create(MOD_ID + ":main");
    public static final Handler_ItemGroups ITEM_GROUPS_ELT = new Handler_ItemGroups();

    @Override
    public void onInitialize() {

        LOGGER.info("~~~~~~~~~~~~~ ELT Core Created by TeamMoeg ~~~~~~~~~~~~");
        LOGGER.info("~~~~~~~~~ https://github.com/MoegTech/ELTCore ~~~~~~~~~");

        ArrayListNoNulls<Runnable> tList = new ArrayListNoNulls<>(F,
                new Handler_WorldTypes()
        );
        for (Runnable tRunnable : tList) try {tRunnable.run();} catch(Throwable e) {e.printStackTrace();}

        Handler_Worldgen Handler_WORLDGEN = new Handler_Worldgen();
        Handler_Items Handler_ITEMS = new Handler_Items();
        Handler_Blocks Handler_BLOCKS = new Handler_Blocks();
        Handler_Components Handler_COMPONENTS = new Handler_Components();

        Handler_WORLDGEN.setBiomesRidge(new ArrayList<Biome>());
        Handler_COMPONENTS.registerComponents();
        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK));

        LOGGER.info("---Energy Level Transition Initialized!---");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}