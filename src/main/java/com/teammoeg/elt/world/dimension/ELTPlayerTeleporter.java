package com.teammoeg.elt.world.dimension;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class ELTPlayerTeleporter {
    public static void to(PlayerEntity entity, ServerWorld world) {
        ServerPlayerEntity playerEntity = (ServerPlayerEntity) entity;
        BlockPos valid = validPlayerSpawnLocation(world, playerEntity.blockPosition(), 64);
        if (valid == null) {
            playerEntity.teleportTo(world, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), entity.yRot, entity.xRot);
        } else {
            playerEntity.teleportTo(world, valid.getX(), valid.getY(), valid.getZ(), entity.yRot, entity.xRot);
        }
    }

    private static BlockPos validPlayerSpawnLocation(World world, BlockPos position, int maximumRange) {
        int radius;
        int outerRadius;
        int distanceSq;
        BlockPos.Mutable currentPos = new BlockPos.Mutable(position.getX(), position.getY(), position.getZ());

        for (int range = 0; range < maximumRange; range++) {
            radius = range * range;
            outerRadius = (range + 1) * (range + 1);

            for (int y = 0; y <= range * 2; y++) {
                int y2 = y > range ? -(y - range) : y;


                for (int x = 0; x <= range * 2; x++) {
                    int x2 = x > range ? -(x - range) : x;


                    for (int z = 0; z <= range * 2; z++) {
                        int z2 = z > range ? -(z - range) : z;

                        distanceSq = x2 * x2 + z2 * z2 + y2 * y2;
                        if (distanceSq >= radius && distanceSq < outerRadius) {
                            currentPos.set(position.asLong(x2, y2, z2));
                            if (world.getBlockState(currentPos.below()).canOcclude() &&
                                    world.getBlockState(currentPos).getMaterial() == Material.AIR &&
                                    world.getBlockState(currentPos.above()).getMaterial() == Material.AIR) {
                                //valid space for player is found
                                return currentPos;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }
}
