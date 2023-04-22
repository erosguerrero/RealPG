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
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage2Binding;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

 //   private PageViewModel pageViewModel;
    private FragmentPage2Binding binding;

    protected ArrayAdapter<String> spinnerAdapter;
    protected ArrayList<String> categories;

    String catToCreate = "Bienestar";
   // protected Spinner categoriesSpinner;

    public void changeShow2(){
        Log.i("MainActivity","pulsado");
    }

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
   //     pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
      //  pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPage2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();


        setButtonsPanelsListeners();
        for(ActivityBasicInfo abi : ((MainActivity) getActivity()).getActivitesBasicInfoList()) {
            Log.i("demo","los ids son: "+ abi.getId());
             addActivityToCatPanel(abi);
        }

        /*TODO
        Al crear la vista hacer lo siguiente:
        1. coger el linearLayout de cada panel (se corresponde a una categoria)
        2. En cada categoria (layout anterior) crear tantos panelItem (archivo xml predefinido) como acciones tenga esa categoria
        3. A cada panelItem asignarle los datos que le toquen (nombre y listeners a los botones que tenga)
         */
        //Al crear la vista coger el linear layout de
/*        LinearLayout p4 =  root.findViewById(R.id.panel4);

        if(p4 != null)
        {
            Log.i("Page2", "view encontrada");*/
           /*
           Por algun motivo el inflater no funciona al usarlo varias veces, y se debe usar getLayoutInflater

           ConstraintLayout item1 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
            TextView tv1 = item1.findViewById(R.id.itemName);
            tv1.setText("Texto dinamico numero 1");

            ConstraintLayout item2 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
            TextView tv2 = item2.findViewById(R.id.itemName);
            tv2.setText("Texto dinamico numero 2");


            p4.addView(item1);
            p4.addView(item2);*/
/*            for(ActivityBasicInfo abi : ((MainActivity) getActivity()).getActivitesBasicInfoList())
            {
                View itemI = getLayoutInflater().inflate(R.layout.item_panel, null);
                TextView tv1 = itemI.findViewById(R.id.itemName);
                tv1.setText(abi.getName());

                itemI.setOnClickListener(new MyOnClickListenerRunAct(abi.getId(), getActivity()));

                p4.addView(itemI);
            }


        }
        else
            Log.i("Page2", "view no encontrada");

        LinearLayout p1 =  root.findViewById(R.id.panel1);
        //datos de demo cat 1
        View itemIn = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tvn = itemIn.findViewById(R.id.itemName);
        tvn.setText("Actividad 1 de la cat 1");
        p1.addView(itemIn);

        List<Integer> ids = new ArrayList<>();
        ids.add(49);
        ids.add(7);

        tvn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel2", "actividad 1 cat 2");
                Intent intent = new Intent(getActivity(), ActivityInfoActivity.class);
                intent.putExtra("activityName", tvn.getText());
                startActivity(intent);
            }
        });

        LinearLayout p2 =  root.findViewById(R.id.panel2);*/
        //datos de demo cat 2
        /*ConstraintLayout item2 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
        TextView tv2 = item2.findViewById(R.id.itemName);
        tv2.setText("Actividad 1 de la cat 2");
        ConstraintLayout item3 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
        TextView tv3 = item3.findViewById(R.id.itemName);
        tv3.setText("Actividad 2 de la cat 2");
        p2.addView(tv2);
        p2.addView(tv3);*/
/*
        View itemI2 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv2 = itemI2.findViewById(R.id.itemName);
        tv2.setText("Actividad 1 de la cat 2");
        p2.addView(itemI2);
        View itemI22 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv22 = itemI22.findViewById(R.id.itemName);
        tv22.setText("Actividad 2 de la cat 2");
        p2.addView(itemI22);
        tv22.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("Panel2", "actividad 2 cat 2");
                Intent intent = new Intent(getActivity(), ActivityInfoActivity.class);
                intent.putExtra("activityName", tv22.getText());
                startActivity(intent);
            }
        });

        LinearLayout p3 =  root.findViewById(R.id.panel3);
        //datos de demo cat 3
        View itemI3 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv3 = itemI3.findViewById(R.id.itemName);
        tv3.setText("Actividad 1 de la cat 3");
        p3.addView(itemI3);

        for(int i = 2; i < 12; i++)
        {
            View itemIi = getLayoutInflater().inflate(R.layout.item_panel, null);
            TextView tvIi = itemIi.findViewById(R.id.itemName);
            tv3.setText("Actividad "+ i+" de la cat 3");
            itemIi.setOnClickListener(new MyOnClickListenerRunAct(i, getActivity()));
            p3.addView(itemIi);
        }


        setButtonsPanelsListeners();
*/

        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        //categoriesSpinner = binding.
        String currentCategory = "Bienestar";

        categories = manageSpinnerList(currentCategory);
        //adapter=new SpinnerAdapter(getApplicationContext());
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
                        //Toast.makeText(ActivityInfoActivity.this, "Selecciona una", Toast.LENGTH_SHORT).show();
                        String selectedCat = (String) spinner.getSelectedItem();

                        if(i == 0) {
                            // cuando se carga la activity/se recarga el adapter, el item en pos 0 se autoselecciona
                            Log.i("ActivityInfo", "Seleccionado categoria actual: " + selectedCat + " en pos 0");
                        } else if (i > 0){

                            Log.i("ActivityInfo", "Cat seleccionada: " + selectedCat);
                            spinner.setSelection(i);
                            spinnerAdapter.clear();
                            spinnerAdapter.addAll(manageSpinnerList(selectedCat));
                            spinner.setAdapter(spinnerAdapter);
                            catToCreate = selectedCat;
                            //ac.setCategory(selectedCat);

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
                            Toast.makeText(getActivity(), "Debes indicar un nombre para la nueva Actividad", Toast.LENGTH_LONG).show();
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

                            Toast.makeText(getActivity(), "Actividad creada con exito", Toast.LENGTH_LONG).show();

                            //TODO, ver que id toca poner e incrementarlo para el siguinte (lectura y escritura dle fihero)
                            ActivityBasicInfo ab= new ActivityBasicInfo(edittext.getText().toString(), newId, Activity.strToCategory(catToCreate));
                            addActivityToCatPanel(ab);
                            ((MainActivity)getActivity()).addActBasInf(ab);

                            Activity a = new Activity(newId,edittext.getText().toString(), catToCreate);


                            //TODO guardar la info de la nueva actividad en la lista de actividades
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
                    //con atributos de tema
                    //ContextCompat.getDrawable(getActivity(), R.drawable.arrow_down);
                    //sin atributos de tema
                    //ResourcesCompat.getDrawable(getResources(),  R.drawable.arrow_down, null);

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
            //View child = getLayoutInflater().inflate(R.layout.child, null);
            //root.ad
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
        //TODO reemplazar con el numero de categorias de la app
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
        Log.i("demo", "id en al add " + abi.getId());
        item.setOnClickListener(new MyOnClickListenerRunAct(abi.getId(), getActivity()));

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
        String[] categoriesList = {"Casa", "Deporte", "Estudios", "Ocio", "Proyectos", "Trabajo", "Viajes", "Otros"};
        ArrayList<String> newCategories = new ArrayList<>();

        newCategories.add(firstCategory);

        for(String cat: categoriesList){
            if(cat != firstCategory) newCategories.add(cat);
        }

        return newCategories;
    }
}