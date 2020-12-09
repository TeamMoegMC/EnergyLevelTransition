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

package com.teammoeg.elt.handlers;

import com.teammoeg.elt.items.tools.*;
import com.teammoeg.eltcore.item.ELTGroups;
import com.teammoeg.eltcore.item.ItemBase;
import com.teammoeg.eltcore.item.ItemTooltip;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.teammoeg.elt.ELT_Main.*;

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
 * ELT_SYMBOL = register("symbol", new ItemTooltip((new Item.Settings()).group(ItemGroups_ELT.ELT_MISC)));
 * }
 * <p>
 * 如果要注册方块的物品形态
 * static {
 * <p>
 * EXAMPLE_BLOCK = register(Handler_Blocks.EXAMPLE_BLOCK, ItemGroups_ELT.ELT_MACHINE);
 * <p>
 * }
 */
public class ELTItems {

    public static final Item
            ELT_SYMBOL,
            OAK_BRANCH, BRICH_BRANCH,
            Flint_Adze, Flint_Awl, Flint_Axe, Flint_Knife, Flint_Shovel, Flint_Spear, Flint_Scraper, Flint_Harpoon, Flint_Hammer,
            Head_Flint_Shovel, Head_Flint_Axe, Head_Flint_Knife, Head_Flint_Spear, Head_Flint_Harpoon, Head_Flint_Hammer,

    //Blockitems
    EXAMPLE_BLOCK, MANUAL_WOOD_CUTTER, TEST;

