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

package com.teammoeg.eltcore.tag;

import com.teammoeg.eltcore.code.*;
import com.teammoeg.eltcore.util.UT;

import java.util.*;

import static com.teammoeg.eltcore.data.CS.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class TagPrefix implements ITagDataContainer<TagPrefix>, ICondition<ITagDataContainer<?>> {

    /** This List is sorted by the length of the Prefixes. */
    public static final List<TagPrefix> VALUES_SORTED = new ArrayListNoNulls<>();
    /** This is to prevent smaller Prefixes from breaking larger ones by just starting with the same few Characters the larger one starts with. */
    private static final List<TagPrefix> VALUES_SORTED_INTERNAL = new ArrayListNoNulls<>();
    /** The List of all Values in the order they have been added. not really sorted at all. */
    public static final List<TagPrefix> VALUES = new ArrayListNoNulls<>();
    /** The Map containing all Prefixes by their Name. */
    public static final Map<String, TagPrefix> sPrefixes = new HashMap<>();

    private final Set<TagData> mTags = new HashSetNoNulls<>();

    public final String mNameInternal;

    @SuppressWarnings("rawtypes")
    private ICondition mCondition = ICondition.TRUE;

    public byte mConfigStackSize = 64, mDefaultStackSize = 64, mMinimumStackSize = 1, mState = STATE_SOLID;

    public String mNameLocal, mMaterialPre, mMaterialPost, mNameCategory, mNameTextureSet;

    private TagPrefix(String aNameInternal, String aNameLocal) {
        mNameInternal = aNameInternal;
        mNameTextureSet = mNameInternal;
        mNameCategory = mNameLocal = aNameLocal;
        if (mNameInternal.contains("|") || mNameInternal.contains("*") || mNameInternal.contains(":") || mNameInternal.contains(".") || mNameInternal.contains("$")) throw new IllegalArgumentException("The Prefix Name contains invalid Characters!");
        if (mNameInternal.length() < 3) throw new IllegalArgumentException("A Prefix must have at least 3 Characters, otherwise it would break other Prefixes way to easily.");
        VALUES.add(this);
        if (VALUES_SORTED_INTERNAL.isEmpty()) {
            VALUES_SORTED_INTERNAL.add(this);
            VALUES_SORTED.add(this);
        } else for (int i = 0, j = VALUES_SORTED_INTERNAL.size(); i < j; i++) {
            if (mNameInternal.length() >= VALUES_SORTED_INTERNAL.get(i).mNameInternal.length()) {
                VALUES_SORTED_INTERNAL.add(i, this);
                VALUES_SORTED.add(i, this);
                break;
            }
        }
        sPrefixes.put(mNameInternal, this);
    }

    public static TagPrefix createPrefix(String aName) {
        String tName = aName.replaceAll(" ", "").replaceAll("-", ""); // Auto-Replace all Spaces and Minuses.
        TagPrefix rPrefix = sPrefixes.get(tName);
        return rPrefix == null ? new TagPrefix(tName, aName) : rPrefix;
    }
    public TagPrefix setCategoryName(String aCategoryName) {
        mNameCategory = aCategoryName;
        return this;
    }

    public TagPrefix setTextureSetName(String aTextureSetName) {
        mNameTextureSet = aTextureSetName;
        return this;
    }

    /** sets the State of things which are of this Prefix. 0 - 3 are from Solid to Plasma. Solid is Default. */
    public TagPrefix setState(long aState) {
        mState = UT.Code.bind2(aState);
        return this;
    }

    public TagPrefix setCondition(ICondition<?> aCondition) {
        mCondition = aCondition==null?ICondition.FALSE:aCondition;
        return this;
    }

    public TagPrefix setLocalItemName(String aPreMaterial, String aPostMaterial) {
        mMaterialPre = aPreMaterial;
        mMaterialPost = aPostMaterial;
        return this;
    }

    public TagPrefix setLocalPrefixName(String aLocalName) {
        mNameLocal = aLocalName;
        return this;
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
    public TagPrefix add(TagData... aTags) {
        if (aTags != null) for (TagData aTag : aTags) mTags.add(aTag);
        return this;
    }

    @Override
    public boolean remove(TagData aTag) {
        return mTags.remove(aTag);
    }

//    public OreDictItemData dat(OreDictMaterial aMaterial) {
//        return new OreDictItemData(this, aMaterial);
//    }

    @Override
    public String toString() {
        return mNameInternal;
    }

    @Override
    public boolean isTrue(ITagDataContainer<?> aMaterial) {
        return aMaterial instanceof TagPrefix;
    }

}
