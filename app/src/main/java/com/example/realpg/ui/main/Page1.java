package com.example.realpg.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.realpg.Activity;
import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.DataManager;
import com.example.realpg.Evolution;
import com.example.realpg.MainActivity;
import com.example.realpg.Pokemon;
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentMain2Binding;
import com.example.realpg.databinding.FragmentPage1Binding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.VISIBLE;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page1 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

 //   private PageViewModel pageViewModel;
    private FragmentPage1Binding binding;

    private Boolean onPause = false;
    private Boolean isActivityDisplaying = false;
    private ManageStopWatch manageStopWatch;
    private String sharedPrefFile;
    SharedPreferences mPreferences;

    public static Page1 newInstance(int index) {
        Page1 fragment = new Page1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    private DataManager DM;
    private JSONObject jsonPokemon, jsonExtra;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
      //  pageViewModel.setIndex(index);
        this.DM = new DataManager(getActivity());
        jsonPokemon = DM.load("pokemon.json");
        jsonExtra = DM.load("extra.json");
    }

    @Override
    public void onResume() {
        super.onResume();

        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        isActivityDisplaying = mPreferences.getBoolean("isActivityDisplaying", false);
        Boolean savedOnPause = mPreferences.getBoolean("onPause", true);
        double savedMinutes = mPreferences.getFloat("savedMinutes", -2);

        if(isActivityDisplaying){
            Log.i("Page1", "Inside displaying activity");
            LocalDateTime time = LocalDateTime.now();
            double actualMinutes = parseLocalDateTimeToFloat(time);
            double stopWatchTime = mPreferences.getFloat("stopWatchTime", -1);
            double minutesPassed = actualMinutes - savedMinutes;
            double timeToDisplay = stopWatchTime + minutesPassed;
            onPause = savedOnPause;
            if(!onPause) {
                Log.i("Page1", "no esta en pausa");
                setStopWatchTime(timeToDisplay);
            } else {
                setStopWatchTime(stopWatchTime);
            }
            showTimeWatch();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // solo hace falta guardar el cronometro, si se estaba ejecutando
        Log.i("Page1", "onPause: " + onPause + " displaying: " + isActivityDisplaying);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        //preferencesEditor.putFloat("stopWatchMinutes", (float)getStopWatchTimeMinutes());
        LocalDateTime time = LocalDateTime.now();
        preferencesEditor.putFloat("savedMinutes", parseLocalDateTimeToFloat(time));
        preferencesEditor.putFloat("stopWatchTime", (float)getStopWatchTimeMinutes());
        preferencesEditor.putBoolean("onPause", onPause);
        preferencesEditor.putBoolean("isActivityDisplaying", isActivityDisplaying);
        Log.i("Page1", "saved minutes: " + parseLocalDateTimeToFloat(time));
        preferencesEditor.apply();
        onPause = true; // necesario para q no se quede el hilo contando de la activity, pq cuando se vuelve a crear
        // la activity se crea otro hilo y cuenta x2 (o algo asi)
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // para q cada vez q se cierre del to.do la app, se muestre la pantalla de actividades recientes
        // ojala, pero no seirve pq onViewCreated creo q se ejecuta antes q onResume
        setStopWatchTime(0);
        onPause = true;
        isActivityDisplaying = false;
        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("onPause", onPause);
        preferencesEditor.putBoolean("isActivityDisplaying", isActivityDisplaying);
    }*/

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPage1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();


        //TODO DEMO BORRAR
        DM.demoPokemonJson();
       // DM.demoExtraJson();//comentado porque ya se genera vacio y no necesario para datos de prueba
        //FIN DEMO

        //LATEST ACTIVITIES
        //TODO Controlar falta de extra.json
        //TODO creo que ya esta hecho

        updateLatestActivitiesPanel();
   /*     try {
            JSONObject last3json = jsonExtra.getJSONObject("Last3");
            LinearLayout latestActCont =  root.findViewById(R.id.LatestActivitiesContainer);
            //TODO ¿Que hago con la ID  de la tarea?
            View item1 = getLayoutInflater().inflate(R.layout.item_panel, null);
            TextView tv1 = item1.findViewById(R.id.itemName);
            tv1.setText(last3json.getJSONObject("1").getString("nombre"));
            ImageButton startActivityButton = item1.findViewById(R.id.startActivityButton);
            startActivityButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("Panel1", "start activity1");
                    onPause = false;
                    isActivityDisplaying = true;
                    preferencesEditor.putBoolean("onPause", onPause);
                    preferencesEditor.putBoolean("isActivityDisplaying", isActivityDisplaying);
                    showTimeWatch();
                }
            });
            latestActCont.addView(item1);

            View item2 = getLayoutInflater().inflate(R.layout.item_panel, null);
            TextView t2 = item2.findViewById(R.id.itemName);
            t2.setText(last3json.getJSONObject("2").getString("nombre"));
            latestActCont.addView(item2);

            View item3 = getLayoutInflater().inflate(R.layout.item_panel, null);
            TextView tv3 = item3.findViewById(R.id.itemName);
            tv3.setText(last3json.getJSONObject("3").getString("nombre"));
            latestActCont.addView(item3);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
*/

        ImageButton pokeball = binding.pokeballButton;//getActivity().findViewById(R.id.pokeballButton);
        ImageButton pokeBall2 = binding.pokeballButton2;
        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        pokeball.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), ListMyPoke.class);
            // intent.putExtra("categoryName", pe.getLabel());
            startActivity(intent);
            //hola
        });

        pokeBall2.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), NewPoke.class);
            startActivity(intent);
        });

        pokeBall2.setOnClickListener(v -> {
            Intent intent = new Intent(this.getActivity(), NewPoke.class);
            startActivity(intent);
        });

        Timer timer = new Timer();
        onPause = true;
        TextView stopWatch = binding.stopWatch;
        ImageButton restartButton = binding.restartButton;
        ImageButton pauseButton = binding.pauseButton;
        ImageButton playButton = binding.playButton; //tmb se puede usar getActivity().findViewById como hago abajo
        ImageButton endButton = binding.endButton;
        if(manageStopWatch == null) {
            manageStopWatch = new ManageStopWatch();
            timer.schedule(manageStopWatch, 1000, 1000);
        }

        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "restart activity1");
                setStopWatchTime(0);
                preferencesEditor.putBoolean("isActivityDisplaying", true);
                LocalDateTime time = LocalDateTime.now();
                preferencesEditor.putFloat("savedMinutes", parseLocalDateTimeToFloat(time));
                preferencesEditor.putFloat("stopWatchTime", (float)getStopWatchTimeMinutes());
                preferencesEditor.apply();

            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "pause activity1");
                onPause = true;
                preferencesEditor.putBoolean("onPause", onPause);
                preferencesEditor.putBoolean("isActivityDisplaying", true);
                LocalDateTime time = LocalDateTime.now();
                preferencesEditor.putFloat("savedMinutes", parseLocalDateTimeToFloat(time));
                preferencesEditor.putFloat("stopWatchTime", (float)getStopWatchTimeMinutes());
                preferencesEditor.apply();
                pauseButton.setVisibility(View.GONE);
                playButton.setVisibility(VISIBLE);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "play activity1");
                onPause = false;
                preferencesEditor.putBoolean("onPause", onPause);
                preferencesEditor.putBoolean("isActivityDisplaying", true);
                preferencesEditor.apply();
                playButton.setVisibility(View.GONE);
                pauseButton.setVisibility(VISIBLE);
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "end activity1");
                onPause = true;
                isActivityDisplaying = false;
                preferencesEditor.putBoolean("onPause", onPause);
                preferencesEditor.putBoolean("isActivityDisplaying", false);
                LocalDateTime time = LocalDateTime.now();
                preferencesEditor.putFloat("savedMinutes", parseLocalDateTimeToFloat(time));
                preferencesEditor.putFloat("stopWatchTime", (float)getStopWatchTimeMinutes());
                preferencesEditor.apply();
                Log.i("Page1", "activity displaying inside endbutton: " + isActivityDisplaying);

                showLatestActivities();
                String currentTime = getStopWatchTime();
                Log.i("Page1", "current time: " + currentTime);
                setStopWatchTime(0);

                isActivityDisplaying = mPreferences.getBoolean("isActivityDisplaying", false);
                Log.i("Page1", "activity displaying inside endbutton2: " + isActivityDisplaying);
            }
        });

        // ImageView demo = binding.selectedPokemonImage;//getActivity().findViewById(R.id.selectedPokemonImage);

        //demo.setImageDrawable(Page1.LoadImageFromWebOperations("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png"));

        //new DownloadImageTask(demo).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png");

        // Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png").into(demo);


        int idEvoSelected = -1;
        try {
            idEvoSelected = jsonExtra.getInt("PokeChosen");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        if(idEvoSelected != -1){ //-1 == NEW USER
            Evolution evoObject = Evolution.createEvolutionFromJson(idEvoSelected, jsonPokemon);
            Pokemon currentPoke = evoObject.getCurrentPokemon();
            changeLvl(root,evoObject.getCurrentXp());
            ImageView demo = binding.selectedPokemonImage;
            Glide.with(this).load(currentPoke.getImage()).into(demo);

            try {
                JSONObject pokemonData = jsonPokemon.getJSONObject(String.valueOf(idEvoSelected));

            } catch (JSONException e) {
                //throw new RuntimeException(e);
            }

        }

        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }


    //TODO: actualizar lista de recientes dinamicamente
    public void updateLatestActivitiesPanel()
    {
        mPreferences = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();

        //TODO crear nueva clase listener para el comienzo de actividades y ponerselo a estas
        JSONObject jsonExtra = DM.load(DataManager.EXTRA_FILE_NAME);

        //JSONObject last3json = jsonExtra.getJSONObject("Last3");
        try {
            JSONArray last3 = jsonExtra.getJSONArray("Last3");

            //last3.put(0);
            //  last3.put(1);
            //  last3.put(2);
            LinearLayout latestActCont = binding.LatestActivitiesContainer;
            if(last3.length() ==0)
                binding.noLatestText.setVisibility(VISIBLE);
            else
                binding.noLatestText.setVisibility(View.GONE);

            for(int i = 0; i < last3.length(); i++)
            {
                View item = getLayoutInflater().inflate(R.layout.item_panel, null);
                TextView tv = item.findViewById(R.id.itemName);
                //dado el id guardado, leemos el archivo de actividades y cogemos la actividad con ese id
                int idAct = last3.getInt(i);
                JSONObject jsonActivities = DM.load(DataManager.ACTIVITIES_FILE_NAME);

                Activity ac = Activity.createActivityFromJson(jsonActivities.getJSONObject(idAct+""), idAct);
                tv.setText(ac.getName());
                ImageButton startActivityButton = item.findViewById(R.id.startActivityButton);
                startActivityButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("Panel1", "start activity1");
                        onPause = false;
                        isActivityDisplaying = true;
                        preferencesEditor.putBoolean("onPause", onPause);
                        preferencesEditor.putBoolean("isActivityDisplaying", isActivityDisplaying);
                        showTimeWatch();

                    }
                });

                latestActCont.addView(item);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void changeLvl(View root, double lvl){
        ProgressBar xpBar = root.findViewById(R.id.selectedPokemonLevel);
        TextView expLabel = root.findViewById(R.id.xpLabel);

        //
        expLabel.setText("Lvl "+(int)Math.floor(lvl));

        xpBar.setProgress((int)Math.floor(lvl%1*100),true);
    }

    private void showTimeWatch(){
        // hide latest activities related views
        TextView recentActividadesLabel = getActivity().findViewById(R.id.recentActividadesLabel);
        recentActividadesLabel.setVisibility(View.GONE);

        LinearLayout latestAcivitiesList = getActivity().findViewById(R.id.LatestActivitiesContainer);
        latestAcivitiesList.setVisibility(View.GONE);

        // show time controls of activity timeWatch
        View activityInProgressHeader = getActivity().findViewById(R.id.activityInProgressHeader);
        activityInProgressHeader.setVisibility(VISIBLE);

        TextView activityInProgressName = getActivity().findViewById(R.id.activityInProgressName);
        activityInProgressName.setVisibility(VISIBLE);

        ImageButton restartButton = getActivity().findViewById(R.id.restartButton);
        restartButton.setVisibility(VISIBLE);

        if(!onPause) {
            ImageButton playButton = getActivity().findViewById(R.id.playButton);
            playButton.setVisibility(View.GONE);

            ImageButton pauseButton = getActivity().findViewById(R.id.pauseButton);
            pauseButton.setVisibility(VISIBLE);
        }

        if(onPause) {
            ImageButton pauseButton = getActivity().findViewById(R.id.pauseButton);
            pauseButton.setVisibility(View.GONE);

            ImageButton playButton = getActivity().findViewById(R.id.playButton);
            playButton.setVisibility(View.VISIBLE);
        }

        ImageButton endButton = getActivity().findViewById(R.id.endButton);
        endButton.setVisibility(VISIBLE);

        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
        stopWatch.setVisibility(VISIBLE);
        if(!isActivityDisplaying) stopWatch.setText("00:00:00");
    }

    private void showLatestActivities(){
        // hide latest activities related views
        TextView recentActividadesLabel = getActivity().findViewById(R.id.recentActividadesLabel);
        recentActividadesLabel.setVisibility(VISIBLE);

        LinearLayout latestAcivitiesList = getActivity().findViewById(R.id.LatestActivitiesContainer);
        latestAcivitiesList.setVisibility(VISIBLE);

        // show time controls of activity timeWatch
        View activityInProgressHeader = getActivity().findViewById(R.id.activityInProgressHeader);
        activityInProgressHeader.setVisibility(View.GONE);

        TextView activityInProgressName = getActivity().findViewById(R.id.activityInProgressName);
        activityInProgressName.setVisibility(View.GONE);

        ImageButton restartButton = getActivity().findViewById(R.id.restartButton);
        restartButton.setVisibility(View.GONE);

        ImageButton pauseButton = getActivity().findViewById(R.id.pauseButton);
        pauseButton.setVisibility(View.GONE);

        ImageButton playButton = getActivity().findViewById(R.id.playButton);
        playButton.setVisibility(View.GONE);

        ImageButton endButton = getActivity().findViewById(R.id.endButton);
        endButton.setVisibility(View.GONE);

        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
        stopWatch.setVisibility(View.GONE);
    }

    class ManageStopWatch extends TimerTask {
        public void run() {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addSecondToStopWatch();
                    }
                });
            }
        }

        private void addSecondToStopWatch(){
            if(!onPause) {
                TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
                String[] parts = stopWatch.getText().toString().split(":");
                Integer hours = Integer.parseInt(parts[0]);
                Integer mins = Integer.parseInt(parts[1]);
                Integer secs = Integer.parseInt(parts[2]);
                //Log.i("Panel1", "Hours: " + hours + " Mins: " + mins + " Seconds: " + secs);

                secs +=1;

                if(secs == 60) {
                    secs = 0;
                    mins +=1;
                }

                if(mins == 60){
                    mins = 0;
                    hours += 1;
                }

                if(hours == 24){
                    secs = 0;
                    mins = 0;
                    hours = 0;
                }

                Log.i("Page1", "Hours: " + hours.toString() + " Mins: " + mins.toString() + " Seconds: " + secs.toString());

                String actualTime = String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
                stopWatch.setText(actualTime);
            }
        }
    }

    private void setStopWatchTime(double minutes){
        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);

        String stringMinutes = String.valueOf(minutes);
        //Log.i("Page1", "stringMins: " + stringMinutes);
        String[] parts = stringMinutes.split("\\.");

        double intPart = Double.parseDouble(parts[0]);
        //int decimalPart = Integer.parseInt(parts[1]);
        //Log.i("Page1", "decimalPart: " + decimalPart);
        double decimals = Double.parseDouble("0." + parts[1]);
        //Log.i("Page1", "decimals: " + decimals);

        Integer hours = (int)intPart/60;
        Integer mins = (int)intPart%60;
        Integer secs = (int)(decimals*60);
        //Log.i("Page1", "Hours: " + hours.toString() + " Mins: " + mins.toString() + " Seconds: " + secs.toString());

        String actualTime = String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
        stopWatch.setText(actualTime);
    }

    private String getStopWatchTime(){
        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
        String[] parts = stopWatch.getText().toString().split(":");
        Integer hours = Integer.parseInt(parts[0]);
        Integer mins = Integer.parseInt(parts[1]);
        Integer secs = Integer.parseInt(parts[2]);
        String actualTime = String.format("%02d", hours) + ":" + String.format("%02d", mins) + ":" + String.format("%02d", secs);
        return actualTime;
    }

    private double getStopWatchTimeMinutes(){
        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
        String[] parts = stopWatch.getText().toString().split(":");
        Integer hours = Integer.parseInt(parts[0]);
        Integer mins = Integer.parseInt(parts[1]);
        Integer secs = Integer.parseInt(parts[2]);
        return hours*60 + mins + (double)secs/60;
    }

    private float parseLocalDateTimeToFloat(LocalDateTime time){
        //returns minutes in float
        float hours = time.getHour();
        float minutes = time.getMinute();
        float seconds = time.getSecond();
        Log.i("Page1", "Inside parseLocalDateTime " + hours + " "+ minutes+" "+seconds);
        return hours*60 + minutes + seconds/60;
    }

}