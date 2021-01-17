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

package com.teammoeg.cuckoolib.gui.widget;

import com.teammoeg.cuckoolib.util.data.ButtonClickData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundEvents;

import java.util.function.Consumer;

public class ButtonWidget extends WidgetBase {
	protected Consumer<ButtonClickData> callback;

	public ButtonWidget(int x, int y, int width, int height, Consumer<ButtonClickData> callback) {
		super(x, y, width, height);
		this.callback = callback;
	}

	@Override
	public boolean onMouseClicked(double mouseX, double mouseY, int button) {
		ButtonClickData data = new ButtonClickData((int) mouseX - this.position.getX(),
				(int) mouseY - this.position.getY(), button);
		this.writeToServer(this.id, (buf) -> {
			data.writeToBuf(buf);
		});
		Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		return true;
	}

	@Override
	public void receiveMessageFromClient(PacketBuffer data) {
		this.callback.accept(ButtonClickData.readFromBuf(data));
	}
}