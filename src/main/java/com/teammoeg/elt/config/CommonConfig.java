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
 * Common Config
 * - not synced, saved per instance
 * - use for things that are only important server side (i.e. world gen), or make less sense to have per-world.
 */
public class CommonConfig {
    // General
    public final ForgeConfigSpec.IntValue defaultMonthLength;
    public final ForgeConfigSpec.BooleanValue enableDevTweaks;
    public final ForgeConfigSpec.BooleanValue setELTWorldTypeAsDefault;

    CommonConfig(ForgeConfigSpec.Builder innerBuilder) {
        // Standardization for translation keys
        Function<String, ForgeConfigSpec.Builder> builder = name -> innerBuilder.translation(MD.ELT.mID + ".config.general." + name);

        innerBuilder.push("general");

        defaultMonthLength = builder.apply("defaultMonthLength").defineInRange("defaultMonthLength", 8, 1, Integer.MAX_VALUE);
        enableDevTweaks = builder.apply("enableDevTweaks").comment(
                "This enables a series of quality of life logging improvements aimed at mod or pack development. It has no end user or gameplay effect.",
                "This currently enables the following tweaks:",
                " - Enables a [Possible DFU FU] log message, which attempts to catch errors due to incorrect world generation data packs. This does produce false errors!",
                " - Improves and shortens the error message for invalid loot tables.",
                " - Improves and shortens the error message for invalid recipes.",
                " - Fixes MC-190122 (Makes the 'Loaded Recipes' log message accurate)"
        ).define("enableDevTweaks", true);

        setELTWorldTypeAsDefault = builder.apply("setELTWorldTypeAsDefault").comment(
                "If the ELT world type (elt:default) should be set as the default world generation.",
                "1. This ONLY sets the corresponding config option in Forge's config.",
                "2. This ONLY will set the default if it was set to 'default' (or vanilla generation)",
                "3. This DOES NOT guarantee that the world generation will be TFC, if another mod sets the default another way"
        ).define("setELTWorldTypeAsDefault", true);

        innerBuilder.pop();
    }
}