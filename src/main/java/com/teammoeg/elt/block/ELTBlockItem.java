package com.teammoeg.elt.block;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ELTBlockItem extends BlockItem {
    public ELTBlockItem(Block blockIn, Properties Properties) {
        super(blockIn, Properties);
    }

    public ELTBlockItem(Block blockIn) {
        this(blockIn, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
        setRegistryName(blockIn.getRegistryName());
    }
}
