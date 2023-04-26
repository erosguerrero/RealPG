package com.example.realpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityInfoActivity extends AppCompatActivity {
    protected ArrayAdapter<String> spinnerAdapter;
    protected ArrayList<String> categories;
    protected String currentCategory;
    protected Spinner categoriesSpinner;

    Activity acBackup;

    Activity ac;
    DataManager dm;

    JSONObject json;

    String initialName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


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

            EditText headerTitle = findViewById(R.id.activityName);
            headerTitle.setText(ac.getName());

            initialName = ac.getName();

            TextView timeWeek = findViewById(R.id.timeWeek);
            TextView timeMonth = findViewById(R.id.timeMonth);
            TextView timeYear = findViewById(R.id.timeYear);
            TextView timeTotal = findViewById(R.id.timeTotal);

            timeWeek.setText(ac.getMinutesLatestDaysFormatted(7));
            timeMonth.setText(ac.getMinutesLatestDaysFormatted(30));
            timeYear.setText(ac.getMinutesLatestDaysFormatted(365));
            timeTotal.setText(ac.getFormattedTimeTotal());



          //  EditText  edit =  findViewById(R.id.edit);
//Hace que el cursor de escritura se muestre solo al pulsar sobre el texto y se muestra el teclado
            //source: https://stackoverflow.com/questions/17711075/android-only-show-cursor-in-edittext-when-keyboard-is-displayed
            final View activityRootView = findViewById(R.id.activityInfoLayout);
            activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Rect r = new Rect();
                    activityRootView.getWindowVisibleDisplayFrame(r);
                    int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                    if (heightDiff > 200) { // if more than 100 pixels, its probably a keyboard...
                        if (headerTitle != null) {
                            Log.i("demo2", "detectado");
                            headerTitle.setCursorVisible(true);
                        }
                    } else {
                        if (headerTitle != null) {
                            headerTitle.setCursorVisible(false);
                        }
                    }
                }
            });


            headerTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Establecer la selección del cursor en la posición final del texto
                    headerTitle.setSelection(headerTitle.getText().length());
                }
            });


            //oculta el teclado si se pulsa fuera de el
            activityRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Ocultar el teclado virtual
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            });


            //esta funcion y la siguiente oculta el teclado si se pulsa enter al final del nombre
            headerTitle.setFilters(new InputFilter[] {
                    new InputFilter() {
                        @Override
                        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                            if (source.length() > 0 && source.charAt(0) == '\n') {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(headerTitle.getWindowToken(), 0);
                                return "";
                            }
                            return null;
                        }
                    }
            });

            headerTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        headerTitle.setFilters(new InputFilter[] {
                                new InputFilter() {
                                    @Override
                                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                                        if (source.length() > 0 && source.charAt(0) == '\n') {
                                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                            imm.hideSoftInputFromWindow(headerTitle.getWindowToken(), 0);
                                            return "";
                                        }
                                        return null;
                                    }
                                }
                        });
                    } else {
                        headerTitle.setFilters(new InputFilter[0]);
                    }
                }
            });


         /*   headerTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        // Aquí se finaliza la edición del EditText
                        Log.i("demo3", "detectado enter");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(headerTitle.getWindowToken(), 0);
                        return true;
                    }

                    return false;
                }
            });*/

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
            Locale currentLocale = getResources().getConfiguration().locale;
            String language = currentLocale.getLanguage();

            if (language.equals("en")) {
                currentCategory = Activity.transalateCatEsToEn( ac.getCatStr());
            }


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

                    if(headerTitle.isEnabled())
                    {
                        Log.i("demo3", "No se borro");
                        String newName = headerTitle.getText().toString();
                        if(!initialName.equals(newName))
                        {

                            try {
                                JSONObject jsonActivities = dm.load(DataManager.ACTIVITIES_FILE_NAME);
                                JSONObject jsonAc = jsonActivities.getJSONObject(idActivity+"");
                                jsonAc.put("name", newName);
                                jsonActivities.put(idActivity+"",jsonAc);
                                dm.save(DataManager.ACTIVITIES_FILE_NAME, jsonActivities);

                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

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
                    headerTitle.setEnabled(false);
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
                    headerTitle.setEnabled(true);

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
        String[] categoriesList = {"Bienestar", "Casa", "Deporte", "Estudios", "Ocio", "Proyectos", "Trabajo", "Viajes", "Otros"};

        Locale currentLocale = getResources().getConfiguration().locale;
        String language = currentLocale.getLanguage();
        if (language.equals("en")) {
            for(int i = 0; i < categoriesList.length;i++)
            {
                categoriesList[i] = Activity.CatStrFormated( Activity.transalateCatEsToEn(categoriesList[i]));
            }
            // El idioma del dispositivo es inglés
        } else {
            // El idioma del dispositivo no es inglés
        }
        ArrayList<String> newCategories = new ArrayList<>();

        newCategories.add(firstCategory);

        for(String cat: categoriesList){
            if(!cat.equals(firstCategory)) newCategories.add(cat);
        }

        return newCategories;
    }
}