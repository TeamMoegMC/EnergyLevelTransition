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

package com.teammoeg.eltcore.util;

import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.teammoeg.eltcore.ELTCORE_Main.*;
import static com.teammoeg.eltcore.ELTCORE_Main.ELTRESOURCE;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class ST {




    /**
     * Methods with Block Item.
     */
    private static Item register(Block block, ItemGroup group) {
        return register(new BlockItem(block, (new Item.Settings()).group(group)));
    }

    private static Item register(Block block) {
        return register(new BlockItem(block, new Item.Settings()));
    }

    private static Item register(BlockItem item) {
        return register(item.getBlock(), item);
    }

    protected static Item register(Block block, Item item) {
        return register(Registry.BLOCK.getId(block), item);
    }

    /**
     * Methods with Item.
     */
    private static Item register(String namespace, String path, Item item) {
        return register(new Identifier(namespace, path), item);
    }

    private static Item register(Identifier id, Item item) {
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registry.ITEM, id, item);
    }

    /**
     * The true method registering an item with full properties.
     */
    // Everything is registered
    private static Item register(String path, String enName, String cnName, String enTooltip, String cnTooltip, int frametime, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);
        registerLang(path, "en_us", enName, enTooltip);
        registerLang(path, "zh_cn", cnName, cnTooltip);
        registerAnimation(path, frametime);

        return register(itemId, item);
    }

    private static Item register(String path, String enName, String cnName, int frametime, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);
        registerLang(path, "en_us", enName, "");
        registerLang(path, "zh_cn", cnName, "");
        registerAnimation(path, frametime);

        return register(itemId, item);
    }

    // No animation is registered
    private static Item register(String path, String enName, String cnName, String enTooltip, String cnTooltip, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);
        registerLang(path, "en_us", enName, enTooltip);
        registerLang(path, "zh_cn", cnName, cnTooltip);
        return register(itemId, item);
    }

    private static Item register(String path, String enName, String cnName, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);
        registerLang(path, "en_us", enName, "");
        registerLang(path, "zh_cn", cnName, "");
        return register(itemId, item);
    }

    // No Chinese localization is registered, not recommended to use this.
    private static Item registerNoCN(String path, String en, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);
        registerLang(path, "en_us", en, "");
        registerLang(path, "zh_cn", en, "");

        return register(itemId, item);
    }

    // No English nor Chinese localization is registered
    private static Item registerNoEN(String path, Item item) {

        Identifier itemId = new Identifier("eltcore", path);
        registerModel(path);

        return register(itemId, item);
    }

    // No model is registered
    private static Item register(String path, Item item) {

        Identifier itemId = new Identifier("eltcore", path);

        return register(itemId, item);
    }

    /**
     * The Run Time Resources registering methods
     */
    private static void registerModel(String path, String parent) {
        Identifier resourceId = new Identifier("eltcore", "item/" + path);
        ELTRESOURCE.addModel(JModel.model(parent).textures(JModel.textures().layer0("eltcore:item/" + path)), resourceId);
    }

    private static void registerModel(String path) {
        Identifier resourceId = new Identifier("eltcore", "item/" + path);
        ELTRESOURCE.addModel(JModel.model("item/generated").textures(JModel.textures().layer0("eltcore:item/" + path)), resourceId);
    }

    // With specified tooltip
    private static void registerLang(String path, String language, String name, String tooltip) {
        String unlocalizedPath;
        if (path.contains("/")) {
            unlocalizedPath = path.replace("/", ".");
            if (language.equalsIgnoreCase("en_us"))
                ELTRESOURCE.addLang(new Identifier("eltcore", language), EN_US.translate("item.eltcore." + unlocalizedPath, name).translate("item.eltcore." + unlocalizedPath + ".tooltip", tooltip));
            if (language.equalsIgnoreCase("zh_cn"))
                ELTRESOURCE.addLang(new Identifier("eltcore", language), ZH_CN.translate("item.eltcore." + unlocalizedPath, name).translate("item.eltcore." + unlocalizedPath + ".tooltip", tooltip));
        } else {
            if (language.equalsIgnoreCase("en_us"))
                ELTRESOURCE.addLang(new Identifier("eltcore", language), EN_US.translate("item.eltcore." + path, name).translate("item.eltcore." + path + ".tooltip", tooltip));
            if (language.equalsIgnoreCase("zh_cn"))
                ELTRESOURCE.addLang(new Identifier("eltcore", language), ZH_CN.translate("item.eltcore." + path, name).translate("item.eltcore." + path + ".tooltip", tooltip));
        }
    }

    private static void registerAnimation(String path, int frametime) {
        Identifier aniID = new Identifier("eltcore", "item/" + path);
        ELTRESOURCE.addAnimation(aniID, JAnimation.animation().frameTime(frametime));
    }
}
