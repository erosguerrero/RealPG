package com.example.realpg;

import android.content.Context;
import android.content.Intent;
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

        Intent intent = new Intent(context, ActivityInfoActivity.class);

        intent.putExtra("idAct", idAct);

        context.startActivity(intent);

    }
}
