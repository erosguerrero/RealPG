package com.example.realpg;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm != null ? cm.getActiveNetworkInfo() : null;
        return info != null && info.isConnected();
    }

    public static void showNoInternetConnection(Context context){
        Toast toast = Toast.makeText(context, "Sin conexión a internet", Toast.LENGTH_LONG);
        toast.show();
    }


}