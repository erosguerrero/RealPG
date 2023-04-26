package com.example.realpg.ui.main;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.realpg.DataManager;
import com.example.realpg.Evolution;
import com.example.realpg.EvolutionLoaderCallbacks;
import com.example.realpg.MainActivity;
import com.example.realpg.R;
import com.example.realpg.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class NewPoke extends AppCompatActivity {

    private static final int EVOLUTION_LOADER_ID = 0;
    private static final int MAX_POKES = 4;
    private EvolutionLoaderCallbacks evolutionLoaderCallbacks = new EvolutionLoaderCallbacks(this);
    private int times; //Contador de las veces que se ha llamado a la api
    private Random random;

    /*
        Como el número de imágnes, barras de progreso y botones es siempre el mismo,
        y su funcionalidad tambíen, se almacenan sus ids en unos arrays para que sea
        más sencillo trabajar con ellos
     */
    private int[] images;

    private int[] progress;

    private int[] buttons;

    //Evolutions guarda el las evoluciones que se han generado llamando a la api
    private ArrayList<Evolution> evolutions;

    private int money;

    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_poke);
        initFields();

        dm = new DataManager(this);

        JSONObject jsonExtra =  dm.load(DataManager.EXTRA_FILE_NAME);

        try {
            money = jsonExtra.getInt("Coins");
            //TODO QUITAR ESET SET de 75
            //money = 75;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        TextView coinsText = findViewById(R.id.coinsText);
        coinsText.setText(getString(R.string.prevDiamondHave)+" "+money+" \uD83D\uDC8E");


        //Se inicializa el boton de volver atras
        ImageButton back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });


        //Para todos los botones, cuando se pulse uno, se llama a la funcion
        //ButtonAction pasandole la evolución asociada a dicho botón
        for (int i = 0; i < 4; i++){
            Button button = findViewById(buttons[i]);
            int finalI = i;
            button.setOnClickListener(view -> {
                buttonAction(evolutions.get(finalI));
            });
            button.setVisibility(View.GONE);
            if (money < 10){
                button.setAlpha(.4f);
                button.setClickable(false);
            }
        }

        if (isNetworkAvailable()){
            apiCalls(null);
        }
        else{
            Utils.showNoInternetConnection(this);
        }


    }

    private void initFields(){
        images = new int[]{R.id.pokeImage1, R.id.pokeImage2, R.id.pokeImage3, R.id.pokeImage4};
        progress = new int[]{R.id.pokeBar1, R.id.pokeBar2, R.id.pokeBar3, R.id.pokeBar4};
        buttons = new int[]{R.id.pokeButton1, R.id.pokeButton2, R.id.pokeButton3, R.id.pokeButton4};
        random = new Random();
        evolutions = new ArrayList<Evolution>();
        times = 0;
      //  money = 10;
    }

    //ApiCall llama una única vez a la api, se le pasa un id previamente generado aleatoriamente
    private void apiCall(Integer id){

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(EVOLUTION_LOADER_ID) != null){
            loaderManager.initLoader(EVOLUTION_LOADER_ID, null, this.evolutionLoaderCallbacks);
        }

        Bundle bundle = new Bundle();
        bundle.putInt(EvolutionLoaderCallbacks.EXTRA_ID, id);

        LoaderManager.getInstance(this).restartLoader(EVOLUTION_LOADER_ID, bundle, evolutionLoaderCallbacks);

    }

    //LoadImage carga la imagen correspondiente con el id en su espacio adecuado
    private void loadImage(Integer id){
        int time = times - 1;
        ImageView imageView = findViewById(images[times - 1]);
        String url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png";
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

                Button b = findViewById(buttons[time]);
                b.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(imageView);
    }

    /*
    * ApiCalls es la funcion que actua como callback del loader, es la que añade la evolucion
    * obtenida a la lista, en caso de ser válida, y la que decide si se vuelve
    * a llamar a la api o ya se para
    *
    * */

    public void apiCalls(Evolution evolution){

        if ((isCorrect(evolution)) && times > 0){
            evolutions.add(evolution);
            loadImage(evolution.getIds().get(0));
        }

        if (!isCorrect(evolution) ||times < MAX_POKES)
            apiCall(Math.abs(random.nextInt() % 530));

        if (isCorrect(evolution))
            times++;


    }

    /*
    * Dada una evolución comprueba que es correcta
    * */
    private boolean isCorrect(Evolution evolution){

        if (evolution == null) return false;

        List<Integer> levels = evolution.getLevels();

        if (levels.size() <= 1) return false;

        for (Integer level : levels)
            if (level == null) return false;


        return true;
    }

    /*
    * Función a completar por Eros:
    * */

    private void buttonAction(Evolution evolution){
        DataManager dm = new DataManager(this);
        JSONObject pokemonJson = dm.load(dm.POKEMON_FILE_NAME);
        JSONObject extraJson = dm.load(dm.EXTRA_FILE_NAME);
        try {
            String evoId = String.valueOf(evolution.getId());

            //Modificaciones
            pokemonJson.put(evoId, evolution.toJson());
            extraJson.put("PokeChosen", evoId);

            //Guardado
            dm.save(dm.POKEMON_FILE_NAME,pokemonJson);
            dm.save(dm.EXTRA_FILE_NAME, extraJson);


            //Restar monedas
            money -= 10;
            TextView coinsText = findViewById(R.id.coinsText);
            coinsText.setText(getString(R.string.prevDiamondHave) +" "+money+" \uD83D\uDC8E");

            JSONObject jsonExtra =  dm.load(DataManager.EXTRA_FILE_NAME);

            try {
                jsonExtra.put("Coins", money);
                dm.save(DataManager.EXTRA_FILE_NAME,jsonExtra);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }


            Log.d("Monedas", "monedas : " + money);
            //Volver
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private boolean isNetworkAvailable() {
        return Utils.isNetworkAvailable(this);
    }
}