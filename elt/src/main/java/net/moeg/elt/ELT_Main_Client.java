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

package net.moeg.elt;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.moeg.elt.gui.WoodCutterScreen;
import net.moeg.elt.handlers.ScreenHandlerTypeELT;
import net.moeg.eltcore.render.LeavesColorProvider;

import static net.moeg.elt.handlers.BlocksELT.EXAMPLE_BLOCK;

public class ELT_Main_Client implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ColorProviderRegistry.BLOCK.register(LeavesColorProvider.INSTANCE, EXAMPLE_BLOCK);
        ScreenRegistry.register(ScreenHandlerTypeELT.WOOD_CUTTER, WoodCutterScreen::new);
    }
}
