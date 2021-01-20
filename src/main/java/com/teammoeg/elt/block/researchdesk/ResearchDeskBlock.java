package com.teammoeg.elt.block.researchdesk;

import com.teammoeg.elt.block.ELTBlockItem;
import com.teammoeg.elt.block.ELTTileBlock;
import com.teammoeg.the_seed.api.ELTProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ResearchDeskBlock extends ELTTileBlock {

    public static final Property<Boolean> MULTI = ELTProperties.MULTIBLOCKSLAVE;
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public ResearchDeskBlock(String name) {
        super(name, Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2, 8), ELTBlockItem::new, MULTI,FACING);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public BlockState rotate(BlockState state, IWorld world, BlockPos pos, Rotation direction) {
        return state.setValue(FACING, direction.rotate(state.getValue(FACING)));
    }

    private static Direction getNeighbourDirection(boolean b, Direction directionIn) {
        return b == false ? directionIn : directionIn.getOpposite();
    }


    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!worldIn.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING));
            worldIn.setBlock(blockpos, state.setValue(MULTI, true), 3);
            worldIn.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(worldIn, pos, 3);
    }
}

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getHorizontalDirection();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction);
        return context.getLevel().getBlockState(blockpos1).canBeReplaced(context) ? this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!worldIn.isClientSide && player.isCreative()) {
            boolean block = state.getValue(MULTI);
            if (block == false) {
                BlockPos blockpos = pos.relative(getNeighbourDirection(state.getValue(MULTI), state.getValue(FACING)));
                BlockState blockstate = worldIn.getBlockState(blockpos);
                if (blockstate.getBlock() == this && blockstate.getValue(MULTI) == true) {
                    worldIn.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    worldIn.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }

        super.playerWillDestroy(worldIn, pos, state, player);
    }
}

