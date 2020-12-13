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

package com.teammoeg.the_seed.api;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.teammoeg.the_seed.data.legacy.CS.OUT;

public abstract class AbstractMod extends AbstractClientMod {
    public AbstractMod() {

    }

    /** Called on Common Setup */
    public abstract void onModCommonSetup2(FMLCommonSetupEvent aEvent);

    // Just add Calls to these from within your Mods load phases.

    public void onModCommonSetup(FMLCommonSetupEvent aEvent) {
        OUT.println(getModNameForLog() + ": ===================");
        OUT.println(getModNameForLog() + ": Common Setup Started");

        onModCommonSetup2(aEvent);

        OUT.println(getModNameForLog() + ": Common Setup Finished");
        OUT.println(getModNameForLog() + ": ===================");
    }
}