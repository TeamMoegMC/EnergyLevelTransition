package com.github.zi_jing.cuckoolib.network;

import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MessageModularGuiOpen implements IMessage {
	private PacketBuffer holderPacket;
	private List<PacketBuffer> updateData;
	private int window;

	public MessageModularGuiOpen() {

	}

	public MessageModularGuiOpen(PacketBuffer holderPacket, List<PacketBuffer> updateData, int window) {
		this.holderPacket = holderPacket;
		this.updateData = updateData;
		this.window = window;
	}

	public static MessageModularGuiOpen decode(PacketBuffer buf) {
		MessageModularGuiOpen msg = new MessageModularGuiOpen();
		msg.holderPacket = new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt())));
		int size = buf.readInt();
		msg.updateData = new ArrayList<PacketBuffer>();
		for (int i = 0; i < size; i++) {
			msg.updateData.add(new PacketBuffer(Unpooled.copiedBuffer(buf.readBytes(buf.readInt()))));
		}
		msg.window = buf.readInt();
		return msg;
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.holderPacket.readableBytes());
		buf.writeBytes(this.holderPacket);
		buf.writeInt(this.updateData.size());
		this.updateData.forEach((data) -> {
			buf.writeInt(data.readableBytes());
			buf.writeBytes(data);
		});
		buf.writeInt(this.window);
	}

	@Override
	public void process(Supplier<Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ResourceLocation codecName = this.holderPacket.readResourceLocation();
			if (ModularGuiInfo.REGISTRY.containsKey(codecName)) {
				ModularGuiInfo.openClientModularGui(this.window,
						ModularGuiInfo.REGISTRY.getValue(codecName).readHolder(this.holderPacket), this.updateData);
			} else {
				throw new RuntimeException("The gui holder registry name is invalid");
			}
		});
	}
}