package com.example.realpg.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.realpg.databinding.FragmentPage1Binding;
import com.example.realpg.databinding.FragmentPage3Binding;

/**
 * A placeholder fragment containing a simple view.
 */
public class Page3 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}