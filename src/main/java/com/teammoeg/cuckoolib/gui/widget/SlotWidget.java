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

package com.teammoeg.cuckoolib.gui.widget;

import com.teammoeg.cuckoolib.client.render.TextureArea;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotWidget extends WidgetBase implements ISlotWidget {
	protected SlotItemHandler slot;
	protected TextureArea texture;
	protected int slotCount;
	protected boolean canTakeItems, canPutItems;

	public SlotWidget(int x, int y, IItemHandler inventory, int index) {
		this(x, y, inventory, index, true, true);
	}

	public SlotWidget(int x, int y, IItemHandler inventory, int index, boolean canPutItems, boolean canTakeItems) {
		super(x, y, 18, 18);
		this.slot = new WidgetSlotItemHandler(inventory, index, position.getX(), position.getY());
		this.canPutItems = canPutItems;
		this.canTakeItems = canTakeItems;
	}

	@Override
	public void setSlotCount(int count) {
		this.slotCount = count;
	}

	@Override
	public int getSlotCount() {
		return this.slotCount;
	}

	@Override
	public Slot getSlot() {
		return this.slot;
	}

	@Override
	public void setEnable(boolean enable) {
		if (this.isEnable ^ enable && this.widgetList != null) {
			this.widgetList.notifySlotChange(this, enable);
		}
		this.isEnable = enable;
	}

	public void setTexture(TextureArea texture) {
		this.texture = texture;
	}

	public boolean canTakeItem(PlayerEntity player) {
		return this.isEnable && this.canTakeItems;
	}

	public boolean canPutItem(ItemStack stack) {
		return this.isEnable && this.canPutItems;
	}

	protected class WidgetSlotItemHandler extends SlotItemHandler {
		public WidgetSlotItemHandler(IItemHandler inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return super.mayPlace(stack) && SlotWidget.this.canPutItem(stack);
		}

		@Override
		public boolean mayPickup(PlayerEntity player) {
			return super.mayPickup(player) && SlotWidget.this.canTakeItem(player);
		}

		@Override
		public boolean isActive() {
			return SlotWidget.this.isEnable;
		}
	}
}