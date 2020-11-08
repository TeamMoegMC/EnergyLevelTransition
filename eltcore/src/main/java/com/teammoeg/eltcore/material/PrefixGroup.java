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

import com.teammoeg.eltcore.ELTCORE_Main;
import com.teammoeg.eltcore.code.ModData;
import com.teammoeg.eltcore.data.TD;
import com.teammoeg.eltcore.handlers.Handler_ItemGroups;
import com.teammoeg.eltcore.items.ItemBase;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class PrefixGroup {
    public final String mPrefixName;
    public final String mModIDTextures;
    public final SimpleMat[] mMaterialList;

    public PrefixGroup(ModData aMod, String aNameInternal) {
        this(aMod.mID, aMod.mID, aNameInternal, MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray(new SimpleMat[0]));
    }

    public PrefixGroup(String aModIDOwner, String aModIDTextures, String aPrefixName, SimpleMat... aMaterialList) {
        mPrefixName = aPrefixName;
        mModIDTextures = aModIDTextures;
        if (aMaterialList.length > 0) {
            mMaterialList = aMaterialList;
        } else {
            mMaterialList = MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray(new SimpleMat[0]);
        }
        for (SimpleMat rMaterial : mMaterialList) {
            ELTCORE_Main.LOGGER.info("Mat:"+rMaterial.mNameLocal);
            if (aPrefixName.equals("dust") && rMaterial.contains(TD.ItemGenerator.DUSTS)) Registry.register(Registry.ITEM, new Identifier(aModIDOwner, aPrefixName + "." + rMaterial.mNameInternal), new ItemBase(new Item.Settings().group(Handler_ItemGroups.ELT_MATERIAL)));
            if (aPrefixName.equals("plate") && rMaterial.contains(TD.ItemGenerator.PLATES)) Registry.register(Registry.ITEM, new Identifier(aModIDOwner, aPrefixName + "." + rMaterial.mNameInternal), new ItemBase(new Item.Settings().group(Handler_ItemGroups.ELT_MATERIAL)));
            if (aPrefixName.equals("ingot") && rMaterial.contains(TD.ItemGenerator.INGOTS)) Registry.register(Registry.ITEM, new Identifier(aModIDOwner, aPrefixName + "." + rMaterial.mNameInternal), new ItemBase(new Item.Settings().group(Handler_ItemGroups.ELT_MATERIAL)));
        }
    }
}
