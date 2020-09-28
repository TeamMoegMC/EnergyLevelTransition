/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.eltcore.handlers;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Handler_ItemGroups {

    public static ItemGroup ELT_MATERIAL = FabricItemGroupBuilder.create(
            new Identifier("elt", "material"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup ELT_MACHINE = FabricItemGroupBuilder.create(
            new Identifier("elt", "machine"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup ELT_TOOLS = FabricItemGroupBuilder.create(
            new Identifier("elt", "tools"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup ELT_FOOD = FabricItemGroupBuilder.create(
            new Identifier("elt", "food"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup ELT_FLUID = FabricItemGroupBuilder.create(
            new Identifier("elt", "fluid"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup ELT_MISC = FabricItemGroupBuilder.create(
            new Identifier("elt", "misc"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

}
