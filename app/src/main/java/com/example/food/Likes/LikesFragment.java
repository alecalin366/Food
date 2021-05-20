package com.example.food.Likes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.food.Interfaces.IGetStringList;
import com.example.food.R;
import com.example.food.Recipe.Recipe;
import com.example.food.RecyclerView.FirebaseRecipeRecyclerViewAdapter;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.example.food.Utils.FirebaseMethods;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LikesFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView _recyclerView;
    private TextView _placeholderText;
    private View _loadingView;
    private ArrayList<Recipe> _recipesList;
    private RecipeRecyclerViewAdapter _adapter;
    private FirebaseMethods _firebaseMethods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_likes, container, false);

        // Inflate the layout for this fragment
        firebaseFirestore = FirebaseFirestore.getInstance();
        _recipesList = new ArrayList<>();

        _recyclerView = view.findViewById(R.id.recyclerView_recipe);
        _placeholderText = view.findViewById(R.id.placeholderText);
        _loadingView = view.findViewById(R.id.loadingView);
        _firebaseMethods = new FirebaseMethods(getContext());
        _adapter = new RecipeRecyclerViewAdapter(getContext(), _recipesList);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        RecipeRecyclerViewSetup();
    }

    public void RecipeRecyclerViewSetup() {
        _loadingView.setVisibility(View.VISIBLE);
        _firebaseMethods.GetFavoriteRecipes(new IGetStringList() {
            @Override
            public void GetStringList(ArrayList<String> list) {
                if (list.size() == 0) {
                    _recipesList.clear();
                    _adapter.notifyDataSetChanged();
                    _placeholderText.setVisibility(View.VISIBLE);
                    _loadingView.setVisibility(View.GONE);

                }
                list.forEach(recipeId -> {

                    _recipesList.clear();

                    firebaseFirestore.collection("Recipes").document(recipeId)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {

                                Recipe model = task.getResult().toObject(Recipe.class);
                                _recipesList.add(model);
                            }

                            if (_recipesList.size() == list.size()) {
                                LinearLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
                                _recyclerView.setLayoutManager(layoutManager);
                                _recyclerView.setAdapter(_adapter);
                                _loadingView.setVisibility(View.GONE);
                            }
                        }
                    });
                });
            }
        });
    }
}