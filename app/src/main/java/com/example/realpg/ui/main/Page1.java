package com.example.realpg.ui.main;

import android.content.Context;
import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.MainActivity;
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentMain2Binding;
import com.example.realpg.databinding.FragmentPage1Binding;

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
    private static final String FILE_NAME = "example.txt";
    private double LevelXP = 0;
 //   private PageViewModel pageViewModel;
    private FragmentPage1Binding binding;

    private Boolean onPause;
    private ManageStopWatch manageStopWatch;
    public static Page1 newInstance(int index) {
        Page1 fragment = new Page1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }
    private Context contexto;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto = context;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
      //  pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPage1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        save(contexto);
        load(contexto);
        //XP BAR
        changeLvl(root,this.LevelXP);

        LinearLayout latestActCont =  root.findViewById(R.id.LatestActivitiesContainer);

        View item1 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv1 = item1.findViewById(R.id.itemName);
        tv1.setText("Actividad mas usada");
        ImageButton startActivityButton = item1.findViewById(R.id.startActivityButton);
        startActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "start activity1");
                showTimeWatch();
                onPause = false;
            }
        });
        latestActCont.addView(item1);

        View item2 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView t2 = item2.findViewById(R.id.itemName);
        t2.setText("Segunda actividad mas usada");
        latestActCont.addView(item2);

        View item3 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv3 = item3.findViewById(R.id.itemName);
        tv3.setText("Tercera actividad mas usada con un texto mas largo");
        latestActCont.addView(item3);

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

        //ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(1);


        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "restart activity1");
                //stopWatch.setText(0);
                setStopWatchTime(128.017);
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "pause activity1");
                onPause = true;
                pauseButton.setVisibility(View.GONE);
                playButton.setVisibility(VISIBLE);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "play activity1");
                onPause = false;
                playButton.setVisibility(View.GONE);
                pauseButton.setVisibility(VISIBLE);
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel1", "end activity1");
                onPause = true;
                showLatestActivities();
                String currentTime = getStopWatchTime();
                Log.i("Page1", "current time: " + currentTime);
                Log.i("Page1", "minutes: " + getStopWatchTimeMinutes());
            }
        });




        // ImageView demo = binding.selectedPokemonImage;//getActivity().findViewById(R.id.selectedPokemonImage);

        //demo.setImageDrawable(Page1.LoadImageFromWebOperations("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png"));

        //new DownloadImageTask(demo).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png");

        // Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/394.png").into(demo);
        int idSelected = getActivity().getIntent().getIntExtra("idPokeSelected", -1);
        if(idSelected != -1)//nunca habia seleccionado un pokemon si es == -1
        {
            ImageView demo = binding.selectedPokemonImage;
            Glide.with(this).load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+idSelected+".png").into(demo);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void save(Context context) {
        String text = "2.2";
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            //Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME,

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(Context context) {
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            this.LevelXP = Double.valueOf(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

        ImageButton pauseButton = getActivity().findViewById(R.id.pauseButton);
        pauseButton.setVisibility(VISIBLE);

        ImageButton endButton = getActivity().findViewById(R.id.endButton);
        endButton.setVisibility(VISIBLE);

        TextView stopWatch = getActivity().findViewById(R.id.stopWatch);
        stopWatch.setVisibility(VISIBLE);
        stopWatch.setText("00:00:00");
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    addSecondToStopWatch();
                }
            });

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

}