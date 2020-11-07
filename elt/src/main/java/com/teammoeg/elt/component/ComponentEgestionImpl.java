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

package com.teammoeg.elt.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

import static net.minecraft.entity.effect.StatusEffects.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class ComponentEgestionImpl implements ComponentEgestion, AutoSyncedComponent {

    private float egestionLevel;
    private float noEgestionTimer;
    private float afterEgestionTimer;
    private final Object provider;

    public ComponentEgestionImpl(Object provider) {
        this.egestionLevel = 10.0F;
        this.provider = provider;
    }

    @Override
    public float getEgestionLevel() {
        return this.egestionLevel;
    }

    @Override
    public boolean doEgest(PlayerEntity playerEntity) {
        return playerEntity.getHungerManager().getSaturationLevel() > 1.0F && egestionLevel <= 20.0F;
    }

    @Override
    public void update(PlayerEntity playerEntity) {
        if (!playerEntity.isCreative()) {
            if (doEgest(playerEntity)) {
                this.egestionLevel = Math.min(this.egestionLevel + 0.004F, 20.0F);
            }
            if (this.egestionLevel > 19.0F) {
                ++this.noEgestionTimer;
                if (this.noEgestionTimer >= 200) {
                    this.noEgestionTimer = 0.0F;
                    playerEntity.applyStatusEffect(new StatusEffectInstance(NAUSEA, 100));
                    playerEntity.applyStatusEffect(new StatusEffectInstance(SLOWNESS, 150));
                }
            }
            if (playerEntity.isSneaking() && this.egestionLevel > 10.0F) {
                this.noEgestionTimer = 0.0F;
                this.egestionLevel -= 0.1F;
                if (this.egestionLevel > 15.0F) {
                    this.afterEgestionTimer++;
                    if (this.afterEgestionTimer > 150.0F) {
                        this.afterEgestionTimer = 0.0F;
                        playerEntity.applyStatusEffect(new StatusEffectInstance(SPEED, 100));
                    }
                }
            }
            ELTComponents.EGESTION.sync(this.provider);
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.egestionLevel = tag.getFloat("egestionLevel");
        this.noEgestionTimer = tag.getFloat("noEgestionTimer");
        this.afterEgestionTimer = tag.getFloat("afterEgestionTimer");
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putFloat("egestionLevel", this.egestionLevel);
        tag.putFloat("noEgestionTimer", this.noEgestionTimer);
        tag.putFloat("afterEgestionTimer", this.afterEgestionTimer);
    }
}
