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

package com.teammoeg.elt.mixin;

import com.teammoeg.elt.world.dimension.ELTDimensions;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.IServerConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Shadow
    protected DynamicRegistries.Impl registryHolder;
    @Shadow
    protected IServerConfiguration worldData;

    /*
     * MinecraftServer#loadLevel
     */
    @Inject(at = @At("HEAD"), method = "loadLevel()V")
    private void initServer(CallbackInfo callback) {
        ELTDimensions.init(this.worldData.worldGenSettings().dimensions(), this.registryHolder.registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY), this.registryHolder.registryOrThrow(Registry.BIOME_REGISTRY), this.registryHolder.registryOrThrow(Registry.NOISE_GENERATOR_SETTINGS_REGISTRY), this.worldData.worldGenSettings().seed());
    }

}
