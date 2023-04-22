package com.example.realpg;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataManager {
    Context context = null;

    public static String ACTIVITIES_FILE_NAME = "Activities";

    public DataManager(Context context) {
        this.context = context;
    }

    public void save(String filename, JSONObject json) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(json.toString().getBytes());
            Log.d("ARCHIVO CREADO","Saved to " + context.getFilesDir());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JSONObject load(String filename) {
        FileInputStream fis = null;
        JSONObject json = null;
        try {
            fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            try {
                json = new JSONObject(sb.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return json;
        }
    }

    public void demoExtraJson(){
        JSONObject extraJson = new JSONObject();
        try {
            extraJson.put("Length", 10);
            JSONObject last3json = new JSONObject();
            JSONObject oneof3json = new JSONObject();
            oneof3json.put("ID", 9);
            oneof3json.put("nombre", "Testeoo");
            last3json.put("1",oneof3json);             last3json.put("2",oneof3json);
            last3json.put("3",oneof3json);
            extraJson.put("Last3", last3json);

            JSONObject pokechosen = new JSONObject();
            pokechosen.put("IdEvolucion", 0);
            extraJson.put("PokeChosen",pokechosen);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.save( "extra.json",extraJson);
    }

    public void demoPokemonJson(){
        JSONObject pokemonJson = null;
        try {
            pokemonJson = new JSONObject("{\n" +
                    "\t\"0\": {\n" +
                    "\t\t\"level\": \"14,7\",\n" +
                    "\t\t\"names\": [\"pichu\", \"pikachu\", \"raichu\"],\n" +
                    "\t\t\"ids\": [14, 15, 16],\n" +
                    "\t\t\"levels\": [12, 25]\n" +
                    "\t},\n" +
                    "\t\"1\": {\n" +
                    "\t\t\"level\": \"22,3\",\n" +
                    "\t\t\"names\": [\"chimchar\", \"monferno\", \"infernape\"],\n" +
                    "\t\t\"ids\": [502, 503, 504],\n" +
                    "\t\t\"levels\":[16,35]\n" +
                    "\t}\n" +
                    "}");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        this.save( "pokemon.json",pokemonJson);
    }
    public static void demoCreateActivitiesData(Context context)
    {
        String demodata= "{\n" +
                "\t\"0\": {\n" +
                "\t\t\"cat\": \"CASA\",\n" +
                "\t\t\"name\": \"Actividad 0\",\n" +
                "\t\t\"totalMin\": 50,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"1\": {\n" +
                "\t\t\"cat\": \"BIENESTAR\",\n" +
                "\t\t\"name\": \"Actividad 1\",\n" +
                "\t\t\"totalMin\": 60,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"2\": {\n" +
                "\t\t\"cat\": \"BIENESTAR\",\n" +
                "\t\t\"name\": \"Actividad 2\",\n" +
                "\t\t\"totalMin\": 20,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"3\": {\n" +
                "\t\t\"cat\": \"ESTUDIOS\",\n" +
                "\t\t\"name\": \"Actividad 3\",\n" +
                "\t\t\"totalMin\": 10,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "}";
        try {
            JSONObject json = new JSONObject(demodata);
            DataManager dm = new DataManager(context);

            dm.save("Activities",json);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
