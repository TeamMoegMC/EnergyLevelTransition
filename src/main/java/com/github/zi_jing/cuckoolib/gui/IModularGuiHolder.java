package com.github.zi_jing.cuckoolib.gui;

import net.minecraft.entity.player.PlayerEntity;

public interface IModularGuiHolder {
	IGuiHolderCodec getCodec();

	ModularGuiInfo createGuiInfo(PlayerEntity player);
}