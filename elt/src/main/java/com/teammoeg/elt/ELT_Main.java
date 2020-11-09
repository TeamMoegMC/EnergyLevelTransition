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

package com.teammoeg.elt;

import com.teammoeg.elt.blockentity.DemoBlockEntity;
import com.teammoeg.elt.blockentity.WoodCutterBlockEntity;
import com.teammoeg.elt.handlers.ELTBlocks;
import com.teammoeg.elt.handlers.ELTItems;
import com.teammoeg.elt.handlers.ELTScreenHandlerTypes;
import com.teammoeg.elt.loader.ItemLoader;
import com.teammoeg.elt.recipe.WoodCutterRecipe;
import com.teammoeg.eltcore.code.ArrayListNoNulls;
import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.teammoeg.eltcore.ELTCORE_Main.MOD_NAME;
import static com.teammoeg.eltcore.data.CS.F;

public class ELT_Main implements ModInitializer {

    public static final String MOD_ID = "elt";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("elt:main");
    public static final Logger LOGGER = LogManager.getFormatterLogger("Energy Level Transition");

    public static RecipeSerializer<WoodCutterRecipe> WOOD_CUTTER_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER, "elt:woodcutter", new WoodCutterRecipe.Serializer());
    public static JLang EN_US = JLang.lang();
    public static JLang ZH_CN = JLang.lang();
    public static BlockEntityType<DemoBlockEntity> DEMO_BLOCK_ENTITY;
    public static BlockEntityType<WoodCutterBlockEntity> BlockEntityWoodCutter;

    @Override
    public void onInitialize() {
        LOGGER.info("ELT is Open Source: https://github.com/MoegTech/EnergyLevelTransition");

        new ELTItems();
        new ELTBlocks();
        new ELTScreenHandlerTypes();

        ArrayListNoNulls<Runnable> tList = new ArrayListNoNulls<>(F,
                new ItemLoader()
        );
        for (Runnable tRunnable : tList) try {tRunnable.run();} catch(Throwable e) {e.printStackTrace();}

        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK)); // register arrp resourcepack

        DEMO_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "elt:demo", BlockEntityType.Builder.create(DemoBlockEntity::new, ELTBlocks.EXAMPLE_BLOCK).build(null));
        BlockEntityWoodCutter = Registry.register(Registry.BLOCK_ENTITY_TYPE, "elt:wood_cutter", BlockEntityType.Builder.create(WoodCutterBlockEntity::new, ELTBlocks.MANUAL_WOOD_CUTTER).build(null));

        LOGGER.info("ELT Nain initialized");
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}
