package com.example.realpg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.realpg.ui.main.NewPoke;

import org.json.JSONException;

import java.util.List;

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
        try{
            NewPoke n = (NewPoke) context;
            n.apiCalls(data);
        } catch (java.lang.ClassCastException e) {
            //si lo primero produce error, es que se llamo desde la nueva clase Initial
            Initial n2 = (Initial) context;
            n2.apiCalls(data);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Evolution> loader) {

    }


}
