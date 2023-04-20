package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivityInfoActivity extends AppCompatActivity {
    protected ArrayAdapter<String> spinnerAdapter;
    protected ArrayList<String> categories;
    protected String currentCategory;
    protected Spinner categoriesSpinner;

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
                "\n" +
                "\t\"0\": {\n" +
                "\t\t\"cat\": \"CASA\",\n" +
                "\t\t\"name\": \"Actividad 0\",\n" +
                "\t\t\"totalMin\": 50,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"1\": {\n" +
                "\t\t\"cat\": \"BIENESTAR\",\n" +
                "\t\t\"name\": \"Actividad 1\",\n" +
                "\t\t\"totalMin\": 60,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"2\": {\n" +
                "\t\t\"cat\": \"BIENESTAR\",\n" +
                "\t\t\"name\": \"Actividad 2\",\n" +
                "\t\t\"totalMin\": 20,\n" +
                "\t\t\"latestSessions\": [{\n" +
                "\t\t\t\"date\": \"2023-04-04\",\n" +
                "\t\t\t\"minutes\": 20\n" +
                "\t\t}, {\n" +
                "\t\t\t\"date\": \"2023-04-17\",\n" +
                "\t\t\t\"minutes\": 15\n" +
                "\t\t}]\n" +
                "\t},\n" +
                "\t\"3\": {\n" +
                "\t\t\"cat\": \"ESTUDIOS\",\n" +
                "\t\t\"name\": \"Actividad 3\",\n" +
                "\t\t\"totalMin\": 10,\n" +
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

            int idActivity = getIntent().getIntExtra("idAct",-1);

            Log.i("demo", "id a cargar: "+ idActivity);

            JSONObject jsonAct = json.getJSONObject(idActivity+"");

            Log.i("demo", "Josn cogido: "+jsonAct.toString());

            Activity ac = Activity.createActivityFromJson(jsonAct, idActivity);

            TextView headerTitle = findViewById(R.id.activityName);
            headerTitle.setText(ac.getName());

            TextView timeWeek = findViewById(R.id.timeWeek);
            TextView timeMonth = findViewById(R.id.timeMonth);
            TextView timeYear = findViewById(R.id.timeYear);
            TextView timeTotal = findViewById(R.id.timeTotal);

            timeWeek.setText(ac.getMinutesLatestDaysFormatted(7));
            timeMonth.setText(ac.getMinutesLatestDaysFormatted(30));
            timeYear.setText(ac.getMinutesLatestDaysFormatted(365));
            timeTotal.setText(ac.getFormattedTimeTotal());



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


        /*    JSONObject jsonAct = json.getJSONObject(idActivity+"");
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
            headerTitle.setText(ac.getName());*/




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

            categoriesSpinner = findViewById(R.id.categorySpinner);

            //currentCategory = "Viajes"; // TODO: remplazar por la category.getCategory()
            currentCategory = ac.getCatStr();

            categories = manageSpinnerList(currentCategory);

            spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);
            categoriesSpinner.setAdapter(spinnerAdapter);

            categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(ActivityInfoActivity.this, "Selecciona una", Toast.LENGTH_SHORT).show();
                    String selectedCat = (String) categoriesSpinner.getSelectedItem();

                    if(i == 0) {
                        // cuando se carga la activity/se recarga el adapter, el item en pos 0 se autoselecciona
                        Log.i("ActivityInfo", "Seleccionado categoria actual: " + selectedCat + " en pos 0");
                    } else if (i > 0){
                        Log.i("ActivityInfo", "Cat seleccionada: " + selectedCat);
                        categoriesSpinner.setSelection(i);
                        spinnerAdapter.clear();
                        spinnerAdapter.addAll(manageSpinnerList(selectedCat));
                        categoriesSpinner.setAdapter(spinnerAdapter);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

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

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }


    private ArrayList<String> manageSpinnerList(String firstCategory){
        String[] categoriesList = {"Casa", "Deporte", "Estudios", "Ocio", "Proyectos", "Trabajo", "Viajes", "Otros"};
        ArrayList<String> newCategories = new ArrayList<>();

        newCategories.add(firstCategory);

        for(String cat: categoriesList){
            if(cat != firstCategory) newCategories.add(cat);
        }

        return newCategories;
    }
}