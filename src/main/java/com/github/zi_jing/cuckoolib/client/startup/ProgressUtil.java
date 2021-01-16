package com.github.zi_jing.cuckoolib.client.startup;

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
