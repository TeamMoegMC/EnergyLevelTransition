package com.teammoeg.elt.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.teammoeg.elt.team.ResearchTeam;
import com.teammoeg.elt.team.TeamDateBase;

import java.io.IOException;

public class ResearchTeamAdapter extends TypeAdapter<ResearchTeam> {
    @Override
    public ResearchTeam read(JsonReader in) throws IOException {
        return null;
    }
    @Override
    public void write(JsonWriter out, ResearchTeam researchTeam) throws IOException {
    out.setIndent(" ");
        out.beginObject();
    for (ResearchTeam t : TeamDateBase.Teams){
            out.name("Team:" + t.getName()).beginArray().beginObject();
            out.name("XP").value(t.getResearchTeamXP());
            out.endObject().endArray();
            out.endObject();
        }
        out.endObject();
    }
}
