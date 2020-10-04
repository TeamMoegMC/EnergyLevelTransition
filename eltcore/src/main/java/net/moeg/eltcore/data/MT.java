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

package net.moeg.eltcore.data;

import net.moeg.eltcore.code.HashSetNoNulls;
import net.moeg.eltcore.tag.TagMaterial;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class MT {

    public static final HashSetNoNulls<TagMaterial> ALL_MATERIALS_REGISTERED_HERE = new HashSetNoNulls<>();

    protected static TagMaterial create (int aID, String aNameTag) {
        TagMaterial rMaterial = TagMaterial.createMaterial(aID, aNameTag, aNameTag);
        ALL_MATERIALS_REGISTERED_HERE.add(rMaterial);
        return rMaterial;
    }

    public static final TagMaterial HYDROGEN = create(100, "Hydrogen");

}
