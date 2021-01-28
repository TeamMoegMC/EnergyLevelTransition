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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.cuckoolib.gui.ISyncedWidgetList;
import com.teammoeg.cuckoolib.gui.ModularGuiInfo;
import com.teammoeg.cuckoolib.util.math.Vector2i;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Consumer;

public interface IWidget {
    ModularGuiInfo getGuiInfo();

    void setGuiInfo(ModularGuiInfo info);

    ISyncedWidgetList getWidgetList();

    void setWidgetList(ISyncedWidgetList list);

    int getWidgetId();

    void setWidgetId(int id);

    Vector2i getSize();

    void setSize(Vector2i size);

    Vector2i getPosition();

    void setPosition(Vector2i position);

    boolean isEnable();

    void setEnable(boolean enable);

    default boolean isInRange(double x, double y) {
        int posX = this.getPosition().getX();
        int posY = this.getPosition().getY();
        return x >= posX && x < posX + this.getSize().getX() && y >= posY && y < posY + this.getSize().getY();
    }

    default void initWidget() {

    }

    default void receiveMessageFromServer(PacketBuffer data) {

    }

    default void receiveMessageFromClient(PacketBuffer data) {

    }

    default void detectAndSendChanges() {

    }

    default void onMouseHovered(int mouseX, int mouseY) {

    }

    default boolean onMouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean onMouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean onMouseClickMove(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    default void drawInForeground(MatrixStack transform, int mouseX, int mouseY, int guiLeft, int guiTop) {

    }

    @OnlyIn(Dist.CLIENT)
    void drawInBackground(MatrixStack transform, float partialTicks, int mouseX, int mouseY, int guiLeft, int guiTop);

    default void writeToClient(int widgetId, Consumer<PacketBuffer> data) {
        this.getWidgetList().writeToClient(widgetId, data);
    }

    default void writeToServer(int widgetId, Consumer<PacketBuffer> data) {
        this.getWidgetList().writeToServer(widgetId, data);
    }
}