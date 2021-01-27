package com.teammoeg.elt.container;

import com.teammoeg.elt.ELT;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ELTContainerType{
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ELT.MOD_ID);
    public static RegistryObject<ContainerType<ResearchDeskContainer>> RESEARCHDESKCONTAINER = CONTAINERS.register("researchdesk_container", () -> IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) -> ResearchDeskContainer.create(windowId,inv,inv.player)));
}
