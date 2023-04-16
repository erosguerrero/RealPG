package com.example.realpg.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
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
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentMain2Binding;
import com.example.realpg.databinding.FragmentPage1Binding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;
/**
 * A placeholder fragment containing a simple view.
 */
public class Page1 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String FILE_NAME = "example.txt";
    private double LevelXP = 0;
 //   private PageViewModel pageViewModel;
    private FragmentPage1Binding binding;

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
}