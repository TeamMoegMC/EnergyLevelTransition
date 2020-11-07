/*
 * Copyright (c) 2020. TeamMoeg
 *
 * This file is part of Energy Level Transition.
 *
 * Energy Level Transition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Energy Level Transition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.elt.blockentity;

import com.teammoeg.elt.ELT_Main;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;

public class DemoBlockEntity extends BlockEntity implements ImplementedInventory, SidedInventory {

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(2, ItemStack.EMPTY);
    // Store the current value of the number
    private int number = 7;

    public DemoBlockEntity() {
        super(ELT_Main.DEMO_BLOCK_ENTITY);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    // Serialize the BlockEntity
    public CompoundTag toTag(CompoundTag tag) {
        // Save the current value of the number to the tag
        tag.putInt("number", number);
        Inventories.toTag(tag, items);
        return super.toTag(tag);
    }

    // Deserialize the BlockEntity
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        number = tag.getInt("number");
        Inventories.fromTag(tag, items);

    }

    @Override
    public int[] getAvailableSlots(Direction var1) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    /// Can insert itemstack from any dir except up
    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return direction != Direction.UP;
    }

    // can extract from every dir
    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

}
