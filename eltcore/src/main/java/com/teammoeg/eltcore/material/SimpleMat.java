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

import com.teammoeg.eltcore.code.HashSetNoNulls;
import com.teammoeg.eltcore.code.ITagDataContainer;
import com.teammoeg.eltcore.code.TagData;
import net.devtech.arrp.json.tags.JTag;

import java.util.*;

import static com.teammoeg.eltcore.data.CS.F;
import static com.teammoeg.eltcore.data.CS.T;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class SimpleMat implements ITagDataContainer<SimpleMat> {
    public static final Map<String, SimpleMat> TAG_MAP = new HashMap<>();

    public static SimpleMat createMat(String aNameTag, String aLocalName) {
        SimpleMat rMaterial1 = TAG_MAP.get(aNameTag);
        SimpleMat rMaterial2 = new SimpleMat(aNameTag.toLowerCase().replace(" ", "_"), aLocalName);
        if (rMaterial1 == null) {
            return rMaterial2;
        }
        return rMaterial1;
    }

    private final Set<TagData> mTags = new HashSetNoNulls<>();

    /** The Tag of the Material */
    public final JTag mTag;
    /** The Tag Name of this Material */
    public final String mNameInternal;
    /** The localised Name for this Material including Spaces and Stuff. It Defaults to the internal Name (if you have Spaces inside the Internal Name then those will be included in the Local Name but not in the final internal Name). */
    public String mNameLocal;

    /** The Colors of this Material */
    public final short[] mRGBa = new short[] {255,255,255,255};

    private SimpleMat(String aNameInternal, String aNameLocal) {
        mNameInternal = aNameInternal;
        mNameLocal = aNameLocal;
        mTag = new JTag();
        TAG_MAP.put(mNameInternal, this);
    }

    public JTag getTag() {
        return this.mTag;
    }

    @Override
    public boolean contains(TagData aTag) {
        return mTags.contains(aTag);
    }

    public boolean containsAny(TagData... aTags) {
        for (TagData aTag : aTags) if (mTags.contains(aTag)) return T;
        return F;
    }

    @Override
    public boolean containsAll(TagData... aTags) {
        return mTags.containsAll(Arrays.asList(aTags));
    }

    @Override
    public boolean containsAll(Collection<TagData> aTags) {
        return mTags.containsAll(aTags);
    }

    @Override
    public SimpleMat add(TagData... aTags) {
        if (aTags != null) for (TagData aTag : aTags) mTags.add(aTag);
        return this;
    }

    @Override
    public boolean remove(TagData aTag) {
        return mTags.remove(aTag);
    }


    public SimpleMat put(TagData... aObjects) {return add(aObjects);}
}
