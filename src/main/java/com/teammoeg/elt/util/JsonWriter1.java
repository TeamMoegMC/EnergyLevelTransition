package com.teammoeg.elt.util;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teammoeg.elt.team.ResearchTeam;

public class JsonWriter1 {

    public static void JsonWriter(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ResearchTeam.class,new ResearchTeamAdapter());
        Gson gson =gsonBuilder.create();
        ResearchTeam researchTeam = new ResearchTeam("wsdsb");
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("saves",
                "a1.json")), "UTF-8"))){

            gson.toJson(researchTeam,out);
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
        }catch (Exception e){


        }
    }
}
