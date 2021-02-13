package com.teammoeg.elt.block.world;

import com.teammoeg.elt.RegistryEventHandler;
import com.teammoeg.elt.block.ELTBlocks;
import com.teammoeg.elt.item.ELTBlockItem;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class ELTGrassBlock extends GrassBlock {
    public ELTGrassBlock(String name) {
        super(Properties.of(Material.GRASS).harvestTool(ToolType.SHOVEL).sound(SoundType.GRASS).strength(1));
        this.setRegistryName(name);
        RegistryEventHandler.registeredBlocks.add(this);
        Item item = new ELTBlockItem(this);
        RegistryEventHandler.registeredItems.add(item);
    }

    @Override
    public void performBonemeal(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
    }

    private static boolean canBeGrass(BlockState state, IWorldReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = worldReader.getBlockState(blockpos);
        if (blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowBlock.LAYERS) == 1) {
            return true;
        } else if (blockstate.getFluidState().getAmount() == 8) {
            return false;
        } else {
            int i = LightEngine.getLightBlockInto(worldReader, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(worldReader, blockpos));
            return i < worldReader.getMaxLightLevel();
        }
    }

    private static boolean canPropagate(BlockState state, IWorldReader worldReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        return canBeGrass(state, worldReader, pos) && !worldReader.getFluidState(blockpos).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!canBeGrass(state, worldIn, pos)) {
            if (!worldIn.isAreaLoaded(pos, 3)) return;
            worldIn.setBlockAndUpdate(pos, ELTBlocks.ELT_DIRTBLOCK.defaultBlockState());
        } else {
            if (worldIn.getMaxLocalRawBrightness(pos.above()) >= 9) {
                BlockState blockstate = this.defaultBlockState();

                for (int i = 0; i < 4; ++i) {
                    BlockPos blockpos = pos.offset(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
                    if (worldIn.getBlockState(blockpos).is(ELTBlocks.ELT_DIRTBLOCK) && canPropagate(blockstate, worldIn, blockpos)) {
                        worldIn.setBlockAndUpdate(blockpos, this.defaultBlockState());
                    }
                }
            }
        }
    }
}
