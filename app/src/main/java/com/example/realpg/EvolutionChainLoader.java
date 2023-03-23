package com.example.realpg;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class EvolutionChainLoader extends AsyncTaskLoader<EvolutionChain> {

    //id chain
    private Integer id;

    public EvolutionChainLoader(Context context, Integer id){
        super(context);
        this.id = id;
    }

    @Nullable
    @Override
    public EvolutionChain loadInBackground() {
        String json = NetworkUtils.getEvolutionChain(id);
        return new EvolutionChain();
    }
}
