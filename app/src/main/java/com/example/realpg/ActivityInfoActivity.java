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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityInfoActivity extends AppCompatActivity {
    protected ArrayAdapter<String> spinnerAdapter;
    protected ArrayList<String> categories;
    protected String currentCategory;
    protected Spinner categoriesSpinner;

    Activity acBackup;

    Activity ac;
    DataManager dm;

    JSONObject json;

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
        /*String readedData = "{\n" +
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
                "}";*/



        try {
            DataManager dm = new DataManager(this);
            //JSONObject json = new JSONObject(readedData);
            json = dm.load(DataManager.ACTIVITIES_FILE_NAME);
            Log.i("demo", "Json leido: "+ json);

            int idActivity = getIntent().getIntExtra("idAct",-1);

            Log.i("demo", "id a cargar: "+ idActivity);

            JSONObject jsonAct = json.getJSONObject(idActivity+"");

            Log.i("demo", "Josn cogido: "+jsonAct.toString());

            ac = Activity.createActivityFromJson(jsonAct, idActivity);

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



            categoriesSpinner = findViewById(R.id.categorySpinner);

            //currentCategory = "Viajes";
            currentCategory = ac.getCatStr();

            categories = manageSpinnerList(currentCategory);

            spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);
            if(spinnerAdapter != null) categoriesSpinner.setAdapter(spinnerAdapter);

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
                        if(spinnerAdapter != null) categoriesSpinner.setAdapter(spinnerAdapter);

                        ac.setCategory(selectedCat);
                        //TODO sobreescribir en el fichero esta Actividad
                        try {
                            json.put(idActivity+"",ac.toJson());
                            Log.i("demo2", "nuevo Json: " +json.toString());
                            dm.save(DataManager.ACTIVITIES_FILE_NAME, json);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
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
                    //TODO leer json completo de actividades y hacer remove(ac.getId())
                    //volver a MainActivity

                    Button undoDeleteBtn = findViewById(R.id.undoDeleteButton);
                    Button deleteButton = findViewById(R.id.deleteButton);
                    deleteButton.setVisibility(View.GONE);
                    undoDeleteBtn.setVisibility(View.VISIBLE);
                    categoriesSpinner.setEnabled(false);
                    Log.i("demo2", "aqui");

                    JSONObject jsonActvities = dm.load(DataManager.ACTIVITIES_FILE_NAME);

                   try {
                        acBackup = Activity.createActivityFromJson(jsonActvities.getJSONObject(idActivity+""),idActivity);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    jsonActvities.remove(idActivity+"");
                    JSONObject jsonExtra = dm.load(DataManager.EXTRA_FILE_NAME);


                        try {
                            JSONArray last3Json = jsonExtra.getJSONArray("Last3");
                            List<String> last3Str = new ArrayList<>();
                            for(int i = 0; i < last3Json.length(); i++)
                            {
                                last3Str.add(last3Json.getInt(i)+"");
                            }
                            if(last3Str.contains(idActivity+""))
                            {
                                last3Str.remove(idActivity+"");
                                JSONArray newLast3 = new JSONArray();
                                for(int i = 0; i < last3Str.size(); i++)
                                {
                                    newLast3.put(Integer.parseInt(last3Str.get(i)));
                                }
                                jsonExtra.put("Last3", newLast3);
                                dm.save(DataManager.EXTRA_FILE_NAME, jsonExtra);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                    dm.save(DataManager.ACTIVITIES_FILE_NAME, jsonActvities);

                 //   Intent intent = new Intent(ActivityInfoActivity.this, MainActivity.class);
                //    startActivity(intent);

                }
            });


           Button undoDeleteBtn = findViewById(R.id.undoDeleteButton);

            undoDeleteBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.i("demo2", "undo pulsaod");
                    undoDeleteBtn.setVisibility(View.GONE);
                    categoriesSpinner.setEnabled(true);

                    Button deleteButton = findViewById(R.id.deleteButton);
                    deleteButton.setVisibility(View.VISIBLE);

                    JSONObject jsonActivities = dm.load(DataManager.ACTIVITIES_FILE_NAME);

                    try {
                        jsonActivities.put(idActivity+"", acBackup.toJson());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    dm.save(DataManager.ACTIVITIES_FILE_NAME, jsonActivities);


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