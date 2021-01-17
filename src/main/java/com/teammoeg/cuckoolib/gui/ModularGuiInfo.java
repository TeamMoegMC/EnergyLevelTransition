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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.cuckoolib.CuckooLib;
import com.teammoeg.cuckoolib.client.gui.ModularScreen;
import com.teammoeg.cuckoolib.client.render.IWidgetRenderer;
import com.teammoeg.cuckoolib.gui.widget.IWidget;
import com.teammoeg.cuckoolib.gui.widget.SlotWidget;
import com.teammoeg.cuckoolib.network.MessageModularGuiOpen;
import com.teammoeg.cuckoolib.util.data.UnorderedRegistry;
import com.teammoeg.cuckoolib.util.math.Vector2i;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ModularGuiInfo {
	public static final UnorderedRegistry<ResourceLocation, IGuiHolderCodec> REGISTRY = new UnorderedRegistry<ResourceLocation, IGuiHolderCodec>();

	protected ModularContainer container;
	protected PlayerEntity player;
	protected Vector2i size;
	protected IWidgetRenderer background;
	protected Map<Integer, IWidget> widgets;
	protected List<Consumer<ModularContainer>> openListeners, closeListeners;

	public ModularGuiInfo(Vector2i size, IWidgetRenderer background, Map<Integer, IWidget> widgets,
			List<Consumer<ModularContainer>> openListeners, List<Consumer<ModularContainer>> closeListeners,
			PlayerEntity player) {
		this.size = size;
		this.background = background;
		this.widgets = widgets;
		this.openListeners = openListeners;
		this.closeListeners = closeListeners;
		this.player = player;
	}

	public static void openModularGui(IModularGuiHolder holder, ServerPlayerEntity player) {
		if (player.level.isClientSide) {
			return;
		}
		if (player instanceof FakePlayer) {
			throw new IllegalArgumentException("The player of the gui info can't be fake");
		}
		IGuiHolderCodec codec = holder.getCodec();
		if (!REGISTRY.containsValue(codec)) {
			throw new IllegalArgumentException("The gui holder codec is unregistered");
		}
		player.doCloseContainer();
		player.nextContainerCounter();
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.initWidgets();
		ModularContainer container = new ModularContainer(null, player.containerCounter, guiInfo);
		container.setDataBlocked(true);
		container.broadcastChanges();
		List<PacketBuffer> updateData = new ArrayList<PacketBuffer>(container.getBlockedData());
		container.clearBlockedData();
		container.setDataBlocked(false);
		PacketBuffer holderBuf = new PacketBuffer(Unpooled.buffer());
		holderBuf.writeResourceLocation(codec.getRegistryName());
		codec.writeHolder(holderBuf, holder);
		CuckooLib.CHANNEL.sendTo(new MessageModularGuiOpen(holderBuf, updateData, player.containerCounter),
				player.connection.getConnection(), NetworkDirection.PLAY_TO_CLIENT);
		player.containerMenu = container;
		player.containerMenu.addSlotListener(player);
		MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, container));
	}

	@OnlyIn(Dist.CLIENT)
	public static void openClientModularGui(int window, IModularGuiHolder holder, List<PacketBuffer> updateData) {
		Minecraft minecraft = Minecraft.getInstance();
		ClientPlayerEntity player = minecraft.player;
		ModularGuiInfo guiInfo = holder.createGuiInfo(player);
		guiInfo.initWidgets();
		ModularScreen screen = new ModularScreen(window, guiInfo, player.inventory, new StringTextComponent("Test"));
		updateData.forEach((data) -> {
			IWidget widget = guiInfo.container.getGuiInfo().getWidget(data.readInt());
			widget.receiveMessageFromServer(data);
		});
		player.containerMenu = guiInfo.container;
		Minecraft.getInstance().setScreen(screen);
	}

	public static void registerGuiHolderCodec(IGuiHolderCodec codec) {
		REGISTRY.register(codec.getRegistryName(), codec);
	}

	public IWidgetRenderer getBackground() {
		return this.background;
	}

	public void setContainer(ModularContainer container) {
		this.container = container;
	}

	public ModularContainer getContainer() {
		return this.container;
	}

	public PlayerEntity getPlayer() {
		return this.player;
	}

	public int getWidth() {
		return this.size.getX();
	}

	public int getHeight() {
		return this.size.getY();
	}

	public IWidget getWidget(int id) {
		return this.widgets.get(id);
	}

	public List<IWidget> getWidgets() {
		List<IWidget> list = new ArrayList<IWidget>();
		this.widgets.forEach((id, widget) -> list.add(widget));
		return list;
	}

	public void onGuiOpen() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	public void onGuiClosed() {
		this.openListeners.forEach((listener) -> listener.accept(this.container));
	}

	@OnlyIn(Dist.CLIENT)
	public void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft,
			int guiTop) {
		this.widgets.forEach(
				(id, widget) -> widget.drawInBackground(transform, partialTicks, mouseX, mouseY, guiLeft, guiTop));
	}

	@OnlyIn(Dist.CLIENT)
	public void drawInForeground(MatrixStack transform, int mouseX, int mouseY, int guiLeft, int guiTop) {
		this.widgets.forEach((id, widget) -> widget.drawInForeground(transform, mouseX, mouseY, guiLeft, guiTop));
	}

	public void handleMouseHovered(int mouseX, int mouseY) {
		this.container.addMouseHoveredData(mouseX, mouseY);
		this.widgets.forEach((id, widget) -> {
			if (widget.isInRange(mouseX, mouseY)) {
				widget.onMouseHovered(mouseX, mouseY);
			}
		});
	}

	public boolean handleMouseClicked(double mouseX, double mouseY, int button) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isInRange(mouseX, mouseY)) {
				widget.onMouseClicked(mouseX, mouseY, button);
			}
		});
		return false;
	}

	public boolean handleMouseReleased(double mouseX, double mouseY, int button) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isInRange(mouseX, mouseY)) {
				widget.onMouseReleased(mouseX, mouseY, button);
			}
		});
		return false;
	}

	public boolean handleMouseClickMove(double mouseX, double mouseY, int button, double dragX, double dragY) {
		this.widgets.forEach((id, widget) -> {
			if (widget.isInRange(mouseX, mouseY)) {
				widget.onMouseClickMove(mouseX, mouseY, button, dragX, dragY);
			}
		});
		return false;
	}

	public void initWidgets() {
		this.widgets.forEach((id, widget) -> {
			widget.setGuiInfo(this);
			widget.initWidget();
		});
	}

	public static Builder builder() {
		return new Builder(176, 166);
	}

	public static Builder builder(int width, int height) {
		return new Builder(width, height);
	}

	public static class Builder {
		private Vector2i size;
		private IWidgetRenderer background;
		private Map<Integer, IWidget> widgets;
		private List<Consumer<ModularContainer>> openListeners, closeListeners;

		public Builder(int width, int height) {
			this.size = new Vector2i(width, height);
			this.widgets = new HashMap<Integer, IWidget>();
			this.openListeners = new ArrayList<Consumer<ModularContainer>>();
			this.closeListeners = new ArrayList<Consumer<ModularContainer>>();
		}

		public Builder setBackground(IWidgetRenderer background) {
			this.background = background;
			return this;
		}

		public Builder addWidget(IWidget widget) {
			int id = this.widgets.size();
			this.widgets.put(id, widget);
			widget.setWidgetId(id);
			return this;
		}

		public Builder addPlayerInventory(PlayerInventory inventory, int x, int y) {
			for (int i = 0; i < 9; i++) {
				this.addWidget(new SlotWidget(x + i * 18, y + 58, new PlayerMainInvWrapper(inventory), i));
			}
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 9; j++) {
					this.addWidget(new SlotWidget(x + j * 18, y + i * 18, new PlayerMainInvWrapper(inventory),
							(i + 1) * 9 + j));
				}
			}
			return this;
		}

		public Builder addOpenListener(Consumer<ModularContainer> listener) {
			this.openListeners.add(listener);
			return this;
		}

		public Builder addCloseListener(Consumer<ModularContainer> listener) {
			this.closeListeners.add(listener);
			return this;
		}

		public ModularGuiInfo build(PlayerEntity player) {
			if (player instanceof FakePlayer) {
				throw new IllegalArgumentException("The player of the gui info can't be fake");
			}
			return new ModularGuiInfo(this.size, this.background, this.widgets, this.openListeners, this.closeListeners,
					player);
		}
	}
}