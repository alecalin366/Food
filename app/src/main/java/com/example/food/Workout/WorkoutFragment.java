package com.example.food.Workout;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.R;

public class WorkoutFragment extends Fragment {

    private RecyclerAdapterArmsWorkout adapter;
    private RecyclerAdapterGlutesWorkout adapter2;
    private RecyclerAdapterAbsWorkout adapter3;
    private RecyclerAdapterFullBodyWorkout adapter4;
    private RecyclerAdapterOnlyGymWorkout adapter5;
    private RecyclerAdapterUpperBodyWorkout adapter6;
    private RecyclerAdapterYogaWorkout adapter7;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private RecyclerView recyclerView7;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_workout, container, false);

        context = getContext();

        recyclerView =  view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterArmsWorkout(context);
        recyclerView.setAdapter(adapter);


        recyclerView2 =  view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context);
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        adapter2 = new RecyclerAdapterGlutesWorkout(context);
        recyclerView2.setAdapter(adapter2);

        recyclerView3 =  view.findViewById(R.id.recyclerView3);
        recyclerView3.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(context);
        linearLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(linearLayoutManager3);
        adapter3 = new RecyclerAdapterAbsWorkout(context);
        recyclerView3.setAdapter(adapter3);

        recyclerView4 =  view.findViewById(R.id.recyclerView4);
        recyclerView4.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(context);
        linearLayoutManager4.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView4.setLayoutManager(linearLayoutManager4);
        adapter4 = new RecyclerAdapterFullBodyWorkout(context);
        recyclerView4.setAdapter(adapter4);

        recyclerView5 =  view.findViewById(R.id.recyclerView5);
        recyclerView5.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager5 = new LinearLayoutManager(context);
        linearLayoutManager5.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView5.setLayoutManager(linearLayoutManager5);
        adapter5 = new RecyclerAdapterOnlyGymWorkout(context);
        recyclerView5.setAdapter(adapter5);

        recyclerView6 =  view.findViewById(R.id.recyclerView6);
        recyclerView6.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager6 = new LinearLayoutManager(context);
        linearLayoutManager6.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView6.setLayoutManager(linearLayoutManager6);
        adapter6 = new RecyclerAdapterUpperBodyWorkout(context);
        recyclerView6.setAdapter(adapter6);

        recyclerView7 =  view.findViewById(R.id.recyclerView7);
        recyclerView7.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager7 = new LinearLayoutManager(context);
        linearLayoutManager7.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView7.setLayoutManager(linearLayoutManager7);
        adapter7 = new RecyclerAdapterYogaWorkout(context);
        recyclerView7.setAdapter(adapter7);

        return view;
    }
}