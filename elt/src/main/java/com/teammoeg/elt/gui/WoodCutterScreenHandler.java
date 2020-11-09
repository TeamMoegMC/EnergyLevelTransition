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

package com.teammoeg.elt.gui;

import com.teammoeg.elt.blockentity.WoodCutterBlockEntity;
import com.teammoeg.elt.handlers.ELTScreenHandlerTypes;
import com.teammoeg.elt.recipe.WoodCutterRecipe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import javax.annotation.Nullable;
import java.util.Optional;

public class WoodCutterScreenHandler extends ScreenHandler implements NamedScreenHandlerFactory {
    final Slot inputSlot;
    final Slot toolSlot1;
    final Slot toolSlot2;
    final Slot outputSlot1;
    final Slot outputSlot2;
    final Inventory inv;
    private final ScreenHandlerContext context;
//    private final ScreenHandlerFactory baseFactory;

    public WoodCutterScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(5), ScreenHandlerContext.EMPTY);
    }

    public WoodCutterScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inv, ScreenHandlerContext context) {
        super(ELTScreenHandlerTypes.WOOD_CUTTER, syncId);
        this.context = context;
        this.inv = inv;
        inv.onOpen(playerInventory.player);
        System.out.println(playerInventory.player.currentScreenHandler);
        this.inputSlot = this.addSlot(new Slot(inv, 0, 45, 16) {
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        this.toolSlot1 = this.addSlot(new Slot(inv, 1, 45, 38) {
            public boolean canInsert(ItemStack stack) {
                return true;
            }
        });
        this.toolSlot2 = this.addSlot(new Slot(inv, 2, 45, 38 + 18) {
            public boolean canInsert(ItemStack stack) {
                return true; /*stack.getItem() == ELT_Main.TOOL_2;*/
            }
        });

        this.outputSlot1 = this.addSlot(new Slot(inv, 3, 98, 16) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });
        this.outputSlot2 = this.addSlot(new Slot(inv, 4, 98, 16 + 18) {
            public boolean canInsert(ItemStack stack) {
                return false;
            }
        });

        int k;
        for (k = 0; k < 3; ++k) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
            }
        }

        for (k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText("elt.wood_cutter.displaytext");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return null;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        Optional<WoodCutterRecipe> optional = player.world.getServer().getRecipeManager().getFirstMatch(WoodCutterRecipe.WOOD_CUTTER, (WoodCutterBlockEntity) inv, player.world);
        optional.ifPresent(i -> {
            i.craft((WoodCutterBlockEntity) inv);
        });
        return true;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public ItemStack transferSlot(PlayerEntity player, int index) {
        Slot slot = this.slots.get(index);
        ItemStack itemStack = ItemStack.EMPTY;
        if (slot != null && slot.hasStack()) {
            itemStack = slot.getStack();
            if (index >= 5 && !this.insertItem(itemStack, 0, 3, false)) {
                return ItemStack.EMPTY;
            } else if (index < 5 && !this.insertItem(itemStack, 5, 5 + 36, false)) {
                return ItemStack.EMPTY;
            }
        }
        return itemStack;
    }
}
