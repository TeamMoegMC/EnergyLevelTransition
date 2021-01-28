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

package com.teammoeg.elt.container;

import com.teammoeg.elt.block.ResearchDeskBlock;
import com.teammoeg.elt.container.Slot.BookSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ResearchDeskContainer extends Container {
    private ResearchDeskContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, BlockPos pos) {
        this(type, id, playerInventory, new Inventory(1), pos);
    }

    public static ResearchDeskContainer create(int id, PlayerInventory playerinventory, BlockPos pos) {
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(), id, playerinventory, pos);
    }

    public static ResearchDeskContainer create(int id, PlayerInventory playerinventory, IInventory blockEntity, BlockPos pos) {
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(), id, playerinventory, blockEntity, pos);
    }

    public ResearchDeskContainer(ContainerType<?> type, int windowsid, PlayerInventory playerinventory, IInventory inventory, BlockPos pos) {
        super(type, windowsid);
        World level = playerinventory.player.level;
        TileEntity tileEntity = level.getBlockEntity(pos);
        addSlot(new BookSlot(inventory, 0, 10, 10) {
            @Override
            public void setChanged() {
                ItemStack stack = this.getItem();
                ResearchDeskBlock.setBlockhasbook(level, pos, tileEntity.getBlockState(), stack.getItem() == Items.BOOK);
                super.setChanged();
            }

        });

        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerinventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerinventory, i1, 8 + i1 * 18, 161));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
