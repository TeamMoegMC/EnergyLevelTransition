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

package com.teammoeg.elt.team;

import java.util.HashMap;
import java.util.UUID;

public class ResearchTeam {
    HashMap<UUID, Integer> players = new HashMap();
    //ArrayList<UUID> uuids = (ArrayList<UUID>) players.keySet();

    private String name;
    private int researchTeamXP;

    public ResearchTeam(String name) {
        this.name = name;
        TeamDataBase.TEAMS.put(name,this);
        //players.put(UUID,0);
    }
    public int getResearchTeamXP() {
        return researchTeamXP;
    }

    public void setResearchTeamXP(int researchTeamXP) {
        this.researchTeamXP = researchTeamXP;
    }

    public String getName() {
        return name;
    }

    public void addPlayer(UUID uuid) {
        players.put(uuid, 1);
    }
}
