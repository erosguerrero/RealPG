package com.example.realpg;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentManager;

import com.example.realpg.ui.main.Page1;

public class MyOnClickListenerStartActivity implements View.OnClickListener {


    int idAct;
    Context context;

    public MyOnClickListenerStartActivity(int idAct, Context context)
    {
        Log.i("demo", "el id seteado es: "+ idAct);
        this.idAct = idAct;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Log.i("demo2", "Activity pulsado para comenzar con id " + idAct);

        MainActivity mainActivityContext = (MainActivity)context;
        mainActivityContext.setTab(1);

        Page1.getInstance().startStopWatch();

       /*Intent intent = new Intent(context, ActivityInfoActivity.class);
        intent.putExtra("idAct", idAct);
        context.startActivity(intent);*/
    }
}