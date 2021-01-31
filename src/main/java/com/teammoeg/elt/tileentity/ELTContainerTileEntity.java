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

package com.teammoeg.elt.tileentity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;

public abstract class ELTContainerTileEntity extends TileEntity implements IInventory, INamedContainerProvider {
    private NonNullList<ItemStack> items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
    private final int size = getContainerSize();

    public ELTContainerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return index >= 0 && index < this.items.size() ? this.items.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack itemstack = ItemStackHelper.removeItem(this.items, index, count);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack itemstack = this.items.get(index);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(index, ItemStack.EMPTY);
            return itemstack;
        }
    }

    public ItemStack addItem(ItemStack stack) {
        ItemStack itemstack = stack.copy();
        this.moveItemToOccupiedSlotsWithSameType(itemstack);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.moveItemToEmptySlots(itemstack);
            return itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
        }
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.items.set(index, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxStackSize()) {
            stack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    private void moveItemToEmptySlots(ItemStack itemStack_) {
        for (int i = 0; i < this.size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (itemstack.isEmpty()) {
                this.setItem(i, itemStack_.copy());
                itemStack_.setCount(0);
                return;
            }
        }

    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack itemStack_) {
        for (int i = 0; i < this.size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (this.isSameItem(itemstack, itemStack_)) {
                this.moveItemsBetweenStacks(itemStack_, itemstack);
                if (itemStack_.isEmpty()) {
                    return;
                }
            }
        }

    }

    private boolean isSameItem(ItemStack itemStack_, ItemStack itemStack1_) {
        return itemStack_.getItem() == itemStack1_.getItem() && ItemStack.tagMatches(itemStack_, itemStack1_);
    }

    private void moveItemsBetweenStacks(ItemStack itemStack_, ItemStack itemStack1_) {
        int i = Math.min(this.getMaxStackSize(), itemStack1_.getMaxStackSize());
        int j = Math.min(itemStack_.getCount(), i - itemStack1_.getCount());
        if (j > 0) {
            itemStack1_.grow(j);
            itemStack_.shrink(j);
            this.setChanged();
        }

    }

    public void fromTag(ListNBT listNBT_) {
        for (int i = 0; i < listNBT_.size(); ++i) {
            ItemStack itemstack = ItemStack.of(listNBT_.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.addItem(itemstack);
            }
        }

    }

    public ListNBT createTag() {
        ListNBT listnbt = new ListNBT();

        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                listnbt.add(itemstack.save(new CompoundNBT()));
            }
        }

        return listnbt;
    }

    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.items);
    }

    public CompoundNBT save(CompoundNBT compound) {
        super.save(compound);
        ItemStackHelper.saveAllItems(compound, this.items);
        return compound;
    }
}