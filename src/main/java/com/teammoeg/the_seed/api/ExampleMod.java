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

import com.teammoeg.the_seed.code.ModData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

/**
 * @author Your Name Here, also might be worth replacing that automatically generated Copyright notice with your GPL compatible License/Name instead of mine.
 *
 * An example implementation for a Mod using my System. Copy and rename this File into your source Directory.
 *
 * If you have ANY Problems with the examples here, then you can contact me on the Forums or Discord.
 *
 * Note: it is important to load after "elt".
 *
 * Note: There are NO TEXTURES contained in ELT that correspond to the Examples. Those you will have to do or copy them yourself.
 *
 * uncomment the @Mod-Annotation for actual usage.
 */
//@Mod(ExampleMod.MOD_ID)
public final class ExampleMod extends AbstractMod {

    /** Your Mod-ID has to be LOWERCASE and without Spaces. Uppercase Chars and Spaces can create problems with Resource Packs. */
    public static final String MOD_ID = "insert_your_modid_here"; // <-- TODO: you need to change this to the ID of your own Mod, and then remove this Comment after you did that.
    /** This is your Mods Name */
    public static final String MOD_NAME = "Insert_your_Mod_Name_here"; // <-- TODO: you need to change this to the Name of your own Mod, and then remove this Comment after you did that.
    /** This is your Mods Version */
    public static final String VERSION = "1.16.4-1.0.0"; // <-- TODO: you need to change this to the Version of your own Mod, and then remove this Comment after you did that.

    public ExampleMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onModCommonSetup2(FMLCommonSetupEvent aEvent) {

    }

    @Override
    public void onModClientSetup2(FMLClientSetupEvent aEvent) {

    }

    @Override public void onModIMCEnqueue2(InterModEnqueueEvent aEvent) {/**/}

    @Override public void onModIMCProcess2(InterModProcessEvent aEvent) {/**/}

    @Override public void onModServerStarting2(FMLServerStartingEvent aEvent) {/**/}

    @Override public void onModServerStarted2(FMLServerStartedEvent aEvent) {/**/}

    @Override public void onModServerStopping2(FMLServerStoppingEvent aEvent) {/**/}

    @Override public void onModServerStopped2(FMLServerStoppedEvent aEvent) {/**/}

    @Override public String getModID() { return MOD_ID; }
    @Override public String getModName() { return MOD_NAME; }
    @Override public String getModNameForLog() { return MOD_NAME.replace(' ', '_') + "_Mod"; }

    @SubscribeEvent public void onCommonSetup    (FMLCommonSetupEvent aEvent)        {onModCommonSetup(aEvent);}
    @SubscribeEvent public void onClientSetup    (FMLClientSetupEvent aEvent)        {onModClientSetup(aEvent);}
    @SubscribeEvent public void onIMCEnqueue     (InterModEnqueueEvent aEvent)       {onModIMCEnqueue(aEvent);}
    @SubscribeEvent public void onIMCProcess     (InterModProcessEvent aEvent)       {onModIMCProcess(aEvent);}
    @SubscribeEvent public void onServerStarting (FMLServerStartingEvent aEvent)     {onModServerStarting(aEvent);}
    @SubscribeEvent public void onServerStarted  (FMLServerStartedEvent aEvent)      {onModServerStarted(aEvent);}
    @SubscribeEvent public void onServerStopping (FMLServerStoppingEvent aEvent)     {onModServerStopping(aEvent);}
    @SubscribeEvent public void onServerStopped  (FMLServerStoppedEvent aEvent)      {onModServerStopped(aEvent);}

}
