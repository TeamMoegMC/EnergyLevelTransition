package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.CuckooLib;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityCodec implements IGuiHolderCodec {
	public static final TileEntityCodec INSTANCE = new TileEntityCodec();

	public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "tile_entity");

	@Override
	public ResourceLocation getRegistryName() {
		return REGISTRY_NAME;
	}

	@Override
	public void writeHolder(PacketBuffer buf, IModularGuiHolder holder) {
		if (holder instanceof TileEntity) {
			buf.writeBlockPos(((TileEntity) holder).getBlockPos());
		} else {
			throw new IllegalArgumentException("The modular gui holder isn't TileEntity");
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public IModularGuiHolder readHolder(PacketBuffer buf) {
		return (IModularGuiHolder) Minecraft.getInstance().level.getBlockEntity(buf.readBlockPos());
	}
}