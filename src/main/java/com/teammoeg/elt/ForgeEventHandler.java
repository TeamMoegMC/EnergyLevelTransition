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

import com.teammoeg.elt.research.Quest;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppedEvent;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 */
@Mod.EventBusSubscriber(modid = ELT.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {
    private static final Logger LOGGER = LogManager.getLogger("ELT");

    @SubscribeEvent public void onServerStarting (FMLServerStartingEvent aEvent) {

    }

    @SubscribeEvent public void onServerStarted  (FMLServerStartedEvent aEvent) {

    }

    @SubscribeEvent public void onServerStopping (FMLServerStoppingEvent aEvent) {

    }

    @SubscribeEvent public void onServerStopped  (FMLServerStoppedEvent aEvent)  {

    }

    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event) {
        if(event.getSource() == null || !(event.getSource().getDirectEntity() instanceof PlayerEntity) || event.getSource().getDirectEntity().level.isClientSide || event.isCanceled()) return;

        if (event.getEntityLiving() instanceof ZombieEntity) {
            for (Quest quest : ELT.weaponResearch.getContainedQuests()) {
                quest.complete();
                event.getSource().getDirectEntity().sendMessage(new StringTextComponent("You have completed a Quest: "), Util.NIL_UUID);
                event.getSource().getDirectEntity().sendMessage(quest.getName(), Util.NIL_UUID);
            }
        }

    }

//    @SubscribeEvent
//    @OnlyIn(Dist.CLIENT)
//    public void onKey(GuiScreenEvent.KeyboardKeyEvent event)
//    {
//        Minecraft mc = Minecraft.getInstance();
//
//        if(ELTKeyBindings.openQuests.isDown())
//        {
//            ModularGuiInfo.openModularGui(new ResearchModularGui(), (ServerPlayerEntity) mc.player);
//        }
//    }

}
