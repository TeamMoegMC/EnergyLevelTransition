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
import com.mojang.blaze3d.systems.RenderSystem;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.container.ResearchDeskContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchDeskScreen extends ContainerScreen<ResearchDeskContainer> {
    private final ResourceLocation GUI = new ResourceLocation(ELT.MOD_ID, "textures/gui/default_title.png");
    private final ResourceLocation BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png");
    private final ResourceLocation FRAMES = new ResourceLocation(ELT.MOD_ID, "textures/gui/research_frames.png");
    private final PlayerEntity player;

    public ResearchDeskScreen(ResearchDeskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.player = inv.player;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        this.imageWidth = (int) (this.minecraft.getWindow().getWidth() * 0.5);
        this.imageHeight = (int) (this.minecraft.getWindow().getHeight() * 0.5);

//        System.out.println("imageWidth - " + this.imageWidth);
//        System.out.println("imageHeight - " + this.imageHeight);
//        System.out.println("width - " + this.width);
//        System.out.println("height - " + this.height);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
        this.renderResearchExperienceBar(matrixStack);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(GUI);

        // Now this is constant
        int i = (this.minecraft.getWindow().getWidth() - this.imageWidth) / 2 / (int) this.minecraft.getWindow().getGuiScale();
        int j = (this.minecraft.getWindow().getHeight() - this.imageHeight) / 2 / (int) this.minecraft.getWindow().getGuiScale();

        this.blit(matrixStack, i, j, 0, 0, 512, 512, 512, 512);
        renderResearchContent(matrixStack, i, j);
    }

    /**
     * @param matrixStack
     * @param bgX
     * @param bgY
     * 渲染研究内容
     */
    public void renderResearchContent(MatrixStack matrixStack, int bgX, int bgY) {
        this.minecraft.getTextureManager().bind(FRAMES);
        this.blit(matrixStack, bgX+20, bgY+20, 0, 0, 24, 24);
    }

    /**
     * @param matrixStack
     * 渲染团队研究经验条
     */
    public void renderResearchExperienceBar(MatrixStack matrixStack) {
        if (this.player != null) {

            this.minecraft.getTextureManager().bind(BARS);

            this.minecraft.getProfiler().push("research");

            // 重点：获取研究经验值
            int researchExpAmt = 182;

            // 经验条
            this.blit(matrixStack, (this.width - 182) / 2, 3, 0, 0, researchExpAmt, 9);

            // 数值文字
            this.font.draw(matrixStack, ""+researchExpAmt, 88, 5, 0);

            this.minecraft.getProfiler().pop();
        }
    }
}
