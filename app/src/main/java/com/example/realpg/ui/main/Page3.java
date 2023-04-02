package com.example.realpg.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realpg.Category;
import com.example.realpg.R;
import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage3Binding;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page3 extends Fragment implements OnChartValueSelectedListener {
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
        pieChart.setOnChartValueSelectedListener(this);

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
        pieChart.setEntryLabelTextSize(16);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("% de horas por categoria");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);
        /*l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);*/

    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Category cat : Category.values()) {

            float p = (float)1/9;
            entries.add(new PieEntry(p, cat.toString()));
        }

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
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    public static void update(){
        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry) e;
        Log.d("page3", "en onValueSelected");
        Log.d("page3", "entry selected: " + ((PieEntry) e).getLabel());

        //TextView v = binding.clickedEntry;
        //v.setText(pe.getLabel());
    }

    @Override
    public void onNothingSelected() {
        Log.i("page3", "en nothing selected");
    }
}