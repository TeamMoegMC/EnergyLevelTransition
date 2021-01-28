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

package com.teammoeg.cuckoolib.tool;

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