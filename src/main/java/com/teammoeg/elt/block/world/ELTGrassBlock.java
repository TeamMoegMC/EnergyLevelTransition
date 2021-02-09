package com.teammoeg.elt.block.world;

import com.teammoeg.elt.RegistryEventHandler;
import com.teammoeg.elt.item.ELTBlockItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class ELTGrassBlock extends GrassBlock {
    public ELTGrassBlock(String name) {
        super(Properties.of(Material.GRASS).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS));
        this.setRegistryName(name);
        RegistryEventHandler.registeredBlocks.add(this);
        Item item = new ELTBlockItem(this);
        RegistryEventHandler.registeredItems.add(item);
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {

    }
}
