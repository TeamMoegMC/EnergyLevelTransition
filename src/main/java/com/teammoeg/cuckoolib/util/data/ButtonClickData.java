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

package com.teammoeg.cuckoolib.util.data;

import io.netty.buffer.ByteBuf;

public class ButtonClickData {
	private int x, y, button;

	public ButtonClickData(int x, int y, int button) {
		this.x = x;
		this.y = y;
		this.button = button;
	}

	public static ButtonClickData readFromBuf(ByteBuf buf) {
		return new ButtonClickData(buf.readInt(), buf.readInt(), buf.readInt());
	}

	public void writeToBuf(ByteBuf buf) {
		buf.writeInt(this.x);
		buf.writeInt(this.y);
		buf.writeInt(this.button);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getButton() {
		return this.button;
	}
}