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

package com.teammoeg.eltcore.data;

import com.teammoeg.eltcore.material.MatPrefix;

/**
 * @author YueSha (GitHub @yuesha-yc)
 * Material Prefix
 */
public class MP {
    private static MatPrefix create(String aPrefixName) {return new MatPrefix(aPrefixName);}

    public static final MatPrefix
            ingot = create("mat.ingot"),
            plate = create("mat.plate"),
            dust = create("mat.dust");
}
