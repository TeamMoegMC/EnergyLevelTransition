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
import com.teammoeg.elt.capability.ELTCapabilities;
import com.teammoeg.elt.client.BlockColor;
import com.teammoeg.elt.client.gui.ResearchDeskScreen;
import com.teammoeg.elt.client.renderer.ResearchDeskTileEntityRenderer;
import com.teammoeg.elt.client.settings.ELTKeyBindings;
import com.teammoeg.elt.container.ELTContainerTypes;
import com.teammoeg.elt.item.ELTItems;
import com.teammoeg.elt.research.ELTResearches;
import com.teammoeg.elt.tileentity.ELTTileEntityTypes;
import com.teammoeg.elt.world.biome.ELTBiomes;
import com.teammoeg.elt.world.dimension.FairyTaleDimension;
import com.teammoeg.the_seed.api.modinitializers.ModInitializer;
import com.teammoeg.the_seed.data.legacy.CS;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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
        IEventBus FORGE_BUS = MinecraftForge.EVENT_BUS, MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
        MOD_BUS.register(this);
        MOD_BUS.register(new RegistryEventHandler());
        FORGE_BUS.register(new ForgeEventHandler());
        ELTItems.init();
        ELTBlocks.init();
        ELTTileEntityTypes.REGISTER.register(MOD_BUS);
        ELTContainerTypes.REGISTER.register(MOD_BUS);
        ELTBiomes.REGISTER.register(MOD_BUS);
    }

    @Override
    public void onModCommonSetup2(FMLCommonSetupEvent aEvent) {
        ELTCapabilities.init();
        ELTResearches.init();
        FairyTaleDimension.initNoiseSettings();
    }

    @Override
    public void onModClientSetup2(FMLClientSetupEvent aEvent) {
        ELTKeyBindings.init();
        ClientRegistry.bindTileEntityRenderer(ELTTileEntityTypes.RESEARCH_DESK.get(), ResearchDeskTileEntityRenderer::new);
        ScreenManager.register(ELTContainerTypes.RESEARCHDESKCONTAINER.get(), ResearchDeskScreen::new);
        RenderTypeLookup.setRenderLayer(ELTBlocks.ELT_GRASSBLOCK, RenderType.cutoutMipped());
        BlockColor.blockcolor();
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
