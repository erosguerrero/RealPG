package com.example.realpg;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class DataManager {
    Context context = null;

    public DataManager(Context context) {
        this.context = context;
    }

    public void save(String filename, JSONObject json) {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(filename, MODE_PRIVATE);
            fos.write(json.toString().getBytes());
            Log.d("ARCHIVO CREADO","Saved to " + context.getFilesDir());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public JSONObject load(String filename) {
        FileInputStream fis = null;
        JSONObject json = null;
        try {
            fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            try {
                json = new JSONObject(sb.toString());
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return json;
        }
    }
}
