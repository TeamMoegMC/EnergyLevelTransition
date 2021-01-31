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
import com.teammoeg.elt.team.ResearchTeam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class JsonWriter {
    public static void writeJson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        ResearchTeam researchTeam = new ResearchTeam("wsdsb1");
        gsonBuilder.registerTypeAdapter(ResearchTeam.class, new ResearchTeamAdapter());
        Gson gson = gsonBuilder.create();
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(JsonRead.SAVE_ELT_FOLDER_PATH,
                "a1.json")), StandardCharsets.UTF_8))) {

            gson.toJson(researchTeam, out);
            out.flush();

            /*           File file = new File("saves/a1.json");
            if (!file.exists()){
                file.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader("saves/a1.json"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("saves/a1.json"));
            JsonWriter jw = new JsonWriter(bw);
            String s = null; String ws = null;
            JsonObject dataJson = new JsonObject();
            //while ((s = br.readLine())!=null){
                try {
                    JsonArray features = dataJson.getAsJsonArray("features");
                    JsonObject properties = new JsonObject();
                    if (features==null){
                        features = new JsonArray();
                        properties.addProperty("id","123");
                        features.add(properties);
                    }
                    dataJson.add("features",features);
                    dataJson.toString();
                    System.out.println("abcdefg" +ws);
                }catch (Exception e){



            }
            Streams.write(dataJson, jw);
            jw.flush();
            br.close();
            bw.close();
            jw.close();*/
        } catch (Exception e) {


        }
    }
}
