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

package com.teammoeg.cuckoolib.gui.codec;

import com.teammoeg.cuckoolib.CuckooLib;
import com.teammoeg.cuckoolib.gui.IGuiHolderCodec;
import com.teammoeg.cuckoolib.gui.IModularGuiHolder;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemStackCodec implements IGuiHolderCodec {
    public static final ItemStackCodec INSTANCE = new ItemStackCodec();

    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "item_stack");

    @Override
    public ResourceLocation getRegistryName() {
        return REGISTRY_NAME;
    }

    @Override
    public void writeHolder(PacketBuffer buf, IModularGuiHolder holder) {
        if (holder instanceof Item) {
            buf.writeItemStack(((Item) holder).getDefaultInstance(), false);
        } else {
            throw new IllegalArgumentException("The modular gui holder isn't ItemStack");
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public IModularGuiHolder readHolder(PacketBuffer buf) {
        return (IModularGuiHolder) buf.readItem().getItem();
    }
}
