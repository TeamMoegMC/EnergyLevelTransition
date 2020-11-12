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

package com.teammoeg.eltcore.item;

import com.teammoeg.eltcore.material.TagMat;
import com.teammoeg.eltcore.material.TagMatPrefix;
import net.minecraft.item.Item;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class ItemMaterial extends Item {

    public final TagMat material;

    public final TagMatPrefix matPrefix;

    public ItemMaterial(Settings settings, TagMat material, TagMatPrefix matPrefix) {
        super(settings);
        this.material = material;
        this.matPrefix = matPrefix;
    }

    public TagMat getMaterial () {
        return material;
    }

    public TagMatPrefix getMatPrefix() {
        return matPrefix;
    }
}