    static {
        ELT_SYMBOL = register("symbol", "Symbol", "能级跃迁", "", "", new ItemTooltip((new Item.Settings()).group(ELTGroups.MISC)));
        OAK_BRANCH = register("oak_branch", "Oak Branch", "橡木树枝", "A branch", "一根树枝", new ItemTooltip((new Item.Settings()).group(ELTGroups.MATERIAL)));
        BRICH_BRANCH = register("birch_branch", "Birch Branch", "白桦树枝", "A branch", "一根树枝", new ItemTooltip((new Item.Settings()).group(ELTGroups.MATERIAL)));

        Head_Flint_Shovel = register("tool/flint_shovel_head", "Flint Shovel Head", "燧石铲头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));
        Head_Flint_Axe = register("tool/flint_axe_head", "Flint Axe Head", "燧石斧头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));
        Head_Flint_Knife = register("tool/flint_knife_head", "Flint Knife Head", "燧石匕首头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));
        Head_Flint_Spear = register("tool/flint_spear_head", "Flint Spear Head", "燧石矛头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));
        Head_Flint_Harpoon = register("tool/flint_harpoon_head", "Flint Harpoon Head", "燧石鱼叉头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));
        Head_Flint_Hammer = register("tool/flint_hammer_head", "Flint Hammer Head", "燧石锤头", new ItemBase((new Item.Settings()).group(ELTGroups.MATERIAL)));

        //tools
        Flint_Adze = register("tool/flint_adze", "Flint Adze", "燧石锛", new ToolAdze(ToolMaterials_ELT.CHIPPED_FLINT, 2, -3.2F, (new Item.Settings())));
        Flint_Shovel = register("tool/flint_shovel", "Flint Shovel", "燧石铲", new ToolShovel(ToolMaterials_ELT.CHIPPED_FLINT, 1, -3.2F, (new Item.Settings())));
        Flint_Axe = register("tool/flint_axe", "Flint Axe", "燧石斧", new ToolAxe(ToolMaterials_ELT.CHIPPED_FLINT, 2, -3.2F, (new Item.Settings())));
        Flint_Knife = register("tool/flint_knife", "Flint Knife", "燧石匕首", new ToolKnife(ToolMaterials_ELT.CHIPPED_FLINT, 2, -3.2F, (new Item.Settings())));
        Flint_Spear = register("tool/flint_spear", "Flint Spear", "燧石矛", new ToolSpear(ToolMaterials_ELT.CHIPPED_FLINT, 2, -3.2F, (new Item.Settings())));
        Flint_Awl = register("tool/flint_awl", "Flint Awl", "燧石锥", new ToolAwl(ToolMaterials_ELT.CHIPPED_FLINT, (new Item.Settings())));
        Flint_Scraper = register("tool/flint_scraper", "Flint Scraper", "燧石刮刀", new ToolScraper(ToolMaterials_ELT.CHIPPED_FLINT, (new Item.Settings())));
        Flint_Harpoon = register("tool/flint_harpoon", "Flint Harpoon", "燧石鱼叉", new ToolHarpoon(ToolMaterials_ELT.CHIPPED_FLINT, (new Item.Settings())));
        Flint_Hammer = register("tool/flint_hammer", "Flint Hammer", "燧石锤", new ToolHarpoon(ToolMaterials_ELT.CHIPPED_FLINT, (new Item.Settings())));

        //blockitems
        EXAMPLE_BLOCK = register(ELTBlocks.EXAMPLE_BLOCK, ELTGroups.MACHINE);
        MANUAL_WOOD_CUTTER = register(ELTBlocks.MANUAL_WOOD_CUTTER, ELTGroups.MACHINE);
        TEST = register(ELTBlocks.TEST, ELTGroups.MACHINE);

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
    private static Item register(String namespace, String path, Item item) {
        Identifier itemId = new Identifier(namespace, path);
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registry.ITEM, itemId, item);
    }

    public static Item register(String path, Item item) {
        Identifier itemId = new Identifier("elt", path);
        if (item instanceof BlockItem) {
            ((BlockItem) item).appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registry.ITEM, itemId, item);
    }

    /**
     * The true method registering an item with full properties.
     */
    // Everything is registered
    private static Item register(String path, String enName, String cnName, String enTooltip, String cnTooltip, int frametime, Item item) {

        registerModel(path);
        registerLang(path, EN_US, enName, enTooltip);
        registerLang(path, ZH_CN, cnName, cnTooltip);
        registerAnimation(path, frametime);

        return register(path, item);
    }

    private static Item register(String path, String en, String cn, int frametime, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");
        registerLang(path, ZH_CN, cn, "");
        registerAnimation(path, frametime);

        return register(path, item);
    }

    // No animation is registered
    private static Item register(String path, String enName, String cnName, String enTooltip, String cnTooltip, Item item) {

        registerModel(path);
        registerLang(path, EN_US, enName, enTooltip);
        registerLang(path, ZH_CN, cnName, cnTooltip);
        return register(path, item);
    }

    private static Item register(String path, String en, String cn, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");
        registerLang(path, ZH_CN, cn, "");
        return register(path, item);
    }

    // No Chinese localization is registered, not recommended to use this.
    private static Item registerNoCN(String path, String en, Item item) {

        registerModel(path);
        registerLang(path, EN_US, en, "");

        return register(path, item);
    }

    // No English nor Chinese localization is registered
    private static Item registerNoEN(String path, Item item) {

        registerModel(path);

        return register(path, item);
    }

    /**
     * The Run Time Resources registering methods
     */
    private static void registerModel(String path, String parent) {
        Identifier resourceId = new Identifier("elt", "item/" + path);
        RESOURCE_PACK.addModel(JModel.model(parent).textures(JModel.textures().layer0("elt:item/" + path)), resourceId);
    }

    private static void registerModel(String path) {
        Identifier resourceId = new Identifier("elt", "item/" + path);
        RESOURCE_PACK.addModel(JModel.model("item/generated").textures(JModel.textures().layer0("elt:item/" + path)), resourceId);
    }

    // With specified tooltip
    private static void registerLang(String path, JLang lang, String name, String tooltip) {
        if (path.contains("/"))
            path = path.replace("/", ".");
        RESOURCE_PACK.addLang(new Identifier("elt", lang.getName()), lang.translate("item.elt." + path, name).translate("item.elt." + path + ".tooltip", tooltip));
    }

    private static void registerAnimation(String path, int frametime) {
        Identifier aniID = new Identifier("elt", "item/" + path);
        RESOURCE_PACK.addAnimation(aniID, JAnimation.animation().frameTime(frametime));
    }

}
