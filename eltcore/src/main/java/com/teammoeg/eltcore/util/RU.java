/*
 * Copyright (c) 2020. TeamMoeg
 *
 * This file is part of Energy Level Transition.
 *
 * Energy Level Transition is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Energy Level Transition is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.eltcore.util;

import com.teammoeg.eltcore.ELTCORE_Main;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.util.Identifier;

/**
 * @author YueSha (GitHub @yuesha-yc)
 * Resource Utils
 */
public class RU {
    
    public static void addAssets(String aModID, String aPath, String aEnglish, String aTooltipEnglish) {
        addModel(aModID, aPath);
        addLang(aModID, aPath, aEnglish, aTooltipEnglish);
    }
    
    /**
     * The Run Time Resources registering methods
     */
    public static void addModel(String aModID, String aPath, String aParent) {
        Identifier resourceId = new Identifier(aModID, "item/" + aPath);
        ELTCORE_Main.ELTRESOURCE.addModel(JModel.model(aParent).textures(JModel.textures().layer0(aModID + ":item/" + aPath)), resourceId);
    }

    /** Default parent as item/generated */
    public static void addModel(String aModID, String aPath) {
        Identifier resourceId = new Identifier(aModID, "item/" + aPath);
        ELTCORE_Main.ELTRESOURCE.addModel(JModel.model("item/generated").textures(JModel.textures().layer0(aModID + ":item/" + aPath)), resourceId);
    }

    /** Animation */
    public static void addAnimation(String aModID, String aPath, int aFrametime) {
        Identifier aniID = new Identifier(aModID, "item/" + aPath);
        ELTCORE_Main.ELTRESOURCE.addAnimation(aniID, JAnimation.animation().frameTime(aFrametime));
    }

    /** Tooltip */
    public static void addLang(String aModID, String aPath, String aEnglish, String aTooltipEnglish) {
        String unlocalizedPath;
        if (aPath.contains("/")) {
            unlocalizedPath = aPath.replace("/", ".");
            ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, "en_us"), ELTCORE_Main.EN_US.translate("item." + aModID + "." + unlocalizedPath, aEnglish).translate("item." + aModID + "." + unlocalizedPath + ".tooltip", aTooltipEnglish));
        } else {
            ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, "en_us"), ELTCORE_Main.EN_US.translate("item." + aModID + "." + aPath, aEnglish).translate("item." + aModID + "." + aPath + ".tooltip", aTooltipEnglish));
        }
    }

    /** Double language */
    @Deprecated
    public static void addLang(String aModID, String aPath, String language, String name, String tooltip) {
        String unlocalizedPath;
        if (aPath.contains("/")) {
            unlocalizedPath = aPath.replace("/", ".");
            if (language.equalsIgnoreCase("en_us"))
                ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, language), ELTCORE_Main.EN_US.translate("item.eltcore." + unlocalizedPath, name).translate("item.eltcore." + unlocalizedPath + ".tooltip", tooltip));
            if (language.equalsIgnoreCase("zh_cn"))
                ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, language), ELTCORE_Main.ZH_CN.translate("item.eltcore." + unlocalizedPath, name).translate("item.eltcore." + unlocalizedPath + ".tooltip", tooltip));
        } else {
            if (language.equalsIgnoreCase("en_us"))
                ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, language), ELTCORE_Main.EN_US.translate("item.eltcore." + aPath, name).translate("item.eltcore." + aPath + ".tooltip", tooltip));
            if (language.equalsIgnoreCase("zh_cn"))
                ELTCORE_Main.ELTRESOURCE.addLang(new Identifier(aModID, language), ELTCORE_Main.ZH_CN.translate("item.eltcore." + aPath, name).translate("item.eltcore." + aPath + ".tooltip", tooltip));
        }
    }

}
