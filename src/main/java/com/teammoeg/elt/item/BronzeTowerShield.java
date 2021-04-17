package com.teammoeg.elt.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class BronzeTowerShield extends ELTShield {
    public BronzeTowerShield(String name) {
        super(name, new Item.Properties().durability(336).tab(ItemGroup.TAB_MATERIALS));
        protect = 50;
    }
}
