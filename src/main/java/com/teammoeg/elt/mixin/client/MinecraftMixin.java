/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.mixin.client;

import com.teammoeg.elt.ELT;
import com.teammoeg.elt.config.ELTConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.concurrent.RecursiveEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin extends RecursiveEventLoop<Runnable> {
    private MinecraftMixin(String name) {
        super(name);
    }

    /**
     * This is a hack to remove the "Experimental Settings" screen which will pop up every time you generate or load an ELT world.
     * <p>
     * Fixed by https://github.com/MinecraftForge/MinecraftForge/pull/7275
     */
    @ModifyVariable(method = "doLoadLevel(Ljava/lang/String;Lnet/minecraft/util/registry/DynamicRegistries$Impl;Ljava/util/function/Function;Lcom/mojang/datafixers/util/Function4;ZLnet/minecraft/client/Minecraft$WorldSelectionType;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft$WorldSelectionType;NONE:Lnet/minecraft/client/Minecraft$WorldSelectionType;", ordinal = 0), ordinal = 2, index = 11, name = "flag1")
    private boolean modify$doLoadLevel$flag1(boolean flag1) {
        if (ELTConfig.CLIENT.ignoreExperimentalWorldGenWarning.get()) {
            ELT.LOGGER.warn("Disabled Experimental World Generation Warning");
            return false;
        }
        return flag1;
    }
}
