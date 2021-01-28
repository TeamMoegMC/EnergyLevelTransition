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

import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;

/**
 * Basic events that extends net.minecraftforge.fml.event.lifecycle.ModLifeCycleEvent
 * You should NOT implement this interface for your Mod!
 */
public interface ModLifeCycleEvents {
    /**
     * Return the Mod ID
     */
    String getModID();

    /**
     * Return the Mod Name
     */
    String getModName();

    /**
     * Used for logging purposes.
     */
    String getModNameForLog();

    /**
     * Called on Inter Mod Enqueue
     */
    default void onModIMCEnqueue2(InterModEnqueueEvent aEvent) {

    }

    /**
     * Called on Inter Mod Process
     */
    default void onModIMCProcess2(InterModProcessEvent aEvent) {

    }

    /**
     * Called on Mod Construct
     */
    default void onFMLConstructMod2(FMLConstructModEvent aEvent) {

    }

    /**
     * Called on Load Complete
     */
    default void onFMLLoadComplete2(FMLLoadCompleteEvent aEvent) {

    }

    default void onFMLContructMod(FMLConstructModEvent aEvent) {
        System.out.println(getModNameForLog() + ": ===================");
        System.out.println(getModNameForLog() + ": FML Construct Mod Started");

        onFMLConstructMod2(aEvent);

        System.out.println(getModNameForLog() + ": FML Construct Mod Finished");
        System.out.println(getModNameForLog() + ": ===================");
    }

    default void onFMLLoadComplete(FMLLoadCompleteEvent aEvent) {
        System.out.println(getModNameForLog() + ": ===================");
        System.out.println(getModNameForLog() + ": FML Load Complete Started");

        onFMLLoadComplete2(aEvent);

        System.out.println(getModNameForLog() + ": FML Load Complete Finished");
        System.out.println(getModNameForLog() + ": ===================");
    }

    default void onModIMCEnqueue(InterModEnqueueEvent aEvent) {
        System.out.println(getModNameForLog() + ": ===================");
        System.out.println(getModNameForLog() + ": Inter Mod Enqueue Started");

        onModIMCEnqueue2(aEvent);

        System.out.println(getModNameForLog() + ": Inter Mod Enqueue Finished");
        System.out.println(getModNameForLog() + ": ===================");
    }

    default void onModIMCProcess(InterModProcessEvent aEvent) {
        System.out.println(getModNameForLog() + ": ===================");
        System.out.println(getModNameForLog() + ": Inter Mod Process Started");

        onModIMCProcess2(aEvent);

        System.out.println(getModNameForLog() + ": Inter Mod Process Finished");
        System.out.println(getModNameForLog() + ": ===================");
    }
}
