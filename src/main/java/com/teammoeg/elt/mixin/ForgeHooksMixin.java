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

import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teammoeg.elt.ELT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ForgeHooks.class)
public class ForgeHooksMixin {
    @Inject(at = @At("HEAD"), remap = false, method = "enhanceBiome(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/world/biome/Biome$Climate;Lnet/minecraft/world/biome/Biome$Category;Ljava/lang/Float;Ljava/lang/Float;Lnet/minecraft/world/biome/BiomeAmbience;Lnet/minecraft/world/biome/BiomeGenerationSettings;Lnet/minecraft/world/biome/MobSpawnInfo;Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;Lnet/minecraftforge/common/ForgeHooks$BiomeCallbackFunction;)Lnet/minecraft/world/biome/Biome;", cancellable = true)
    private static void enhanceBiome(final ResourceLocation name, final Biome.Climate climate, final Biome.Category category, final Float depth, final Float scale, final BiomeAmbience effects, final BiomeGenerationSettings gen, final MobSpawnInfo spawns, final RecordCodecBuilder.Instance<Biome> codec, final ForgeHooks.BiomeCallbackFunction callback, CallbackInfoReturnable<Biome> callbackInfo) {
        if (name.getNamespace().equals(ELT.MOD_ID)) {
            BiomeLoadingEvent event = new BiomeLoadingEvent(name, climate, category, depth, scale, effects, new BiomeGenerationSettingsBuilder(gen), new MobSpawnInfoBuilder(spawns));
            MinecraftForge.EVENT_BUS.post(event);
            callbackInfo.setReturnValue(callback.apply(event.getClimate(), event.getCategory(), event.getDepth(), event.getScale(), event.getEffects(), event.getGeneration().build(), event.getSpawns().build()));
        }
    }

}
