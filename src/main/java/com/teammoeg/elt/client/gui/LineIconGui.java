/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.elt.research.ResearchLine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class LineIconGui extends AbstractGui {
    private static final int CORNER_SIZE = 10;
    private static final int WIDGET_WIDTH = 256, WIDGET_HEIGHT = 26, TITLE_SIZE = 32, ICON_OFFSET = 128, ICON_SIZE = 26;
    private final ResourceLocation FRAMES = new ResourceLocation("textures/gui/advancements/widgets.png");
    private final Minecraft minecraft;
    private final ResearchLine researchLine;
    protected int x;
    protected int y;

    public LineIconGui(Minecraft mc, ResearchLine researchLine, int x, int y) {
        this.minecraft = mc;
        this.researchLine = researchLine;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void draw(MatrixStack matrixStack, int x, int y) {
        this.minecraft.getTextureManager().bind(FRAMES);
        this.blit(matrixStack, x + this.x + 3, y + this.y, 0, ICON_OFFSET, ICON_SIZE, ICON_SIZE);
        this.minecraft.getItemRenderer().renderAndDecorateFakeItem(new ItemStack(this.researchLine.getIcon()), x + this.x + 8, y + this.y + 5);
    }

    public boolean isMouseOver(int x, int y, double mouseX, double mouseY) {
        int i = x + this.x;
        int j = i + ICON_SIZE;
        int k = y + this.y;
        int l = k + ICON_SIZE;
        return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
    }

    public ResearchLine getResearchLine() {
        return researchLine;
    }
}
