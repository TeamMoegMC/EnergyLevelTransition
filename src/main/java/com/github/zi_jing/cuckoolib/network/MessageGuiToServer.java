package com.github.zi_jing.cuckoolib.network;

import com.github.zi_jing.cuckoolib.gui.ModularContainer;
import com.github.zi_jing.cuckoolib.gui.widget.IWidget;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class MessageGuiToServer implements IMessage {
	private PacketBuffer data;
	private int window;

	public MessageGuiToServer() {

	}

	public MessageGuiToServer(PacketBuffer data, int window) {
		this.data = data;
		this.window = window;
	}

	public static MessageGuiToServer decode(PacketBuffer buf) {
		return new MessageGuiToServer(new PacketBuffer(Unpooled.copiedBuffer((buf.readBytes(buf.readInt())))),
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
			PlayerEntity player = ctx.get().getSender();
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
					widget.receiveMessageFromClient(this.data);
				}
			}
		});
	}
}