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

package com.teammoeg.the_seed.api.example;

import com.teammoeg.the_seed.api.modinitializers.ModInitializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.*;

//@Mod(ExampleMod.MOD_ID) //TODO: you need to uncomment this line to make it as your own mod
public final class ExampleMod implements ModInitializer {

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

    @Override
    public void onModServerSetup2(FMLDedicatedServerSetupEvent event) {

    }

    @Override public void onModIMCEnqueue2(InterModEnqueueEvent aEvent) {

    }

    @Override public void onModIMCProcess2(InterModProcessEvent aEvent) {

    }

    // Do not modify the codes below

    @Override
    public String getModID() {
        return MOD_ID;
    }

    @Override
    public String getModName() {
        return MOD_NAME;
    }

    @Override
    public String getModNameForLog() {
        return MOD_NAME.toUpperCase().replace(' ', '_') + "_Mod";
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent aEvent) {
        onModCommonSetup(aEvent);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent aEvent) {
        onModClientSetup(aEvent);
    }

    @SubscribeEvent
    public void onServerSetup(FMLDedicatedServerSetupEvent aEvent) {
        onModServerSetup(aEvent);
    }

    @SubscribeEvent
    public void onIMCEnqueue(InterModEnqueueEvent aEvent) {
        onModIMCEnqueue(aEvent);
    }

    @SubscribeEvent
    public void onIMCProcess(InterModProcessEvent aEvent) {
        onModIMCProcess(aEvent);
    }

}
