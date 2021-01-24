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

package com.teammoeg.elt.block;

import com.google.common.collect.ImmutableSet;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.block.researchdesk.ResearchDeskTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collection;
import java.util.function.Supplier;

public class ELTTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES_REGISTRY =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ELT.MOD_ID);

    public static final RegistryObject<TileEntityType<ResearchDeskTileEntity>> RESEARCH_DESK = TILE_ENTITIES_REGISTRY.register(
            "research_desk", makeType(ResearchDeskTileEntity::new, () -> ELTBlocks.RESEARCH_DESK));

    private static <T extends TileEntity> Supplier<TileEntityType<T>> makeType(Supplier<T> create, Supplier<Block> valid) {
        return makeTypeMultipleBlocks(create, () -> ImmutableSet.of(valid.get()));
    }

    private static <T extends TileEntity> Supplier<TileEntityType<T>> makeTypeMultipleBlocks(
            Supplier<T> create,
            Supplier<Collection<Block>> valid) {
        return () -> new TileEntityType<>(create, ImmutableSet.copyOf(valid.get()), null);
    }
}
