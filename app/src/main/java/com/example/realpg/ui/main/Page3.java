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

/**
 * A placeholder fragment containing a simple view.
 */
public class Page3 extends Fragment implements OnChartValueSelectedListener {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static PieChart pieChart;
    private static Page3 instance;

 //   private PageViewModel pageViewModel;
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
   //     pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
      //  pageViewModel.setIndex(index);

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
        /*l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);*/

    }

    //TODO: problema. Los datos de las categorias y actividades se cargan de fichero desde aqui
    //sin embargo este codigo solo se ejecuta cuando se carga esta pagina dese 0
    //es decir, si vienes de la izquierda del todo a esta
    //Por tanto se pierde informacion para las estadisticas de una actividad que acabes de terminar
    //ya que pasarais de la del centro a a esta, y por tanto este codigo no se ejecutaria
    //TODO: pensar en una solucion para eso o hacer como si nada (aunque puede que se note que no va bien)
    private void loadPieChartData() throws JSONException {
        ArrayList<PieEntry> entries = new ArrayList<>();
        JSONObject jsonCatMinutes = Activity.getCategoriesMinutes(getActivity());
        Log.i("demo2", jsonCatMinutes.toString());
        for (Category cat : Category.values()) {

            float p = (float)jsonCatMinutes.getInt(cat.toString())/jsonCatMinutes.getInt("TOTAL");
            //TODO utilizar metodo de cats en minuscula con la primera mayuscula
            if(jsonCatMinutes.getInt(cat.toString()) != 0) entries.add(new PieEntry(p, cat.toString()));
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
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        PieEntry pe = (PieEntry) e;
        Log.d("page3", "en onValueSelected");
        Log.d("page3", "entry selected: " + ((PieEntry) e).getLabel());

        //TextView v = binding.clickedEntry;
        //v.setText(pe.getLabel());

        SharedPreferences mPreferences = getActivity().getSharedPreferences("previousTab", MODE_PRIVATE);
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putBoolean("tabHasToChange", true);
        preferencesEditor.putInt("prevTab", 2);
        preferencesEditor.apply();

        Intent intent = new Intent(this.getActivity(), CategoryInfoActivity.class);
        intent.putExtra("categoryName", pe.getLabel());
        startActivity(intent);
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
    public void onNothingSelected() {
        Log.i("page3", "en nothing selected");
    }
}