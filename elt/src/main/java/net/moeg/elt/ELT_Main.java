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

package net.moeg.elt;

import net.devtech.arrp.api.RRPCallback;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.lang.JLang;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.registry.Registry;
import net.moeg.elt.blockentity.DemoBlockEntity;
import net.moeg.elt.blockentity.WoodCutterBlockEntity;
import net.moeg.elt.handlers.BlocksELT;
import net.moeg.elt.handlers.Handler_Items;
import net.moeg.elt.handlers.ScreenHandlerTypeELT;
import net.moeg.elt.recipe.WoodCutterRecipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.devtech.arrp.api.RuntimeResourcePack.id;
import static net.devtech.arrp.json.loot.JLootTable.*;
import static net.moeg.elt.handlers.BlocksELT.EXAMPLE_BLOCK;
import static net.moeg.elt.handlers.BlocksELT.MANUAL_WOOD_CUTTER;

public class ELT_Main implements ModInitializer {

    public static final String MOD_ID = "elt";
    public static final RuntimeResourcePack RESOURCE_PACK = RuntimeResourcePack.create("elt:main");
    public static final Logger LOGGER = LogManager.getFormatterLogger("Energy Level Transition");
    /**
     * Load the ItemGroups
     */
    public static RecipeSerializer<WoodCutterRecipe> WOOD_CUTTER_RECIPE = Registry.register(Registry.RECIPE_SERIALIZER, "elt:woodcutter", new WoodCutterRecipe.Serializer());
    public static JLang EN_US = JLang.lang();
    public static JLang ZH_CN = JLang.lang();
    public static BlockEntityType<DemoBlockEntity> DEMO_BLOCK_ENTITY;
    public static BlockEntityType<WoodCutterBlockEntity> BlockEntityWoodCutter;

    @Override
    public void onInitialize() {

        LOGGER.info("~~~~~ Thanks for playing Energy Level Transition! ~~~~~");
        LOGGER.info("~~~~~~~~~~~~~~~~~ Created by TeamMoeg ~~~~~~~~~~~~~~~~~");
        LOGGER.info("~~ https://github.com/MoegTech/EnergyLevelTransition ~~");


        Handler_Items Handler_ITEMS = new Handler_Items();
        BlocksELT Handler_BLOCKS = new BlocksELT();
        ScreenHandlerTypeELT SHTELT = new ScreenHandlerTypeELT();

        RESOURCE_PACK.addLootTable(id("minecraft:blocks/acacia_fence"),
                loot("minecraft:block")
                        .pool(pool()
                                .rolls(1)
                                .entry(entry()
                                        .type("minecraft:item")
                                        .name("minecraft:diamond"))
                                .condition(condition("minecraft:survives_explosion")))
        );

        RRPCallback.EVENT.register(a -> a.add(RESOURCE_PACK)); // register arrp resourcepack

        DEMO_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "elt:demo", BlockEntityType.Builder.create(DemoBlockEntity::new, EXAMPLE_BLOCK).build(null));
        BlockEntityWoodCutter = Registry.register(Registry.BLOCK_ENTITY_TYPE, "elt:wood_cutter", BlockEntityType.Builder.create(WoodCutterBlockEntity::new, MANUAL_WOOD_CUTTER).build(null));
        LOGGER.info("---Energy Level Transition Initialized!---");
    }
}
