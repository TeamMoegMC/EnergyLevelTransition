package com.teammoeg.elt.world.dimension;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class ELTPlayerTeleporter {
    public static void to(PlayerEntity entity, ServerWorld world) {
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;
        playerEntity.teleportTo(world, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), entity.yRot, entity.xRot);
    }
}
