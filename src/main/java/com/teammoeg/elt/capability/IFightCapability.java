package com.teammoeg.elt.capability;

import net.minecraft.item.Item;

public interface IFightCapability {
    int getPhysicalStrength();

    void increasePhysicalStrength(int i);

    void decreasePhysicalStrength(Item Shield, int damage);
}
