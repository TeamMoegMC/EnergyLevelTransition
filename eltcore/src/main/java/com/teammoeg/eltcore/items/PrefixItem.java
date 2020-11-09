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

package com.teammoeg.eltcore.items;

import com.teammoeg.eltcore.code.ModData;
import com.teammoeg.eltcore.material.MatFactory;
import com.teammoeg.eltcore.material.SimpleMat;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class PrefixItem extends Item {
    public final String mNameInternal;
    public final String mModIDTextures;
    public final SimpleMat[] mMaterialList;

    public PrefixItem(ModData aMod, String aNameInternal) {
        this(aMod.mID, aMod.mID, aNameInternal, new Item.Settings(), (SimpleMat[]) MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray());
    }

    public PrefixItem(String aModIDOwner, String aModIDTextures, String aNameInternal, Settings aSettings, SimpleMat... aMaterialList) {
        super(aSettings);
        mNameInternal = aNameInternal;
        mModIDTextures = aModIDTextures;
        if (aMaterialList.length > 0) {
            mMaterialList = aMaterialList;
        } else {
            mMaterialList = (SimpleMat[]) MatFactory.ALL_MATERIALS_REGISTERED_HERE.toArray();
        }
        for (SimpleMat rMaterial : mMaterialList) {
            Registry.register(Registry.ITEM, new Identifier(aModIDOwner, aNameInternal + "." + rMaterial.mNameInternal), this);
        }
        aSettings.group(ELTItemGroups.MATERIAL);
    }

}
