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

import com.teammoeg.elt.block.ELTBlocks;
import com.teammoeg.elt.block.ResearchDeskBlock;
import com.teammoeg.elt.container.ResearchDeskContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;

public class ResearchDeskTileEntity extends ELTContainerTileEntity implements ITickableTileEntity {
    public float leftPages;
    protected int openCount;
    private int tickInterval;

    public ResearchDeskTileEntity() {
        super(ELTTileEntityTypes.RESEARCH_DESK.get());
    }

    @Override
    public int getContainerSize() {
        return 3;
    }

    @Override
    public ITextComponent getDisplayName() {
        return new StringTextComponent("Research Container");
    }

    @Nullable
    @Override
    public Container createMenu(int ID, PlayerInventory inventory, PlayerEntity player) {
        return ResearchDeskContainer.create(ID, inventory, this, this.getBlockPos());
    }

    @Override
    public void tick() {
        if (++this.tickInterval % 20 * 4 == 0) {
            this.level.blockEvent(this.worldPosition, ELTBlocks.RESEARCH_DESK, 1, this.openCount);
        }

        if (this.openCount > 0 && this.leftPages == 0.0F && getBlockState().getValue(ResearchDeskBlock.BOOK)) {
            this.playSound(SoundEvents.BOOK_PAGE_TURN);
        }
        if (this.openCount > 0) {
            this.leftPages += 0.1F;
        } else {
            this.leftPages -= 0.1F;
        }
        if (this.leftPages > 1.0F) {
            this.leftPages = 1.0F;
        }
        if (this.leftPages < 0.0F) {
            this.leftPages = 0.0F;
        }
    }


    @Override
    public void startOpen(PlayerEntity player) {
        if (this.openCount < 0) {
            this.openCount = 0;
        }
        ++this.openCount;
        this.level.blockEvent(this.worldPosition, ELTBlocks.RESEARCH_DESK, 1, this.openCount);
    }

    @Override
    public void stopOpen(PlayerEntity player) {
        --this.openCount;
        this.level.blockEvent(this.worldPosition, ELTBlocks.RESEARCH_DESK, 1, this.openCount);
    }

    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.openCount = type;
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }
    private void playSound(SoundEvent soundIn) {
            this.level.playSound((PlayerEntity)null, this.worldPosition.getX(), this.worldPosition.getY() ,this.worldPosition.getZ(), soundIn, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 1F);
        }
}
