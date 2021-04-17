package com.example.food.Home;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.R;
import com.example.food.RecyclerView.CategoryRecyclerViewAdapter;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    //aici o sa scriu codul
    private ChipNavigationBar chipNavigationBar;
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<Integer> mImageUrls = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        chipNavigationBar = (ChipNavigationBar) view.findViewById(R.id.navBar);

        //getImages(inflater, container);
        mImageUrls.add(R.drawable.category_sweet);
        mNames.add("Desert");

        mImageUrls.add(R.drawable.category_bbq);
        mNames.add("BBQ");

        mImageUrls.add(R.drawable.category_breakfast);
        mNames.add("Mic dejun");

        mImageUrls.add(R.drawable.category_vegan);
        mNames.add("Vegan");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        CategoryRecyclerViewAdapter adapter = new CategoryRecyclerViewAdapter(getContext(), mNames, mImageUrls);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}