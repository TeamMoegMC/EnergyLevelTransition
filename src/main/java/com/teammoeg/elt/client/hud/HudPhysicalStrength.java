package com.teammoeg.elt.client.hud;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.capability.ELTCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class HudPhysicalStrength extends AbstractGui {
    private final int width;
    private final int height;
    private final Minecraft minecraft;
    private final ResourceLocation HUD = new ResourceLocation(ELT.MOD_ID, "textures/gui/physicalstrength.png");

    public HudPhysicalStrength() {
        this.width = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        this.height = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        this.minecraft = Minecraft.getInstance();
    }

    public void render(MatrixStack matrixStack_) {
        minecraft.player.getCapability(ELTCapabilities.fightCapability).ifPresent(cap -> {
            this.minecraft.getTextureManager().bind(HUD);
            blit(matrixStack_, width / 2 + 10, height - 48, 0, 0, cap.getPhysicalStrength(), 8);
        });
    }
}
