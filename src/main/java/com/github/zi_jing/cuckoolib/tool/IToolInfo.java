package com.github.zi_jing.cuckoolib.tool;

import com.github.zi_jing.cuckoolib.material.type.Material;
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