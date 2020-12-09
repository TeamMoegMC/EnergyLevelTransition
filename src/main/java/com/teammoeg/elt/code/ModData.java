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

package com.teammoeg.elt.code;

import net.minecraftforge.fml.ModList;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public final class ModData implements ICondition<ITagDataContainer<?>>  {
    public static final Map<String, ModData> MODS = new HashMap<>();

    public boolean mLoaded;

    public final String mID, mName;

    public ModData(String aID, String aName) {
        mID = aID;
        mName = aName;
        mLoaded = ModList.get().isLoaded(aID);
        MODS.put(aID, this);
    }

    public ModData setLoaded(boolean aLoaded) {
        mLoaded = aLoaded;
        return this;
    }

    @Override
    public String toString() {
        return mID;
    }

    @Override
    public boolean isTrue(ITagDataContainer<?> aObject) {
        return mLoaded;
    }

    @Override
    public boolean equals(Object aObject) {
        return aObject instanceof ModData && ((ModData)aObject).mID.equals(mID);
    }

    @Override
    public int hashCode() {
        return mID.hashCode();
    }
}
