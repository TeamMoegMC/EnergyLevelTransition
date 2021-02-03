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


import com.teammoeg.elt.block.ELTBlocks;
import com.teammoeg.elt.capability.ELTCapabilityRegister;
import com.teammoeg.elt.client.gui.ResearchDeskScreen;
import com.teammoeg.elt.client.renderer.ResearchDeskTileEntityRenderer;
import com.teammoeg.elt.client.settings.ELTKeyBindings;
import com.teammoeg.elt.container.ELTContainerTypes;
import com.teammoeg.elt.item.ELTItems;
import com.teammoeg.elt.research.Quest;
import com.teammoeg.elt.research.Research;
import com.teammoeg.elt.research.team.ResearchTeamDatabase;
import com.teammoeg.elt.tileentity.ELTTileEntityTypes;
import com.teammoeg.elt.world.biome.ELTBiomes;
import com.teammoeg.the_seed.api.modinitializers.ModInitializer;
import com.teammoeg.the_seed.data.legacy.CS;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Items;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Mod(ELT.MOD_ID)
public class ELT implements ModInitializer {
    public static final String MOD_ID = CS.ModIDs.ELT;
    public static final String MOD_NAME = "Energy Level Transition";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public ELT() {
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new RegistryEventHandler());
        new ELTItems();
        new ELTBlocks();
        ELTTileEntityTypes.TILE_TYPE_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
        ELTContainerTypes.CONTAINER_TYPE_REG.register(FMLJavaModLoadingContext.get().getModEventBus());
        ELTBiomes.BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final Quest KILL_ZOMBIE = new Quest("kill_zombie");
    public static final Research FIRST_RESEARCH = new Research("first_research");
    public static final Research SECOND_RESEARCH = new Research("second_research", Items.DIRT);
    public static final Research THIRD_RESEARCH = new Research("third_research", Items.NETHERITE_SCRAP);
    public static final Research WEAPON_RESEARCH = new Research("weapon_research", Items.WOODEN_SWORD, FIRST_RESEARCH, SECOND_RESEARCH);

    @Override
    public void onModCommonSetup2(FMLCommonSetupEvent aEvent) {
        ELTCapabilityRegister.registerCapability();
        WEAPON_RESEARCH.addQuest(KILL_ZOMBIE);
        FIRST_RESEARCH.addQuest(KILL_ZOMBIE);
        SECOND_RESEARCH.addQuest(KILL_ZOMBIE);
        THIRD_RESEARCH.addQuest(KILL_ZOMBIE);
        ResearchTeamDatabase.createTeam("dsb");
        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(ELTBiomes.getKey(ELTBiomes.fallasleepbiome.get()), 1000));
    }

    @Override
    public void onModClientSetup2(FMLClientSetupEvent aEvent) {
        ELTKeyBindings.registerKeyBindings();
        ClientRegistry.bindTileEntityRenderer(ELTTileEntityTypes.RESEARCH_DESK.get(), ResearchDeskTileEntityRenderer::new);
        ScreenManager.register(ELTContainerTypes.RESEARCHDESKCONTAINER.get(), ResearchDeskScreen::new);
    }

    @Override
    public void onModServerSetup2(FMLDedicatedServerSetupEvent event) {

    }

    @Override
    public void onModIMCEnqueue2(InterModEnqueueEvent aEvent) {

    }

    @Override
    public void onModIMCProcess2(InterModProcessEvent aEvent) {

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
