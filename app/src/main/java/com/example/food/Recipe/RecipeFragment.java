package com.example.food.Recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.food.Models.Category;
import com.example.food.R;
import com.example.food.RecyclerView.CategoryRecyclerViewAdapter;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;


public class RecipeFragment extends Fragment {
    private ChipNavigationBar chipNavigationBar;
    //Firestore cloud
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView _categoryRecyclerView, _recipeRecyclerView;
    private CategoryRecyclerViewAdapter adapterCategory;
    private RecipeRecyclerViewAdapter adapterRecipe;
    private EditText _searchEditText;
    private ArrayList<Recipe> _recipesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);
        chipNavigationBar = (ChipNavigationBar) view.findViewById(R.id.navBar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        _searchEditText = view.findViewById(R.id.editTextTextPersonName);
        _recipeRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_recipe);
        _categoryRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_category);
        _recipesList = new ArrayList<>();

        SetupSearchEditText();
        CategoryRecyclerViewSetup();
        RecipeRecyclerViewSetup();

        return view;
    }

    private void SetupSearchEditText() {
        _searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                firebaseFirestore.collection("Recipes")
                        .whereGreaterThanOrEqualTo("name", charSequence.toString().toLowerCase())
                        .orderBy("name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            _recipesList.clear();

                            task.getResult().getDocuments().forEach(documentSnapshot -> {
                                Recipe model = documentSnapshot.toObject(Recipe.class);
                                if(model.name.contains(charSequence.toString().toLowerCase()))
                                    _recipesList.add(model);
                            });

                            adapterRecipe.notifyDataSetChanged();
                        }

                    }

                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void CategoryRecyclerViewSetup() {
        //Query
        Query query = firebaseFirestore.collection("Category").orderBy("name_category");
        //RecyclerOptions
        FirestoreRecyclerOptions<Category> options = new FirestoreRecyclerOptions.Builder<Category>()
                .setQuery(query, Category.class)
                .build();

        adapterCategory = new CategoryRecyclerViewAdapter(getContext(), options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        _categoryRecyclerView.setLayoutManager(layoutManager);
        _categoryRecyclerView.setAdapter(adapterCategory);

    }

    public void RecipeRecyclerViewSetup() {
        //Query
        firebaseFirestore.collection("Recipes").orderBy("miliseconds", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    _recipesList.clear();

                    task.getResult().getDocuments().forEach(documentSnapshot -> {
                        Recipe model = documentSnapshot.toObject(Recipe.class);
                        _recipesList.add(model);
                    });
                    adapterRecipe = new RecipeRecyclerViewAdapter(getContext(), _recipesList);
                    LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                    _recipeRecyclerView.setLayoutManager(layoutManager);
                    _recipeRecyclerView.setAdapter(adapterRecipe);
                }

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        adapterCategory.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterCategory.stopListening();
    }
}