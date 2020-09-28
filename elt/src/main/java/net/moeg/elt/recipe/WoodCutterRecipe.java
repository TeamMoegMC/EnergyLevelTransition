/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *   Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.moeg.elt.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.moeg.elt.ELT_Main;
import net.moeg.elt.blockentity.WoodCutterBlockEntity;
import net.moeg.eltcore.util.ItemStackUtil;

public class WoodCutterRecipe implements Recipe<WoodCutterBlockEntity> {
    public static RecipeType<WoodCutterRecipe> WOOD_CUTTER = Registry.register(Registry.RECIPE_TYPE, new Identifier("elt:woofcutter"), new RecipeType<WoodCutterRecipe>() {
        public String toString() {
            return "woofcutter";
        }
    });
    private final ItemStack output;
    private final ItemStack output2;
    private final Identifier id;
    private final Ingredient input;
    private final DefaultedList<Ingredient> tools;

    public WoodCutterRecipe(Identifier id, ItemStack output, ItemStack output2, Ingredient input, DefaultedList<Ingredient> tools) {
        this.output = output;
        this.id = id;
        this.output2 = output2;
        this.input = input;
        this.tools = tools;
    }

    @Override
    public boolean matches(WoodCutterBlockEntity inv, World world) {
        if (input.test(inv.getStack(0))) {
            if (tools.size() == 1) {
                return tools.get(0).test(inv.getStack(1)) || tools.get(0).test(inv.getStack(2));
            } else if (tools.size() == 2) {
                return tools.get(0).test(inv.getStack(1)) && tools.get(0).test(inv.getStack(2));
            }
        }
        return false;
    }

    @Override
    public ItemStack craft(WoodCutterBlockEntity inv) {
        ItemStack inv0 = inv.getStack(0);
        ItemStack inv1 = inv.getStack(1);
        ItemStack inv2 = inv.getStack(2);
        ItemStack inv3 = inv.getStack(3);
        ItemStack inv4 = inv.getStack(4);
        if (ItemStack.areItemsEqual(inv3, output) && inv3.getCount() < inv3.getMaxCount() + output.getCount()) {
            inv3.increment(output.getCount());
        } else if (inv3.isEmpty()) {
            inv.setStack(3, output.copy());
        }
        if (ItemStack.areItemsEqual(inv4, output2) && inv4.getCount() < inv4.getMaxCount() + output2.getCount()) {
            inv4.increment(output2.getCount());
        } else if (inv4.isEmpty()) {
            inv.setStack(4, output.copy());
        }
        if (inv0.getTag() != null && inv0.getTag().contains("TEST")) {
            int t = inv0.getTag().getInt("TEST");
            inv0.getTag().putInt("TEST", t - 1);
            if (t <= 1) {
                inv.setStack(0, ItemStack.EMPTY);
            }
        } else {
            inv0.setCount(inv0.getCount() - 1);
        }
        if (tools.size() == 2) {
            damage(inv1);
            damage(inv2);
        } else if (tools.size() == 1) {
            if (!inv1.isEmpty() && tools.get(0).test(inv1)) {
                damage(inv1);
            } else if (!inv2.isEmpty() && tools.get(0).test(inv2)) {
                damage(inv2);
            }
        }
        return ItemStack.EMPTY;
    }

    private void damage(ItemStack i) {
        if (!i.isEmpty()) {
            i.setDamage(i.getDamage() + 1);
            if (i.getDamage() >= i.getMaxDamage()) {
                i.setCount(i.getCount() - 1);
            }
        }
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ELT_Main.WOOD_CUTTER_RECIPE;
    }

    @Override
    public RecipeType<?> getType() {
        return WOOD_CUTTER;
    }

    public static class Serializer implements RecipeSerializer<WoodCutterRecipe> {
        private static DefaultedList<Ingredient> getIngredients(JsonArray json) {
            DefaultedList<Ingredient> defaultedList = DefaultedList.of();

            for (int i = 0; i < json.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(json.get(i));
                if (!ingredient.isEmpty()) {
                    defaultedList.add(ingredient);
                }
            }

            return defaultedList;
        }

        public WoodCutterRecipe read(Identifier identifier, JsonObject jsonObject) {
            return new WoodCutterRecipe(identifier,
                    ItemStackUtil.loadStackFromJson(jsonObject.get("output")),
                    ItemStackUtil.loadStackFromJson(jsonObject.get("output2")),
                    Ingredient.fromJson(jsonObject.get("input")),
                    getIngredients(jsonObject.get("tools").getAsJsonArray())
            );
        }

        public WoodCutterRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            ItemStack output = packetByteBuf.readItemStack();
            ItemStack output2 = packetByteBuf.readItemStack();
            Ingredient input = Ingredient.fromPacket(packetByteBuf);
            DefaultedList<Ingredient> tools = DefaultedList.of();
            for (int i = 0; i < packetByteBuf.readShort(); i++) {
                tools.add(Ingredient.fromPacket(packetByteBuf));
            }
            return new WoodCutterRecipe(identifier, output, output2, input, tools);
        }

        public void write(PacketByteBuf packetByteBuf, WoodCutterRecipe recipe) {
            packetByteBuf.writeItemStack(recipe.output);
            packetByteBuf.writeItemStack(recipe.output2);
            recipe.input.write(packetByteBuf);
            packetByteBuf.writeShort(recipe.tools.size());
            for (Ingredient i : recipe.tools) {
                i.write(packetByteBuf);
            }
        }
    }
}
