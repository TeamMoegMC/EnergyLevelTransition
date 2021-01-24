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

package com.teammoeg.elt.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ELTBlockItem extends BlockItem {
    public ELTBlockItem(Block blockIn, Properties Properties) {
        super(blockIn, Properties);
    }

    public ELTBlockItem(Block blockIn) {
        this(blockIn, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
        if (blockIn.getRegistryName() != null) {
            setRegistryName(blockIn.getRegistryName());
        }
    }
}
