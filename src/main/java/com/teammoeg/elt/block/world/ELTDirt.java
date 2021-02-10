package com.teammoeg.elt.block.world;

import com.teammoeg.elt.block.ELTBlock;
import com.teammoeg.elt.item.ELTBlockItem;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class ELTDirt extends ELTBlock {
    public ELTDirt(String name) {
        super(name, Properties.of(Material.DIRT, MaterialColor.DIRT).harvestTool(ToolType.SHOVEL).sound(SoundType.GRAVEL).strength(0.5F), ELTBlockItem::new);
    }
}
