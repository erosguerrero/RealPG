package com.example.realpg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class PokemonLoaderCallbacks implements LoaderManager.LoaderCallbacks<Pokemon> {

    private Context context;
    public static final String EXTRA_ID = "id";

    public PokemonLoaderCallbacks(Context context){
        this.context = context;
    }


    @NonNull
    @Override
    public Loader<Pokemon> onCreateLoader(int id, @Nullable Bundle args) {
        Integer idPokemon = (args != null) ? args.getInt(EXTRA_ID) : null;
        return new PokemonLoader(context, idPokemon);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Pokemon> loader, Pokemon data) {
        Log.d("POKEMONAPI", "Carga completada: " + data.toString());

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Pokemon> loader) {

    }
}
