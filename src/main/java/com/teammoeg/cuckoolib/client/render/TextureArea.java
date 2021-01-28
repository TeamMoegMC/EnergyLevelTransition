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

package com.teammoeg.cuckoolib.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.ResourceLocation;

public class TextureArea implements IWidgetRenderer {
    private final ResourceLocation location;
    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public TextureArea(ResourceLocation location, float x, float y, float width, float height) {
        this.location = location;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public static TextureArea createFullTexture(ResourceLocation location) {
        return createTexture(location, 0, 0, 1, 1);
    }

    public static TextureArea createTexture(ResourceLocation location, float x, float y, float width, float height) {
        return new TextureArea(location, x, y, width, height);
    }

    public static TextureArea createSubTexture(TextureArea area, float x, float y, float width, float height) {
        return new TextureArea(area.location, area.x + x * area.width, area.y + y * area.height, width * area.width,
                height * area.height);
    }

    @Override
    public void draw(MatrixStack transform, int x, int y, int width, int height) {
        GLUtil.bindTexture(this.location);
        GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x, this.y, this.width, this.height);
    }

    public void drawSubArea(MatrixStack transform, int x, int y, int width, int height, float subX, float subY,
                            float subWidth, float subHeight) {
        GLUtil.bindTexture(this.location);
        GLUtil.drawScaledTexturedRect(transform, x, y, width, height, this.x + x * this.width, this.y + y * this.height,
                width * this.width, height * this.height);
    }
}