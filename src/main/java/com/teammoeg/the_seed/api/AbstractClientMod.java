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

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

import static com.teammoeg.the_seed.data.legacy.CS.OUT;

public abstract class AbstractClientMod {
    public AbstractClientMod() {

    }

    /** Return the Mod ID */
    public abstract String getModID();
    /** Return the Mod Name */
    public abstract String getModName();
    /** Used for logging purposes. */
    public abstract String getModNameForLog();

    @Override public String toString() {return getModID();}

    /** Called on Client Setup */
    public abstract void onModClientSetup2(FMLClientSetupEvent aEvent);
    /** Called on Inter Mod Enqueue */
    public abstract void onModIMCEnqueue2(InterModEnqueueEvent aEvent);
    /** Called on Inter Mod Process */
    public abstract void onModIMCProcess2(InterModProcessEvent aEvent);
    /** Called on Server Start */
    public abstract void onModServerStarting2(FMLServerStartingEvent aEvent);
    /** Called after Server Start */
    public abstract void onModServerStarted2(FMLServerStartedEvent aEvent);
    /** Called on Server Stop */
    public abstract void onModServerStopping2(FMLServerStoppingEvent aEvent);
    /** Called after Server Stop */
    public abstract void onModServerStopped2(FMLServerStoppedEvent aEvent);


    // Just add Calls to these from within your Mods load phases.

    public void onModClientSetup(FMLClientSetupEvent aEvent) {
        OUT.println(getModNameForLog() + ": ===================");
        OUT.println(getModNameForLog() + ": Client Setup Started");

        onModClientSetup2(aEvent);

        OUT.println(getModNameForLog() + ": Client Setup Finished");
        OUT.println(getModNameForLog() + ": ===================");
    }

    public void onModIMCEnqueue(InterModEnqueueEvent aEvent) {
        OUT.println(getModNameForLog() + ": ===================");
        OUT.println(getModNameForLog() + ": Inter Mod Enqueue Started");

        onModIMCEnqueue2(aEvent);

        OUT.println(getModNameForLog() + ": Inter Mod Enqueue Finished");
        OUT.println(getModNameForLog() + ": ===================");
    }
    public void onModIMCProcess(InterModProcessEvent aEvent) {
        OUT.println(getModNameForLog() + ": ===================");
        OUT.println(getModNameForLog() + ": Inter Mod Process Started");

        onModIMCProcess2(aEvent);

        OUT.println(getModNameForLog() + ": Inter Mod Process Finished");
        OUT.println(getModNameForLog() + ": ===================");
    }

    public void onModServerStarting(FMLServerStartingEvent aEvent) {
        onModServerStarting2(aEvent);
    }

    public void onModServerStarted(FMLServerStartedEvent aEvent) {
        onModServerStarted2(aEvent);
    }

    public void onModServerStopping(FMLServerStoppingEvent aEvent) {
        onModServerStopping2(aEvent);
    }

    public void onModServerStopped(FMLServerStoppedEvent aEvent) {
        onModServerStopped2(aEvent);
    }

}
