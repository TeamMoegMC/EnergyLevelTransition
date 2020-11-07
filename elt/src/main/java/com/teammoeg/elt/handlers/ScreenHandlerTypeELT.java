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

package com.teammoeg.elt.handlers;

import com.teammoeg.elt.gui.WoodCutterScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ScreenHandlerTypeELT {

    public static final ScreenHandlerType<WoodCutterScreenHandler> WOOD_CUTTER = ScreenHandlerRegistry.registerExtended(new Identifier("elt", "wood_cutter_screenhandler"), (syncId, inventory, buf) -> {
        BlockPos pos = buf.readBlockPos();
        World world = inventory.player.world;
        return new WoodCutterScreenHandler(syncId, inventory, (Inventory) world.getBlockEntity(pos), ScreenHandlerContext.create(inventory.player.world, pos));
    });

}
