package com.example.realpg;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

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
        if (id != null){
            String json = NetworkUtils.getEvolutionChain(id);
            if (json == null)
                Log.d("NETWORKAPI", "JSON ES NULL");
            else
                Log.d("NETWORKAPI", json);
            return new Evolution();
        }
        else{
            Log.e("NETWORKAPI", "ID es null");
            return null;
        }


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
