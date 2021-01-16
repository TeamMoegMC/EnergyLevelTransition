package com.github.zi_jing.cuckoolib.util.math;

import com.github.zi_jing.cuckoolib.client.render.GLUtil;

public class ColorUtil {
    public static int parseColor(String colorString) {
        switch (colorString.toLowerCase()) {
            case "aqua":
                return 0x00FFFF;
            case "black":
                return 0x000000;
            case "blue":
                return 0x0000FF;
            case "fuchsia":
                return 0xFF00FF;
            case "gray":
                return 0x808080;
            case "green":
                return 0x008000;
            case "lime":
                return 0x00FF00;
            case "maroon":
                return 0x800000;
            case "navy":
                return 0x000080;
            case "olive":
                return 0x808000;
            case "purple":
                return 0x800080;
            case "red":
                return 0xFF0000;
            case "silver":
                return 0xC0C0C0;
            case "teal":
                return 0x008080;
            case "white":
                return 0xFFFFFF;
            case "yellow":
                return 0xFFFF00;
            default:
                ColorRGBA c = GLUtil.hexStringToRGB(colorString);
                return GLUtil.rgbToHex(c.getR(), c.getG(), c.getB());
        }
    }
}
