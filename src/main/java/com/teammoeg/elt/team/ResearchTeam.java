package com.teammoeg.elt.team;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ResearchTeam {
    HashMap<UUID,Integer> players = new HashMap();
    //ArrayList<UUID> uuids = (ArrayList<UUID>) players.keySet();

    public String name;
    public int ResearchTeamXP;

    public ResearchTeam(String name){
        this.name= name;
        TeamDateBase.Teams.add(this);
        //players.put(UUID,0);
    }

    public int getResearchTeamXP() {
        return ResearchTeamXP;
    }

    public String getName() {
        return name;
    }

    public void addPlayer(UUID uuid){
        players.put(uuid,1);
    }
}
