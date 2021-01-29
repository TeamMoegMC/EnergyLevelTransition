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
import com.teammoeg.elt.container.Slot.InkSlot;
import com.teammoeg.elt.container.Slot.SolidInspirationSlot;
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
    private final IInventory container;
    private ResearchDeskContainer(ContainerType<?> type, int id, PlayerInventory playerInventory,BlockPos pos) { this(type, id, playerInventory, new Inventory(3), pos);
    }

    public static ResearchDeskContainer create ( int id, PlayerInventory playerinventory, BlockPos pos){
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(), id, playerinventory, pos);
    }

    public static ResearchDeskContainer create ( int id, PlayerInventory playerinventory, IInventory blockEntity, BlockPos pos){
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(), id, playerinventory, blockEntity, pos);
    }

    public static int SIDE = 9, TOP = 4, SUB_SLOT_SIDE = 227;

    public ResearchDeskContainer(ContainerType < ? > type,int windowsid, PlayerInventory playerinventory, IInventory inventory, BlockPos pos){
        super(type, windowsid);
        this.container = inventory;
        World level = playerinventory.player.level;
        inventory.startOpen(playerinventory.player);
        TileEntity tileEntity = level.getBlockEntity(pos);

        // Book Slot
        addSlot(new BookSlot(inventory, 0, SUB_SLOT_SIDE, TOP) {
            @Override
            public void setChanged() {
                ItemStack stack = this.getItem();
                ResearchDeskBlock.setBlockhasbook(level, pos, tileEntity.getBlockState(), stack.getItem() == Items.BOOK);
                super.setChanged();
            }
        });

        // Ink
        addSlot(new InkSlot(inventory, 1, SUB_SLOT_SIDE, TOP + 29) {
            @Override
            public void setChanged() {
                ItemStack stack = this.getItem();
//                ResearchDeskBlock.setBlockhasbook(level, pos, tileEntity.getBlockState(), stack.getItem() == Items.BOOK);
                super.setChanged();
            }
        });

        // 实体灵感消化曹
        addSlot(new SolidInspirationSlot(inventory, 2, SUB_SLOT_SIDE, TOP  + 29  + 28) {
            @Override
            public void setChanged() {
                ItemStack stack = this.getItem();
//                ResearchDeskBlock.setBlockhasbook(level, pos, tileEntity.getBlockState(), stack.getItem() == Items.BOOK);
                super.setChanged();
            }
        });

        // Player Inventory
        for (int k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerinventory, j + k * 9 + 9,  SIDE + j * 18, TOP + k * 18));
            }
        }

        // Player Inventory Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerinventory, i, SIDE + i * 18, TOP + 58));
        }
    }

    @Override
    public boolean stillValid (PlayerEntity playerIn){
        return true;
    }
            @Override
    public ItemStack quickMoveStack (PlayerEntity playerIn,int index){
        return ItemStack.EMPTY;
    }
    public IInventory getContainer () {
        return this.container;
    }

    public void removed (PlayerEntity playerIn){
        super.removed(playerIn);
        this.container.stopOpen(playerIn);
    }
}
