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

package com.teammoeg.elt.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.teammoeg.elt.research.team.ResearchTeamDatabase;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.StringTextComponent;

public class JoinTeamCommand implements Command<CommandSource> {
    public static JoinTeamCommand instance = new JoinTeamCommand();

    @Override
    public int run(CommandContext<CommandSource> context) throws CommandSyntaxException {
        ResearchTeamDatabase.TEAMS.get("dsb").addPlayer(context.getSource().getPlayerOrException().getUUID());
        context.getSource().sendSuccess(new StringTextComponent("Joined the team"), false);
        return 0;
    }
}
