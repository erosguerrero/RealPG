package com.example.realpg;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.example.realpg.ui.main.SectionsPagerAdapter;
import com.example.realpg.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int EVOLUTION_LOADER_ID = 0;
    private ActivityMainBinding binding;
    private EvolutionLoaderCallbacks evolutionLoaderCallbacks = new EvolutionLoaderCallbacks(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;


        LoaderManager loaderManager = LoaderManager.getInstance(this);
        if (loaderManager.getLoader(EVOLUTION_LOADER_ID) != null){
            loaderManager.initLoader(EVOLUTION_LOADER_ID, null, this.evolutionLoaderCallbacks);
        }

        int id = 1;
        Bundle bundle = new Bundle();
        bundle.putInt(EvolutionLoaderCallbacks.EXTRA_ID, id);

        Log.i("NETWORKAPI", "Bundle creado con id " + id);

        LoaderManager.getInstance(this).restartLoader(EVOLUTION_LOADER_ID, bundle, evolutionLoaderCallbacks);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}