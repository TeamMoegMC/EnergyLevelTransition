package com.teammoeg.elt.blocks.ResourceBlock;

import com.teammoeg.elt.blocks.ELTTileEntity;
import com.teammoeg.elt.blocks.ELTTileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class TileEntityResearchDesk extends ELTTileEntity {
    public TileEntityResearchDesk() {
        super(ELTTileEntityType.ResearchDesk.get());
    }
    @OnlyIn(Dist.CLIENT)
    private AxisAlignedBB renderAABB;
    @Override
    public AxisAlignedBB getRenderBoundingBox()
    {
        if(renderAABB==null)
            renderAABB = new AxisAlignedBB(getBlockPos().getX()-1, getBlockPos().getY(), getBlockPos().getZ()-1, getBlockPos().getX()+2, getBlockPos().getY()+2, getBlockPos().getZ()+2);
         return renderAABB;
    }
}
