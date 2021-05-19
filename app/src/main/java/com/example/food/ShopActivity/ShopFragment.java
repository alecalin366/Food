package com.example.food.ShopActivity;

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

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.Interfaces.IGetCartList;
import com.example.food.Interfaces.IGetRecipeData;
import com.example.food.Interfaces.IGetSelectedIngredient;
import com.example.food.Interfaces.IGetStringList;
import com.example.food.Interfaces.IGetStringListener;
import com.example.food.Models.CartRecipe;
import com.example.food.Models.SpecialIngredient;
import com.example.food.R;
import com.example.food.Recipe.Recipe;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.example.food.ShopActivity.ShopModels.IShopModel;
import com.example.food.ShopActivity.ShopModels.SectionModel;
import com.example.food.ShopActivity.ShopModels.SelectableModel;
import com.example.food.Utils.FirebaseMethods;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView _recyclerView;
    private TextView _placeholderText;
    private View _loadingView;
    private ArrayList<CartRecipe> _recipesList;
    private MyAdapter _adapter;
    private FirebaseMethods _firebaseMethods;
    private int index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        // Inflate the layout for this fragment
        firebaseFirestore = FirebaseFirestore.getInstance();
        _recipesList = new ArrayList<>();

        _recyclerView = view.findViewById(R.id.recyclerView_recipe);
        _placeholderText = view.findViewById(R.id.placeholderText);
        _loadingView = view.findViewById(R.id.loadingView);
        _firebaseMethods = new FirebaseMethods(getContext());

        RecipeRecyclerViewSetup();

        return view;
    }

    public void RecipeRecyclerViewSetup() {
        _loadingView.setVisibility(View.VISIBLE);
        _firebaseMethods.GerUserCart(new IGetCartList() {

            @Override
            public void GetCartList(ArrayList<CartRecipe> list) {
                if (list.size() == 0) {
                    _recipesList.clear();
                    _placeholderText.setVisibility(View.VISIBLE);
                    _loadingView.setVisibility(View.GONE);

                } else {
                    _recipesList = list;
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    _recyclerView.setLayoutManager(layoutManager);
                    _adapter = new MyAdapter(FormatShopMenu(_recipesList), new IGetSelectedIngredient() {
                        @Override
                        public void GetSelectedIngredient(SpecialIngredient ingredient) {
                            UpdateUserCard(ingredient);
                        }
                    },
                            new IGetRecipeData() {
                                @Override
                                public void getRecipeData(Recipe recipeData) {
                                    _loadingView.setVisibility(View.VISIBLE);
                                    _firebaseMethods.RemoveRecipeFromCart(new CartRecipe(recipeData, null), new ICompleteListener() {
                                        @Override
                                        public void OnComplete(boolean isSuccessfulCompleted) {
                                            _recipesList.forEach(recipe -> {
                                                if (recipe.getRecipe().recipeId.equals(recipeData.recipeId)) {
                                                    index = _recipesList.indexOf(recipe);
                                                }
                                            });

                                            _recipesList.remove(index);
                                            if(_recipesList.size() == 0)
                                            {
                                                _recyclerView.setVisibility(View.GONE);
                                                _placeholderText.setVisibility(View.VISIBLE);
                                            }
                                            else {
                                                _adapter.list = FormatShopMenu(_recipesList);
                                                _adapter.notifyDataSetChanged();
                                            }
                                            _loadingView.setVisibility(View.GONE);
                                        }
                                    });

                                }
                            });
                    _recyclerView.setAdapter(_adapter);
                    _adapter.notifyDataSetChanged();
                }
                _loadingView.setVisibility(View.GONE);

            }
        });
    }

    private ArrayList<IShopModel> FormatShopMenu(ArrayList<CartRecipe> list) {
        ArrayList<IShopModel> shopModels = new ArrayList<>();

        list.forEach(cartRecipe -> {
            shopModels.add(new SectionModel(cartRecipe.getRecipe()));
            cartRecipe.getIngredients().forEach(specialIngredient -> {
                shopModels.add(new SelectableModel(specialIngredient));
            });
        });

        return shopModels;
    }

    private void UpdateUserCard(SpecialIngredient ingredient) {
        _loadingView.setVisibility(View.VISIBLE);

        _recipesList.forEach(cartRecipe -> {
            cartRecipe.getIngredients().forEach(specialIngredient -> {
                if (specialIngredient.get_recipeId().equals(ingredient.get_recipeId()) &&
                        specialIngredient.get_ingredient().getName_ingredient().equals(ingredient.get_ingredient().getName_ingredient())) {
                    specialIngredient.set_isChecked(ingredient.is_isChecked());

                    _firebaseMethods.AddRecipeToCart(cartRecipe, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {
                            _loadingView.setVisibility(View.GONE);

                        }
                    });
                }
            });
        });
    }
}