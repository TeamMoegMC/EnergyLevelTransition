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
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TileEntityCodec implements IGuiHolderCodec {
    public static final TileEntityCodec INSTANCE = new TileEntityCodec();

    public static final ResourceLocation REGISTRY_NAME = new ResourceLocation(CuckooLib.MODID, "tile_entity");

    @Override
    public ResourceLocation getRegistryName() {
        return REGISTRY_NAME;
    }

    @Override
    public void writeHolder(PacketBuffer buf, IModularGuiHolder holder) {
        if (holder instanceof TileEntity) {
            buf.writeBlockPos(((TileEntity) holder).getBlockPos());
        } else {
            throw new IllegalArgumentException("The modular gui holder isn't TileEntity");
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public IModularGuiHolder readHolder(PacketBuffer buf) {
        return (IModularGuiHolder) Minecraft.getInstance().level.getBlockEntity(buf.readBlockPos());
    }
}