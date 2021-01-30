package com.teammoeg.elt.handler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SaveHandler {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    public static  void Save2(){
        Save(new File("saves","abc.json"));
    }
   public static void Save(File file){



   }
}