package com.teammoeg.elt.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.teammoeg.elt.team.ResearchTeam;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonRead {
    public static File SAVESELTPATH;
        public static void readFile(){
            if (!SAVESELTPATH.exists()) {
                try {
                    SAVESELTPATH.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(ResearchTeam.class, new ResearchTeamAdapter());
            Gson gson = gsonBuilder.create();
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(new FileInputStream(new File(SAVESELTPATH,
                    "a1.json")), StandardCharsets.UTF_8))) {
                gson.fromJson(rd,ResearchTeam.class);
            }catch (Exception e){

            }
        };

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
