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

package com.teammoeg.eltcore.handlers;

import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.teammoeg.eltcore.ELT_Core.*;
import static com.teammoeg.eltcore.data.CS.F;

/**
 * 这是一个用来注册方块的类
 * 先定义一个方块的静态字段
 * 然后在静态域注册
 * <p>
 * 例子
 * public static final Block EXAMPLE_BLOCK;
 * <p>
 * static {
 * <p>
 * EXAMPLE_BLOCK = register("example_block", new ExampleBlock(FabricBlockSettings.of(Material.STONE)));*
 * }
 */

public class Handler_Blocks {

    public static final Block EXAMPLE_BLOCK;

    static {
         EXAMPLE_BLOCK = register("example_block", new Block(FabricBlockSettings.of(Material.STONE)));
    }

    // No Model is registered
    private static Block register(String path, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier("eltcore", path), block);
    }

    @Deprecated
    private static Block registerNoEN(boolean isCustomModel, boolean isCustomBlockState, String path, Block block) {
        registerModel(isCustomModel, path);
        registerBlockItemModel(path);
        registerBlockState(isCustomBlockState, path);
        return Registry.register(Registry.BLOCK, new Identifier("eltcore", path), block);
    }

    @Deprecated
    private static Block registerNoCN(boolean isCustomModel, boolean isCustomBlockState, String path, String en, Block block) {
        registerModel(isCustomModel, path);
        registerBlockItemModel(path);
        registerBlockState(isCustomBlockState, path);
        registerLang(path, "en_us", en);
        return Registry.register(Registry.BLOCK, new Identifier("eltcore", path), block);
    }

    // no custom model nor blockstate
    private static Block register(String path, String en, String cn, Block block) {
        return register(F, path, en, cn, block);
    }

    // no custom blockstate
    private static Block register(boolean isCustomModel, String path, String en, String cn, Block block) {
        return register(isCustomModel, F, path, en, cn, block);
    }

    /**
     * THe recommended method to register a block.
     */
    private static Block register(boolean isCustomModel, boolean isCustomBlockState, String path, String en, String cn, Block block) {
        registerModel(isCustomModel, path);
        registerBlockItemModel(path);
        registerBlockState(isCustomBlockState, path);
        registerLang(path, "en_us", en);
        registerLang(path, "zh_cn", cn);
        return Registry.register(Registry.BLOCK, new Identifier("eltcore", path), block);
    }

    /**
     * The Run Time Resources registering methods.
     */
    private static void registerBlockState(boolean isCustomBlockState, String path) {
        if (!isCustomBlockState) {
            Identifier resourceId = new Identifier("eltcore", path);
            ELTRESOURCE.addBlockState(JState.state(JState.variant(JState.model("eltcore:block/" + path))), resourceId);
        }
    }

    private static void registerModel(boolean isCustomModel, String path, String parent) {
        if (!isCustomModel) {
            Identifier resourceId = new Identifier("eltcore", "block/" + path);
            ELTRESOURCE.addModel(JModel.model(parent).textures(JModel.textures().var("all", "eltcore:block/" + path).particle("#all")), resourceId);
        }
    }

    private static void registerBlockItemModel(String path) {
        Identifier resourceId = new Identifier("eltcore", "item/" + path);
        ELTRESOURCE.addModel(JModel.model("eltcore:block/" + path), resourceId);
    }

    private static void registerModel(boolean isCustomModel, String path) {
        if (!isCustomModel) {
            Identifier resourceId = new Identifier("eltcore", "block/" + path);
            ELTRESOURCE.addModel(JModel.model("block/cube_all").textures(JModel.textures().var("all", "eltcore:block/" + path).particle("#all")), resourceId);
        }
    }

    private static void registerLang(String path, String language, String name) {
        if (language.equalsIgnoreCase("en_us"))
            ELTRESOURCE.addLang(new Identifier("eltcore", language), EN_US.translate("block.eltcore." + path, name));
        if (language.equalsIgnoreCase("zh_cn"))
            ELTRESOURCE.addLang(new Identifier("eltcore", language), ZH_CN.translate("block.eltcore." + path, name));
    }

    private static void registerAnimation(String path, int frametime) {
        Identifier aniID = new Identifier("eltcore", "block/" + path);
        ELTRESOURCE.addAnimation(aniID, JAnimation.animation().frameTime(frametime));
    }

}
