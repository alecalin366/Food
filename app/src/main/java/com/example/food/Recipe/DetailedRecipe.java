package com.example.food.Recipe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food.Interfaces.IExistsListener;
import com.example.food.Interfaces.IGetNumberListener;
import com.example.food.R;
import com.example.food.RecyclerView.IngredientRecyclerViewAdapter;
import com.example.food.Utils.FirebaseMethods;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


public class DetailedRecipe extends AppCompatActivity {
    private Recipe recipe;
    Button favorite, cart;
    ImageView backArrow, photo;
    TextView name, ownerName, preparationTime, servings, description, macro_calorii, macro_proteine, macro_carbo, macro_grasimi;
    private View _likeView, _dislikeView, _loadingView;
    RecyclerView ingredientsRecyclerView;
    private FirebaseMethods _firebaseMethods;
    private ImageView _likeImage, _dislikeImage, _activeLikeImage, _activeDislikeImage;
    private TextView _likesText,_dislikeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_recipe);

        Intent intent = getIntent();
        recipe = new Gson().fromJson(intent.getStringExtra("recipe"), Recipe.class);
        _firebaseMethods = new FirebaseMethods(this);

        FindViews();
        SetViewsFields();
        setupBackButton();
        UpdateLikeAndDislikeState();
        SetLikeAndDislikeClickMethods();
        UpdateLikeAndDislikeState();
    }

    public void setupBackButton() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void FindViews() {
        favorite = findViewById(R.id.favoriteButton);
        cart = findViewById(R.id.addToCart);
        backArrow = findViewById(R.id.backArrow);
        photo = findViewById(R.id.detailed_recipe_photo);
        name = findViewById(R.id.detailed_recipe_title);
        ownerName = findViewById(R.id.owner);
        preparationTime = findViewById(R.id.detailed_recipe_time);
        servings = findViewById(R.id.portii);
        ingredientsRecyclerView = findViewById(R.id.recyclerView1);
        description = findViewById(R.id.descriere);
        macro_calorii = findViewById(R.id.cantitate_calorii);
        macro_proteine = findViewById(R.id.cantitate_proteine);
        macro_carbo = findViewById(R.id.cantitate_carbo);
        macro_grasimi = findViewById(R.id.cantitate_grasimi);
        _likeView = findViewById(R.id.likeView);
        _dislikeView = findViewById(R.id.dislikeView);
        _likeImage = findViewById(R.id.likeImage);
        _dislikeImage = findViewById(R.id.dislikeImage);
        _dislikeView = findViewById(R.id.dislikeView);
        _activeLikeImage = findViewById(R.id.likeImage2);
        _activeDislikeImage = findViewById(R.id.dislikeImage2);
        _loadingView = findViewById(R.id.loadingView);
        _likesText = findViewById(R.id.likesText);
        _dislikeText = findViewById(R.id.dislikesText);
    }

    @SuppressLint("DefaultLocale")
    private void SetViewsFields() {
        name.setText(recipe.getName());
        description.setText(recipe.getDescription());
        preparationTime.setText(recipe.getPreparationTime());
        servings.setText(recipe.getServingSize());

        if (!recipe.photo.isEmpty()) {
            Picasso.get()
                    .load(recipe.photo)
                    .placeholder(R.drawable.ic_loading)
                    .error(R.drawable.ic_error)
                    .into(photo);
        }

        IngredientRecyclerViewAdapter recyclerAdapter3 = new IngredientRecyclerViewAdapter(recipe.ingredients);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        ingredientsRecyclerView.setAdapter(recyclerAdapter3);

        macro_calorii.setText(recipe.macro.getCalorii());
        macro_proteine.setText(recipe.macro.getProteine());
        macro_carbo.setText(recipe.macro.getCarbo());
        macro_grasimi.setText(recipe.macro.getGrasimi());

        _likesText.setText(String.format("%d Likes", recipe.getLikesCount()));
        _dislikeText.setText(String.format("%d Dislikes", recipe.getDislikesCount()));

    }


    private void UpdateLikeAndDislikeState() {

        _loadingView.setVisibility(View.VISIBLE);

        _firebaseMethods.CheckIfILikedRecipe(recipe.getRecipeId(), new IExistsListener() {
            @Override
            public void Exists(boolean exists) {
                if (exists) {
                    _likeImage.setVisibility(View.GONE);
                    _activeDislikeImage.setVisibility(View.GONE);
                    _dislikeImage.setVisibility(View.VISIBLE);
                    _activeLikeImage.setVisibility(View.VISIBLE);
                    UpdateLikeAndDislikesText();
                } else {
                    _activeLikeImage.setVisibility(View.GONE);
                    _likeImage.setVisibility(View.VISIBLE);

                    _firebaseMethods.CheckIfIDislikedRecipe(recipe.getRecipeId(), new IExistsListener() {
                        @Override
                        public void Exists(boolean exists) {
                            if (exists) {
                                _dislikeImage.setVisibility(View.GONE);
                                _activeDislikeImage.setVisibility(View.VISIBLE);
                            } else {
                                _dislikeImage.setVisibility(View.VISIBLE);
                                _activeDislikeImage.setVisibility(View.GONE);
                            }
                            UpdateLikeAndDislikesText();
                        }
                    });
                }
            }
        });
    }

    private void UpdateLikeAndDislikesText()
    {
        _firebaseMethods.GetRecipeLikesCount(recipe.getRecipeId(), new IGetNumberListener() {
            @Override
            public void getNumber(int numb) {
                recipe.setLikesCount(numb);

                _firebaseMethods.GetRecipeDislikesCount(recipe.getRecipeId(), new IGetNumberListener() {
                    @Override
                    public void getNumber(int numb) {
                        recipe.setDislikesCount(numb);

                        _loadingView.setVisibility(View.GONE);
                        _likesText.setText(String.format("%d Likes", recipe.getLikesCount()));
                        _dislikeText.setText(String.format("%d Dislikes", recipe.getDislikesCount()));
                    }
                });
            }
        });
    }
    private void SetLikeAndDislikeClickMethods() {
        _likeView.setOnClickListener(view ->
        {
            _loadingView.setVisibility(View.VISIBLE);

            _firebaseMethods.LikeRecipe(recipe.getRecipeId(),
                    isSuccessfulCompleted -> {
                        UpdateLikeAndDislikeState();
                    });
        });

        _dislikeView.setOnClickListener(view -> {
            _loadingView.setVisibility(View.VISIBLE);

            _firebaseMethods.DislikeRecipe(recipe.getRecipeId(),
                    isSuccessfulCompleted -> {
                        UpdateLikeAndDislikeState();
                    });
        });
    }
}
