package com.github.zi_jing.cuckoolib.tool;

import net.minecraft.block.material.Material;

public class ToolUtil {
	private ToolUtil() {

	}

	public static boolean canPickaxeHarvest(Material material) {
		return material == Material.METAL || material == Material.HEAVY_METAL || material == Material.STONE;
	}

	public static boolean canAxeHarvest(Material material) {
		return material == Material.WOOD || material == Material.NETHER_WOOD || material == Material.PLANT
				|| material == Material.REPLACEABLE_PLANT || material == Material.BAMBOO || material == Material.VEGETABLE;
	}
}