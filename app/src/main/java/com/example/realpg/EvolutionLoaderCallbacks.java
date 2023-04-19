package com.example.realpg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.realpg.ui.main.NewPoke;

public class EvolutionLoaderCallbacks implements LoaderManager.LoaderCallbacks<Evolution> {

    private Context context;
    public static final String EXTRA_ID = "id";

    public EvolutionLoaderCallbacks(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Loader<Evolution> onCreateLoader(int id, @Nullable Bundle args) {

        Integer idChain = (args != null) ? args.getInt(EXTRA_ID) : null;
        return new EvolutionLoader(context, idChain);

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Evolution> loader, Evolution data) {
        Log.d("EVOLUTIONAPI", "Carga completada");
        NewPoke n = (NewPoke) context;
        n.apiCalls(data.getIds().get(0));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Evolution> loader) {

    }
}
