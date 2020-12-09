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

package com.teammoeg.elt;

import com.teammoeg.elt.data.CS;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = CS.ModIDs.ELT, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EventHandlerClient {

    private static final Logger LOGGER = LogManager.getLogger("ELT");

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        LOGGER.info("ELT Client Setup");
    }

    @SubscribeEvent
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
        LOGGER.info("ELT Registering Color Handler Blocks");
    }

    @SubscribeEvent
    public static void registerParticleFactoriesAndOtherStuff(ParticleFactoryRegisterEvent event) {
        LOGGER.info("ELT Registering Particle Factories");
    }


}
