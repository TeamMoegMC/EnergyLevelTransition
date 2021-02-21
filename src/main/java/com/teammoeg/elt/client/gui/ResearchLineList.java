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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.AbstractList;

public class ResearchLineList extends AbstractList<ResearchLineList.Entry> {

    public ResearchLineList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int itemHeightIn) {
        super(mcIn, widthIn, heightIn, topIn, bottomIn, itemHeightIn);
    }

    static class Entry extends AbstractList.AbstractListEntry<ResearchLineList.Entry> {

        @Override
        public void render(MatrixStack matrixStack_, int int_, int int1_, int int2_, int int3_, int int4_, int int5_, int int6_, boolean boolean_, float float_) {

        }
    }
}
