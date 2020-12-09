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

import com.teammoeg.elt.data.MD;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.function.Function;

/**
 * Server Config
 * - synced, stored per world, can be shipped per instance with default configs
 * - use for the majority of config options, or any that need to be present on both sides
 */
public class ServerConfig
{
    // General
    public final ForgeConfigSpec.BooleanValue enableNetherPortals;
    // Climate
    public final ForgeConfigSpec.IntValue temperatureScale;
    public final ForgeConfigSpec.IntValue rainfallScale;


    ServerConfig(ForgeConfigSpec.Builder innerBuilder)
    {
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MD.ELT.mID + ".config.server." + name);

        innerBuilder.push("general");

        enableNetherPortals = builder.apply("enableNetherPortals").comment("Enable nether portal creation").define("enableNetherPortals", false);

        innerBuilder.pop().push("climate");

        temperatureScale = builder.apply("temperatureScale").comment("This is the distance in blocks to the first peak (Either cold or hot) temperature zone, in the north-south direction.").defineInRange("temperatureScale", 20_000, 1_000, 1_000_000);
        rainfallScale = builder.apply("rainfallScale").comment("This is the distance in blocks to the first peak (Either wet or dry) rainfall zone, in the east-west direction").defineInRange("rainfallScale", 20_000, 1_000, 1_000_000);
    }
}