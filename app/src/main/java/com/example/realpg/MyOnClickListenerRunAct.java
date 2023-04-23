package com.example.realpg;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.example.realpg.ui.main.ListMyPoke;

public class MyOnClickListenerRunAct implements View.OnClickListener {


    int idAct;
    Context context;

    public MyOnClickListenerRunAct(int idAct, Context context)
    {
        Log.i("demo", "el id seteado es: "+ idAct);
        this.idAct = idAct;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Log.i("demo", "Activity pulsado con id " + idAct);

        //MainActivity mainActivityContext = (MainActivity)context;
        //mainActivityContext.setTab(0);

        SharedPreferences mPreferences = context.getSharedPreferences("previousTab", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("tabHasToChange", true);
        preferencesEditor.putInt("prevTab", 0);
        preferencesEditor.apply();

        Intent intent = new Intent(context, ActivityInfoActivity.class);

        intent.putExtra("idAct", idAct);

        context.startActivity(intent);

    }
}
