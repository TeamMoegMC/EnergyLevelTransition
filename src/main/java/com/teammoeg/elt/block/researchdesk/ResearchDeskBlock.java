package com.teammoeg.elt.block.researchdesk;

import com.teammoeg.elt.block.ELTBlockItem;
import com.teammoeg.elt.block.ELTTileBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ResearchDeskBlock extends ELTTileBlock {
    public static final BooleanProperty multi = BooleanProperty.create("MULTI");

    public ResearchDeskBlock(String name) {
        super(name, Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2, 8), ELTBlockItem::new, multi);
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ResearchDeskTileEntity();
    }
}
