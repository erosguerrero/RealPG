package com.example.realpg.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.realpg.Evolution;
import com.example.realpg.EvolutionLoaderCallbacks;
import com.example.realpg.MainActivity;
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
    private int[] progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poke);
        initFields();

        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            //hola
        });

        apiCalls(null);


    }

    private void initFields(){
        images = new int[]{R.id.pokeImage1, R.id.pokeImage2, R.id.pokeImage3, R.id.pokeImage4};
        progress = new int[]{R.id.pokeBar1, R.id.pokeBar2, R.id.pokeBar3, R.id.pokeBar4};
        random = new Random();
        times = 0;
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
        int time = times - 1;
        ImageView imageView = findViewById(images[times - 1]);
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png";
        Log.d("Imagenes", url);
        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return hideBar();
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return hideBar();
            }

            private boolean hideBar(){
                View v  = findViewById(progress[time]);
                v.setVisibility(View.GONE);
                return false;
            }
        }).into(imageView);
    }

    public void apiCalls(Evolution evolution){

        if ((isCorrect(evolution)) && times > 0)
            loadImage(evolution.getIds().get(0));

        if (!isCorrect(evolution) ||times < MAX_POKES)
            apiCall(Math.abs(random.nextInt() % 530));

        if (isCorrect(evolution))
            times++;


    }

    private boolean isCorrect(Evolution evolution){

        if (evolution == null) return false;

        List<Integer> levels = evolution.getLevels();

        if (levels.size() <= 1) return false;

        for (Integer level : levels)
            if (level == null) return false;


        return true;
    }
}