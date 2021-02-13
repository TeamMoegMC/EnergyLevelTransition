package com.teammoeg.elt.block.world;

import com.teammoeg.elt.block.ELTBlock;
import com.teammoeg.elt.item.ELTBlockItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ELTleaves extends ELTBlock {
    public ELTleaves(String name) {
        super(name, Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isSuffocating(ELTleaves::never)
                .isValidSpawn(ELTleaves::never).isViewBlocking(ELTleaves::never), ELTBlockItem::new);
    }

    private static boolean never(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    private static Boolean never(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entityIn) {
        return (boolean) false;
    }
}
