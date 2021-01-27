package com.teammoeg.elt.container.Slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.AbstractFurnaceContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class BookSlot extends Slot {
    public BookSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }
    public boolean mayPlace(ItemStack stack) {
        return isBook(stack);
    }
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }
    public static boolean isBook(ItemStack stack) {
        return stack.getItem() == Items.BOOK;
    }
}
