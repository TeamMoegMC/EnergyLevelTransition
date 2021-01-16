package com.github.zi_jing.cuckoolib.gui;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.gui.widget.ISlotWidget;
import com.github.zi_jing.cuckoolib.network.MessageGuiToClient;
import com.github.zi_jing.cuckoolib.network.MessageGuiToServer;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.network.NetworkDirection;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;

public class ModularContainer extends Container implements ISyncedWidgetList {
	protected ModularGuiInfo guiInfo;
	protected Map<ISlotWidget, Slot> slotMap;
	protected Deque<Pair<Long, Vector2i>> mouseHoveredData;
	protected List<PacketBuffer> blockedData;
	protected boolean isDataBlocked;

	public ModularContainer(ContainerType type, int id, ModularGuiInfo info) {
		super(type, id);
		this.guiInfo = info;
		info.container = this;
		this.slotMap = new HashMap<ISlotWidget, Slot>();
		this.mouseHoveredData = new LinkedList<Pair<Long, Vector2i>>();
		this.blockedData = new ArrayList<PacketBuffer>();
		this.isDataBlocked = false;
		info.getWidgets().forEach((widget) -> {
			widget.setWidgetList(this);
			if (widget instanceof ISlotWidget && widget.isEnable()) {
				Slot slot = ((ISlotWidget) widget).getSlot();
				this.slotMap.put((ISlotWidget) widget, slot);
				this.addSlot(slot);
				((ISlotWidget) widget).setSlotCount(slot.index);
			}
		});
		info.onGuiOpen();
	}

	public static List<IContainerListener> getContainerListeners(Container container) {
		try {
			return (List<IContainerListener>) ObfuscationReflectionHelper.findField(Container.class, "containerListeners")
					.get(container);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ModularGuiInfo getGuiInfo() {
		return this.guiInfo;
	}

	public void setDataBlocked(boolean blocked) {
		this.isDataBlocked = blocked;
	}

	public List<PacketBuffer> getBlockedData() {
		return this.blockedData;
	}

	public void clearBlockedData() {
		this.blockedData.clear();
	}

	public void addMouseHoveredData(int mouseX, int mouseY) {
		this.addMouseHoveredData(mouseX, mouseY, System.currentTimeMillis());
	}

	public void addMouseHoveredData(int mouseX, int mouseY, long time) {
		while (!this.mouseHoveredData.isEmpty() && this.mouseHoveredData.getFirst().getLeft() < time - 10000) {
			this.mouseHoveredData.removeFirst();
		}
		this.mouseHoveredData.addLast(Pair.of(time, Vector2i.of(mouseX, mouseY)));
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return true;
	}

	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		for (int i = 0; i < this.slots.size(); i++) {
			ItemStack stack = this.slots.get(i).getItem();
			if (CapabilityListener.shouldSync(stack)) {
				List<IContainerListener> listListener = getContainerListeners(this);
				for (IContainerListener listener : listListener) {
					if (listener instanceof CapabilityListener) {
						listener.slotChanged(this, i, stack);
					}
				}
			}
		}
		this.guiInfo.widgets.forEach((id, widget) -> widget.detectAndSendChanges());
	}

	@Override
	public void writeToClient(int widgetId, Consumer<PacketBuffer> data) {
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeInt(widgetId);
		data.accept(buffer);
		if (this.isDataBlocked) {
			this.blockedData.add(buffer);
		} else {
			CuckooLib.CHANNEL.sendTo(new MessageGuiToClient(buffer, this.containerId),
					((ServerPlayerEntity) (this.guiInfo.getPlayer())).connection.getConnection(),
					NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	@Override
	public void writeToServer(int widgetId, Consumer<PacketBuffer> data) {
		PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
		buffer.writeInt(widgetId);
		data.accept(buffer);
		CuckooLib.CHANNEL.sendToServer(new MessageGuiToServer(buffer, this.containerId));
	}

	@Override
	public void notifySlotChange(ISlotWidget widget, boolean isEnable) {
		if (isEnable && !this.slotMap.containsKey(widget)) {
			Slot slotAdd = widget.getSlot();
			this.slotMap.put((ISlotWidget) widget, slotAdd);
			OptionalInt optional = this.slots.stream().filter((slot) -> slot instanceof EmptySlot)
					.mapToInt((slot) -> slot.index).findFirst();
			if (optional.isPresent()) {
				int idx = optional.getAsInt();
				slotAdd.index = idx;
				this.slots.set(idx, slotAdd);
				this.setInventoryItemStacks(idx, ItemStack.EMPTY);
				widget.setSlotCount(idx);
			} else {
				this.addSlot(slotAdd);
				widget.setSlotCount(slotAdd.index);
			}
		} else if (!isEnable && this.slotMap.containsKey(widget)) {
			Slot slotRemove = widget.getSlot();
			this.slotMap.remove(widget);
			EmptySlot emptySlot = new EmptySlot();
			emptySlot.index = slotRemove.index;
			this.slots.set(slotRemove.index, emptySlot);
			this.setInventoryItemStacks(slotRemove.index, ItemStack.EMPTY);
		}
	}

	protected void setInventoryItemStacks(int slotNumber, ItemStack stack) {
		Field fieldItemStacks;
		try {
			fieldItemStacks = Arrays.stream(Container.class.getDeclaredFields())
					.filter(field -> field.getType() == NonNullList.class).findFirst()
					.orElseThrow(ReflectiveOperationException::new);
			fieldItemStacks.setAccessible(true);
			NonNullList itemStacks = (NonNullList) fieldItemStacks.get(this);
			itemStacks.set(slotNumber, stack);
		} catch (SecurityException | ReflectiveOperationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ItemStack notifyTransferStack(ItemStack stack, boolean simulate) {
		return stack;
	}

	public static class EmptySlot extends Slot {
		private static final IInventory EMPTY_INVENTORY = new Inventory(0);

		public EmptySlot() {
			super(EMPTY_INVENTORY, 0, -0, -0);
		}

		@Override
		public ItemStack getItem() {
			return ItemStack.EMPTY;
		}

		@Override
		public void set(ItemStack stack) {

		}

		@Override
		public boolean mayPlace(ItemStack stack) {
			return false;
		}

		@Override
		public boolean mayPickup(PlayerEntity playerIn) {
			return false;
		}

		@Override
		public boolean isActive() {
			return false;
		}
	}
}