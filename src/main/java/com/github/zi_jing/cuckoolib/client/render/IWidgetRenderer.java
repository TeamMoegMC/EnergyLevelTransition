package com.github.zi_jing.cuckoolib.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;

@FunctionalInterface
public interface IWidgetRenderer {
	void draw(MatrixStack transform, int x, int y, int width, int height);
}