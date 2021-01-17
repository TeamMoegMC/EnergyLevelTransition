package com.teammoeg.elt.block.researchblock;

import com.teammoeg.elt.block.ELTTileEntity;
import com.teammoeg.elt.block.ELTTileEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class ResearchDeskTileEntity extends ELTTileEntity {
    public static final DirectionProperty FACING_HORIZONTAL = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BlockPos MASTER_POS = BlockPos.ZERO;
    public static final BlockPos DUMMY_POS = new BlockPos(1, 0, 0);
    public ResearchDeskTileEntity() {
        super(ELTTileEntityType.ResearchDesk.get());
    }
    public boolean isDummy()
    {
         BlockState blockstate = getState();
    }
    @OnlyIn(Dist.CLIENT)
    private AxisAlignedBB renderAABB;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if (renderAABB == null)
            renderAABB = new AxisAlignedBB(getBlockPos().getX() - 1, getBlockPos().getY(), getBlockPos().getZ() - 1, getBlockPos().getX() + 2, getBlockPos().getY() + 2, getBlockPos().getZ() + 2);
        return renderAABB;
    }

    public void placeDummies(BlockItemUseContext ctx, BlockState state)
    {
        BlockPos dummyPos = pos.offset(dummyDir);
        level.getBlockState(pos)
        level.setBlock(dummyPos, state);
    }

    public EnumProperty<Direction> getFacingProperty()
    {

        return FACING_HORIZONTAL;
    }
}
