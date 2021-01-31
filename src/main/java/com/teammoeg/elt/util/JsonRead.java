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

package com.teammoeg.elt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.teammoeg.elt.team.ResearchTeam;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonRead {
    public static File SAVE_ELT_FOLDER_PATH;

    public static void readFile() {
        if (!SAVE_ELT_FOLDER_PATH.exists()) {
            try {
                SAVE_ELT_FOLDER_PATH.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ResearchTeam.class, new ResearchTeamAdapter());
        Gson gson = gsonBuilder.create();
        try (BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(new File(SAVE_ELT_FOLDER_PATH,
                "a1.json")), StandardCharsets.UTF_8))) {
            gson.fromJson(rd, ResearchTeam.class);
        } catch (Exception e) {

        }
    }

    public static ResearchTeam readjson(JsonReader in) throws IOException {
/*            in.beginObject();
            while (in.hasNext()) {
                ResearchTeam researchTeam = new ResearchTeam(in.nextName());
                in.beginArray();
                switch (in.nextName()) {
                    case "XP":
                        researchTeam.setResearchTeamXP(in.nextInt());
                }
                in.endArray();
                return researchTeam;
            }
            in.endObject();*/
        return null;
    }
}
