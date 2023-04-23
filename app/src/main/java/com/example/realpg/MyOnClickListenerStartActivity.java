package com.example.realpg;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

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

       /*Intent intent = new Intent(context, ActivityInfoActivity.class);

        intent.putExtra("idAct", idAct);

        context.startActivity(intent);*/

    }
}