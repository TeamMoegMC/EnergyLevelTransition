package com.github.zi_jing.cuckoolib.network;

import com.github.zi_jing.cuckoolib.gui.CapabilityListener;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class MessageCapabilityUpdate implements IMessage {
	private Map<Integer, CompoundNBT> data = new HashMap<Integer, CompoundNBT>();
	private int window;

	public MessageCapabilityUpdate() {

	}

	public MessageCapabilityUpdate(int window, int slot, ItemStack stack) {
		this.window = window;
		CompoundNBT nbt = CapabilityListener.readCapabilityData(stack);
		if (!nbt.isEmpty()) {
			this.data.put(slot, nbt);
		}
	}

	public MessageCapabilityUpdate(int window, NonNullList<ItemStack> items) {
		this.window = window;
		for (int i = 0; i < items.size(); i++) {
			ItemStack stack = items.get(i);
			CompoundNBT nbt = CapabilityListener.readCapabilityData(stack);
			if (!nbt.isEmpty()) {
				data.put(i, nbt);
			}
		}
	}

	public static MessageCapabilityUpdate decode(PacketBuffer buf) {
		MessageCapabilityUpdate msg = new MessageCapabilityUpdate();
		msg.window = buf.readInt();
		int size = buf.readInt();
		for (int i = 0; i < size; i++) {
			int slot = buf.readInt();
			CompoundNBT nbt = buf.readNbt();
			msg.data.put(slot, nbt);
		}
		return msg;
	}

	public boolean hasData() {
		return !this.data.isEmpty();
	}

	@Override
	public void encode(PacketBuffer buf) {
		buf.writeInt(this.window);
		buf.writeInt(this.data.size());
		this.data.forEach((slot, nbt) -> {
			buf.writeInt(slot);
			buf.writeNbt(nbt);
		});
	}

	@Override
	public void process(Supplier<Context> ctx) {
		if (!this.hasData()) {
			return;
		}
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
				this.data.forEach(
						(slot, nbt) -> CapabilityListener.applyCapabilityData(container.getSlot(slot).getItem(), nbt));
			}
		});
	}
}