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

// 一项研究
public class Research {
    private ResourceLocation id;
    private HashSet<Research> parents;
    private final TranslationTextComponent name;
    private final TranslationTextComponent desc;

    public Research(String path, Research... parents) {
        this(new ResourceLocation(ELT.MOD_ID, path), parents);
    }

    public Research(ResourceLocation id, Research... parents) {
        this.id = id;
        for (Research parent : parents) this.parents.add(parent);
        this.name = new TranslationTextComponent(id.getNamespace() + "." + id.getPath() + ".name");
        this.desc = new TranslationTextComponent(id.getNamespace() + "." + id.getPath() + ".desc");
    }

    public ResourceLocation getId() {
        return id;
    }

    public HashSet<Research> getParents() {
        return parents;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public void setParents(Research... parents) {
        HashSet<Research> newSet = new HashSet<>();
        for (Research parent : parents) newSet.add(parent);
        this.parents = newSet;
    }
}
