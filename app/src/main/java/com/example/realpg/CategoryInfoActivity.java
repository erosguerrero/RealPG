package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

public class CategoryInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_info);

        String title = getIntent().getStringExtra("categoryName");

        TextView categoryTitle = findViewById(R.id.categoryTitle);
        categoryTitle.setText(title);

        LinearLayout layout = findViewById(R.id.scrollLayout);
        for(int i = 0; i < 20; i++){
            View p = getLayoutInflater().inflate(R.layout.activity_info_panel, null);
            TextView activityName = p.findViewById(R.id.activityName);
            activityName.setText("Prueba texto");

            layout.addView(p);
        }

        ImageButton backArrowButton = findViewById(R.id.backArrow);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CategoryInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}