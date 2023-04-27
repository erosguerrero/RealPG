package com.example.realpg.ui.main;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.realpg.Activity;
import com.example.realpg.Category;
import com.example.realpg.CategoryInfoActivity;
import com.example.realpg.MyOnClickListenerRunAct;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page3 extends Fragment implements OnChartValueSelectedListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static PieChart pieChart;
    private static Page3 instance;

    private FragmentPage3Binding binding;

    public static Page3 newInstance(int index) {
        Page3 fragment = new Page3();
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

        instance = this;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentPage3Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //para acceder a las views a traves de su ID, hay que usar binding
        pieChart = binding.activityPiechart;
        if(pieChart != null){
            Log.i("page3", "piechart no es null");
            pieChart.animateY(1400, Easing.EaseInOutQuad);
        }
        setupPieChart();
        try {
            loadPieChartData();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        pieChart.setOnChartValueSelectedListener(this);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<Activity> topAct = Activity.getTop3Act(getActivity());
        if(topAct.size() == 0) {
            getActivity().findViewById(R.id.noActMsg).setVisibility(View.VISIBLE);
            View v = getActivity().findViewById(R.id.myRectangleView2);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(0,150,0,0);
            v.requestLayout();
        } else {
            getActivity().findViewById(R.id.noActMsg).setVisibility(View.GONE);
            for(int i = 0; i < topAct.size(); i++){
                loadPanelTop(topAct.get(i),i);
            }
        }

    }

    public static Page3 getInstance(){
        return instance;
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
        pieChart.setCenterText("Horas por categoría");
        pieChart.setCenterTextSize(24);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setEnabled(false);
    }

    private void loadPieChartData() throws JSONException {
        ArrayList<PieEntry> entries = new ArrayList<>();
        JSONObject jsonCatMinutes = Activity.getCategoriesMinutes(getActivity());
        Locale currentLocale = getResources().getConfiguration().locale;
        String language = currentLocale.getLanguage();
        for (Category cat : Category.values()) {
           String catTrans = cat.toString();
            if (language.equals("en")) {
                catTrans = Activity.transalateCatEsToEn(cat.toString());
            }
            float p = (float)jsonCatMinutes.getInt(cat.toString())/jsonCatMinutes.getInt("TOTAL");
            if(jsonCatMinutes.getInt(cat.toString()) != 0) entries.add(new PieEntry(p, catTrans));
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

    public void updatePieChartData() throws JSONException {
        List<Activity> topAct = Activity.getTop3Act(getActivity());
        if(topAct.size()!=0) {
            getActivity().findViewById(R.id.noActMsg).setVisibility(View.GONE);
            View v = getActivity().findViewById(R.id.myRectangleView2);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(0,50,0,0);
            v.requestLayout();
        }
        for(int i = 0; i < topAct.size(); i++){
            loadPanelTop(topAct.get(i),i);
        }
        pieChart.clear();
        loadPieChartData();
    }

    private void loadPanelTop(Activity ac, int index)
    {
        TextView tv = new TextView(getActivity());
        TextView tvTime = new TextView(getActivity());
        View rectangle = new View(getActivity());
        if(index== 0)
        {
            tv= getActivity().findViewById(R.id.top1Activity);
            tvTime = getActivity().findViewById(R.id.top1ActivityTime);
            rectangle = getActivity().findViewById(R.id.top1rectangle);
            getActivity().findViewById(R.id.imageViewTop1).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.clock1).setVisibility(View.VISIBLE);
        }

        if(index== 1)
        {
            tv= getActivity().findViewById(R.id.top2Activity);
            tvTime = getActivity().findViewById(R.id.top2ActivityTime);
            rectangle = getActivity().findViewById(R.id.top2rectangle);
            getActivity().findViewById(R.id.imageViewTop2).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.clock2).setVisibility(View.VISIBLE);
        }
        if(index== 2)
        {
            tv= getActivity().findViewById(R.id.top3Activity);
            tvTime = getActivity().findViewById(R.id.top3ActivityTime);
            rectangle = getActivity().findViewById(R.id.top3rectangle);
            getActivity().findViewById(R.id.imageViewTop3).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.clock3).setVisibility(View.VISIBLE);
        }


        tv.setText(ac.getName());
        tv.setVisibility(View.VISIBLE);
        tvTime.setText(ac.getFormattedTimeTotal());
        tvTime.setVisibility(View.VISIBLE);

        rectangle.setVisibility(View.VISIBLE);
        rectangle.setOnClickListener(new MyOnClickListenerRunAct(ac.getIdActivity(), getActivity(),2));

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry) e;

        SharedPreferences mPreferences = getActivity().getSharedPreferences("previousTab", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("tabHasToChange", true);
        preferencesEditor.putInt("prevTab", 2);
        preferencesEditor.apply();

        Intent intent = new Intent(this.getActivity(), CategoryInfoActivity.class);
        intent.putExtra("categoryName", pe.getLabel());
        startActivity(intent);
    }

    @Override
    public void onNothingSelected() {
        Log.i("page3", "en nothing selected");
    }
}