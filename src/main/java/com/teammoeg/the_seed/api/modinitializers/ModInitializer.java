/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.the_seed.api.modinitializers;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * For constructing a Common-Side (both Client and Server) Mod, implement this interface
 */
public interface ModInitializer extends ClientModInitializer, ServerModInitializer {

    /**
     * Called on Common Setup
     */
    void onModCommonSetup2(FMLCommonSetupEvent aEvent);

    default void onModCommonSetup(FMLCommonSetupEvent aEvent) {
        System.out.println(getModNameForLog() + ": ===================");
        System.out.println(getModNameForLog() + ": Common Setup Started");

        onModCommonSetup2(aEvent);

        System.out.println(getModNameForLog() + ": Common Setup Finished");
        System.out.println(getModNameForLog() + ": ===================");
    }
}
