/*
 *  Copyright (c) 2021. TeamMoeg
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

import com.mojang.brigadier.CommandDispatcher;
import com.teammoeg.elt.capability.ResearchProgressProvider;
import com.teammoeg.elt.client.ELTCommands;
import com.teammoeg.elt.research.JsonRead;
import com.teammoeg.elt.research.JsonWriter;
import com.teammoeg.elt.research.Quest;
import com.teammoeg.elt.research.team.ResearchTeamDatabase;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.storage.FolderName;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent;
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

    @SubscribeEvent
    public static void WorldSave(WorldEvent.Save event) {
        JsonWriter.writeJson();
    }

    @SubscribeEvent
    public static void onAttachCapabilityEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(ELT.MOD_ID, "researchprogress"), new ResearchProgressProvider());
        }
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
        ELTCommands.register(dispatcher);
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent aEvent) {
        JsonRead.SAVE_ELT_FOLDER_PATH = aEvent.getServer().getWorldPath(new FolderName(ELT.MOD_ID)).toFile();
        JsonRead.readFile();
    }

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent aEvent) {

    }

    @SubscribeEvent
    public void onServerStopping(FMLServerStoppingEvent aEvent) {

    }

    @SubscribeEvent
    public void onServerStopped(FMLServerStoppedEvent aEvent) {

    }

    @SubscribeEvent
    public void onEntityKilled(LivingDeathEvent event) {
        if (event.getSource() == null || !(event.getSource().getDirectEntity() instanceof PlayerEntity) || event.getSource().getDirectEntity().level.isClientSide || event.isCanceled())
            return;

        if (event.getEntityLiving() instanceof ZombieEntity) {
            ResearchTeamDatabase.TEAMS.get("dsb").addResearchTeamXP(10);
            ResearchTeamDatabase.TEAMS.get("ys").addResearchTeamXP(10);
            for (Quest quest : ELT.WEAPON_RESEARCH.getContainedQuests()) {
                quest.complete();
                event.getSource().getDirectEntity().sendMessage(new StringTextComponent("You have completed a Quest: "), Util.NIL_UUID);
                event.getSource().getDirectEntity().sendMessage(quest.getName(), Util.NIL_UUID);
            }
        }

    }

}
