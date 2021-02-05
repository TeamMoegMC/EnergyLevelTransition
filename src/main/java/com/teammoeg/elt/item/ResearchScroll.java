/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.item;


import com.teammoeg.cuckoolib.client.render.TextureArea;
import com.teammoeg.cuckoolib.gui.IGuiHolderCodec;
import com.teammoeg.cuckoolib.gui.IModularGuiHolder;
import com.teammoeg.cuckoolib.gui.ModularGuiInfo;
import com.teammoeg.cuckoolib.gui.codec.ItemStackCodec;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 右键打开研究界面
 */
public class ResearchScroll extends ELTItemBase implements IModularGuiHolder {

    public static final TextureArea BG = TextureArea.createFullTexture(new ResourceLocation("minecraft", "block/stone.png"));

    public ResearchScroll(String name) {
        super(name, new Item.Properties());
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        if (!worldIn.isClientSide) {
            ModularGuiInfo.openModularGui(this, (ServerPlayerEntity) playerIn);
        }
        return ActionResult.success(itemstack);
    }

    @Override
    public IGuiHolderCodec getCodec() {
        return ItemStackCodec.INSTANCE;
    }

    @Override
    public ModularGuiInfo createGuiInfo(PlayerEntity player) {
        return ModularGuiInfo.builder(176, 216).setBackground(BG).addPlayerInventory(player.inventory, 8, 134).build(player);
    }
}
