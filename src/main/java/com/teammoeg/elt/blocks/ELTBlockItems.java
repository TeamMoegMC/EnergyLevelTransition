package com.teammoeg.elt.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ELTBlockItems extends BlockItem {
    public ELTBlockItems(Block blockIn, Properties Properties) {
       super(blockIn,Properties);
    }
    public ELTBlockItems(Block blockIn) {
        this(blockIn, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
        setRegistryName(blockIn.getRegistryName());
    }
}
