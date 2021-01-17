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

package com.teammoeg.cuckoolib.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.cuckoolib.gui.ModularContainer;
import com.teammoeg.cuckoolib.gui.ModularGuiInfo;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

public class ModularScreen extends ContainerScreen<ModularContainer> {
	protected ModularGuiInfo guiInfo;

	public ModularScreen(int window, ModularGuiInfo guiInfo, PlayerInventory inv, ITextComponent title) {
		super(new ModularContainer(null, window, guiInfo), inv, title);
		this.guiInfo = guiInfo;
	}

	public ModularGuiInfo getGuiInfo() {
		return this.guiInfo;
	}

	public ModularContainer getMenu() {
		return this.menu;
	}

	@Override
	public void init() {
		this.imageWidth = this.guiInfo.getWidth();
		this.imageHeight = this.guiInfo.getHeight();
		super.init();
	}

	@Override
	public void render(MatrixStack transform, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(transform);
		this.guiInfo.getBackground().draw(transform, this.leftPos, this.topPos, this.imageWidth, this.imageHeight);
		super.render(transform, mouseX, mouseY, partialTicks);
		this.renderTooltip(transform, mouseX, mouseY);
		this.guiInfo.handleMouseHovered(mouseX, mouseY);
	}

	@Override
	protected void renderLabels(MatrixStack transform, int mouseX, int mouseY) {
		this.guiInfo.drawInForeground(transform, mouseX - this.leftPos, mouseY - this.topPos, this.leftPos,
				this.topPos);
	}

	@Override
	protected void renderBg(MatrixStack transform, float partialTicks, int mouseX, int mouseY) {
		this.guiInfo.drawInBackground(transform, partialTicks, mouseX - this.leftPos, mouseY - this.topPos,
				this.leftPos, this.topPos);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		super.mouseClicked(mouseX, mouseY, button);
		return this.guiInfo.handleMouseClicked(mouseX - this.leftPos, mouseY - this.topPos, button);
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		super.mouseReleased(mouseX, mouseY, button);
		return this.guiInfo.handleMouseReleased(mouseX - this.leftPos, mouseY - this.topPos, button);
	}

	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int mouseButton, double dragX, double dragY) {
		super.mouseDragged(mouseX, mouseY, mouseButton, dragX, dragY);
		return this.guiInfo.handleMouseClickMove(mouseX - this.leftPos, mouseY - this.topPos, mouseButton, dragX,
				dragY);
	}
}