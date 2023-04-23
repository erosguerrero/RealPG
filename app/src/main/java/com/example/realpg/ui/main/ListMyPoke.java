package com.example.realpg.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.realpg.ActivityBasicInfo;
import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.DataManager;
import com.example.realpg.Evolution;
import com.example.realpg.MainActivity;
import com.example.realpg.Pokemon;
import com.example.realpg.R;
import com.github.mikephil.charting.renderer.scatter.ChevronUpShapeRenderer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class ListMyPoke extends AppCompatActivity {


    RecyclerView recycler;

   // int previousSelected = -1;



    private PokeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.list_my_poke);


        //Recycler view
        recycler = findViewById(R.id.pokeRecycler);
        GridLayoutManager mGridLayoutManager;
        mGridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(mGridLayoutManager);
        //recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

       // loadingText = findViewById(R.id.loadingText);
        //adapter.setLoadingTextView(loadingText);

        DataManager dm = new DataManager(this);
        JSONObject extraJson = dm.load(dm.EXTRA_FILE_NAME);
        int idSelected = -1;
        try {
            idSelected = extraJson.getInt("PokeChosen");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JSONObject pokemonJson = dm.load(dm.POKEMON_FILE_NAME);
        List<PokeRecyclerInfo> pokeRecyclerList = new ArrayList<>();



        String key;
        for (Iterator<String> it = pokemonJson.keys(); it.hasNext(); ) {
            key = it.next();
            String name; int idPoke;

                Evolution evoSelected = Evolution.createEvolutionFromJson(Integer.valueOf(key), pokemonJson);
                Pokemon currentPoke = evoSelected.getCurrentPokemon();
                name = currentPoke.getName();
                idPoke = currentPoke.getId();
                pokeRecyclerList.add(new PokeRecyclerInfo(name, idPoke,Integer.valueOf(key)));
                if(idSelected == Integer.valueOf(key)){
                    idSelected = currentPoke.getId();
                }
        }

        adapter = new PokeListAdapter(this, idSelected);
        adapter.setPokeListData(pokeRecyclerList);
        recycler.setAdapter(adapter);

        ImageButton backArrowButton = findViewById(R.id.backArrowListPoke);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ListMyPoke.this, MainActivity.class);
                int idSelected = adapter.getSelectedPoke().getIdPoke();
                int idEvoSelected = adapter.getSelectedPoke().getIdEvolve();
                Log.i("demo", "el id seleccionado es "+idSelected);

                intent.putExtra("idPokeSelected", idSelected);
                intent.putExtra("idEvoSelected", idEvoSelected);
                startActivity(intent);
            }
        });

    }

 /*   public void updateSelected(int idSelected) {

        adapter.updateSelected(idSelected, previousSelected);
        previousSelected = idSelected;
    }*/
}