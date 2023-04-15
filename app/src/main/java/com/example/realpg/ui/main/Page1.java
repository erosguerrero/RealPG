package com.example.realpg.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realpg.R;
import com.example.realpg.databinding.FragmentMain2Binding;
import com.example.realpg.databinding.FragmentPage1Binding;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page1 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

 //   private PageViewModel pageViewModel;
    private FragmentPage1Binding binding;

    public static Page1 newInstance(int index) {
        Page1 fragment = new Page1();
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

        binding = FragmentPage1Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        
        //XP BAR
        changeLvl(root,9.1);

        LinearLayout latestActCont =  root.findViewById(R.id.LatestActivitiesContainer);

        View item1 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv1 = item1.findViewById(R.id.itemName);
        tv1.setText("Actividad mas usada");
        latestActCont.addView(item1);

        View item2 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView t2 = item2.findViewById(R.id.itemName);
        t2.setText("Segunda actividad mas usada");
        latestActCont.addView(item2);

        View item3 = getLayoutInflater().inflate(R.layout.item_panel, null);
        TextView tv3 = item3.findViewById(R.id.itemName);
        tv3.setText("Tercera actividad mas usada con un texto mas largo");
        latestActCont.addView(item3);







        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void changeLvl(View root, double lvl){
        ProgressBar xpBar = root.findViewById(R.id.selectedPokemonLevel);
        TextView expLabel = root.findViewById(R.id.xpLabel);

        //
        expLabel.setText("Lvl "+(int)Math.floor(lvl));

        xpBar.setProgress((int)Math.floor(lvl%1*100),true);
    }
}