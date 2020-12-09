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

import com.teammoeg.eltcore.ELT_Core;
import com.teammoeg.eltcore.item.ELTGroups;
import com.teammoeg.eltcore.item.ItemBase;
import com.teammoeg.eltcore.item.ItemTooltip;
import com.teammoeg.eltcore.item.WorldGenDebugger;
import com.teammoeg.eltcore.tag.ELTTag;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.teammoeg.eltcore.ELT_Core.EN_US;
import static com.teammoeg.eltcore.ELT_Core.ZH_CN;
import static com.teammoeg.eltcore.ELT_Core.RU_RU;

/**
 * 这是一个用来注册物品和方块的物品形态的类
 * 先定义一个物品的静态字段
 * 然后在静态域注册
 * <p>
 * 例子
 * public static final Item ELT_SYMBOL;
 * <p>
 * static {
 * <p>
 * ELT_SYMBOL = register("symbol", new ItemTooltip((new Item.Settings()).group(ItemGroups_ELTCore.ELT_MISC)));
 * }
 * <p>
 * 如果要注册方块的物品形态
 * static {
 * <p>
 * EXAMPLE_BLOCK = register(Handler_Blocks.EXAMPLE_BLOCK, ItemGroups_ELTCore.ELT_MACHINE);
 * <p>
 * }
 */
public class Handler_Items {

    public static final Item ELT_SYMBOL = register("symbol", "Symbol", "", "能级跃迁", "", "Символ", "", new ItemTooltip((new Item.Settings()).group(ELTGroups.MISC)));
    public static final Item WORLD_GEN_DEBUGGER = register("world_gen_debugger", "Worldgen Debugger", "Clear 5x5 Chunks", "世界生成调试工具", "清理5x5的区块", "Отладчик генерации мира", "Очищает 5x5 чанки", new WorldGenDebugger(new Item.Settings().group(ELTGroups.TOOLS)));

    /** Register with Tag */
    private static Item create(String path, String enName, String enTooltip, String cnName, String cnTooltip, String ruName, String ruTooltip, Item item, ELTTag... aTags) {
        Item rItem = register(path, enName, enTooltip, cnName, cnTooltip, ruName, ruTooltip, item);
        if (aTags.length > 0) {
            for (ELTTag tTag : aTags) {
                tTag.mTag.values.remove("minecraft:air");
                tTag.mTag.add(Registry.ITEM.getId(rItem));
            }
        }
        return rItem;
    }

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
        return register(Registry.BLOCK.getId(block).getPath(), item);
    }

    /**
     * Methods with Item.
     */
    private static Item register(String path, Item item) {
        Identifier itemId = new Identifier("eltcore", path);
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registry.ITEM, itemId, item);
    }

    /**
     * The true method registering an item with full properties.
     */
    // Everything is registered
    private static Item register(String path, String enName, String enTooltip, String cnName, String cnTooltip, String ruName, String ruTooltip, int frametime, Item item) {

        registerModel(path);
        registerLang(path, EN_US, enName, enTooltip);
        registerLang(path, ZH_CN, cnName, cnTooltip);
        registerLang(path, RU_RU, ruName, ruTooltip);
        registerAnimation(path, frametime);

        return register(path, item);
    }

    private static Item register(String path, String en, String cn, String ru, int frametime, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");
        registerLang(path, ZH_CN, cn, "");
        registerLang(path, RU_RU, ru, "");
        registerAnimation(path, frametime);

        return register(path, item);
    }

    // No animation is registered
    private static Item register(String path, String enName, String enTooltip, String cnName, String cnTooltip, String ruName, String ruTooltip, Item item) {

        registerModel(path);
        registerLang(path, EN_US, enName, enTooltip);
        registerLang(path, ZH_CN, cnName, cnTooltip);
        registerLang(path, RU_RU, ruName, ruTooltip);
        return (ItemBase)register(path, item);
    }

    private static Item register(String path, String en, String cn, String ru, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");
        registerLang(path, ZH_CN, cn, "");
        registerLang(path, RU_RU, ru, "");
        return register(path, item);
    }

    // No Chinese localization is registered, not recommended to use this.
    private static Item registerNoCN(String path, String en, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");

        return register(path, item);
    }

    // No English nor Chinese localization is registered
    private static Item registerNoLang(String path, Item item) {

        registerModel(path);
        return register(path, item);
    }

    /**
     * The Run Time Resources registering methods
     */
    private static void registerModel(String path, String parent) {
        Identifier resourceId = new Identifier("eltcore", "item/" + path);
        ELT_Core.ELTRESOURCE.addModel(JModel.model(parent).textures(JModel.textures().layer0("eltcore:item/" + path)), resourceId);
    }

    private static void registerModel(String path) {
        Identifier resourceId = new Identifier("eltcore", "item/" + path);
        ELT_Core.ELTRESOURCE.addModel(JModel.model("item/generated").textures(JModel.textures().layer0("eltcore:item/" + path)), resourceId);
    }

    // With specified tooltip
    private static void registerLang(String path, JLang lang, String name, String tooltip) {
        if (path.contains("/"))
            path = path.replace("/", ".");
        ELT_Core.ELTRESOURCE.addLang(new Identifier("eltcore", lang.getName()), lang
                .translate("item.eltcore." + path, name)
                .translate("item.eltcore." + path + ".tooltip", tooltip));
    }

    private static void registerAnimation(String path, int frametime) {
        Identifier aniID = new Identifier("eltcore", "item/" + path);
        ELT_Core.ELTRESOURCE.addAnimation(aniID, JAnimation.animation().frameTime(frametime));
    }

}
