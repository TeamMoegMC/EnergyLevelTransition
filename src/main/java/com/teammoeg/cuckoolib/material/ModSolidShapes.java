/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.cuckoolib.material;

import com.teammoeg.cuckoolib.material.type.IMaterialFlag;
import com.teammoeg.cuckoolib.material.type.Material;

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