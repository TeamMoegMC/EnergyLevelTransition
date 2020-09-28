/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.eltcore.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import javax.annotation.Nullable;

import static net.moeg.eltcore.data.CS.WHITE;

@Environment(EnvType.CLIENT)
public class LeavesColorProvider implements BlockColorProvider {

    public static final LeavesColorProvider INSTANCE = new LeavesColorProvider();

    @Override
    public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {

        int color = WHITE;
        int biomecolor;

        if (world != null) {

            biomecolor = BiomeColors.getFoliageColor(world, pos);
            return biomecolor;

        } else return color;

//        if (pos != null && pos.getY() == 60) {
//            color = RED;
//        }
//        else if (pos != null && pos.getX() == 101) {
//            color = BLUE;
//        }
//        else {
//            color = WHITE;
//        }

    }
}
