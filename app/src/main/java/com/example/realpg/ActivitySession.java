package com.example.realpg;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;

public class ActivitySession {

    ///Date solo es info del dia (dia-mes-anio) no incluye hora
    private LocalDateTime date;
    private int minutes;

    public ActivitySession(LocalDateTime date, int minutes){
        this.date = date;
        this.minutes = minutes;
    }

    /***
     *
     * @param dateStr Fecha de la actividad en formato AAAA-MM-DD
     * @param minutes
     */
    public ActivitySession(String dateStr, int minutes){
        this.date = LocalDateTime.parse(dateStr + "T00:00:00");
        this.minutes = minutes;
    }

    public void addMinutes(int min) {
        minutes += min;
    }


    public LocalDateTime getDate(){return date;}
    public int getMinutes(){return minutes;}

    public String getDateStrESFormat()
    {
        String dateStr ="";
        String day =date.getDayOfMonth() +"";
        if(day.length() == 1)
            day = "0"+day;

        String month = date.getMonthValue() + "";
        if(month.length() == 1)
            month = "0" + month;
        dateStr = day + "/" + month + "/" + date.getYear();
        return dateStr;
    }

    public String getDateStr()
    {
        String dateStr ="";
        String day =date.getDayOfMonth() +"";
        if(day.length() == 1)
            day = "0"+day;

        String month = date.getMonthValue() + "";
        if(month.length() == 1)
            month = "0" + month;
        dateStr = date.getYear() + "-" + month + "-" + day;
        return dateStr;
    }



    public JSONObject toJson() {
        JSONObject json = new JSONObject();

        /*json.append("date", getDateStr());
        json.append("minutes", minutes);/*
         */
        try {
            json.put("date", getDateStr());
            json.put("minutes", minutes);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

    public static  ActivitySession createFromJson(JSONObject json)
    {
        try {
            String dateStr = json.getString("date");
            int minutes = json.getInt("minutes");
            return new ActivitySession(dateStr,minutes);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }



}
