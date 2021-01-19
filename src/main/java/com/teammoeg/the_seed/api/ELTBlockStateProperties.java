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

package com.teammoeg.the_seed.api;

import com.teammoeg.elt.block.researchtable.ResearchTablePart;
import net.minecraft.state.EnumProperty;


/**
 * 用于存储所有的Properties
 */
public class ELTBlockStateProperties {
    public static final EnumProperty<ResearchTablePart> RESEARCH_TABLE_PART = EnumProperty.create("part", ResearchTablePart.class);

}
