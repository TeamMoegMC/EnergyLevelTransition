package com.github.zi_jing.cuckoolib.gui.widget;

import com.github.zi_jing.cuckoolib.gui.ISyncedWidgetList;
import com.github.zi_jing.cuckoolib.gui.ModularGuiInfo;
import com.github.zi_jing.cuckoolib.util.math.Vector2i;
import com.mojang.blaze3d.matrix.MatrixStack;
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