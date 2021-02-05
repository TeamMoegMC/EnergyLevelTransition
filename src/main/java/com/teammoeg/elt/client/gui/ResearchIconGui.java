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
import com.teammoeg.elt.research.Research;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ResearchIconGui extends AbstractGui {

    private final ResourceLocation FRAMES = new ResourceLocation("textures/gui/advancements/widgets.png");

    private static final int ICON_OFFSET = 128, ICON_SIZE = 26;

    private final Research research;
    private final Minecraft minecraft;
    protected int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int y;

    public ResearchIconGui(Minecraft mc, Research research, int x, int y) {
        this.research = research;
        this.minecraft = mc;
        this.x = x;
        this.y = y;
    }

    public void draw(MatrixStack matrixStack, int mouseX, int mouseY, int x, int y) {
        if (!this.research.isHidden()) {
            this.minecraft.getTextureManager().bind(FRAMES);
            this.blit(matrixStack, x + this.x + 3, y + this.y, 0, ICON_OFFSET, ICON_SIZE, ICON_SIZE);
            if (isMouseOver(x, y, mouseX, mouseY)) {
                this.minecraft.font.draw(matrixStack, this.research.getName(), x + this.x + 3, y + this.y, -5592406);
            }
            this.minecraft.getItemRenderer().renderAndDecorateFakeItem(new ItemStack(this.research.getIcon()), x + this.x + 8, y + this.y + 5);
        }
    }

    public boolean isMouseOver(int x, int y, int mouseX, int mouseY) {
        if (!this.research.isHidden()) {
            int i = x + this.x + ICON_SIZE;
            int j = i + ICON_SIZE;
            int k = y + this.y + ICON_SIZE;
            int l = k + ICON_SIZE;
            return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
        } else {
            return false;
        }
    }

    public Research getResearch() {
        return research;
    }
}
