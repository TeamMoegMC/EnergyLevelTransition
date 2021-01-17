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

package com.teammoeg.cuckoolib.gui.widget;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public interface ISlotWidget extends IWidget {
	public static final ItemStack TAG = ItemStack.EMPTY.copy();

	Slot getSlot();

	void setSlotCount(int count);

	int getSlotCount();

	default boolean canMergeSlot(ItemStack stack) {
		return false;
	}

	default ItemStack onItemTake(PlayerEntity player, ItemStack stack) {
		return TAG;
	}
}