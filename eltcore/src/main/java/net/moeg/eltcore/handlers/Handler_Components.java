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

package net.moeg.eltcore.handlers;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.moeg.eltcore.component.ComponentPlayerEgestion;
import net.moeg.eltcore.component.IComponentPlayerEgestion;

public class Handler_Components {

    public static final ComponentType<IComponentPlayerEgestion> PLAYER_EGESTION_COMPONENT_TYPE =
            ComponentRegistry.INSTANCE.registerIfAbsent(new Identifier("eltcore:player_egestion"), IComponentPlayerEgestion.class);

    public void registerComponents() {
        EntityComponentCallback.event(PlayerEntity.class).register((entity, components) -> components.put(PLAYER_EGESTION_COMPONENT_TYPE, new ComponentPlayerEgestion(entity)));
    }

}
