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
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ResearchDeskScreen extends ContainerScreen<ResearchDeskContainer> {
    private static final int WIDTH = 252, HEIGHT = 140, CORNER_SIZE = 30, INV_WIDTH = WIDTH, INV_HEIGHT = 87;
    private static final int SIDE = 10, TOP = 10, BOTTOM = 87, PADDING = 9;
    private static final float MIN_ZOOM = 1, MAX_ZOOM = 2, ZOOM_STEP = 0.2F;

    private final ResourceLocation WINDOW = new ResourceLocation(ELT.MOD_ID, "textures/gui/vanilla_window.png");
    private final ResourceLocation INVENTORY = new ResourceLocation(ELT.MOD_ID, "textures/gui/window.png");
    private final ResourceLocation BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png");
    private final ResourceLocation FRAMES = new ResourceLocation(ELT.MOD_ID, "textures/gui/research_frames.png");
    private final PlayerEntity player;
    private boolean isScrolling;

    public ResearchDeskScreen(ResearchDeskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.player = inv.player;
    }

    @Override
    protected void init() {
        super.init();
        this.imageWidth = INV_WIDTH;
        this.imageHeight = INV_HEIGHT;
        this.leftPos = (width - INV_WIDTH) / 2;
        this.topPos = height - BOTTOM;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int left = SIDE;
        int top = TOP;
        int right = width - SIDE;
        int bottom = height - BOTTOM;

        this.renderWindow(matrixStack, left, top, right, bottom);
        this.renderResearchExperienceBar(matrixStack, left, top, right, bottom);
        if (this.menu.getSlot(0).hasItem()) {
            this.renderResearchContent(matrixStack, left, top, right, bottom);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {

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

        String windowTitle = I18n.get("elt.gui.research");
        this.font.draw(matrixStack, windowTitle, left + 8, top + 6, 4210752);

        this.minecraft.getTextureManager().bind(INVENTORY);
        this.blit(matrixStack, (width - INV_WIDTH) / 2, bottom, 0, 141, INV_WIDTH, INV_HEIGHT);

    }

    /**
     * 渲染研究内容
     */
    public void renderResearchContent(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        this.minecraft.getTextureManager().bind(FRAMES);
        this.blit(matrixStack, left + 20, top + 20, 0, 0, 24, 24);
        this.blit(matrixStack, left + 20, top + 60, 0, 0, 24, 24);
        this.blit(matrixStack, left + 60, top + 20, 0, 0, 24, 24);
    }

    /**
     * @param matrixStack
     * 渲染团队研究经验条
     */
    public void renderResearchExperienceBar(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        if (this.player != null) {

            this.minecraft.getTextureManager().bind(BARS);

            this.minecraft.getProfiler().push("research");

            // 重点：获取研究经验值
            int researchExpAmt = WIDTH - CORNER_SIZE - CORNER_SIZE;

            // 经验条
            this.blit(matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, 0, 0, WIDTH - CORNER_SIZE * 2, 9);

            // 数值文字
            this.font.draw(matrixStack, "Research Experience: "+researchExpAmt, left + CORNER_SIZE + 8, bottom - CORNER_SIZE - 9, 0);

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

//    @Override
//    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDeltaX, double mouseDeltaY) {
//        int left = SIDE;
//        int top = TOP;
//
//        if (button != 0) {
//            this.isScrolling = false;
//            return false;
//        }
//
//        if (!this.isScrolling) {
//            if (this.advConnectedToMouse == null) {
//                boolean inGui = mouseX < left + width - 2*SIDE - PADDING && mouseX > left + PADDING && mouseY < top + height - TOP + 1 && mouseY > top + 2*PADDING;
//                if (this.selectedTab != null && inGui) {
//                    for (BetterAdvancementEntryGui betterAdvancementEntryGui : this.selectedTab.guis.values()) {
//                        if (betterAdvancementEntryGui.isMouseOver(this.selectedTab.scrollX, this.selectedTab.scrollY, mouseX - left - PADDING, mouseY - top - 2*PADDING)) {
//
//                            if (betterAdvancementEntryGui.betterDisplayInfo.allowDragging())
//                            {
//                                this.advConnectedToMouse = betterAdvancementEntryGui;
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//            else {
//                this.advConnectedToMouse.x = (int)Math.round(this.advConnectedToMouse.x + mouseDeltaX);
//                this.advConnectedToMouse.y = (int)Math.round(this.advConnectedToMouse.y + mouseDeltaY);
//            }
//        }
//        else {
//            if (this.advConnectedToMouse != null) {
//                //Create and post event for the advancement movement
//                final AdvancementMovedEvent event = new AdvancementMovedEvent(advConnectedToMouse);
//                MinecraftForge.EVENT_BUS.post(event);
//            }
//            this.advConnectedToMouse = null;
//        }
//
//        if (this.advConnectedToMouse == null) {
//            if (!this.isScrolling) {
//                this.isScrolling = true;
//            } else if (this.selectedTab != null) {
//                this.selectedTab.scroll(mouseDeltaX , mouseDeltaY, internalWidth - 2 * SIDE - 3 * PADDING, internalHeight - TOP - BOTTOM - 3 * PADDING);
//            }
//        }
//
//        return true;
//    }
}
