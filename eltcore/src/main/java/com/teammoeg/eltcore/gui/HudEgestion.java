/*
 * Copyright (c) 2020. TeamMoeg
 *
 * This file is part of Energy Level Transition.
 *
 * Energy Level Transition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Energy Level Transition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.eltcore.gui;

import com.teammoeg.eltcore.component.IComponentPlayerEgestion;
import com.teammoeg.eltcore.handlers.Handler_Components;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class HudEgestion extends DrawableHelper {

    private final MinecraftClient client;
    private int scaledWidth;
    private int scaledHeight;

    public HudEgestion(MinecraftClient client) {
        this.client = client;
    }

    public void render(MatrixStack matrixStack) {
        this.scaledWidth = this.client.getWindow().getScaledWidth();
        this.scaledHeight = this.client.getWindow().getScaledHeight();
        assert this.client.interactionManager != null;
        if (this.client.interactionManager.hasStatusBars()) {
            this.renderEgestionBar(matrixStack);
        }
    }

    public void renderEgestionBar(MatrixStack matrixStack) {

        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            this.client.getTextureManager().bindTexture(new Identifier("eltcore:textures/gui/icons/player_status/egestion.png"));
            this.client.getProfiler().push("shit");
            int barWidth = this.scaledWidth / 2 - 91;
            int barHeight = this.scaledHeight - 39;
            float general_max_health = (float) playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
            int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
            int q = MathHelper.ceil((general_max_health + (float) p) / 2.0F / 10.0F);
            IComponentPlayerEgestion iComponentPlayerEgestion = Handler_Components.PLAYER_EGESTION_COMPONENT_TYPE.get(playerEntity);
            int egestionLevel = (int) iComponentPlayerEgestion.getEgestionLevel();

            int r = Math.max(10 - (q - 2), 3);
            int s = barHeight - (q - 1) * r - 10 - 10;

            int z;
            int aa;
            for (z = 0; z < 10; ++z) {
                aa = barWidth + z * 8;
                if (egestionLevel == 0) {
                    this.drawTexture(matrixStack, aa, s, 18, 0, 9, 9);
                }
                if (egestionLevel > 0) {
                    if (z * 2 + 1 < egestionLevel) {
                        this.drawTexture(matrixStack, aa, s, 0, 0, 9, 9);
                    }

                    if (z * 2 + 1 == egestionLevel) {
                        this.drawTexture(matrixStack, aa, s, 9, 0, 9, 9);
                    }

                    if (z * 2 + 1 > egestionLevel) {
                        this.drawTexture(matrixStack, aa, s, 18, 0, 9, 9);
                    }
                }
            }

            this.client.getProfiler().pop();
        }
    }

    private PlayerEntity getCameraPlayer() {
        return !(this.client.getCameraEntity() instanceof PlayerEntity) ? null : (PlayerEntity) this.client.getCameraEntity();
    }
}