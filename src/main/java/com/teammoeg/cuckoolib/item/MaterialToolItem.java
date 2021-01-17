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

package com.teammoeg.cuckoolib.item;

import com.teammoeg.cuckoolib.material.ModMaterials;
import com.teammoeg.cuckoolib.material.type.Material;
import com.teammoeg.cuckoolib.tool.IToolInfo;
import com.teammoeg.cuckoolib.util.data.NBTAdapter;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class MaterialToolItem extends ToolItem {
	public static final List<MaterialToolItem> REGISTERED_TOOL_ITEM = new ArrayList<MaterialToolItem>();

	public MaterialToolItem(String modid, String name, Properties properties, IToolInfo toolInfo) {
		super(modid, name, properties, toolInfo);
		REGISTERED_TOOL_ITEM.add(this);
	}

	public static Map<Integer, Pair<String, Material>> getToolAllMaterial(ItemStack stack) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			Map<Integer, Pair<String, Material>> map = new HashMap<Integer, Pair<String, Material>>();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				map.put(compound.getInt("index"), Pair.of(compound.getString("part"),
						Material.getMaterialByName(compound.getString("material"))));
			}
			return map;
		}
		return Collections.emptyMap();
	}

	public static Material getToolMaterial(ItemStack stack, int index) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getInt("index") == index) {
					return Material.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return ModMaterials.EMPTY;
	}

	public static Material getToolMaterial(ItemStack stack, String part) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		if (nbt.contains("material")) {
			ListNBT list = nbt.getList("material", 10);
			Iterator<INBT> ite = list.iterator();
			while (ite.hasNext()) {
				CompoundNBT compound = (CompoundNBT) ite.next();
				if (compound.getString("part").equals(part)) {
					return Material.getMaterialByName(compound.getString("material"));
				}
			}
		}
		return ModMaterials.EMPTY;
	}

	public static void setToolMaterial(ItemStack stack, int index, String part, Material material) {
		CompoundNBT nbt = getToolBaseNBT(stack);
		ListNBT list = NBTAdapter.getList(nbt, "material", 10);
		CompoundNBT compound = new CompoundNBT();
		compound.putInt("index", index);
		compound.putString("part", part);
		compound.putString("material", material.getName());
		list.add(compound);
	}

	public ItemStack createItemStack() {
		return this.createItemStack(1);
	}

	public ItemStack createItemStack(int count) {
		ItemStack stack = new ItemStack(this, count);
		this.toolInfo.getDefaultMaterial()
				.forEach((index, pair) -> setToolMaterial(stack, index, pair.getLeft(), pair.getRight()));
		CompoundNBT nbt = getToolBaseNBT(stack);
		ListNBT list = nbt.getList("material", 10);
		return stack;
	}

	public boolean validateToolMaterial(Material material) {
		return material.hasFlag(Material.GENERATE_TOOL);
	}

	public int getItemColor(ItemStack stack, int tintIndex) {
		Material material = getToolMaterial(stack, tintIndex);
		if (!material.isEmpty()) {
			return material.getColor();
		}
		return 0xffffff;
	}

	@Override
	public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
		if (this.allowdedIn(group)) {
			items.add(this.createItemStack());
		}
	}
}