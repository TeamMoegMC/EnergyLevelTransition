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

package com.teammoeg.elt.loader;

import com.teammoeg.eltcore.data.MD;
import com.teammoeg.eltcore.item.ELTGroups;
import com.teammoeg.eltcore.item.ItemBase;
import com.teammoeg.eltcore.material.MP;
import com.teammoeg.eltcore.material.PrefixGroup;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class ItemLoader implements Runnable {

    @Override
    public void run() {
        new PrefixGroup(MD.ELT, MP.dust);
        new PrefixGroup(MD.ELT, MP.plate);
        new PrefixGroup(MD.ELT, MP.ingot);
        Registry.register(Registry.ITEM, new Identifier(MD.ELT.mID, "duckegg"), new ItemBase(new Item.Settings().group(ELTGroups.MATERIAL)));
    }
}
