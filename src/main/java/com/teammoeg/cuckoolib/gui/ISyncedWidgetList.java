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

package com.teammoeg.cuckoolib.gui;

import com.teammoeg.cuckoolib.gui.widget.ISlotWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.function.Consumer;

public interface ISyncedWidgetList {
	void writeToClient(int widgetId, Consumer<PacketBuffer> data);

	void writeToServer(int widgetId, Consumer<PacketBuffer> data);

	void notifySlotChange(ISlotWidget widget, boolean isEnable);

	ItemStack notifyTransferStack(ItemStack stack, boolean simulate);
}