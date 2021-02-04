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

package com.teammoeg.the_seed.api.gui;

import com.teammoeg.cuckoolib.util.math.Vector2i;
import net.minecraft.client.gui.AbstractGui;

public class Button extends AbstractGui {
    public Vector2i size, position;

    public Button(int x, int y, int width, int height) {
        this.position = new Vector2i(x, y);
        this.size = new Vector2i(width, height);
    }

    public boolean onMouseClicked(double mouseX, double mouseY, int buttonIn) {
        return false;
    }

    public boolean isMouseOver(int x, int y, int mouseX, int mouseY) {
        int i = x + this.position.getX();
        int j = i + this.size.getX();
        int k = y + this.position.getY();
        int l = k + this.size.getY();
        return mouseX >= i && mouseX <= j && mouseY >= k && mouseY <= l;
    }
}
