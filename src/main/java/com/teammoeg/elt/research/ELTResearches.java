/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.research;

import net.minecraft.item.Items;

public class ELTResearches {
    public static final ResearchLine PALEOLITHIC_AGE = new ResearchLine("paleolithic_age_line", Items.FLINT);
    public static final ResearchLine NEOLITHIC_AGE = new ResearchLine("neolithic_age_line", Items.STONE_HOE);
    public static final ResearchLine COPPER_AGE = new ResearchLine("copper_age_line", Items.BRICK);
    public static final ResearchLine BRONZE_AGE = new ResearchLine("bronze_age_line", Items.NETHER_BRICK);
    public static final ResearchLine IRON_AGE = new ResearchLine("iron_age_line", Items.IRON_HOE);
    public static final ResearchLine STEEL_AGE = new ResearchLine("steel_age_line", Items.GOLD_INGOT);
    public static final ResearchLine STEAM_AGE = new ResearchLine("steam_age_line", Items.FURNACE);
    public static final ResearchLine ELECTRIC_AGE = new ResearchLine("electric_age_line", Items.IRON_BLOCK);
    public static final ResearchLine ATOMIC_AGE = new ResearchLine("atomic_age_line", Items.HEART_OF_THE_SEA);
    public static final ResearchLine SPACE_AGE = new ResearchLine("space_age_line", Items.FIREWORK_ROCKET);
    public static final Research FIRST_RESEARCH = new Research("first_research");
    public static final Research SECOND_RESEARCH = new Research("second_research", Items.DIRT);
    public static final Research THIRD_RESEARCH = new Research("third_research", Items.NETHERITE_SCRAP);
    public static final Research WEAPON_RESEARCH = new Research("weapon_research", Items.WOODEN_SWORD, FIRST_RESEARCH, SECOND_RESEARCH);
    public static final Quest KILL_ZOMBIE = new Quest("kill_zombie");

    public static void init() {
        WEAPON_RESEARCH.addQuest(KILL_ZOMBIE);
        FIRST_RESEARCH.addQuest(KILL_ZOMBIE);
        SECOND_RESEARCH.addQuest(KILL_ZOMBIE);
        THIRD_RESEARCH.addQuest(KILL_ZOMBIE);

        WEAPON_RESEARCH.setLine(PALEOLITHIC_AGE);
        FIRST_RESEARCH.setLine(NEOLITHIC_AGE);
        SECOND_RESEARCH.setLine(COPPER_AGE);
        THIRD_RESEARCH.setLine(BRONZE_AGE);
    }
}
