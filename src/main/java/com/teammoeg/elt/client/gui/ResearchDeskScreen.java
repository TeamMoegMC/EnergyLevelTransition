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
import com.teammoeg.elt.capability.ELTCapabilities;
import com.teammoeg.elt.container.ResearchDeskContainer;
import com.teammoeg.the_seed.api.IResearchProgress;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;

public class ResearchDeskScreen extends ContainerScreen<ResearchDeskContainer> {
    private static final int WIDTH = 252, HEIGHT = 140, CORNER_SIZE = 30, INV_WIDTH = WIDTH, INV_HEIGHT = 87;
    private static final int SIDE = 10, TOP = 10, BOTTOM = 87, PADDING = 9;
    private static final float MIN_ZOOM = 1, MAX_ZOOM = 2, ZOOM_STEP = 0.2F;

    private final ResourceLocation WINDOW = new ResourceLocation(ELT.MOD_ID, "textures/gui/vanilla_window.png");
    private final ResourceLocation INVENTORY = new ResourceLocation(ELT.MOD_ID, "textures/gui/window.png");
    private final ResourceLocation BARS = new ResourceLocation(ELT.MOD_ID, "textures/gui/bars.png");
    private final ResourceLocation FRAMES = new ResourceLocation("textures/gui/advancements/widgets.png");
    private final ResourceLocation ICON_PIC = new ResourceLocation(ELT.MOD_ID, "textures/item/materialicons/dust.png");
    private final ResourceLocation BG_PIC = new ResourceLocation("textures/block/netherite_block.png");
    private final PlayerEntity player;
    private final ArrayList<ResearchContentGui> widgets = new ArrayList<>();
    private boolean isScrolling;
    private float zoom = MIN_ZOOM;
    private double scrollX, scrollY;
    private boolean centered;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public ResearchDeskScreen(ResearchDeskContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.player = inv.player;
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

    @Override
    protected void init() {
        super.init();
        this.imageWidth = INV_WIDTH;
        this.imageHeight = INV_HEIGHT;
        this.leftPos = (width - INV_WIDTH) / 2;
        this.topPos = height - BOTTOM;

        // 添加研究图标
        for (int i = 1; i <= 20; i++)
            for (int j = 0; j < 20; j++) {
                addWidget(new ResearchContentGui(this.minecraft, ELT.WEAPON_RESEARCH, "Weapon Research 1", SIDE + 40 * i, TOP + 30 * i));
            }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        int left = SIDE;
        int top = TOP;
        int right = width - SIDE;
        int bottom = height - BOTTOM;

        int scrollRangeX = width - 2 * SIDE - 2 * PADDING;
        int scrollRangeY = height - TOP - BOTTOM - 2 * PADDING;

        int i = (this.width - scrollRangeX) / 2;
        int j = (this.height - scrollRangeY) / 2;

        this.renderWindow(matrixStack, left, top, right, bottom);
        if (this.menu.getSlot(0).hasItem()) {
            this.renderInside(matrixStack, mouseX, mouseY, i, j);
        }
        this.renderResearchXpBar(matrixStack, left, top, right, bottom);

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        int wheel = (int) scroll;
        if (wheel < 0 && zoom > MIN_ZOOM) {
            zoom -= ZOOM_STEP;
        } else if (wheel > 0 && zoom < MAX_ZOOM) {
            zoom += ZOOM_STEP;
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double mouseDeltaX, double mouseDeltaY) {
        int left = SIDE;
        int top = TOP;
        boolean inGui = mouseX < left + width - 2 * SIDE - PADDING && mouseX > left + PADDING && mouseY < top + height - TOP + 1 && mouseY > top + 2 * PADDING;

        if (button != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else if (inGui) {
                this.scroll(mouseDeltaX, mouseDeltaY);
            }
            return true;
        }
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {

    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {

    }

    /**
     * 渲染研究窗口的框，以及背包，和标题
     *
     * @param matrixStack
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void renderWindow(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        RenderHelper.setupForFlatItems();
        this.minecraft.getTextureManager().bind(WINDOW);

        // 研究界面边框
        // Top left corner
        this.blit(matrixStack, left, top, 0, 0, CORNER_SIZE, CORNER_SIZE);
        // Top side
        renderRepeating(this, matrixStack, left + CORNER_SIZE, top, width - CORNER_SIZE - 2 * SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, 0, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Top right corner
        this.blit(matrixStack, right - CORNER_SIZE, top, WIDTH - CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE);
        // Left side
        renderRepeating(this, matrixStack, left, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, 0, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Right side
        renderRepeating(this, matrixStack, right - CORNER_SIZE, top + CORNER_SIZE, CORNER_SIZE, bottom - top - 2 * CORNER_SIZE, WIDTH - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE - CORNER_SIZE);
        // Bottom left corner
        this.blit(matrixStack, left, bottom - CORNER_SIZE, 0, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);
        // Bottom side
        renderRepeating(this, matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, width - CORNER_SIZE - 2 * SIDE - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE, HEIGHT - CORNER_SIZE, WIDTH - CORNER_SIZE - CORNER_SIZE, CORNER_SIZE);
        // Bottom right corner
        this.blit(matrixStack, right - CORNER_SIZE, bottom - CORNER_SIZE, WIDTH - CORNER_SIZE, HEIGHT - CORNER_SIZE, CORNER_SIZE, CORNER_SIZE);

        // 标题
        String windowTitle = I18n.get("elt.gui.research");
        this.font.draw(matrixStack, windowTitle, left + 8, top + 6, 4210752);

        // 玩家背包界面
        this.minecraft.getTextureManager().bind(INVENTORY);
        this.blit(matrixStack, (width - INV_WIDTH) / 2, bottom, 0, 141, INV_WIDTH, INV_HEIGHT);

    }

    /**
     * 渲染框里面的东西
     */
    private void renderInside(MatrixStack matrixStack, int mouseX, int mouseY, int offsetX, int offsetY) {
        RenderSystem.pushMatrix();
        /// 这似乎是把坐标系迁移
        RenderSystem.translatef(18.0f, 28.0f, 0.0F);
        this.drawContents(matrixStack);
        RenderSystem.popMatrix();
        RenderSystem.depthFunc(515);
        RenderSystem.disableDepthTest();
    }

    /**
     * 渲染内部背景，研究图标，研究连线等内容。
     *
     * @param matrixStack
     */
    public void drawContents(MatrixStack matrixStack) {
        // calculations
        int scrollRangeX = width - 2 * SIDE - 2 * PADDING + 2;
        int scrollRangeY = height - TOP - BOTTOM - 2 * PADDING - 6;

        if (!this.centered) {
            this.scrollX = (double) (scrollRangeX / 2 - (this.maxX + this.minX) / 2);
            this.scrollY = (double) (scrollRangeY / 2 - (this.maxY + this.minY) / 2);
            this.centered = true;
        }

        int deltaX = MathHelper.floor(this.scrollX);
        int deltaY = MathHelper.floor(this.scrollY);
        int hexReducedDeltaX = deltaX % 16;
        int hexReducedDeltaY = deltaY % 16;

        // render borders and color masks
        RenderSystem.pushMatrix();
        RenderSystem.enableDepthTest();
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.depthFunc(518);
        fill(matrixStack, scrollRangeX, scrollRangeY, 0, 0, -16777216);
        RenderSystem.depthFunc(515);

        // bind texture of background
        ResourceLocation resourcelocation = BG_PIC; // TODO: this should be a get function from the gui content
        if (resourcelocation != null) {
            this.minecraft.getTextureManager().bind(resourcelocation);
        } else {
            this.minecraft.getTextureManager().bind(TextureManager.INTENTIONAL_MISSING_TEXTURE);
        }

        // draw background from 16 x 16 texture
        for (int i1 = -1; i1 <= scrollRangeX / 16 + 1; ++i1) {
            for (int j1 = -1; j1 <= scrollRangeY / 16 + 1; ++j1) {
                blit(matrixStack, hexReducedDeltaX + 16 * i1, hexReducedDeltaY + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        // bind texture of frames
        this.minecraft.getTextureManager().bind(FRAMES);

        // draw frames
        for (ResearchContentGui researchContentGui : widgets) {
            researchContentGui.draw(matrixStack, deltaX, deltaY);
        }

        // TODO: draw connectivity
//        this.root.drawConnectivity(matrixStack, i, j, true);
//        this.root.drawConnectivity(matrixStack, i, j, false);

        RenderSystem.depthFunc(518);
        RenderSystem.translatef(0.0F, 0.0F, -950.0F);
        RenderSystem.colorMask(false, false, false, false);
        fill(matrixStack, 4680, 2260, -4680, -2260, -16777216);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.translatef(0.0F, 0.0F, 950.0F);
        RenderSystem.depthFunc(515);
        RenderSystem.popMatrix();
    }

    /**
     * 渲染研究经验条
     */
    public void renderResearchXpBar(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        if (this.player != null) {
            this.minecraft.getTextureManager().bind(BARS);
            this.minecraft.getProfiler().push("research");

            // 重点：获取研究经验值
            LazyOptional<IResearchProgress> Cap = player.getCapability(ELTCapabilities.RESEARCHPROGRESS);
            Cap.ifPresent((P) -> {
                int researchExpAmt = P.getResearchExperience();
                // 经验条
                this.blit(matrixStack, left + CORNER_SIZE, bottom - CORNER_SIZE, 0, 0, researchExpAmt, 9);
                // 数值文字
                this.font.draw(matrixStack, "Research Experience: " + researchExpAmt, left + CORNER_SIZE + 8, bottom - CORNER_SIZE - 9, 0);
            });

            this.minecraft.getProfiler().pop();
        }
    }

    /**
     * 鼠标拖动时调用
     *
     * @param dragX
     * @param dragY
     */
    public void scroll(double dragX, double dragY) {

        int scrollRangeX = width - 2 * SIDE - 2 * PADDING;
        int scrollRangeY = height - TOP - BOTTOM - 2 * PADDING;

        if (this.maxX - this.minX > scrollRangeX) {
            this.scrollX = MathHelper.clamp(this.scrollX + dragX, (double) (-(this.maxX - scrollRangeX)), 0.0D);
        }

        if (this.maxY - this.minY > scrollRangeY) {
            this.scrollY = MathHelper.clamp(this.scrollY + dragY, (double) (-(this.maxY - scrollRangeY)), 0.0D);
        }

    }

    /**
     * 添加一个研究图标
     *
     * @param gui
     */
    private void addWidget(ResearchContentGui gui) {
        this.widgets.add(gui);
        int i = gui.getX();
        int j = i + 28;
        int k = gui.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);
    }

}
