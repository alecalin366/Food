package com.example.food.Recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.food.Models.Category;
import com.example.food.R;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RecipeFiltredByCategory extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView _recipeRecyclerView;
    private ArrayList<Recipe> _recipesList;
    private RecipeRecyclerViewAdapter adapterRecipe;
    private Category _currentCategory;
    private View _loadingView;
    private TextView _categoryName, _placeholderText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_filtred_by_category);

        Intent intent = getIntent();
        _currentCategory = new Gson().fromJson(intent.getStringExtra("category"), Category.class);

        firebaseFirestore = FirebaseFirestore.getInstance();
        _recipesList = new ArrayList<>();

        FindViews();
        _categoryName.setText(_currentCategory.getName_category());
        RecipeRecyclerViewSetup();

    }

    private void FindViews()
    {
        _recipeRecyclerView = findViewById(R.id.recyclerView_recipe);
        _categoryName = findViewById(R.id.categoryName);
        _loadingView = findViewById(R.id.loadingView);
        _placeholderText = findViewById(R.id.placeholderText);
    }

    public void RecipeRecyclerViewSetup() {
        //Query
        _loadingView.setVisibility(View.VISIBLE);
        // firebaseFirestore.collection("Recipes").whereEqualTo("category", _currentCategory.getName_category()).orderBy("miliseconds", Query.Direction.DESCENDING)

        firebaseFirestore.collection("Recipes").orderBy("miliseconds", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    _recipesList.clear();

                    task.getResult().getDocuments().forEach(documentSnapshot -> {
                        Recipe model = documentSnapshot.toObject(Recipe.class);
                        if(model.category.contains(_currentCategory.getName_category()))
                            _recipesList.add(model);
                    });

                    adapterRecipe = new RecipeRecyclerViewAdapter(getBaseContext(), _recipesList);
                    LinearLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 2);
                    _recipeRecyclerView.setLayoutManager(layoutManager);
                    _recipeRecyclerView.setAdapter(adapterRecipe);

                }
                if(_recipesList.size() == 0) _placeholderText.setVisibility(View.VISIBLE);
                _loadingView.setVisibility(View.GONE);
            }
        });
    }
}