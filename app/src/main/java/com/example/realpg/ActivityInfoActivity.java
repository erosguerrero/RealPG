package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        //Leer datos del json almacenado en disco
        //simulados ahora con el string readedData


        /*String readedData = "\n" +
                "{\"activities\":[{\"idAct\":0,\"totalMin\":50,\"latestSessions\":[{\"date\":\"2023-04-04\",\"minutes\":20},{\"date\":\"2023-04-17\",\"minutes\":15}]},\n" +
                "\t       {\"idAct\":1,\"totalMin\":12,\"latestSessions\":[{\"date\":\"2023-03-03\",\"minutes\":20},{\"date\":\"2023-03-22\",\"minutes\":2}]}]}";
*/
        //data como json completo, sin jsonArray
        //TODO quizas no necesario primera clave activities si este json esta en un fichero diferente a los demas
       //ahora mismo esta hecho como si esuviera en otro archivo diferente a lso demas
        //TODO ver si necesario guardra un valor en el json que sea siguiente id
        //se utilizaria como id al crear una actividad y se incrementaria en uno
        //Asi asegura ids unicas sin mucha complejidad
        String readedData = "{\n" +
                "\t\"0\": {\n" +
                "\t\"cat\": \"CASA\",\n" +
                "\t\"name\": \"nombre actividad\",\n" +
                "\t\"totalMin\": 50,\n" +
                "\t\"latestSessions\": [{\n" +
                "\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\"minutes\": 20\n" +
                "\t}, {\n" +
                "\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\"minutes\": 15\n" +
                "\t}, {\n" +
                "\t\t\"date\": \"2023-03-29\",\n" +
                "\t\t\"minutes\": 135\n" +
                "\t}, {\n" +
                "\t\t\"date\": \"2023-01-17\",\n" +
                "\t\t\"minutes\": 4526\n" +
                "\t}]\n" +
                "},\n" +
                "\t\"1\": {\n" +
                "\t\t\"totalMin\": 50,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t}\n" +
                "\n" +
                "}";
        try {
            JSONObject json = new JSONObject(readedData);
            Log.i("demo", "Json leido: "+ json);



        /*    JSONArray jsonArrayAct = new JSONArray();

            jsonArrayAct = json.getJSONArray("activities");
            Log.i("demo", "JsonArray leido: "+ jsonArrayAct);
             //JSONObject jsonAct = jsonArrayAct.getJSONObject(0);
            */
/*Todo: el index coincide con el id. para que esto sea asi cuando una actividad se borre seguira
estando en el json pero se debera poner con info vacia para que no ocupe espacio innecesariamente
Sino habria que ver como guardar las actividades referenciandolas por su id
Podria ser un Json actividades que contiene claves idActividad y sus valores es toda la info de esa actividad

*/
            //TODO Ahora mismo lo anterior no es necesario, ya que se accede por ids

            int idActivity = 0;
            JSONObject jsonAct = json.getJSONObject(idActivity+"");
            Log.i("demo", "Json act: "+ jsonAct);

            Activity ac = Activity.createActivityFromJson(jsonAct, idActivity);

            TextView timeWeek = findViewById(R.id.timeWeek);
            TextView timeMonth = findViewById(R.id.timeMonth);
            TextView timeYear = findViewById(R.id.timeYear);
            TextView timeTotal = findViewById(R.id.timeTotal);

            Log.i("demo", "tiempo semana: "+ac.getMinutesLatestdays(7));
            timeWeek.setText(ac.getMinutesLatestDaysFormatted(7));
            timeMonth.setText(ac.getMinutesLatestDaysFormatted(30));
            timeYear.setText(ac.getMinutesLatestDaysFormatted(365));
            timeTotal.setText(ac.getFormattedTimeTotal());

            Log.i("demo", "nuevo: " + ac.toJson().toString());

            TextView headerTitle = findViewById(R.id.activityName);
            headerTitle.setText(ac.getName());


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

/////////pruebas
        ActivitySession demo = new ActivitySession("2023-04-17",10);

        Activity demoAc = new Activity();




        //Cuidado, si se meten datos de prueba deben meterse en orden de antiguo a nuevo
        demoAc.demoAddSession("2022-01-01", 5);
        demoAc.demoAddSession("2022-01-02", 10);
        demoAc.demoAddSession("2023-04-04", 20);
        demoAc.addNewSession(5);
        demoAc.addNewSession(10);



        Log.i("demo", "info json: "+ demo.toJson().toString());

        Log.i("demo", "mins en 17-04-2023: " + demoAc.getMinutesLatestdays(1));

        Log.i("demo", "mins en 17-03-2023: " + demoAc.getMinutesLatestdays(30));

        Log.i("demo", "mins en mas de un anio: " + demoAc.getMinutesLatestdays(600));

        demoAc.updateLatestSessions();

        Log.i("demo", "mins en mas de un anio: " + demoAc.getMinutesLatestdays(600));


        Log.i("demo","Json activity: "+ demoAc.toJson().toString());


///////// fin pruebas

        //TODO por el intent se tendra que pasar ahora el id de la actividad
        //String activityName = getIntent().getStringExtra("activityName");

       // TextView headerTitle = findViewById(R.id.activityName);
       // headerTitle.setText(activityName);

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