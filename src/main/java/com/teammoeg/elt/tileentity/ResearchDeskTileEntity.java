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

package com.teammoeg.elt.tileentity;

import com.teammoeg.elt.container.ResearchDeskContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class ResearchDeskTileEntity extends ELTContainerTileEntity implements INamedContainerProvider{
    public ResearchDeskTileEntity() {
        super(ELTTileEntityTypes.RESEARCH_DESK.get());
    }


    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Container");
    }

    @Nullable
    @Override
    public Container createMenu(int ID, PlayerInventory inventory, PlayerEntity player) {
        return ResearchDeskContainer.create(ID, inventory,this,player);
    }
}
