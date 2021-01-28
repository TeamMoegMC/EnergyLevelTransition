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

package com.teammoeg.cuckoolib;

import com.teammoeg.cuckoolib.gui.ModularGuiInfo;
import com.teammoeg.cuckoolib.gui.codec.ItemStackCodec;
import com.teammoeg.cuckoolib.gui.codec.TileEntityCodec;
import com.teammoeg.cuckoolib.material.ModMaterials;
import com.teammoeg.cuckoolib.material.ModSolidShapes;
import com.teammoeg.cuckoolib.network.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.function.Function;

@Mod(CuckooLib.MODID)
public class CuckooLib {
    public static final String MODID = "cuckoolib";
    public static final String MODNAME = "Cuckoo Lib";
    public static final String VERSION = "1.0.1";

    private static final Logger LOGGER = LogManager.getLogger("CuckooLib");

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(MODID, "main")).networkProtocolVersion(() -> VERSION)
            .serverAcceptedVersions(VERSION::equals).clientAcceptedVersions(VERSION::equals).simpleChannel();

    public CuckooLib() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        ModMaterials.register();
        ModSolidShapes.register();
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        this.registerMessage(0, MessageModularGuiOpen.class, MessageModularGuiOpen::decode,
                NetworkDirection.PLAY_TO_CLIENT);
        this.registerMessage(1, MessageGuiToServer.class, MessageGuiToServer::decode, NetworkDirection.PLAY_TO_SERVER);
        this.registerMessage(2, MessageGuiToClient.class, MessageGuiToClient::decode, NetworkDirection.PLAY_TO_CLIENT);
        this.registerMessage(3, MessageCapabilityUpdate.class, MessageCapabilityUpdate::decode,
                NetworkDirection.PLAY_TO_CLIENT);
        ModularGuiInfo.registerGuiHolderCodec(TileEntityCodec.INSTANCE);
        ModularGuiInfo.registerGuiHolderCodec(ItemStackCodec.INSTANCE);
    }

    public void onServerStarting(FMLServerStartingEvent event) {

    }

    private <T extends IMessage> void registerMessage(int id, Class<T> type, Function<PacketBuffer, T> decoder,
                                                      NetworkDirection direction) {
        CHANNEL.registerMessage(id, type, (msg, buf) -> {
            msg.encode(buf);
        }, decoder, (msg, ctx) -> {
            msg.process(ctx);
            ctx.get().setPacketHandled(true);
        }, Optional.of(direction));
    }
}