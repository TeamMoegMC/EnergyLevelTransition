package com.github.zi_jing.cuckoolib.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public interface IMessage {
	void encode(PacketBuffer buf);

	void process(Supplier<Context> ctx);
}