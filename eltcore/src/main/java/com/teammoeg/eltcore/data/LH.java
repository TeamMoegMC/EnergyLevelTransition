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

package com.teammoeg.eltcore.data;

import net.minecraft.util.Formatting;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class LH {
    public static class Chat {
        public static final String
                BLACK = Formatting.BLACK.toString()
                , DBLUE = Formatting.DARK_BLUE.toString()
                , DGREEN = Formatting.DARK_GREEN.toString()
                , DCYAN = Formatting.DARK_AQUA.toString()
                , DRED = Formatting.DARK_RED.toString()
                , PURPLE = Formatting.DARK_PURPLE.toString()
                , ORANGE = Formatting.GOLD.toString()
                , GOLD = Formatting.GOLD.toString()
                , GRAY = Formatting.GRAY.toString()
                , DGRAY = Formatting.DARK_GRAY.toString()
                , BLUE = Formatting.BLUE.toString()
                , GREEN = Formatting.GREEN.toString()
                , CYAN = Formatting.AQUA.toString()
                , RED = Formatting.RED.toString()
                , PINK = Formatting.LIGHT_PURPLE.toString()
                , YELLOW = Formatting.YELLOW.toString()
                , WHITE = Formatting.WHITE.toString()
                , OBFUSCATED = Formatting.OBFUSCATED.toString()
                , BOLD = Formatting.BOLD.toString()
                , STRIKETHROUGH = Formatting.STRIKETHROUGH.toString()
                , UNDERLINE = Formatting.UNDERLINE.toString()
                , ITALIC = Formatting.ITALIC.toString()
                , RESET = Formatting.RESET.toString()
                , RESET_TOOLTIP = RESET + GRAY
                ;

        public static String
                RAINBOW_FAST = BLACK
                , RAINBOW = BLACK
                , RAINBOW_SLOW = BLACK
                , BLINKING_CYAN = CYAN
                , BLINKING_RED = RED
                , BLINKING_ORANGE = ORANGE
                , BLINKING_GRAY = GRAY
                ;
    }
}


