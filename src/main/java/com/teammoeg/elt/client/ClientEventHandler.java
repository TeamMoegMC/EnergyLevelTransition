package com.teammoeg.elt.client;

import com.teammoeg.elt.client.gui.ResearchDeskScreen;
import com.teammoeg.elt.container.ELTContainerType;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ClientEventHandler {
/*    @SubscribeEvent
    public static void ELTClientSetupEvent(FMLClientSetupEvent event) {
        ScreenManager.register(ELTContainerType.RESEARCHDESKCONTAINER.get(), ResearchDeskScreen::new);
    }*/
}