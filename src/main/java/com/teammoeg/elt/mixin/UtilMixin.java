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

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.types.Type;
import com.teammoeg.elt.ELT;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Util.class)
public class UtilMixin {
    /**
     * Util#doFetchChoiceType
     */
    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/util/datafix/DataFixesManager.getDataFixer()Lcom/mojang/datafixers/DataFixer;"), method = "doFetchChoiceType(Lcom/mojang/datafixers/DSL$TypeReference;Ljava/lang/String;)Lcom/mojang/datafixers/types/Type;", cancellable = true)
    private static void attemptDataFixInternal(DSL.TypeReference typeIn, String choiceName, CallbackInfoReturnable<Type<?>> callback) {
        if (choiceName.startsWith(ELT.MOD_ID))
            callback.setReturnValue(null);
    }
}

