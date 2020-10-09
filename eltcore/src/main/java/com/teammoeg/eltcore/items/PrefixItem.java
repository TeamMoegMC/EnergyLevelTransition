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

import com.teammoeg.eltcore.handlers.Handler_ItemGroups;
import com.teammoeg.eltcore.tag.TagMaterial;
import com.teammoeg.eltcore.tag.TagPrefix;
import com.teammoeg.eltcore.util.RU;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class PrefixItem extends Item {
    public final String mNameInternal;
    public final TagMaterial[] mMaterialList;
    public final TagPrefix mTagPrefix;

    /**
     * @param aModIDOwner the ID of the owning Mod. DO NOT INSERT ANY GREGTECH MODID!!!
     * @param aModIDTextures the ID of the Texture providing Mod (for the "ModID:TextureName" thing)
     * @param aNameInternal the internal Name of this Item. DO NOT START YOUR UNLOCALISED NAME WITH "gt."!!!
     * @param aPrefix the OreDictPrefix corresponding to this Item.
     */
    public PrefixItem(String aModIDOwner, String aModIDTextures, String aNameInternal, TagPrefix aPrefix, Settings aSettings, TagMaterial... aMaterialList) {
        super(aSettings);
        mTagPrefix = aPrefix;
        mNameInternal = aNameInternal;
        mMaterialList = (aMaterialList.length > 0 ? aMaterialList : new TagMaterial[32767]);
        Registry.register(Registry.ITEM, new Identifier(aModIDOwner, aNameInternal), this);
        aSettings.group(Handler_ItemGroups.ELT_MATERIAL);
    }

}
