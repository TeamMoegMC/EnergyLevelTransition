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

package com.teammoeg.eltcore.render;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.item.ItemStack;

import static com.teammoeg.eltcore.data.CS.*;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class MaterialColorProvider implements ItemColorProvider {

    public static final MaterialColorProvider INSTANCE = new MaterialColorProvider();

    @Override
    public int getColor(ItemStack stack, int tintIndex) {
        int color;
        return 0;
    }

}
