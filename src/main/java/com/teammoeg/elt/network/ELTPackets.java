package com.teammoeg.elt.network;

import com.teammoeg.elt.util.Helpers;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ELTPackets {
    private static final String VERSION = Integer.toString(1);
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(Helpers.identifier("main"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void send(PacketDistributor.PacketTarget target, Object message)
    {
        CHANNEL.send(target, message);
    }

    public static SimpleChannel get()
    {
        return CHANNEL;
    }

    @SuppressWarnings("UnusedAssignment")
    public static void init()
    {
        int id = 0;

//        CHANNEL.registerMessage(id++, ExamplePacket.class, ExamplePacket::encode, ExamplePacket::new, ExamplePacket::handle);
    }
}
