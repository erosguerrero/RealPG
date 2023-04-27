package com.example.realpg;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;

import com.example.realpg.ui.main.Page3;
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

import androidx.core.app.NotificationCompat;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity{
    private ActivityMainBinding binding;

    DataManager dm;

    NotificationChannel channel;
    NotificationManager notificationManager;



    private List<ActivityBasicInfo> activitesBasicInfoList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);



        ArrayList<Activity> alist = new ArrayList<>();
        Activity a1 = new Activity(100, 5, null, "act1", Category.CASA);
        Activity a2 = new Activity(101, 1, null, "act2", Category.CASA);
        Activity a3 = new Activity(102, 3, null, "act3", Category.CASA);
        Activity a4 = new Activity(103, 10, null, "act4", Category.CASA);
        Activity a5 = new Activity(104, 100, null, "act5", Category.CASA);
        Activity a6 = new Activity(105, 6, null, "act6", Category.CASA);
        alist.add(a1);
        alist.add(a2);
        alist.add(a3);
        alist.add(a4);
        alist.add(a5);
        alist.add(a6);

        Collections.sort(alist, new ActivityComparator());


        if(DataManager.initFiles(this))
        {
            Intent intent = new Intent(MainActivity.this, Initial.class);
            startActivity(intent);
        }

        channel = new NotificationChannel("channel_id", "Nombre del canal", NotificationManager.IMPORTANCE_DEFAULT);


        notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "channel_id")
                .setSmallIcon(R.drawable.playicon)
                .setContentTitle("Título de la notificación")
                .setContentText("Texto de la notificación")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
               // tabSelected = position;
                if(position == 2)
                {
                    try {
                        Page3 p3 = Page3.getInstance();
                        if(p3!=null)
                            p3.updatePieChartData();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;


        SharedPreferences mPreferences = getSharedPreferences("previousTab", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        int tabIndex = mPreferences.getInt("prevTab", -1);
        boolean tabHasToChange = mPreferences.getBoolean("tabHasToChange", false);
        if(tabHasToChange) {
            setTab(tabIndex);
        } else {
            setTab(1);
        }
        preferencesEditor.putBoolean("tabHasToChange", false);
        preferencesEditor.apply();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dm = new DataManager(this);
        activitesBasicInfoList = new ArrayList<>();
        JSONObject activitiesJson = dm.load(DataManager.ACTIVITIES_FILE_NAME);

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


    }


    public List<ActivityBasicInfo> getActivitesBasicInfoList() {
        return activitesBasicInfoList;
    }

    public void addActBasInf(ActivityBasicInfo ab)
    {
        activitesBasicInfoList.add(ab);
    }


    public void setTab(int tabIndex){
        TabLayout tabs = binding.tabs;
        TabLayout.Tab tab = tabs.getTabAt(tabIndex);
        tab.select();
    }

    public void showNotification(int notificationId,NotificationCompat.Builder builder)
    {
        notificationManager.notify(notificationId, builder.build());
    }

    public void hideNotification(int notificationId)
    {
        notificationManager.cancel(notificationId);
    }



}