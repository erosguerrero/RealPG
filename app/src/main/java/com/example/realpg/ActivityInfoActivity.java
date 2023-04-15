package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ActivityInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        String activityName = getIntent().getStringExtra("activityName");

        TextView headerTitle = findViewById(R.id.activityName);
        headerTitle.setText(activityName);

        ImageButton backArrowButton = findViewById(R.id.backArrow);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ActivityInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("ActivityInfo", "Borrando actividad");

            }
        });


    }
}