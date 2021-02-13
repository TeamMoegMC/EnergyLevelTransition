package com.teammoeg.elt.block.world;

import com.teammoeg.elt.block.ELTBlock;
import com.teammoeg.elt.item.ELTBlockItem;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class ELTWood extends ELTBlock {
    public ELTWood(String name) {
        super(name, Properties.of(Material.WOOD, MaterialColor.WOOD).sound(SoundType.WOOD).strength(2F), ELTBlockItem::new);
    }
}
