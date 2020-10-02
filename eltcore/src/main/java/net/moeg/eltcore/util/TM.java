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

package net.moeg.eltcore.util;

import net.devtech.arrp.json.tags.JTag;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;

import static net.moeg.eltcore.ELTCORE_Main.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 * This class is a Tag Manager
 */
public class TM {

    public static void addTags() {
        // testing
        ELTRESOURCE.addTag(new Identifier("c", "items/branch"), JTag.tag().add(new Identifier("eltcore:oak_branch")));
        ELTRESOURCE.addTag(new Identifier("c", "items/long_branch"), JTag.tag().add(new Identifier("eltcore:birch_branch")));
        ELTRESOURCE.addTag(new Identifier("minecraft", "items/rails"), JTag.tag().add(new Identifier("minecraft:dirt")));
        ELTRESOURCE.addTag(new Identifier("c", "items/block_branch"), JTag.tag().add(new Identifier("minecraft:stone")));
    }

    @ApiStatus.Experimental
    public static void addTag(String aTagName, Item... aItems) {
        JTag jTag = new JTag();
        JTag jTagBlocks = new JTag();
        for (Item tItem : aItems) {
            if (tItem instanceof BlockItem) jTagBlocks.add(Registry.ITEM.getId(tItem));
            jTag.add(Registry.ITEM.getId(tItem));
        }
        ELTRESOURCE.addTag(new Identifier("c", "items/" + aTagName), jTag);
        ELTRESOURCE.addTag(new Identifier("c", "blocks/" + aTagName), jTag);
    }
}
