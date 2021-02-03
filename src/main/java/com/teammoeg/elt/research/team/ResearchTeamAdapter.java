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

package com.teammoeg.elt.research.team;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ResearchTeamAdapter extends TypeAdapter<ResearchTeam> {

    @Override
    public ResearchTeam read(JsonReader in) throws IOException {
        while (in.hasNext()) {
            in.beginObject();
            while (in.hasNext()) {
                ResearchTeam researchTeam = new ResearchTeam(in.nextName());
                in.beginArray();
                in.beginObject();
                switch (in.nextName()) {
                    case "XP":
                        researchTeam.setResearchTeamXP(in.nextInt());
                        break;
                }
                in.endObject();
                in.endArray();
            }
            in.endObject();
        }
        return null;
    }

    @Override
    public void write(JsonWriter writer, ResearchTeam value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.setIndent(" ");
        writer.beginObject();
        writer.name(value.getName()).beginArray().beginObject();
        writer.name("XP").value(value.getResearchTeamXP());
        writer.endObject().endArray();
        writer.endObject();
    }
}
