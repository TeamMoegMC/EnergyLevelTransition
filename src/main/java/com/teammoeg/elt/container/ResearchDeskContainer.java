package com.teammoeg.elt.container;

import com.teammoeg.elt.container.Slot.BookSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;


public class ResearchDeskContainer extends Container {
    private ResearchDeskContainer(ContainerType<?> type, int id, PlayerInventory playerInventory, PlayerEntity player) {
        this(type, id, playerInventory, new Inventory(1),player);
    }
    public static ResearchDeskContainer create(int id, PlayerInventory playerinventory,PlayerEntity player) {
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(),id,playerinventory,player);
    }
    public static ResearchDeskContainer create(int id, PlayerInventory playerinventory, IInventory blockEntity,PlayerEntity player) {
        return new ResearchDeskContainer(ELTContainerType.RESEARCHDESKCONTAINER.get(),id,playerinventory,blockEntity,player);
    }
    public ResearchDeskContainer(ContainerType<?> type,int windowsid, PlayerInventory playerinventory, IInventory inventory,PlayerEntity player) {
        super(type, windowsid);
        addSlot(new BookSlot(inventory, 0, 10, 10));
        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerinventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
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
