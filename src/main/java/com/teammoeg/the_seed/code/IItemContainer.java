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

package com.teammoeg.the_seed.code;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author YueSha (GitHub @yuesha-yc)
 */
public interface IItemContainer {
    public Item item();
    public Block block();
//    public boolean equal(Object aStack);
//    public boolean equal(Object aStack, boolean aWildcard, boolean aIgnoreNBT);
//    public ItemStack get(long aAmount, Object... aReplacements);
//    public ItemStack wild(long aAmount, Object... aReplacements);
//    public ItemStack getUndamaged(long aAmount, Object... aReplacements);
//    public ItemStack getAlmostBroken(long aAmount, Object... aReplacements);
//    public ItemStack getWithMeta(long aAmount, long aMetaValue, Object... aReplacements);
//    public ItemStack getWithDamage(long aAmount, long aMetaValue, Object... aReplacements);
//    public ItemStack getWithNBT(long aAmount, CompoundTag aNBT, Object... aReplacements);
//    public ItemStack getWithName(long aAmount, String aDisplayName, Object... aReplacements);
//    public ItemStack getWithNameAndNBT(long aAmount, String aDisplayName, CompoundTag aNBT, Object... aReplacements);
//    public ItemStack getWithCharge(long aAmount, long aEnergy, Object... aReplacements);
//    public IItemContainer set(Item aItem);
//    public IItemContainer set(ItemStack aStack);
//    public IItemContainer registerOre(Object... aOreNames);
//    public IItemContainer registerWildcardAsOre(Object... aOreNames);
//    public boolean hasBeenSet();
//    public boolean exists();

    @Deprecated public Item getItem();
    @Deprecated public Block getBlock();
//    @Deprecated public ItemStack getWildcard(long aAmount, Object... aReplacements);
}
