package com.example.realpg.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import com.example.realpg.EvolutionChain;
import com.example.realpg.EvolutionChainLoader;

public class EvolutionChainCallbacks implements LoaderManager.LoaderCallbacks<EvolutionChain> {

    private Context context;
    private static final String EXTRA_ID = "id";

    public EvolutionChainCallbacks(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Loader<EvolutionChain> onCreateLoader(int id, @Nullable Bundle args) {

        Integer idChain = null;

        if (args != null){
            idChain = args.getInt(EXTRA_ID);
        }

        return new EvolutionChainLoader(context, idChain);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<EvolutionChain> loader, EvolutionChain data) {
        Log.d("MAIN", "Carga completada");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<EvolutionChain> loader) {

    }
}
