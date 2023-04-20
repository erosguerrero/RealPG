package com.example.realpg.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.realpg.EvolutionLoaderCallbacks;
import com.example.realpg.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NewPoke extends AppCompatActivity {

    private static final int EVOLUTION_LOADER_ID = 0;
    private static final int MAX_POKES = 4;
    private EvolutionLoaderCallbacks evolutionLoaderCallbacks = new EvolutionLoaderCallbacks(this);

    private int times;
    private Random random;
    private int[] images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poke);

        images = new int[]{R.id.pokeImage1, R.id.pokeImage2, R.id.pokeImage3, R.id.pokeImage4};


        random = new Random();
        times = 0;


        apiCalls(null);


    }

    private void apiCall(Integer id){

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(EVOLUTION_LOADER_ID) != null){
            loaderManager.initLoader(EVOLUTION_LOADER_ID, null, this.evolutionLoaderCallbacks);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EvolutionLoaderCallbacks.EXTRA_ID, id);

        LoaderManager.getInstance(this).restartLoader(EVOLUTION_LOADER_ID, bundle, evolutionLoaderCallbacks);

    }

    private void loadImage(Integer id){
        ImageView imageView = findViewById(images[times - 1]);
        Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png").into(imageView);
    }

    public void apiCalls(Integer id){

        if (times > 0) loadImage(id);

        if (times < MAX_POKES)
            apiCall(Math.abs(random.nextInt() % 530));

        times++;
    }
}