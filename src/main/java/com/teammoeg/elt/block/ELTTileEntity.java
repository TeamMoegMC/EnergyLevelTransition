package com.teammoeg.elt.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class ELTTileEntity extends TileEntity {

    public ELTTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }
    public BlockState getState()
    {
        return getBlockState();
    }
}
