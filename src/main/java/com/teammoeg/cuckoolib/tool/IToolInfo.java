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

package com.teammoeg.cuckoolib.tool;

import com.teammoeg.cuckoolib.material.type.Material;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public interface IToolInfo {
	ResourceLocation getRegistryName();

	boolean validateMaterial(Material material);

	int getMaxDamage(ItemStack stack);

	int getBlockBreakDamage();

	int getInteractionDamage();

	int getContainerCraftDamage();

	int getEntityHitDamage();

	int getHarvestLevel(ItemStack stack, ToolType tool, PlayerEntity player, BlockState blockState);

	boolean canHarvestBlock(BlockState state);

	float getDestroySpeed(ItemStack stack);

	default Map<Integer, Pair<String, Material>> getDefaultMaterial() {
		return Collections.emptyMap();
	}

	@OnlyIn(Dist.CLIENT)
	default void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flag) {

	}

	default ItemStack getBrokenItemStack(ItemStack stack) {
		return ItemStack.EMPTY;
	}
}