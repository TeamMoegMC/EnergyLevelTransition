package com.teammoeg.elt.capability;

import com.teammoeg.elt.item.ELTShield;
import net.minecraft.item.Item;

public class FightCapability implements IFightCapability {
    public FightCapability(int physicalStrength) {
        this.PhysicalStrength = physicalStrength;
    }

    public int PhysicalStrength;

    public int getPhysicalStrength() {
        return PhysicalStrength;
    }

    @Override
    public void increasePhysicalStrength(int i) {
        int a = 0;
        a += 1;
        if (a > 100) {
            if (PhysicalStrength < 100) {
                PhysicalStrength += i;
            }
            a = 0;
        }
    }

    @Override
    public void decreasePhysicalStrength(Item shield, int damage) {
        if (PhysicalStrength > 0) {
            int i = 40 + damage;
            if (shield instanceof ELTShield) {
                i -= ((ELTShield) shield).getProtect();
            }
            if (i > 0)
                this.PhysicalStrength -= i;
        }
    }
}
