package com.teammoeg.elt.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.state.Property;

import java.util.function.BiFunction;

public class ELTTileBlock extends ELTBlock {
    public ELTTileBlock(String name, Properties properties, BiFunction<Block, Item.Properties, Item> BlockItem, Property... stateProps) {
        super(name, properties, BlockItem);
    }
}
