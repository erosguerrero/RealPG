package com.example.realpg.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.realpg.Activity;
import com.example.realpg.ActivityBasicInfo;
import com.example.realpg.ActivityInfoActivity;
import com.example.realpg.Category;
import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.DataManager;
import com.example.realpg.MainActivity;
import com.example.realpg.MyOnClickListenerRunAct;
import com.example.realpg.MyOnClickListenerStartActivity;
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage2Binding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Dialog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentPage2Binding binding;

    protected ArrayAdapter<String> spinnerAdapter;
    protected ArrayList<String> categories;

    String catToCreate = "Bienestar";

    public static Page2 newInstance(int index) {
        Page2 fragment = new Page2();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPage2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();


        setButtonsPanelsListeners();
        for(ActivityBasicInfo abi : ((MainActivity) getActivity()).getActivitesBasicInfoList()) {
             addActivityToCatPanel(abi);
        }

        Locale currentLocale = getResources().getConfiguration().locale;
        String language = currentLocale.getLanguage();
        String currentCategory = "Bienestar";
        if (language.equals("en")) {
            currentCategory = "Well-being";
        }

        categories = manageSpinnerList(currentCategory);
        spinnerAdapter = new ArrayAdapter<>(getActivity(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categories);

        binding.addActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("demo", "boton add activity pulsado");
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.row_spinner);
                dialog.setCancelable(true);

                final Spinner spinner = (Spinner) dialog.findViewById(R.id.spinnerDialog);
                final EditText edittext = (EditText) dialog.findViewById(R.id.editText1);
                Button button = (Button) dialog.findViewById(R.id.button1);

                spinner.setAdapter(spinnerAdapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedCat = (String) spinner.getSelectedItem();

                        if(i == 0) {
                            // cuando se carga la activity/se recarga el adapter, el item en pos 0 se autoselecciona
                        } else if (i > 0){

                            Log.i("ActivityInfo", "Cat seleccionada: " + selectedCat);
                            spinner.setSelection(i);
                            spinnerAdapter.clear();
                            spinnerAdapter.addAll(manageSpinnerList(selectedCat));
                            spinner.setAdapter(spinnerAdapter);
                            catToCreate = selectedCat;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(edittext.getText().toString().equals(""))
                            Toast.makeText(getActivity(), getString(R.string.warningNoName), Toast.LENGTH_LONG).show();
                        else {
                            dialog.dismiss();

                            DataManager dm = new DataManager(getActivity());
                            JSONObject jsonExtra = dm.load(DataManager.EXTRA_FILE_NAME);
                            int newId;
                            try {
                                //lee la nueva id a asignar e incrementa el contador de ids
                                newId = jsonExtra.getInt("Length");
                                jsonExtra.put("Length", newId+1);
                                dm.save(DataManager.EXTRA_FILE_NAME, jsonExtra);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }

                            Toast.makeText(getActivity(), getString(R.string.newActOk), Toast.LENGTH_LONG).show();

                            ActivityBasicInfo ab= new ActivityBasicInfo(edittext.getText().toString(), newId, Activity.strToCategory(catToCreate));
                            addActivityToCatPanel(ab);
                            ((MainActivity)getActivity()).addActBasInf(ab);

                            Activity a = new Activity(newId,edittext.getText().toString(), catToCreate);

                            JSONObject jsonAct = dm.load(DataManager.ACTIVITIES_FILE_NAME);
                            try {
                                jsonAct.put(a.getIdActivity()+"", a.toJson());
                                dm.save(DataManager.ACTIVITIES_FILE_NAME, jsonAct);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                       }
                });

                dialog.show();

            }
        });


        return root;
    }


    private void setButtonsPanelsListeners()
    {
        HideAllPanels();
        binding.activatePanel1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel1;
                Log.i("demo", "pulsado el 1");
                if(!binding.panel1.isShown())
                {
                    binding.panel1.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else {
                    binding.panel1.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });
        binding.activatePanel2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel2;

                if(!binding.panel2.isShown()) {
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                    binding.panel2.setVisibility(View.VISIBLE);
                }
                else {
                    binding.panel2.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }

            LinearLayout root = (LinearLayout) binding.panel2;
        });
        binding.activatePanel3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel3;

                if(!binding.panel3.isShown()) {
                    binding.panel3.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else {
                    binding.panel3.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });
        binding.activatePanel4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel4;

                if(!binding.panel4.isShown()) {
                    binding.panel4.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel4.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });

        binding.activatePanel5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel5;

                if(!binding.panel5.isShown()) {
                    binding.panel5.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel5.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });

        binding.activatePanel6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel6;

                if(!binding.panel6.isShown()) {
                    binding.panel6.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel6.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });

        binding.activatePanel7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel7;

                if(!binding.panel7.isShown()) {
                    binding.panel7.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel7.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });

        binding.activatePanel8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel8;

                if(!binding.panel8.isShown()) {
                    binding.panel8.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel8.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });

        binding.activatePanel9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel9;

                if(!binding.panel9.isShown()) {
                    binding.panel9.setVisibility(View.VISIBLE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down));
                }
                else{
                    binding.panel9.setVisibility(View.GONE);
                    b.setIcon(ContextCompat.getDrawable(getActivity(), R.drawable.arrow_forward));
                }

            }
        });





    }

    private void HideAllPanels()
    {
        binding.panel1.setVisibility(View.GONE);
        binding.panel2.setVisibility(View.GONE);
        binding.panel3.setVisibility(View.GONE);
        binding.panel4.setVisibility(View.GONE);
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void addActivityToCatPanel(ActivityBasicInfo abi)
    {

        View item = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv = item.findViewById(R.id.itemName);
        tv.setText(abi.getName());
        tv.setOnClickListener(new MyOnClickListenerRunAct(abi.getId(), getActivity(),0));

        ImageButton playButton = item.findViewById(R.id.startActivityButton);
        playButton.setOnClickListener(new MyOnClickListenerStartActivity(abi.getId(), getActivity()));


        switch (abi.getCat()) {
            case BIENESTAR:{
                LinearLayout p1 =  binding.panel1;//bind.findViewById(R.id.panel1);
                p1.addView(item);
                break;}
            case CASA:
                LinearLayout p2 =  binding.panel2;//bind.findViewById(R.id.panel1);
                p2.addView(item);
                break;
            case DEPORTE:
                LinearLayout p3 =  binding.panel3;//bind.findViewById(R.id.panel1);
                p3.addView(item);
                break;
            case ESTUDIOS:
                LinearLayout p4 =  binding.panel4;//bind.findViewById(R.id.panel1);
                p4.addView(item);
                break;
            case OCIO:
                LinearLayout p5 =  binding.panel5;//bind.findViewById(R.id.panel1);
                p5.addView(item);
                break;
            case OTROS:
                LinearLayout p6 =  binding.panel6;//bind.findViewById(R.id.panel1);
                p6.addView(item);
                break;
            case PROYECTOS:
                LinearLayout p7 =  binding.panel7;//bind.findViewById(R.id.panel1);
                p7.addView(item);
                break;
            case TRABAJO:
                LinearLayout p8 =  binding.panel8;//bind.findViewById(R.id.panel1);
                p8.addView(item);
                break;
            case VIAJES:
                LinearLayout p9 =  binding.panel9;//bind.findViewById(R.id.panel1);
                p9.addView(item);
                break;
            default:
                // Default secuencia de sentencias.
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