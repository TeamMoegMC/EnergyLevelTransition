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

package com.teammoeg.eltcore.world;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public class Temperature {

    public static final int GLOBAL_MAX_BUILD_HEIGHT = 256;
    public static final int GLOBAL_SEA_SURFACE_HEIGHT = 64;
    public static final int GLOBAL_AVERAGE_TEMP = 16; // Celsius
    public static final int TOTAL_LATITUDE = 720; //72000;
    public static final int MAX_LATITUDE = 360; //36000;
    public static final int MIN_LATITUDE = -360; //-36000;

    public static double getLatitude(int z) {
        return ((double) z / TOTAL_LATITUDE) * Math.PI;
    }

    public static double getAvgTemp(int x, int y, int z) {
        return GLOBAL_AVERAGE_TEMP * 2 * Math.cos(getLatitude(z)) * getAltitudeFactor(y);
    }

    private static double getAltitudeFactor(int y) {
        int altitude = y - GLOBAL_SEA_SURFACE_HEIGHT;
        // 暂时只考虑对流层
        return y < GLOBAL_SEA_SURFACE_HEIGHT ? 1 : 1 - (double) altitude / 192;
    }
}
