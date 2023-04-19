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



        if (id == null){
            Log.e("EVOLUTIONAPI", "ID es null");
            return null;
        }

        String json = EvolutionNetwork.getEvolutionChain(id);

        if (json == null){
            Log.d("EVOLUTIONAPI", "El json es null");
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

            evolution.setId(jsonObject.getInt("id"));

            JSONObject specie = chain;

            while(specie != null){

                String name = specie.getJSONObject("species").getString("name");
                String url = specie.getJSONObject("species").getString("url");
                String[] idFinder = url.split("/");
                Integer pokeId = Integer.parseInt(idFinder[idFinder.length - 1]);




                JSONArray details = specie.getJSONArray("evolution_details");

                Integer level = null;
                if (details.length() > 0){
                    JSONObject detail = details.getJSONObject(0);
                    if (!detail.isNull("min_level")){
                        level = detail.getInt("min_level");
                    }
                }


                evolution.add(pokeId, level, name);

                JSONArray evolves = specie.getJSONArray("evolves_to");

                specie = (evolves.length() > 0) ? evolves.getJSONObject(0) : null;

            }

            Log.d("EVOLUTIONAPI", evolution.toString());

        }
        catch (Exception e){
            Log.e("EVOLUTIONAPI", "Error al convertir el json: " + e.getMessage());
        }


        return evolution;
    }


    private static void printJSON(String json){
        Log.d("EVOLUTIONAPI", json);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
