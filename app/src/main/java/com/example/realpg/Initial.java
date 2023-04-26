package com.example.realpg;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Initial  extends AppCompatActivity {



    private static final int EVOLUTION_LOADER_ID = 0;
    private static final int MAX_POKES = 4;
    private EvolutionLoaderCallbacks evolutionLoaderCallbacks = new EvolutionLoaderCallbacks(this);
    private int times; //Contador de las veces que se ha llamado a la api
    private Random random;

    int idSelected = -1;

    Evolution evolutionSelected;

    int indexSelected = -1;

    /*
        Como el número de imágnes, barras de progreso y botones es siempre el mismo,
        y su funcionalidad tambíen, se almacenan sus ids en unos arrays para que sea
        más sencillo trabajar con ellos
     */
    private int[] images;

    private int[] progress;

    private boolean[] readies;

  //  private int[] buttons;

    //Evolutions guarda el las evoluciones que se han generado llamando a la api
    private ArrayList<Evolution> evolutions;

    DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.initial_view);



        initFields();

        dm = new DataManager(this);

        setVisibilityPage1(View.VISIBLE);

        findViewById(R.id.next12).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityPage1(View.GONE);
                setVisibilityPage2(View.VISIBLE);
            }
        });

        findViewById(R.id.next23).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityPage2(View.GONE);
                setVisibilityPage3(View.VISIBLE);
            }
        });
        findViewById(R.id.prev21).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityPage1(View.VISIBLE);
                setVisibilityPage2(View.GONE);
            }
        });
        findViewById(R.id.prev32).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityPage2(View.VISIBLE);
                setVisibilityPage3(View.GONE);
            }
        });

        findViewById(R.id.comenzar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setVisibilityPage3(View.GONE);
                setVisibilityPage4(View.VISIBLE);
            }
        });


        for (int i = 0; i < 4; i++){
            ImageView button = findViewById(images[i]);
            int finalI = i;
            button.setOnClickListener(view -> {
                buttonAction(evolutions.get(finalI), finalI);
            });
            button.setEnabled(false);
           /* button.setVisibility(View.GONE);
            if (money < 10){
                button.setEnabled(false);
            }*/
        }

        if (isNetworkAvailable()){
            apiCalls(null);
        }
        else{
            Utils.showNoInternetConnection(this);
        }


        findViewById(R.id.chooseFirst).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(idSelected == -1)
                {
                    Toast.makeText(Initial.this, R.string.warningFirstPoke, Toast.LENGTH_LONG).show();
                    return;
                }


                try {
                    JSONObject jsonExtra = dm.load(DataManager.EXTRA_FILE_NAME);
                    JSONObject pokemonJson = dm.load(dm.POKEMON_FILE_NAME);

                    pokemonJson.put(idSelected+"", evolutionSelected.toJson());
                    jsonExtra.put("PokeChosen",idSelected);

                    dm.save(dm.POKEMON_FILE_NAME,pokemonJson);
                    dm.save(DataManager.EXTRA_FILE_NAME, jsonExtra);

                    Intent intent = new Intent(Initial.this, MainActivity.class);
                    startActivity(intent);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    //View.VISIBLE
    //View.GONE;
    private void setVisibilityPage1(int state)
    {
        findViewById(R.id.text1Bienv).setVisibility(state);
        findViewById(R.id.text1SubTitle).setVisibility(state);
        findViewById(R.id.text1title).setVisibility(state);
        findViewById(R.id.next12).setVisibility(state);
    }

    private void setVisibilityPage2(int state)
    {
        findViewById(R.id.iconoReloj).setVisibility(state);
        findViewById(R.id.iconoStats).setVisibility(state);
        findViewById(R.id.text2Stats).setVisibility(state);
        findViewById(R.id.text2Reloj).setVisibility(state);
        findViewById(R.id.next23).setVisibility(state);
        findViewById(R.id.prev21).setVisibility(state);
    }


    private void setVisibilityPage3(int state)
    {
        findViewById(R.id.iconoUp).setVisibility(state);
        findViewById(R.id.iconoCoins).setVisibility(state);
        findViewById(R.id.text3Up).setVisibility(state);
        findViewById(R.id.text3Coins).setVisibility(state);
        findViewById(R.id.text3End).setVisibility(state);
        findViewById(R.id.comenzar).setVisibility(state);
        findViewById(R.id.prev32).setVisibility(state);
    }

    private void setVisibilityPage4(int state)
    {
        findViewById(R.id.text4Info).setVisibility(state);
        findViewById(R.id.text4Info2).setVisibility(state);
        findViewById(R.id.pokeImageP41).setVisibility(state);
        findViewById(R.id.pokeImageP42).setVisibility(state);
        findViewById(R.id.pokeImageP43).setVisibility(state);
        findViewById(R.id.pokeImageP44).setVisibility(state);
        findViewById(R.id.chooseFirst).setVisibility(state);

        if(state == View.VISIBLE)
        {
            if(!readies[0])
                findViewById(R.id.pokeBarP41).setVisibility(View.VISIBLE);
            if(!readies[1])
                findViewById(R.id.pokeBarP42).setVisibility(View.VISIBLE);
            if(!readies[2])
                findViewById(R.id.pokeBarP43).setVisibility(View.VISIBLE);
            if(!readies[3])
                findViewById(R.id.pokeBarP44).setVisibility(View.VISIBLE);
        }
    }


    private void initFields(){
        images = new int[]{R.id.pokeImageP41, R.id.pokeImageP42, R.id.pokeImageP43, R.id.pokeImageP44};
        progress = new int[]{R.id.pokeBarP41, R.id.pokeBarP42, R.id.pokeBarP43, R.id.pokeBarP44};
        readies = new boolean[]{false, false, false, false};
      //  buttons = new int[]{R.id.pokeButton1, R.id.pokeButton2, R.id.pokeButton3, R.id.pokeButton4};
        random = new Random();
        evolutions = new ArrayList<Evolution>();
        times = 0;
        //  money = 10;
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
                readies[time] = true;
                v.setVisibility(View.GONE);

                ImageView button = findViewById(images[time]);
                button.setEnabled(true);

              //  Button b = findViewById(buttons[time]);
              //  b.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(imageView);
    }


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

    private void buttonAction(Evolution evolution, int index){
        Log.i("demo2", "poke pulsado con id "+ evolution.getId()+ " e index: "+ index);
        idSelected = evolution.getId();
        indexSelected = index;
        evolutionSelected = evolution;
        findViewById(R.id.pokeImageP41).setSelected(false);
        findViewById(R.id.pokeImageP42).setSelected(false);
        findViewById(R.id.pokeImageP43).setSelected(false);
        findViewById(R.id.pokeImageP44).setSelected(false);

        if(index == 0)
            findViewById(R.id.pokeImageP41).setSelected(true);
        if(index == 1)
            findViewById(R.id.pokeImageP42).setSelected(true);
        if(index == 2)
            findViewById(R.id.pokeImageP43).setSelected(true);
        if(index == 3)
            findViewById(R.id.pokeImageP44).setSelected(true);
       /* DataManager dm = new DataManager(this);
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
            coinsText.setText("Monedas "+money);

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
        }*/

    }


    private boolean isNetworkAvailable() {
        return Utils.isNetworkAvailable(this);
    }

}
