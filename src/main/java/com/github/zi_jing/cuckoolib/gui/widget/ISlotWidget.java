package com.github.zi_jing.cuckoolib.gui.widget;

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