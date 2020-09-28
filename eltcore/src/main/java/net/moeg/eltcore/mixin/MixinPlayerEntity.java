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

package net.moeg.eltcore.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.moeg.eltcore.component.IComponentPlayerEgestion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.moeg.eltcore.handlers.Handler_Components.PLAYER_EGESTION_COMPONENT_TYPE;

@Mixin(PlayerEntity.class)
public class MixinPlayerEntity {
    @Inject(method = "tick()V", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/HungerManager.update(Lnet/minecraft/entity/player/PlayerEntity;)V"))
    public void update(CallbackInfo em) {
        IComponentPlayerEgestion iComponentPlayerEgestion = PLAYER_EGESTION_COMPONENT_TYPE.get((Object) this);
        iComponentPlayerEgestion.update((PlayerEntity) (Object) this);
    }
}