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
    private final ResourceLocation FRAMES = new ResourceLocation(ELT.MOD_ID, "textures/gui/research_frames.png");
    private final PlayerEntity player;

    private boolean isScrolling;
    private float zoom = MIN_ZOOM;
    private double scrollX, scrollY;
    private boolean centered;

    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    private final ArrayList<ResearchContentGui> widgets = new ArrayList<>();

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

        for (int i = 1; i <= 20; i++) {
            addWidget(new ResearchContentGui(this.minecraft, ELT.WEAPON_RESEARCH, "Weapon Research 1", SIDE + 40 * i, TOP + 30 * 1));
        }

        for (int i = 1; i <= 20; i++) {
            addWidget(new ResearchContentGui(this.minecraft, ELT.WEAPON_RESEARCH, "Weapon Research 1", SIDE + 40 * 1, TOP + 30 * i));
        }

        for (int i = 2; i <= 20; i++) {
            addWidget(new ResearchContentGui(this.minecraft, ELT.WEAPON_RESEARCH, "Weapon Research 1", SIDE + 40 * i, TOP + 30 * i));
        }

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

    public void renderResearchContent(MatrixStack matrixStack, int left, int top, int right, int bottom) {
        int scrollRangeX = width - 2 * SIDE - 2 * PADDING;
        int scrollRangeY = height - TOP - BOTTOM - 2*PADDING;

        if (!this.centered) {
            this.scrollX = (double)(scrollRangeX / 2 - (this.maxX + this.minX) / 2);
            this.scrollY = (double)(scrollRangeY / 2 - (this.maxY + this.minY) / 2);
            this.centered = true;
        }

        int i = MathHelper.floor(this.scrollX);
        int j = MathHelper.floor(this.scrollY);

        this.minecraft.getTextureManager().bind(FRAMES);
        for (ResearchContentGui researchContentGui : widgets) {
            researchContentGui.draw(matrixStack, i, j);
        }
    }

    public void renderResearchExperienceBar(MatrixStack matrixStack, int left, int top, int right, int bottom) {
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

    public void scroll(double dragX, double dragY) {

        int scrollRangeX = width - 2 * SIDE - 2 * PADDING;
        int scrollRangeY = height - TOP - BOTTOM - 2*PADDING;

        if (this.maxX - this.minX > scrollRangeX) {
            this.scrollX = MathHelper.clamp(this.scrollX + dragX, (double)(-(this.maxX - scrollRangeX)), 0.0D);
        }

        if (this.maxY - this.minY > scrollRangeY) {
            this.scrollY = MathHelper.clamp(this.scrollY + dragY, (double)(-(this.maxY - scrollRangeY)), 0.0D);
        }

    }

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

//        for(ResearchContentGui researchContentGui : this.widgets) {
//            advancemententrygui.attachToParent();
//        }

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
