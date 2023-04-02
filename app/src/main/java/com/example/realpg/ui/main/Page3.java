package com.example.realpg.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realpg.R;
import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage3Binding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page3 extends Fragment {
    private static Context mContext;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static PieChart pieChart;
 //   private PageViewModel pageViewModel;
    private FragmentPage3Binding binding;

    public static Page3 newInstance(int index, Context context) {
        Page3 fragment = new Page3();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        mContext = context;

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

        binding = FragmentPage3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.sectionLabel;
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        //pieChart = getView().findViewById(R.id.activity_piechart);
        //setupPieChart();
        //loadPieChartData();

        //para acceder a las views a traves de su ID, hay que usar binding
        pieChart = binding.activityPiechart;
        if(pieChart != null){
            Log.i("page3", "piechart no es null");
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        }
        setupPieChart();
        loadPieChartData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("% de horas por categoria");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        /*Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);*/
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(0.2f, "Food & Dining"));
        entries.add(new PieEntry(0.15f, "Medical"));
        entries.add(new PieEntry(0.10f, "Entertainment"));
        entries.add(new PieEntry(0.25f, "Electricity and Gas"));
        entries.add(new PieEntry(0.3f, "Housing"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(14f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    public static void update(){
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }
}