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
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchDeskScreen extends ContainerScreen<ResearchDeskContainer> {
    private static final int WIDTH = 252, HEIGHT = 140, CORNER_SIZE = 30;
    private static final int SIDE = 30, TOP = 40, BOTTOM = 30, PADDING = 9;
    private static final float MIN_ZOOM = 1, MAX_ZOOM = 2, ZOOM_STEP = 0.2F;

    private final ResourceLocation WINDOW = new ResourceLocation("minecraft", "textures/gui/advancements/window.png");
    private final ResourceLocation GUI = new ResourceLocation(ELT.MOD_ID, "textures/gui/default_title.png");
    private final ResourceLocation BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png");
    private final ResourceLocation FRAMES = new ResourceLocation(ELT.MOD_ID, "textures/gui/research_frames.png");
    private final PlayerEntity player;

    public ResearchDeskScreen(ResearchDeskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.player = inv.player;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int left = SIDE;
        int top = TOP;
        int right = width - SIDE;
        int bottom = height - SIDE;

        renderWindow(matrixStack, left, top, right, bottom);
        renderResearchContent(matrixStack, left + 10, top + 10);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderResearchExperienceBar(matrixStack);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, SIDE, TOP, 4210752);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    public void renderWindow(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderHelper.setupForFlatItems();
        this.minecraft.getTextureManager().bind(WINDOW);
        // Top left corner
        this.blit(matrixStack, left, top, 0, 0, CORNER_SIZE, CORNER_SIZE);
        // Top side
        renderRepeating(this, matrixStack, left + CORNER_SIZE, top, width - CORNER_SIZE - 2*SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, 0, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Top right corner
        this.blit(matrixStack, right - CORNER_SIZE, top, WIDTH - CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE);
        // Left side
        renderRepeating(this, matrixStack, left, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Right side
        renderRepeating(this, matrixStack, right - CORNER_SIZE, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, WIDTH - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Bottom left corner
        this.blit(matrixStack, left, bottom - CORNER_SIZE, 0, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);
        // Bottom side
        renderRepeating(this, matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, width - CORNER_SIZE - 2*SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Bottom right corner
        this.blit(matrixStack, right - CORNER_SIZE, bottom - CORNER_SIZE, WIDTH - CORNER_SIZE, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);

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

    private static void renderRepeating(AbstractGui abstractGui, MatrixStack matrixStack, int x, int y, int width, int height, int textureX, int textureY, int textureWidth, int textureHeight) {
        for (int i = 0; i < width; i += textureWidth) {
            int drawX = x + i;
            int drawWidth = Math.min(textureWidth, width - i);

            for (int l = 0; l < height; l += textureHeight) {
                int drawY = y + l;
                int drawHeight = Math.min(textureHeight, height - l);
                abstractGui.blit(matrixStack, drawX, drawY, textureX, textureY, drawWidth, drawHeight);
            }
        }
    }
}
