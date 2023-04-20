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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    private List<ActivityBasicInfo> activitesBasicInfoList;

    //private PieChart pieChart;
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

        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //datos de prueba para actividades
        activitesBasicInfoList = new ArrayList<>();
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
        }

        //pieChart = findViewById(R.id.activity_piechart);
        //setupPieChart();
        //loadPieChartData();
    }


    public List<ActivityBasicInfo> getActivitesBasicInfoList() {
        return activitesBasicInfoList;
    }




}