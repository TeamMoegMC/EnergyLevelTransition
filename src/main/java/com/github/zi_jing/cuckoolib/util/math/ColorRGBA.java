package com.github.zi_jing.cuckoolib.util.math;

import java.util.Objects;

public class ColorRGBA {
    private final int r;
    private final int g;
    private final int b;
    private final int a;

    public ColorRGBA(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 255;
    }

    public ColorRGBA(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public int getA() {
        return a;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorRGBA colorRGBA = (ColorRGBA) o;
        return r == colorRGBA.r && g == colorRGBA.g && b == colorRGBA.b && a == colorRGBA.a;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, g, b, a);
    }

    @Override
    public String toString() {
        return String.format(
                "%s[%d, %d, %d, %d]", this.getClass().getName(), this.r, this.g, this.b, this.a);
    }
}
