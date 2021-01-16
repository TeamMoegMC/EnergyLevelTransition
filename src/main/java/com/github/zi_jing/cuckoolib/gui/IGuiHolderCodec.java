package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface IGuiHolderCodec {
	ResourceLocation getRegistryName();

	void writeHolder(PacketBuffer buf, IModularGuiHolder holder);

	@OnlyIn(Dist.CLIENT)
	IModularGuiHolder readHolder(PacketBuffer buf);
}