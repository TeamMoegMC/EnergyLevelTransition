package com.github.zi_jing.cuckoolib.material;

import com.github.zi_jing.cuckoolib.material.type.IMaterialFlag;
import com.github.zi_jing.cuckoolib.material.type.Material;

import java.util.function.Predicate;

public class ModSolidShapes {
	public static final SolidShape DUST = new SolidShape("dust", 144, flag(Material.GENERATE_DUST));
	public static final SolidShape PLATE = new SolidShape("plate", 144, flag(Material.GENERATE_PLATE));

	public static final SolidShape KNIFE_HEAD = new SolidShape("knife_head", 144, flag(Material.GENERATE_TOOL));
	public static final SolidShape HAMMER_HEAD = new SolidShape("hammer_head", 144, flag(Material.GENERATE_TOOL));
	public static final SolidShape CHISEL_HEAD = new SolidShape("chisel_head", 144, flag(Material.GENERATE_TOOL));

	public static Predicate<Material> flag(IMaterialFlag flag) {
		return (material) -> material.hasFlag(flag);
	}

	public static void register() {
		DUST.addIgnoredMaterial(ModMaterials.REDSTONE);
	}
}