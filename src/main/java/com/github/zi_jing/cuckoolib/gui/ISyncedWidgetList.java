package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.gui.widget.ISlotWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

import java.util.function.Consumer;

public interface ISyncedWidgetList {
	void writeToClient(int widgetId, Consumer<PacketBuffer> data);

	void writeToServer(int widgetId, Consumer<PacketBuffer> data);

	void notifySlotChange(ISlotWidget widget, boolean isEnable);

	ItemStack notifyTransferStack(ItemStack stack, boolean simulate);
}