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

package com.teammoeg.elt.research;

import com.teammoeg.elt.ELT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.HashSet;

public class Quest {

    private ResourceLocation type;
    private HashSet<Quest> parents;
    private final TranslationTextComponent name;
    private final TranslationTextComponent desc;
    private boolean unlocked, completed;

    public Quest(String path, Quest... parents) {
        this(new ResourceLocation(ELT.MOD_ID, path), parents);
    }

    public Quest(ResourceLocation type, Quest... parents) {
        this.type = type;
        for (Quest parent : parents) this.parents.add(parent);
        this.name = new TranslationTextComponent(type.getNamespace() + ".quest." + type.getPath() + ".name");
        this.desc = new TranslationTextComponent(type.getNamespace() + ".quest." + type.getPath() + ".desc");
    }

    public ResourceLocation getId() {
        return type;
    }

    public HashSet<Quest> getParents() {
        return parents;
    }

    public void setId(ResourceLocation type) {
        this.type = type;
    }

    public void setParents(Quest... parents) {
        HashSet<Quest> newSet = new HashSet<>();
        for (Quest parent : parents) newSet.add(parent);
        this.parents = newSet;
    }

    public boolean isUnlocked() {
        return unlocked;
    }

    public void unlock() {
        this.unlocked = true;
    }

    public boolean isCompleted() {
        System.out.println("You have completed Quest [" + name + "]");
        return completed;
    }

    public void complete() {
        this.completed = true;
    }

    public TranslationTextComponent getName() {
        return name;
    }

    public TranslationTextComponent getDesc() {
        return desc;
    }
}
