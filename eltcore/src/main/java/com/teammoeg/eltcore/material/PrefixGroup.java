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

package com.teammoeg.eltcore.material;

import com.teammoeg.eltcore.code.ModData;
import com.teammoeg.eltcore.data.TD;
import com.teammoeg.eltcore.item.ELTGroups;
import com.teammoeg.eltcore.item.ItemMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class PrefixGroup {
    public final TagMatPrefix tagMatPrefix;
    public final String mModIDTextures;
    public final TagMat[] mMaterialList;

    public PrefixGroup(ModData aMod, TagMatPrefix tagMatPrefix) {
        this(aMod.mID, aMod.mID, tagMatPrefix, MT.ALL_MATERIALS_REGISTERED_HERE.toArray(new TagMat[0]));
    }

    public PrefixGroup(String aModIDOwner, String aModIDTextures, TagMatPrefix tagMatPrefix, TagMat... aMaterialList) {
        this.tagMatPrefix = tagMatPrefix;
        mModIDTextures = aModIDTextures;
        if (aMaterialList.length > 0) {
            mMaterialList = aMaterialList;
        } else {
            mMaterialList = MT.ALL_MATERIALS_REGISTERED_HERE.toArray(new TagMat[0]);
        }
        for (TagMat rMaterial : mMaterialList) {
            if (tagMatPrefix == MP.dust && rMaterial.contains(TD.ItemGenerator.DUSTS)) reg(aModIDOwner, tagMatPrefix, rMaterial, ELTGroups.DUST);
            if (tagMatPrefix == MP.plate && rMaterial.contains(TD.ItemGenerator.PLATES)) reg(aModIDOwner, tagMatPrefix, rMaterial, ELTGroups.PLATE);
            if (tagMatPrefix == MP.ingot && rMaterial.contains(TD.ItemGenerator.INGOTS)) reg(aModIDOwner, tagMatPrefix, rMaterial, ELTGroups.INGOT);
        }
    }

    private static Item reg(String modId, TagMatPrefix tagMatPrefix, TagMat tagMat, ItemGroup itemGroup) {
        Item item = Registry.register(Registry.ITEM, new Identifier(modId, tagMatPrefix.mPrefixName + "." + tagMat.mNameInternal), new ItemMaterial(new Item.Settings().group(itemGroup), tagMat, tagMatPrefix));
        tagMatPrefix.mRegisteredItems.put(tagMat, item);
        return item;
    }
}
