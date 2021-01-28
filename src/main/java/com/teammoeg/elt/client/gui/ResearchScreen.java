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

package com.teammoeg.elt.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.elt.ELT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchScreen extends Screen {
    private final ResourceLocation BG = new ResourceLocation(ELT.MOD_ID, "textures/gui/researchdesk.png");
    private final ResourceLocation BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png");

    private PlayerEntity player;

    // 问题1：需要获取打开这个GUI的玩家的实例
    // 问题2：
    protected ResearchScreen(ITextComponent titleIn, PlayerEntity player) {
        super(titleIn);
        this.player = player;
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderResearchExperienceBar(matrixStack);
    }

    /**
     * @param matrixStack
     * 渲染团队研究经验条
     */
    public void renderResearchExperienceBar(MatrixStack matrixStack) {

        Minecraft mc = Minecraft.getInstance();

        if (this.player != null) {
            mc.getTextureManager().bind(BARS);

            mc.getProfiler().push("research");

            // 重点：获取研究经验值

            int researchExpAmt = 182;

            this.blit(matrixStack, 0, 0, 0, 0, researchExpAmt, 9);

            mc.getProfiler().pop();
        }
    }

}
