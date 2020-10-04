/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.eltcore.component;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.entity.effect.StatusEffects.*;

public class ComponentPlayerEgestion implements IComponentPlayerEgestion {

    private float egestionLevel;
    private final PlayerEntity playerEntity;
    private float noEgestionTimer;
    private float afterEgestionTimer;

    public ComponentPlayerEgestion(PlayerEntity playerEntity) {
        this.playerEntity = playerEntity;
        this.egestionLevel = 10.0F;
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
            this.sync();
        }
    }

    @Override
    public void fromTag(CompoundTag tag) {
        this.egestionLevel = tag.getFloat("egestionLevel");
        this.noEgestionTimer = tag.getFloat("noEgestionTimer");
        this.afterEgestionTimer = tag.getFloat("afterEgestionTimer");
    }

    @Override
    public @NotNull CompoundTag toTag(CompoundTag tag) {
        tag.putFloat("egestionLevel", this.egestionLevel);
        tag.putFloat("noEgestionTimer", this.noEgestionTimer);
        tag.putFloat("afterEgestionTimer", this.afterEgestionTimer);
        return tag;
    }

    @Override
    public Entity getEntity() {
        return this.playerEntity;
    }
}
