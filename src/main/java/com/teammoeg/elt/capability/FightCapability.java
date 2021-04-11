package com.teammoeg.elt.capability;

public class FightCapability implements IFightCapability {
    public FightCapability(int physicalStrength) {
        this.PhysicalStrength = physicalStrength;
    }

    public int PhysicalStrength;

    public int getPhysicalStrength() {
        return PhysicalStrength;
    }

    @Override
    public void decreasePhysicalStrength() {
        this.PhysicalStrength -= 10;
    }
}
