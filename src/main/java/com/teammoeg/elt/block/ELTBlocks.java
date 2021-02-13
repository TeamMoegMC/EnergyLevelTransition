/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.block;

import com.teammoeg.elt.block.world.ELTDirt;
import com.teammoeg.elt.block.world.ELTGrassBlock;
import com.teammoeg.elt.block.world.ELTWood;
import com.teammoeg.elt.block.world.ELTleaves;
import net.minecraft.block.Block;

public class ELTBlocks {
    public static final Block RESEARCH_DESK = new ResearchDeskBlock("research_desk");
    public static final Block ELT_GRASSBLOCK = new ELTGrassBlock("elt_grass_block");
    public static final Block ELT_DIRTBLOCK = new ELTDirt("elt_dirt_block");
    public static final Block ELT_SPRUCEWOOD = new ELTWood("elt_spruce_wood");
    public static final Block ELT_SPRUCELEAVES = new ELTleaves("elt_spruce_leaves");

    public static void init() {
    }

}
