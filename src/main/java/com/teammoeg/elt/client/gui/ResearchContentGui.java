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
import net.minecraft.util.ResourceLocation;

public class ResearchContentGui extends AbstractGui {

    private final ResourceLocation FRAMES = new ResourceLocation("textures/gui/advancements/widgets.png");

    protected static final int ADVANCEMENT_SIZE = 26;
    private static final int CORNER_SIZE = 10;
    private static final int WIDGET_WIDTH = 256, WIDGET_HEIGHT = 26, TITLE_SIZE = 32, ICON_OFFSET = 128, ICON_SIZE = 26;

    private final Research research;
    private final Minecraft minecraft;
    private final String title;
    protected int x;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    protected int y;
    private final int screenScale;
    private int width;

    public ResearchContentGui(Minecraft mc, Research research, String title, int x, int y) {
        this.research = research;
        this.minecraft = mc;
        this.title = title;
        this.x = x;
        this.y = y;
        this.screenScale = mc.getWindow().calculateScale(0, false);
    }

    public void draw(MatrixStack matrixStack, int x, int y) {
        if (true) {
//            float f = this.progress == null ? 0.0F : this.progress.getPercent();
            this.minecraft.getTextureManager().bind(FRAMES);
            this.blit(matrixStack, x + this.x + 3, y + this.y, 0, 130, 24, 24);
        }
    }
}
