package com.teammoeg.elt.blocks.ResourceBlock;

import com.teammoeg.elt.blocks.ELTBlockItems;
import com.teammoeg.elt.blocks.ELTTileBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ResearchDesk extends ELTTileBlocks {
    public static final BooleanProperty multi = BooleanProperty.create("MULTI");
    public ResearchDesk(String name) {
        super(name, Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2, 8), ELTBlockItems::new,multi);
    }
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TileEntityResearchDesk();
    }
}
