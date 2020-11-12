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

package com.teammoeg.eltcore.mixin;

import com.teammoeg.eltcore.data.MD;
import com.teammoeg.eltcore.util.JsonUtil;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
@Mixin(ModelLoader.class)
public class ModelLoaderMixin {
    @Inject(method = "loadModelFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"), cancellable = true)
    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        // Declare a raw Json string
        String modelJson;
        // First, we check if the current item model that is being registered is from our mod. If it isn't, we continue.
        if (!"elt".equals(id.getNamespace())) return;
        // Ignore the blocks for now
        if (!id.getPath().startsWith("block/")) {
            // Material Prefix Items
            if (id.getPath().startsWith("item/prefix.plate")) {
                modelJson = JsonUtil.createItemModelJson(new Identifier(MD.ELT.mID, "item/materialicons/plate"), "generated");
            } else if (id.getPath().startsWith("item/prefix.ingot")) {
                modelJson = JsonUtil.createItemModelJson(new Identifier(MD.ELT.mID, "item/materialicons/ingot"), "generated");
            } else if (id.getPath().startsWith("item/prefix.dust")) {
                modelJson = JsonUtil.createItemModelJson(new Identifier(MD.ELT.mID, "item/materialicons/dust"), "generated");
            } else {
                modelJson = JsonUtil.createItemModelJson(id, "generated");
            }
        } else {
            return;
        }
        System.out.println(modelJson);
        if ("".equals(modelJson)) return;
        //We check if the json string we get is valid before continuing.
        JsonUnbakedModel model = JsonUnbakedModel.deserialize(modelJson);
        model.id = id.toString();
        cir.setReturnValue(model);
        cir.cancel();
    }
}
