package com.teammoeg.elt.block.researchblock;

import com.teammoeg.elt.block.ELTBlockItem;
import com.teammoeg.elt.block.ELTTileBlock;
import com.teammoeg.the_seed.api.ELTProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockReader;

public class ResearchDeskBlock extends ELTTileBlock {

    public static final Property<Boolean> multi = ELTProperties.MULTIBLOCKSLAVE;
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public ResearchDeskBlock(String name) {
        super(name, Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2, 8), ELTBlockItem::new, multi);
    }
}

