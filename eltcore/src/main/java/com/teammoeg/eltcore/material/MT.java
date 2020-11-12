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

import com.teammoeg.eltcore.code.ArrayListNoNulls;
import com.teammoeg.eltcore.code.HashSetNoNulls;

import static com.teammoeg.eltcore.data.TD.ItemGenerator.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class MT {
    public static final HashSetNoNulls<TagMat> ALL_MATERIALS_REGISTERED_HERE = new HashSetNoNulls<>();
    public static final ArrayListNoNulls<TagMat> MAT_ARRAY = new ArrayListNoNulls<>();

    protected static TagMat create(String aNameTag) {
        TagMat rMaterial = TagMat.createMat(aNameTag, aNameTag);
        ALL_MATERIALS_REGISTERED_HERE.add(rMaterial);
        MAT_ARRAY.add(rMaterial);
        return rMaterial;
    }

    protected static TagMat create(String aNameTag, long aR, long aG, long aB, long aA) {
        TagMat rMaterial = TagMat.createMat(aNameTag, aNameTag).setRGBa(aR, aG, aB, aA);
        ALL_MATERIALS_REGISTERED_HERE.add(rMaterial);
        MAT_ARRAY.add(rMaterial);
        return rMaterial;
    }


    public static final TagMat
    EMPTY = create("Empty", 0, 0, 0, 0),
    H = create("Hydrogen", 0, 0, 255, 255).put(GASSES),
    Br = create("Bromine", 80, 10, 10, 100).put(LIQUID, INGOTS),
    Sn = create("Tin", 220, 220, 220, 255).put(INGOTS, PLATES, DUSTS),
    Cu = create("Copper", 243, 156, 18, 255).put(INGOTS, PLATES, DUSTS),
    Cu3Sn = create("Bronze", 247, 220, 111, 255).put(INGOTS, PLATES, DUSTS);



}