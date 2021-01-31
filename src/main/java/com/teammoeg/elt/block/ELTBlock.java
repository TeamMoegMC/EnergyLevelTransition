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

package com.teammoeg.elt.block;

import com.teammoeg.elt.ELT;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class ELTBlock extends Block {
    public static Property[] pr;
    public final Property[] properties;
    public String name;

    public ELTBlock(String name, Properties properties, BiFunction<Block, Item.Properties, Item> BlockItem, Property... property) {
        super(setProperties(properties, property));
        this.name = name;
        this.setRegistryName(name);
        this.registerDefaultState(getInitDefaultState());
        this.properties = Arrays.copyOf(pr, pr.length);
        ELT.RegistryEvents.registeredBlocks.add(this);

        Item item = BlockItem.apply(this, new Item.Properties().tab(ItemGroup.TAB_MATERIALS));
        if (item != null) {
            item.setRegistryName(name);
            ELT.RegistryEvents.registeredItems.add(item);
        }
    }

    protected static Block.Properties setProperties(Properties blockProps, Object[] objects) {
        List<Property<?>> propList = new ArrayList<>();
        for (Object o : objects) {
            if (o instanceof Property)
                propList.add((Property<?>) o);
            if (o instanceof Property[])
                propList.addAll(Arrays.asList(((Property<?>[]) o)));
        }
        pr = propList.toArray(new Property[0]);
        return blockProps.dynamicShape();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        if (pr != null)
            builder.add(pr);
    }

    protected BlockState getInitDefaultState() {
        BlockState state = this.stateDefinition.any();
        if (state.hasProperty(BlockStateProperties.WATERLOGGED))
            state = state.setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE);
        return state;
    }

}
