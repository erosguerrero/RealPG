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

import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.MainActivity;
import com.example.realpg.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListMyPoke extends AppCompatActivity {


    RecyclerView recycler;

   // int previousSelected = -1;



    private PokeListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO sustituir el id del first selected por el que tengamos almacenado
         adapter = new PokeListAdapter(this, 393);

        setContentView(R.layout.list_my_poke);


        //Recycler view
        recycler = findViewById(R.id.pokeRecycler);
        GridLayoutManager mGridLayoutManager;
        mGridLayoutManager = new GridLayoutManager(this, 3);
        recycler.setLayoutManager(mGridLayoutManager);
        //recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));

       // loadingText = findViewById(R.id.loadingText);
        //adapter.setLoadingTextView(loadingText);

        //TODO datos de prueba: sustituir por la lista de pokes que tengamos almacenados
         //TODO   (solo necesitamos su id y nombre para mostrar esta vista)

        List<PokeRecyclerInfo> demoPokes = new ArrayList<>();
        demoPokes.add(new PokeRecyclerInfo("Poke1", 1,1));
        demoPokes.add(new PokeRecyclerInfo("Poke124", 124,124));
        PokeRecyclerInfo firstSelected = new PokeRecyclerInfo("Poke393", 393,393);
       // firstSelected.isSelected = true;
        demoPokes.add(firstSelected);
        demoPokes.add(new PokeRecyclerInfo("Poke4",4,4));

        Random rand = new Random();

        for(int i = 0; i< 20; i++)
        {
            int idPoke = rand.nextInt((845) + 1) +10;
            String urlImg = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+idPoke+".png";
            demoPokes.add(new PokeRecyclerInfo("Poke"+idPoke, idPoke, idPoke));
        }
        adapter.setPokeListData(demoPokes);

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