package com.example.realpg;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Activity {
    private int idActivity;
    private Category category;
    
    private String name;

    private int totalMinutes;

    /**
     * Array con las ultimas sesiones de la actividad
     * Almacena datos de tipo ActivitySession que son los minutos utilizados en un dia dado
     * Se utiliza para las estadisticas. Como la mas larga es a un anio, cada vez que se actualice
     * la lista se eliminaran aquellas sessiones anteriores a un anio
     */
    List<ActivitySession>   latestSessions;


    //Funcion de demo
     public Activity()
     {

         latestSessions = new ArrayList<>();
         totalMinutes = 0;
     }

     public Activity(int id, int totalMinutes, List<ActivitySession> actSesList, String name, Category cat){
        idActivity = id;
        this.totalMinutes = totalMinutes;
        latestSessions = actSesList;
        this.name = name;
        category = cat;
     }

     public void demoAddSession(String date, int min)
     {
         ActivitySession as = new ActivitySession(date, min);
         latestSessions.add(as);
         totalMinutes += min;
     }


    public void addNewSession(int minutes)
    {
        LocalDateTime date = LocalDateTime.now();

        LocalDateTime actualDateFormatted = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),0, 0);
        if(latestSessions.size() >0)
        {
            if(latestSessions.get(latestSessions.size()-1).getDate().compareTo(actualDateFormatted) == 0)
            {
                latestSessions.get(latestSessions.size()-1).addMinutes(minutes);
                Log.i("demo", "fecha igual");
                totalMinutes += minutes;
                return;
            }
        }
        
        //si llego aqui o el size era 0 o era mayor que 0 pero no con fecha igual
        Log.i("demo", "nueva sesion");
        ActivitySession as = new ActivitySession(actualDateFormatted, minutes);
        latestSessions.add(as);

        totalMinutes += minutes;
        Log.i("demo", "Tiempo total actividad"+totalMinutes);

    }

    public int getMinutesLatestdays(int days)
    {
        int minutes = 0;
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime actualDateFormatted = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),0, 0);
        LocalDateTime prevDate = actualDateFormatted.minusDays(days);
       // Log.i("demo", "prev: " + prevDate.toString());
       // time.compareTo(il.getLoanStart());// > 0 time es mas tarde  <0  es antes  = 0 es igual

        for(ActivitySession as: latestSessions)
        {
            //La fecha leida es despues o igual a la fecha previa dada
            if(as.getDate().compareTo(prevDate) >= 0)
            {
                minutes += as.getMinutes();
            }
            //como las fechas se van guardando por orden, en cuanto se encuentra una
            //anterior al limite puesto dejamos de buscar
            else {
               // break;
            }

        }


        return minutes;
    }

    /**Calcula cuanto tiempo se hecho la actividad  desde la fecha actual - dias, hasta la fecha actual
     *
     * @param days Dias a restar desde la fecha actual para hacer el calculo
     * @return Devuelve el tiempo en un string formateado (DIASd - HORASh - MINUTOSm). Si no se llega a tener dias, u horas, se omiten
     */
    public String getMinutesLatestDaysFormatted(int days)
    {
        int minutes = getMinutesLatestdays(days);


        return getFormattedTimePassed(minutes);
    }

    public String getFormattedTimeTotal()
    {
        return getFormattedTimePassed(totalMinutes);
    }

    private String getFormattedTimePassed(int minutes)
    {
        int hours = minutes /60;
        minutes = minutes %60;
        int d = hours /24;
        hours = hours %24;
        if(d ==0 && hours == 0)
            return (minutes+"min");
        if(d==0 && hours > 0)
            return (hours+"h - "+minutes + "min");

        return (d + "d - " + hours+"h - "+minutes + "min");
    }


    /**
     * Actualiza la lista de ultimas sessiones eliminando las anteriores a hace un anio
     */
    public void updateLatestSessions()
    {
        LocalDateTime date = LocalDateTime.now();
        LocalDateTime actualDateFormatted = LocalDateTime.of(date.getYear(), date.getMonthValue(), date.getDayOfMonth(),0, 0);
        LocalDateTime prevDate = actualDateFormatted.minusDays(365);

        int index = 0;

        while (index < latestSessions.size()) {
            //la fecha leida es anterior a la limite
            if(latestSessions.get(index).getDate().compareTo(prevDate) < 0) {
                Log.i("demo","fecha eliminada");
                latestSessions.remove(index);
            }
            else
            {
                //TODO si las fechas por orden como deberia aqui valdria un break
                index++;
            }
        }

    }


    /**Prepara el objeto como Json con el formato utilizado para almacenar una Activity
     *
     * @return devuelve el Json con todos los datos del objeto
     */
    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();

        try {
          //  json.put("idAct", idActivity);
            json.put("cat", category);
            json.put("name", name);
            json.put("totalMin", totalMinutes);

            JSONArray jsonArray = new JSONArray();

            for(ActivitySession as: latestSessions)
            {
                JSONObject jsonSession = as.toJson();

                jsonArray.put(jsonSession);
            }

            json.put("latestSessions", jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return json;

    }

    /**Genera un objeto nuevo de tipo Activity a partir de los datos dados
     *
     * @param json Json con el formato y datos necesarios para construir una Actividad
     * @param idAct Id asignado a esta nueva actividad
     * @return
     */
    public static Activity createActivityFromJson(JSONObject json, int idAct)
    {
        try {
            int totalMin = json.getInt("totalMin");
            String name = json.getString("name");
            Category cat = Activity.StrToCategory(json.getString("cat"));
            JSONArray jsonArray = json.getJSONArray("latestSessions");

            List<ActivitySession> actSesList = new ArrayList<>();

            for(int i = 0; i < jsonArray.length(); i++)
            {
                ActivitySession as = ActivitySession.createFromJson(jsonArray.getJSONObject(i));
                actSesList.add(as);
            }

            return new Activity(idAct,totalMin,actSesList, name, cat);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }

    public static Category StrToCategory(String catStr) {

        switch (catStr) {
            case "BIENESTAR":
                    return Category.BIENESTAR;
            case "CASA":
                    return Category.CASA;
            case "DEPORTE":
                    return Category.DEPORTE;
            case "ESTUDIOS":
                    return Category.ESTUDIOS;
            case "OCIO":
                    return Category.OCIO;
            case "OTROS":
                    return Category.OTROS;
            case "PROYECTOS":
                    return Category.PROYECTOS;
            case "TRABAJO":
                    return Category.TRABAJO;
            case "VIAJES":
                    return Category.VIAJES;
            default:
                throw new IllegalArgumentException("Invalid category: " + catStr);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMinutes()
    {
        return totalMinutes;
    }

    public Category getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}
