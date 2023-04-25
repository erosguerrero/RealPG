package com.example.realpg;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
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

    public static String ACTIVITIES_FILE_NAME = "activities.json";


    public static String POKEMON_FILE_NAME = "pokemon.json";

    public static String EXTRA_FILE_NAME = "extra.json";



    public DataManager(Context context) {
        this.context = context;
    }

    /**
     * Inicializa los ficheros de datos en caso de que no existieran
     * @param c
     * @return Devuelve true si se hizo la inicializacion (no existian los ficheros) o false si no hizo nada (los ficheros existian)
     */
    public static boolean initFiles(Context c)
    {
        DataManager dm = new DataManager(c);
        JSONObject emptyJson = new JSONObject();
        //comprueba que exista un fichero cualquiera de datos

        boolean nuevo = false;
        JSONObject json = dm.load(ACTIVITIES_FILE_NAME);
        if(json == null) {
            dm.save(ACTIVITIES_FILE_NAME, emptyJson);
            nuevo = true;
        }

        json = dm.load(POKEMON_FILE_NAME);
        if(json == null) {
            dm.save(POKEMON_FILE_NAME, emptyJson);
            nuevo = true;
        }

        json = dm.load(EXTRA_FILE_NAME);
        if(json == null) {
            nuevo = true;

            JSONObject jsonExtra = new JSONObject();
            try {
                jsonExtra.put("Last3", new JSONArray());

                //TODO por ahora puesto valor 10. En un futuro poner -1 o directamente se creara
                //desde MainActivity un nuevo pokemon inicial, escribiendo el valor que toque
                jsonExtra.put("PokeChosen",-1);


                jsonExtra.put("Length",0);
                jsonExtra.put("Coins", 10);
                dm.save(EXTRA_FILE_NAME, jsonExtra);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

        }
        else {
            try {
                //si el id seleccionado era -1 es que no se acabo de elegir el primer pokemon
                int idSelected = json.getInt("PokeChosen");
                if(idSelected == -1)
                    nuevo = true;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


        return nuevo;
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

    /*Aux: Formato de los archivos

    jsonExtra:
    {"Last3":[], "PokeChosen":5, "Coins": 16, "Length":0}

    activities.json:
    {
        "0": {
            "cat": "CASA",
            "name": "Actividad 0",
            "totalMin": 50,
            "latestSessions": [{
                "date": "2023-04-04",
                "minutes": 20
            }, {
                "date": "2023-04-17",
                "minutes": 15
            }]
        },
        "1": {
            "cat": "VIAJES",
            "name": "Actividad 1",
            "totalMin": 60,
            "latestSessions": [{
                "date": "2023-04-04",
                "minutes": 20
            }, {
                "date": "2023-04-17",
                "minutes": 15
            }]
        }
    }


    pokemon.json:
    {
	"7": {
		"level": "14,7",
		"names": ["pichu", "pikachu", "raichu"],
		"ids": [14, 15, 16],
		"levels": [12, 25]
	},
	"352": {
		"level": "22,3",
		"names": ["chimchar", "monferno", "infernape"],
		"ids": [502, 503, 504],
		"levels": [16, 35]
	}
}




     */

    public void demoExtraJson(){
        //TODO comentado por nuevo sistema de deteccion de ficheros vacios
      /*  JSONObject extraJson = new JSONObject();
        try {
            extraJson.put("Length", 10);
            extraJson.put("Coins", 10);
            JSONObject last3json = new JSONObject();
            JSONObject oneof3json = new JSONObject();
            oneof3json.put("ID", 9);
            oneof3json.put("nombre", "Testeoo");
            last3json.put("1",oneof3json);             last3json.put("2",oneof3json);
            last3json.put("3",oneof3json);
            extraJson.put("Last3", last3json);

            extraJson.put("PokeChosen",1);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        this.save( "extra.json",extraJson);*/
    }

    public void demoPokemonJson(){
        JSONObject pokemonJson = null;
        try {
            pokemonJson = new JSONObject("{\n" +
                    "\t\"1\": {\n" +
                    "\t\t\"level\": \"17\",\n" +
                    "\t\t\"names\": [\"bulbasaur\", \"ivysaur\", \"venusaur\"],\n" +
                    "\t\t\"ids\": [1, 2, 3],\n" +
                    "\t\t\"levels\": [16, 36]\n" +
                    "\t},\n" +
                    "\t\"2\": {\n" +
                    "\t\t\"level\": \"22.3\",\n" +
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
