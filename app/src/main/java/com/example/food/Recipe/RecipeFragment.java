package com.example.food.Recipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food.Models.Category;
import com.example.food.R;
import com.example.food.RecyclerView.CategoryRecyclerViewAdapter;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


public class RecipeFragment extends Fragment {
    private ChipNavigationBar chipNavigationBar;
    //Firestore cloud
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;
    private CategoryRecyclerViewAdapter adapterCategory;
    private RecipeRecyclerViewAdapter adapterRecipe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        chipNavigationBar = (ChipNavigationBar) view.findViewById(R.id.navBar);
        firebaseFirestore = FirebaseFirestore.getInstance();

        CategoryRecyclerViewSetup(view);
        RecipeRecyclerViewSetup(view);

        return view;
    }

    public void CategoryRecyclerViewSetup(View view){
        //Query
        Query query = firebaseFirestore.collection("Category").orderBy("name_category");
        //RecyclerOptions
        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapterCategory = new CategoryRecyclerViewAdapter(getContext(), options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterCategory);

    }

    public void RecipeRecyclerViewSetup(View view){
        //Query
        Query query = firebaseFirestore.collection("Recipes").orderBy("name"); //TREBUIE DUPA DATA

        FirestoreRecyclerOptions<Recipe> options = new FirestoreRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();

        adapterRecipe = new RecipeRecyclerViewAdapter(getContext(), options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_recipe);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecipe);

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCategory.startListening();
        adapterRecipe.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCategory.stopListening();
        adapterRecipe.startListening();
    }
}