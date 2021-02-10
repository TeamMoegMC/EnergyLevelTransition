package com.teammoeg.elt.client;

import com.teammoeg.elt.block.ELTBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.world.GrassColors;
import net.minecraft.world.biome.BiomeColors;

public class BlockColor {
    public static void blockcolor() {
        Minecraft.getInstance().getBlockColors().register((state, reader, pos, colorIn) -> {
            return reader != null && pos != null ? BiomeColors.getAverageGrassColor(reader, pos) : GrassColors.get(0.5D, 1.0D);
        }, ELTBlocks.ELTGRASSBLOCK);
    }
}
