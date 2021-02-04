/*
 *  Copyright (c) 2021. TeamMoeg
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

package com.teammoeg.elt.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.teammoeg.elt.ELT;
import com.teammoeg.elt.capability.ELTCapabilities;
import com.teammoeg.elt.capability.ITeamCapability;
import com.teammoeg.elt.research.team.ResearchTeam;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraftforge.common.util.LazyOptional;

import java.util.UUID;

public class CreateTeamCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> node = dispatcher.register(Commands.literal(ELT.MOD_ID).then(Commands.literal("create_team")).then(Commands.argument("team", StringArgumentType.word()).executes((commandContext_) -> {
            createTeam(commandContext_.getSource(), StringArgumentType.getString(commandContext_, "team"));
            return 0;
        })));
    }

    private static void createTeam(CommandSource source, String team) {
        if (source.getEntity() instanceof ServerPlayerEntity) {
            UUID uuid = source.getEntity() == null ? Util.NIL_UUID : source.getEntity().getUUID();
            ResearchTeam researchTeam = new ResearchTeam(team);
            researchTeam.addPlayer(uuid);
            LazyOptional<ITeamCapability> Cap = source.getEntity().getCapability(ELTCapabilities.teamCapability);
            Cap.ifPresent((P) -> P.setTeam(team));
        }
    }


}
