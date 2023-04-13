package com.example.realpg.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.realpg.R;
import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage2Binding;
import com.google.android.material.button.MaterialButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

 //   private PageViewModel pageViewModel;
    private FragmentPage2Binding binding;

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

        /*TODO
        Al crear la vista hacer lo siguiente:
        1. coger el linearLayout de cada panel (se corresponde a una categoria)
        2. En cada categoria (layout anterior) crear tantos panelItem (archivo xml predefinido) como acciones tenga esa categoria
        3. A cada panelItem asignarle los datos que le toquen (nombre y listeners a los botones que tenga)
         */
        //Al crear la vista coger el linear layout de
        LinearLayout p4 =  root.findViewById(R.id.panel4);

        if(p4 != null)
        {
            Log.i("Page2", "view encontrada");
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

            View itemI = getLayoutInflater().inflate(R.layout.item_panel, null);
            TextView tv1 = itemI.findViewById(R.id.itemName);
            tv1.setText("Actividad de la cat 4");
            p4.addView(itemI);

        }
        else
            Log.i("Page2", "view no encontrada");

        LinearLayout p1 =  root.findViewById(R.id.panel1);
        //datos de demo cat 1
        View itemIn = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tvn = itemIn.findViewById(R.id.itemName);
        tvn.setText("Actividad 1 de la cat 2");
        p1.addView(itemIn);

        LinearLayout p2 =  root.findViewById(R.id.panel2);
        //datos de demo cat 2
        /*ConstraintLayout item2 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
        TextView tv2 = item2.findViewById(R.id.itemName);
        tv2.setText("Actividad 1 de la cat 2");
        ConstraintLayout item3 = (ConstraintLayout) inflater.inflate(R.layout.item_panel, null, false);
        TextView tv3 = item3.findViewById(R.id.itemName);
        tv3.setText("Actividad 2 de la cat 2");
        p2.addView(tv2);
        p2.addView(tv3);*/

        View itemI2 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv2 = itemI2.findViewById(R.id.itemName);
        tv2.setText("Actividad 1 de la cat 2");
        p2.addView(itemI2);
        View itemI22 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv22 = itemI22.findViewById(R.id.itemName);
        tv22.setText("Actividad 2 de la cat 2");
        p2.addView(itemI22);

        LinearLayout p3 =  root.findViewById(R.id.panel3);
        //datos de demo cat 3
        View itemI3 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv3 = itemI3.findViewById(R.id.itemName);
        tv3.setText("Actividad 1 de la cat 2");
        p3.addView(itemI3);



        setButtonsPanelsListeners();


        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }


    private void setButtonsPanelsListeners()
    {
        HideAllPanels();
        binding.activatePanel1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //HideAllPanels();
                MaterialButton b = (MaterialButton) binding.activatePanel1;

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
}