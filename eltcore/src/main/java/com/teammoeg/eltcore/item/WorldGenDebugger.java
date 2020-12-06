/*
 * Copyright (c) 2020. TeamMoeg
 *
 * This file is part of Energy Level Transition.
 *
 * Energy Level Transition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Energy Level Transition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.eltcore.item;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class WorldGenDebugger extends Item {

    public WorldGenDebugger(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        int aX = blockPos.getX();
        int aY = blockPos.getY();
        int aZ = blockPos.getZ();
        if (!world.isClient) {
            for (int tX = (aX&~15)-32, eX = (aX&~15)+16+32; tX < eX; tX++) for (int tZ = (aZ&~15)-32, eZ = (aZ&~15)+16+32; tZ < eZ; tZ++) for (int tY = 1; tY < 250; tY++) {
                BlockPos blockPos2 = new BlockPos(tX, tY, tZ);
                world.setBlockState(blockPos2, Blocks.AIR.getDefaultState());
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.FAIL;
        }
    }
}
