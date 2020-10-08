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

import net.devtech.arrp.json.tags.JTag;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueSha (GitHub @yuesha-yc)
 *
 * This class is for Material Tags.
 */
public class TagMaterial {
    public static final Map<String, TagMaterial> MATERIAL_MAP = new HashMap<>();


    public static TagMaterial createMaterial(int aID, String aNameTag, String aLocalName) {
        TagMaterial rMaterial1 = MATERIAL_MAP.get(aNameTag);
        if (rMaterial1 == null) return new TagMaterial((short)aID, aNameTag, aLocalName);
        return rMaterial1;
    }

    /** The Tag of the Material */
    public final JTag mTag;
    /** The index of the Material */
    public final short mID;
    /** The Tag Name of this Material */
    public final String mNameInternal;
    /** The localised Name for this Material including Spaces and Stuff. It Defaults to the internal Name (if you have Spaces inside the Internal Name then those will be included in the Local Name but not in the final internal Name). */
    public String mNameLocal;

    /** The Colors of this Material in its 4 different states. Any change to these 4 final Arrays will be reflected in the Color of the Material at that state. */
    public final short[] mRGBaSolid = new short[] {255,255,255,255}, mRGBaLiquid = new short[] {255,255,255,255}, mRGBaGas = new short[] {255,255,255,255}, mRGBaPlasma = new short[] {255,255,255,255};
    public final short[][] mRGBa = new short[][] {mRGBaSolid, mRGBaLiquid, mRGBaGas, mRGBaPlasma};
    /** Do not modify these Colors for effects! They are supposed to be final! */
    public final short[] fRGBaSolid = new short[] {255,255,255,255}, fRGBaLiquid = new short[] {255,255,255,255}, fRGBaGas = new short[] {255,255,255,255}, fRGBaPlasma = new short[] {255,255,255,255};
    public final short[][] fRGBa = new short[][] {fRGBaSolid, fRGBaLiquid, fRGBaGas, fRGBaPlasma};

    private TagMaterial(short aID, String aNameInternal, String aNameLocal) {
        mID = aID;
        mNameInternal = aNameInternal;
        mNameLocal = aNameLocal;
        mTag = new JTag();
        MATERIAL_MAP.put(mNameInternal, this);
    }

    /** The Description inside the Material Dictionaries. */
    public String mDescription[] = null, mTooltipChemical = null;
    /** The amount of crushed Ores you get from an Ore Block. */
    public byte mOreMultiplier = 1, mOreProcessingMultiplier = 1;
    /** The time 1 Unit of this Material takes to burn in a vanilla Furnace. */
    public long mFurnaceBurnTime = 0;
    /** The Types Tools allowed, 0 = No Tools, 1 = Flint/Stone/Wood Tools, 2 = Early Tools, 3 = Advanced Tools */
    public byte mToolTypes = 0;
    /** The Quality of the Material as Tool Material (ranges from 0 to 15) */
    public byte mToolQuality = 0;
    /** The Durability of the Material in Tool Form */
    public long mToolDurability = 0;
    /** The Speed of the Material as mining Material */
    public float mToolSpeed = 1.0F;
    public float mHeatDamage = 0.0F;
    /** g/cm^3 of this Material at Room Temperature. 0 Means that it is not determined. */
    public double mGramPerCubicCentimeter = 1.0;

    /** The energetic boundaries of this Material, with somewhat reasonable Defaults for a Solid. Data in Kelvin. */
    public long mMeltingPoint = 1000, mBoilingPoint = 3000, mPlasmaPoint = 10000;
    /** The Atomic Values of one Molecule of this Naterial, Defaults to Technetium. If the Element is Antimatter, then mProtons means Antiprotons and mElectrons means Positrons */
    public long mNeutrons = 55, mProtons = 43, mElectrons = 43, mMass = mNeutrons + mProtons;


}
