package com.example.food.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.food.R;
//import com.example.food.Utils.BottomNavigationViewHelper;
//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        return view;
    }


}
