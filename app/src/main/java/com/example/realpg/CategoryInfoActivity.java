package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class CategoryInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info);

        String catUpperCase = getIntent().getStringExtra("categoryName");

        String title = Activity.CatStrFormated(catUpperCase);

        TextView categoryTitle = findViewById(R.id.categoryTitle);
        categoryTitle.setText(title);

        List<Activity> acList = Activity.getActivitiesOfCat(this, Activity.strToCategory(catUpperCase));

        LinearLayout layout = findViewById(R.id.scrollLayout);
        //carga las actividades de la categoria
        for(Activity ac: acList)
        {
            View p = getLayoutInflater().inflate(R.layout.activity_info_panel, null);
            TextView activityName = p.findViewById(R.id.activityName);
            activityName.setText(ac.getName());
            TextView totalTimeText = p.findViewById(R.id.activityTime);
            totalTimeText.setText(ac.getFormattedTimeTotal());

            p.setOnClickListener(new MyOnClickListenerRunAct(ac.getIdActivity(), this,2));
            layout.addView(p);
        }

        //carga el top actividades de la categoria
       // List<Activity> acTop = Activity.getTop3Act(this);
        List<Activity> acTop = Activity.getTop3ActCat(this,Activity.strToCategory(catUpperCase));
        for(int i = 0; i < acTop.size(); i++)
        {
            loadPanelTop(acTop.get(i), i);
        }


        ImageButton backArrowButton = findViewById(R.id.backArrow);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }


    private void loadPanelTop(Activity ac, int index)
    {
        TextView tv = new TextView(this);
        TextView tvTime = new TextView(this);
        View rectangle = new View(this);
        if(index== 0)
        {
           tv= findViewById(R.id.top1Activity);
           tvTime = findViewById(R.id.top1ActivityTime);
           rectangle = findViewById(R.id.top1rectangle);
           findViewById(R.id.imageViewTop1).setVisibility(View.VISIBLE);
           findViewById(R.id.clock1).setVisibility(View.VISIBLE);
        }

        if(index== 1)
        {
            tv= findViewById(R.id.top2Activity);
            tvTime = findViewById(R.id.top2ActivityTime);
            rectangle =findViewById(R.id.top2rectangle);
            findViewById(R.id.imageViewTop2).setVisibility(View.VISIBLE);
            findViewById(R.id.clock2).setVisibility(View.VISIBLE);
        }
        if(index== 2)
        {
            tv= findViewById(R.id.top3Activity);
            tvTime = findViewById(R.id.top3ActivityTime);
            rectangle = findViewById(R.id.top3rectangle);
            findViewById(R.id.imageViewTop3).setVisibility(View.VISIBLE);
            findViewById(R.id.clock3).setVisibility(View.VISIBLE);
        }


        tv.setText(ac.getName());
        tv.setVisibility(View.VISIBLE);
        tvTime.setText(ac.getFormattedTimeTotal());
        tvTime.setVisibility(View.VISIBLE);

        rectangle.setVisibility(View.VISIBLE);
        rectangle.setOnClickListener(new MyOnClickListenerRunAct(ac.getIdActivity(), this,2));

    }
}