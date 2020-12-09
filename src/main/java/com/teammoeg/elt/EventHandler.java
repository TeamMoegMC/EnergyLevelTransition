package com.teammoeg.elt;

import com.teammoeg.elt.data.CS;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = CS.ModIDs.ELT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventHandler {
    private static final Logger LOGGER = LogManager.getLogger("ELT");

    @SubscribeEvent
    public static void onCreateWorldSpawn(WorldEvent.CreateSpawnPosition event) {

    }

}
