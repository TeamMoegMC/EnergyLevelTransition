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

package net.moeg.eltcore.world.type;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.gen.GeneratorOptions;

import java.util.Properties;

/**
 * @author: Linguardium
 */
public interface WorldTypeGenerator {
    // return null for no handling, otherwise return GeneratorOptions for your level type
    Event<WorldTypeGenerator> GET_GENERATOR_OPTIONS = EventFactory.createArrayBacked(WorldTypeGenerator.class,
            (listeners) -> (levelType, seed, properties) -> {
                for (WorldTypeGenerator listener : listeners) {
                    GeneratorOptions result = listener.interact(levelType, seed, properties);

                    if (result != null) {
                        return result;
                    }
                }

                return null;
            });

    GeneratorOptions interact(String levelType, long seed, Properties properties);

}
