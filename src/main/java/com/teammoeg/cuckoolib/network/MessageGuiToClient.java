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

package com.teammoeg.cuckoolib.network;

import com.teammoeg.cuckoolib.gui.ModularContainer;
import com.teammoeg.cuckoolib.gui.widget.IWidget;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageGuiToClient implements IMessage {
	private PacketBuffer data;
	private int window;

	public MessageGuiToClient() {

	}

	public MessageGuiToClient(PacketBuffer data, int window) {
		this.data = data;
		this.window = window;
	}

	public static MessageGuiToClient decode(PacketBuffer buf) {
		return new MessageGuiToClient(new PacketBuffer(Unpooled.copiedBuffer((buf.readBytes(buf.readInt())))),
				buf.readInt());
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.data.readableBytes());
		buf.writeBytes(this.data);
		buf.writeInt(this.window);
	}

	@Override
	public void process(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			PlayerEntity player = Minecraft.getInstance().player;
			Container container;
			if (player != null) {
				if (this.window == 0) {
					container = player.inventoryMenu;
				} else if (this.window == player.containerMenu.containerId) {
					container = player.containerMenu;
				} else {
					return;
				}
				if (container instanceof ModularContainer) {
					IWidget widget = ((ModularContainer) container).getGuiInfo().getWidget(this.data.readInt());
					widget.receiveMessageFromServer(this.data);
				}
			}
		});
	}
}