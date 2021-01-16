package com.github.zi_jing.cuckoolib.client.render;

import net.minecraft.util.ResourceLocation;

public class TextureObject {
    private final ResourceLocation location;
    private final int width;
    private final int height;

    public TextureObject(ResourceLocation location, int width, int height) {
        this.location = location;
        this.width = width;
        this.height = height;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
