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
import com.teammoeg.elt.research.ELTResearches;
import com.teammoeg.elt.research.ResearchLine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;

public class ResearchLineList extends ExtendedList<ResearchLineList.Entry> {

    private ResearchDeskScreen parent;

    public ResearchLineList(ResearchDeskScreen parent, Minecraft mc, int width, int height, int topPos, int bottomPos, int itemHeightIn) {
        super(mc, width, height, topPos, bottomPos, itemHeightIn);
        this.parent = parent;
        this.setRenderBackground(false);
        this.setRenderHeader(false, 0);
        this.setRenderTopAndBottom(false);
        this.addEntry(new ResearchLineList.Entry(ELTResearches.PALEOLITHIC_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.NEOLITHIC_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.COPPER_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.BRONZE_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.IRON_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.STEEL_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.STEEL_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.STEEL_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.STEEL_AGE));
        this.addEntry(new ResearchLineList.Entry(ELTResearches.STEEL_AGE));
    }

    @Override
    public int getRowWidth() {
        return 68;
    }

    @Override
    protected int getScrollbarPosition() {
        return 68;
    }

    public class Entry extends ExtendedList.AbstractListEntry<ResearchLineList.Entry> {

        private final ResearchLine researchLine;

        public Entry(ResearchLine researchLine) {
            this.researchLine = researchLine;
        }

        @Override
        public void render(MatrixStack mStack, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean p_194999_5_, float partialTicks) {
            FontRenderer font = ResearchLineList.this.parent.getFontRenderer();
            ITextComponent s = this.researchLine.getName();
            font.draw(mStack, LanguageMap.getInstance().getVisualOrder(ITextProperties.composite(font.substrByWidth(s, 68))), left + 3, top + 2, 0xFFFFFF);

//            font.drawShadow(mStack, s, (float)(ResearchLineList.this.width / 2 - font.width(s) / 2), (float)(top + 1), 16777215);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int buttonIn) {
            if (buttonIn == 0) {
//                this.select();
                return true;
            } else {
                return false;
            }
        }
    }
}
