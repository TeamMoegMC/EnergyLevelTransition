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

import com.teammoeg.eltcore.tag.TagPrefix;

import static com.teammoeg.eltcore.data.TD.ItemGenerator.ORES;
import static com.teammoeg.eltcore.data.TD.Prefix.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class OP {
    private static TagPrefix create(String aName, String aCategory, String aPreMaterial, String aPostMaterial) {return TagPrefix.createPrefix(aName).setCategoryName(aCategory).setLocalPrefixName(aCategory).setLocalItemName(aPreMaterial, aPostMaterial);}
    private static TagPrefix create(String aName, String aCategory) {return TagPrefix.createPrefix(aName).setCategoryName(aCategory).setLocalPrefixName(aCategory);}
//    private static TagPrefix unused(String aName) {return TagPrefix.createPrefix(aName).add(PREFIX_UNUSED);}

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final TagPrefix
    ore                         = create("ore"                          , "Ores"                            , ""                                , " Ore"                            )                             .setCondition(ORES)                                                                                         .add(ORE, UNIFICATABLE_RECIPES, BLOCK_BASED, STANDARD_ORE).setTextureSetName("ore"); // Regular Ore Prefix. Ore -> Material is a One-Way Operation! Introduced by Eloraam

}
