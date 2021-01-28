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

package com.teammoeg.cuckoolib.util.math;

public class Vector2i {
    public static final Vector2i ORIGIN = new Vector2i(0, 0);

    private final int x;
    private final int y;

    public Vector2i(int width, int height) {
        this.x = width;
        this.y = height;
    }

    public static Vector2i of(int x, int y) {
        return new Vector2i(x, y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Vector2i plus(Vector2i vec) {
        return new Vector2i(this.x + vec.x, this.y + vec.y);
    }

    public Vector2i minus(Vector2i vec) {
        return new Vector2i(this.x - vec.x, this.y - vec.y);
    }

    public Vector2i multiply(int scale) {
        return new Vector2i(this.x * scale, this.y * scale);
    }

    public int scalarMultiply(Vector2i vec) {
        return this.x * vec.x + this.y * vec.y;
    }
}