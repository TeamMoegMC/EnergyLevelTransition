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
public class TagMat implements ITagDataContainer<TagMat> {
    public static final Map<String, TagMat> TAG_MAP = new HashMap<>();

    public static TagMat createMat(String aNameTag, String aLocalName) {
        TagMat rMaterial1 = TAG_MAP.get(aNameTag);
        TagMat rMaterial2 = new TagMat(aNameTag.toLowerCase().replace(" ", "_"), aLocalName);
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
    public final short[] mRGBaSimple = new short[] {255,255,255,255};

    private TagMat(String aNameInternal, String aNameLocal) {
        mNameInternal = aNameInternal;
        mNameLocal = aNameLocal;
        mTag = new JTag();
        TAG_MAP.put(mNameInternal, this);
    }

    public JTag getTag() {
        return this.mTag;
    }

    public TagMat put(TagData... aObjects) {return add(aObjects);}

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
    public TagMat add(TagData... aTags) {
        if (aTags != null) for (TagData aTag : aTags) mTags.add(aTag);
        return this;
    }

    @Override
    public boolean remove(TagData aTag) {
        return mTags.remove(aTag);
    }

    // The following are not used yet...

    /** The Colors of this Material in its 4 different states. Any change to these 4 final Arrays will be reflected in the Color of the Material at that state. */
    public final short[] mRGBaSolid = new short[] {255,255,255,255}, mRGBaLiquid = new short[] {255,255,255,255}, mRGBaGas = new short[] {255,255,255,255}, mRGBaPlasma = new short[] {255,255,255,255};
    public final short[][] mRGBa = new short[][] {mRGBaSolid, mRGBaLiquid, mRGBaGas, mRGBaPlasma};

    /** The Description inside the Material Dictionaries. */
    public String mDescription[] = null, mTooltipChemical = null;
    /** The amount of crushed Ores you get from an Ore Block. */
    public byte mOreMultiplier = 1, mOreProcessingMultiplier = 1;
    /** The time 1 Unit of this Material takes to burn in a vanilla Furnace. */
    public long mFurnaceBurnTime = 0;

    /** g/cm^3 of this Material at Room Temperature. 0 Means that it is not determined. */
    public double mGramPerCubicCentimeter = 1.0;
    /** The energetic boundaries of this Material, with somewhat reasonable Defaults for a Solid. Data in Kelvin. */
    public long mMeltingPoint = 1000, mBoilingPoint = 3000, mPlasmaPoint = 10000;
    /** The Atomic Values of one Molecule of this Naterial, Defaults to Technetium. If the Element is Antimatter, then mProtons means Antiprotons and mElectrons means Positrons */
    public long mNeutrons = 55, mProtons = 43, mElectrons = 43, mMass = mNeutrons + mProtons;

    /** The Types Tools allowed, 0 = No Tools, 1 = Flint/Stone/Wood Tools, 2 = Early Tools, 3 = Advanced Tools */
    public byte mToolTypes = 0;
    /** The Quality of the Material as Tool Material (ranges from 0 to 15) */
    public byte mToolQuality = 0;
    /** The Durability of the Material in Tool Form */
    public long mToolDurability = 0;
    /** The Speed of the Material as mining Material */
    public float mToolSpeed = 1.0F;
    public float mHeatDamage = 0.0F;
}
