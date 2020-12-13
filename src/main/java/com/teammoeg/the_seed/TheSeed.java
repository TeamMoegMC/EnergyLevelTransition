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

package com.teammoeg.the_seed;

import com.teammoeg.the_seed.api.AbstractMod;
import com.teammoeg.the_seed.data.MD;
import com.teammoeg.the_seed.data.legacy.CS;
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

@Mod(TheSeed.MOD_ID)
public class TheSeed extends AbstractMod {

    public static final String MOD_ID = CS.ModIDs.SEED;
    public static final String MOD_NAME = MD.SEED.mName;

    public TheSeed() {
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
