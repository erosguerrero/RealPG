package com.example.realpg;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONObject;

public class PokemonLoader extends AsyncTaskLoader<Pokemon> {

    private Integer id;

    public PokemonLoader(Context context, Integer id){
        super(context);
        this.id = id;
    }

    @Nullable
    @Override
    public Pokemon loadInBackground() {

        if (id == null){
            Log.e("POKEMONAPI", "ID es null");
            return null;
        }

        String json = PokemonNetwork.getPokemon(id);

        if (json == null){
            Log.e("POKEMONAPI", "El json es null");
            return null;
        }

        Log.d("POKEMONAPI", json);

        return pokemonFromJson(json);
    }

    private Pokemon pokemonFromJson(String json){

        try{

            JSONObject jsonObject = new JSONObject(json);

            Integer id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String image = jsonObject.getJSONObject("sprites")
                                     .getJSONObject("other")
                                     .getJSONObject("official-artwork")
                                     .getString("front_default");

            return new Pokemon(id, name, image);

        }
        catch (Exception e){
            Log.e("POKEMONAPI", "Error al parsear el json");
            return null;
        }

    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

}
