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

package com.teammoeg.cuckoolib.material.type;

import com.teammoeg.cuckoolib.CuckooLib;
import com.teammoeg.cuckoolib.material.MatterState;
import com.teammoeg.cuckoolib.material.ModMaterials;
import com.teammoeg.cuckoolib.util.data.UnorderedRegistry;
import net.minecraft.client.resources.I18n;
import net.minecraft.fluid.Fluid;

import java.util.HashMap;
import java.util.Map;

public class Material {
    public static final UnorderedRegistry<String, Material> REGISTRY = new UnorderedRegistry<String, Material>();

    public static final Map<Class<? extends Material>, Long> DEFAULT_FLAGS = new HashMap<Class<? extends Material>, Long>();

    public static final IMaterialFlag GENERATE_DUST = createFlag(0);
    public static final IMaterialFlag GENERATE_PLATE = createFlag(1);
    public static final IMaterialFlag GENERATE_ROD = createFlag(2);
    public static final IMaterialFlag GENERATE_GEAR = createFlag(3);
    public static final IMaterialFlag GENERATE_CRYSTAL = createFlag(4);
    public static final IMaterialFlag GENERATE_INGOT = createFlag(5);
    public static final IMaterialFlag GENERATE_FOIL = createFlag(6);
    public static final IMaterialFlag GENERATE_SCREW = createFlag(7);
    public static final IMaterialFlag GENERATE_SPRING = createFlag(8);
    public static final IMaterialFlag GENERATE_RING = createFlag(9);
    public static final IMaterialFlag GENERATE_WIRE = createFlag(10);
    public static final IMaterialFlag GENERATE_ROTOR = createFlag(11);
    public static final IMaterialFlag GENERATE_TOOL = createFlag(16);

    protected String name;
    protected int color, durability, harvestLevel;
    protected long flagValue;
    protected float efficiency, meltingPoint, boilingPoint, plasmaPoint;

    public Material(String name, int color) {
        this.name = name;
        this.color = color;
        this.register();
    }

    public static void registerDefaultFlags(Class<? extends Material> type, IMaterialFlag... flags) {
        DEFAULT_FLAGS.put(type, combineFlags(flags));
    }

    public static IMaterialFlag createFlag(int id) {
        if (id < 64 && id >= 0) {
            return () -> id;
        }
        throw new IllegalArgumentException("The maximum Material Flag ID is 63");
    }

    public static long combineFlags(IMaterialFlag... flags) {
        long ret = 0;
        for (IMaterialFlag flag : flags) {
            ret |= flag.getValue();
        }
        return ret;
    }

    public void setPointTemp(float meltingPoint, float boilingPoint, float plasmaPoint) {
        if (!this.validatePointTemp(meltingPoint, boilingPoint, plasmaPoint)) {
            throw new IllegalArgumentException("The matter state points [ " + meltingPoint + ", " + boilingPoint + ", "
                    + plasmaPoint + " ] is invalied");
        }
        this.meltingPoint = meltingPoint;
        this.boilingPoint = boilingPoint;
        this.plasmaPoint = plasmaPoint;
    }

    public boolean validatePointTemp(float meltingPoint, float boilingPoint, float plasmaPoint) {
        return plasmaPoint >= boilingPoint && boilingPoint >= meltingPoint && meltingPoint >= 0;
    }

    public MatterState getState(float temp) {
        if (temp < this.meltingPoint) {
            return MatterState.SOLID;
        }
        if (temp < this.boilingPoint) {
            return MatterState.LIQUID;
        }
        if (temp < this.plasmaPoint) {
            return MatterState.GAS;
        }
        return MatterState.PLASMA;
    }

    public boolean existState(MatterState state) {
        switch (state) {
            case SOLID:
                return this.meltingPoint != 0;
            case LIQUID:
                return this.meltingPoint <= this.boilingPoint;
            case GAS:
                return this.boilingPoint <= this.plasmaPoint;
            case PLASMA:
                return true;
            default:
                return false;
        }
    }

    public static Material getMaterialByName(String name) {
        Material material = REGISTRY.get(name);
        return material == null ? ModMaterials.EMPTY : material;
    }

    public void addFlag(IMaterialFlag flag) {
        this.flagValue |= flag.getValue();
    }

    public String getName() {
        return this.name;
    }

    public int getColor() {
        return this.color;
    }

    public boolean isEmpty() {
        return this == ModMaterials.EMPTY;
    }

    public boolean hasFlag(IMaterialFlag flag) {
        if (DEFAULT_FLAGS.containsKey(this.getClass())) {
            return ((this.flagValue | DEFAULT_FLAGS.get(this.getClass())) & (flag.getValue())) != 0;
        }
        return (this.flagValue & (flag.getValue())) != 0;
    }

    public boolean generateLiquid() {
        return false;
    }

    public boolean generateGas() {
        return false;
    }

    public boolean generatePlasma() {
        return false;
    }

    public Fluid getLiquid() {
        return null;
    }

    public Fluid getGas() {
        return null;
    }

    public Fluid getPlasma() {
        return null;
    }

    public void setToolProperty(int durability, int harvestLevel, float efficiency) {
        this.durability = Math.max(durability, 0);
        this.harvestLevel = Math.max(harvestLevel, 0);
        this.efficiency = Math.max(efficiency, 0);
    }

    public int getDurability() {
        return this.durability;
    }

    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public String getUnlocalizedName() {
        return CuckooLib.MODID + ".material." + this.name + ".name";
    }

    public String getLocalizedName() {
        return I18n.get(this.getUnlocalizedName());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void register() {
        REGISTRY.register(this.name, this);
    }
}