package com.teammoeg.elt.blocks;

import com.teammoeg.elt.ELT;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.function.BiFunction;

public class ELTBlocks extends Block {

    public String name;

    public ELTBlocks(String name, Properties properties, BiFunction<Block, Item.Properties, Item> BlockItem) {
        super(properties);
        this.name = name;
        this.setRegistryName(name);
        ELT.RegistryEvents.registeredBlocks.add(this);
        Item item = BlockItem.apply(this, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
        if(item!=null)
        {
            item.setRegistryName(name);
            ELT.RegistryEvents.registeredItems.add(item);
        }
    }
}
