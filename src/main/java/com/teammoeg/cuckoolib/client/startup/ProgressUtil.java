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

package com.teammoeg.cuckoolib.client.startup;

import net.minecraftforge.fml.StartupMessageManager;

/**
 * 用于控制Mod初始化时的进度提示信息(显示在Minecraft加载界面左下角)
 */
public class ProgressUtil {
    /**
     * 用于显示Mod初始化时的进度提示信息(显示在Minecraft加载界面左下角)
     */
    public static void addProgressMessage(String msg) {
        StartupMessageManager.addModMessage(msg);
    }
}
