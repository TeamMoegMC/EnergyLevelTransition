package com.teammoeg.elt.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.state.Property;
import java.util.function.BiFunction;

public class ELTTileBlocks extends ELTBlocks{
    public ELTTileBlocks(String name, Properties properties, BiFunction<Block, Item.Properties, Item> BlockItem,Property... stateProps) {
        super(name, properties,BlockItem);
    }
}
