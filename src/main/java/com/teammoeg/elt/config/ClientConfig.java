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

package com.teammoeg.elt.config;

import com.teammoeg.the_seed.data.MD;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Function;

/**
 * Client Config
 * - not synced, only loaded client side
 * - only use for PURELY AESTHETIC options
 */
public class ClientConfig
{
    public final ForgeConfigSpec.BooleanValue ignoreExperimentalWorldGenWarning;

    ClientConfig(ForgeConfigSpec.Builder innerBuilder)
    {
        // Standardization for translation keys
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MD.ELT.mID + ".config.client." + name);

        ignoreExperimentalWorldGenWarning = builder.apply("ignoreExperimentalWorldGenWarning").comment("Should ELT forcefully skip the 'Experimental World Generation' warning screen when creating or loading a world?").define("ignoreExperimentalWorldGenWarning", true);
    }
}