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

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class TagMatPrefix {
    public final Map<TagMat, Item> mRegisteredItems = new HashMap<>();

    public final String mPrefixName;

    public TagMatPrefix(String aPrefixName){
        mPrefixName = aPrefixName;
    }

    public Item mat(TagMat tagMat) {
        if (!mRegisteredItems.isEmpty()) return mRegisteredItems.get(tagMat);
        else return null;
    }

}
