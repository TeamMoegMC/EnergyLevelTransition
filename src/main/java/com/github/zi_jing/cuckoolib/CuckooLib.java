package com.github.zi_jing.cuckoolib;

import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.gui.TileEntityCodec;
import com.github.zi_jing.cuckoolib.material.ModMaterials;
import com.github.zi_jing.cuckoolib.material.ModSolidShapes;
import com.github.zi_jing.cuckoolib.network.*;
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