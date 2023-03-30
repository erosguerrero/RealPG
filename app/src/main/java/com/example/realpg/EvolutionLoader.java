package com.example.realpg;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

public class EvolutionLoader extends AsyncTaskLoader<Evolution> {

    //id chain
    private Integer id;

    public EvolutionLoader(Context context, Integer id){
        super(context);
        this.id = id;
    }

    @Nullable
    @Override
    public Evolution loadInBackground() {

        Log.d("NETWORKAPI", "Iniciada tarea en segundo plano");

        if (id == null){
            Log.e("NETWORKAPI", "ID es null");
            return null;
        }

        String json = NetworkUtils.getEvolutionChain(id);

        if (json == null){
            Log.d("NETWORKAPI", "El json es null");
            return null;
        }

        printJSON(json);
        
        return evolutionFromJson(json);



    }

    private static Evolution evolutionFromJson(String json){
        
        Evolution evolution = new Evolution();

        try{
            
            JSONObject jsonObject = new JSONObject(json);
            JSONObject chain = jsonObject.getJSONObject("chain");


            JSONObject specie = chain;

            while(specie != null){
                evolution.add(specie.getJSONObject("species").getString("url"));

                JSONArray evolves = specie.getJSONArray("evolves_to");
                specie = (evolves.length() > 0) ? evolves.getJSONObject(0) : null;

            }

            Log.d("NETWORKAPI", evolution.toString());

        }
        catch (Exception e){
            Log.e("NETWORKAPI", "Error al convertir el json: " + e.getMessage());
        }


        return evolution;
    }


    private static void printJSON(String json){
        Log.d("NETWORKAPI", json);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
