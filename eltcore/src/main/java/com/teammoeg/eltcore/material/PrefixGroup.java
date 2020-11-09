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
import com.teammoeg.eltcore.data.MP;
import com.teammoeg.eltcore.data.TD;
import com.teammoeg.eltcore.item.ELTItemGroups;
import com.teammoeg.eltcore.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class PrefixGroup {
    public final MatPrefix mMatPrefix;
    public final String mModIDTextures;
    public final SimpleMat[] mMaterialList;

    public PrefixGroup(ModData aMod, MatPrefix aMatPrefix) {
        this(aMod.mID, aMod.mID, aMatPrefix, MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray(new SimpleMat[0]));
    }

    public PrefixGroup(String aModIDOwner, String aModIDTextures, MatPrefix aMatPrefix, SimpleMat... aMaterialList) {
        mMatPrefix = aMatPrefix;
        mModIDTextures = aModIDTextures;
        if (aMaterialList.length > 0) {
            mMaterialList = aMaterialList;
        } else {
            mMaterialList = MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray(new SimpleMat[0]);
        }
        for (SimpleMat rMaterial : mMaterialList) {
            if (aMatPrefix == MP.dust && rMaterial.contains(TD.ItemGenerator.DUSTS)) reg(aModIDOwner, aMatPrefix, rMaterial, ELTItemGroups.DUST);
            if (aMatPrefix == MP.plate && rMaterial.contains(TD.ItemGenerator.PLATES)) reg(aModIDOwner, aMatPrefix, rMaterial, ELTItemGroups.PLATE);
            if (aMatPrefix == MP.ingot && rMaterial.contains(TD.ItemGenerator.INGOTS)) reg(aModIDOwner, aMatPrefix, rMaterial, ELTItemGroups.INGOT);
        }
    }

    private static Item reg(String modId, MatPrefix matPrefix, SimpleMat simpleMat, ItemGroup itemGroup) {
        Item item = Registry.register(Registry.ITEM, new Identifier(modId, matPrefix.mPrefixName + "." + simpleMat.mNameInternal), new ItemBase(new Item.Settings().group(itemGroup)));
        matPrefix.mRegisteredItems.put(simpleMat, item);
        return item;
    }
}
