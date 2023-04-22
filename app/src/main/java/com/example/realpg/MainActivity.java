package com.example.realpg;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    DataManager dm;

    private List<ActivityBasicInfo> activitesBasicInfoList;

    //private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: datos de prueba, quitar en version final
        //solo llamarla una vez y luego comentarla
        //DataManager.demoCreateActivitiesData(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;

        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

     /*   dm = new DataManager(this);
        activitesBasicInfoList = new ArrayList<>();
        JSONObject activitiesJson = dm.load(DataManager.ACTIVITIES_FILE_NAME);
        Log.i("demo2","json leido de fichero"+ activitiesJson.toString());

        ArrayList<String> keys = new ArrayList<>();
        String key;

        for (Iterator<String> it = activitiesJson.keys(); it.hasNext(); ) {

            key = it.next();
            String name;
            String cat;
            try {
                name = activitiesJson.getJSONObject(key).getString("name");
                cat = activitiesJson.getJSONObject(key).getString("cat");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            activitesBasicInfoList.add(new ActivityBasicInfo(name,Integer.parseInt(key),cat));

            keys.add(key);
        }
        Log.i("demo2", "keys: "+keys.toString());

        //datos de prueba para actividades


      */
       /* activitesBasicInfoList = new ArrayList<>();
        for(int i = 0; i < 1; i++)
        {
            activitesBasicInfoList.add(new ActivityBasicInfo("Actividad "+i,i, Category.CASA));
        }

        for(int i = 1; i < 3; i++)
        {
            activitesBasicInfoList.add(new ActivityBasicInfo("Actividad "+i,i, Category.BIENESTAR));
        }

        for(int i = 3; i < 4; i++)
        {
            activitesBasicInfoList.add(new ActivityBasicInfo("Actividad "+i,i, Category.ESTUDIOS));
        }*/

        //pieChart = findViewById(R.id.activity_piechart);
        //setupPieChart();
        //loadPieChartData();
    }


    public List<ActivityBasicInfo> getActivitesBasicInfoList() {
        return activitesBasicInfoList;
    }

    public void addActBasInf(ActivityBasicInfo ab)
    {
        activitesBasicInfoList.add(ab);
    }




}