package com.teammoeg.elt.world.dimension;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ELTPlayerTeleporter {
    public static void to(PlayerEntity entity, ServerWorld world) {
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;
        BlockPos valid = validPlayerSpawnLocation(world, playerEntity.blockPosition());
        if (valid == null) {
            playerEntity.teleportTo(world, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), entity.yRot, entity.xRot);
        } else {
            playerEntity.teleportTo(world, valid.getX(), valid.getY(), valid.getZ(), entity.yRot, entity.xRot);
        }
    }

    private static BlockPos validPlayerSpawnLocation(World world, BlockPos position) {

        BlockPos.Mutable currentPos = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());

        for (int Y = currentPos.getY(); Y < 256; Y++) {
            currentPos.setY(Y);
            if (world.getBlockState(currentPos).isAir()) {
                return currentPos;
            }
        }


        return null;
    }
}
