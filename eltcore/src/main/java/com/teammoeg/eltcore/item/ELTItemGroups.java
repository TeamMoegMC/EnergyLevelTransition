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

import com.teammoeg.eltcore.handlers.Handler_Items;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ELTItemGroups {

    public static ItemGroup MATERIAL = FabricItemGroupBuilder.create(
            new Identifier("elt", "material"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup MACHINE = FabricItemGroupBuilder.create(
            new Identifier("elt", "machine"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup TOOLS = FabricItemGroupBuilder.create(
            new Identifier("elt", "tools"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup FOOD = FabricItemGroupBuilder.create(
            new Identifier("elt", "food"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup FLUID = FabricItemGroupBuilder.create(
            new Identifier("elt", "fluid"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup MISC = FabricItemGroupBuilder.create(
            new Identifier("elt", "misc"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup DUST = FabricItemGroupBuilder.create(
            new Identifier("elt", "dust"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup PLATE = FabricItemGroupBuilder.create(
            new Identifier("elt", "plate"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

    public static ItemGroup INGOT = FabricItemGroupBuilder.create(
            new Identifier("elt", "ingot"))
            .icon(() -> new ItemStack(Handler_Items.ELT_SYMBOL))
            .build();

}
