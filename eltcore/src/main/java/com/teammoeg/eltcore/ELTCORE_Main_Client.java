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

package com.teammoeg.eltcore;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import com.teammoeg.eltcore.gui.HudEltCore;

public class ELTCORE_Main_Client implements ClientModInitializer {

    public final HudEltCore hud_eltCore = new HudEltCore(MinecraftClient.getInstance());

    @Override
    public void onInitializeClient() {
//        WorldTypeRegistry.registerWorldType("eltcore.realistic.type", GeneratorTypeELT::new);
        HudRenderCallback.EVENT.register(hud_eltCore::render);
    }
}
