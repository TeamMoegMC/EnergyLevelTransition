package com.teammoeg.elt;

import com.teammoeg.elt.data.CS;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = CS.ModIDs.ELT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandlerServer {
    private static final Logger LOGGER = LogManager.getLogger("ELT");

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("ELT Server Starting");
    }
}
