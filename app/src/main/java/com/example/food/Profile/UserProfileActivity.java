package com.example.food.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.food.Interfaces.IGetNumberListener;
import com.example.food.R;
import com.example.food.Recipe.Recipe;
import com.example.food.RecyclerView.FirebaseRecipeRecyclerViewAdapter;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.example.food.User.User;
import com.example.food.Utils.FirebaseMethods;
import com.example.food.Utils.UniversalImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {

    private View _loadingView;
    private User user;
    private TextView mDisplayName;
    private CircleImageView mProfilePhoto;
    private FirebaseMethods mFirebaseMethods;
    private RecyclerView recyclerView;
    private RecipeRecyclerViewAdapter adapterRecipe;
    private TextView _warningText;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Recipe> _recipesList;
    private TextView _likesText, _dislikeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        user = new Gson().fromJson(intent.getStringExtra("user"), User.class);
        firebaseFirestore = FirebaseFirestore.getInstance();
        _recipesList = new ArrayList<>();

        FindViews();
        GetUserLikesCount();
        SetProfileWidgets();
        RecipeRecyclerViewSetup();

    }

    private void FindViews()
    {
        mDisplayName = findViewById(R.id.display_name);
        mProfilePhoto = findViewById(R.id.profile_photo);
        _loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView);
        _warningText = findViewById(R.id.warning_text);
        _likesText = findViewById(R.id.likesText);
        _dislikeText = findViewById(R.id.dislikesText);
    }

    private void SetProfileWidgets(){
        UniversalImageLoader.setImage(user.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(user.getDisplay_name());
    }

    public void RecipeRecyclerViewSetup(){
        //Query
        _loadingView.setVisibility(View.VISIBLE);
        firebaseFirestore.collection("Recipes").whereEqualTo("user_id",
                user.getUser_id()).orderBy("miliseconds", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    _recipesList.clear();

                    task.getResult().getDocuments().forEach(documentSnapshot -> {
                        _recipesList.add(documentSnapshot.toObject(Recipe.class));
                    });

                    adapterRecipe = new RecipeRecyclerViewAdapter(getBaseContext(), _recipesList);
                    GridLayoutManager layoutManager = new GridLayoutManager(getBaseContext(), 1, GridLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterRecipe);
                }

                _loadingView.setVisibility(View.GONE);
                if(_recipesList.size() == 0)
                    _warningText.setVisibility(View.VISIBLE);
            }
        });
    }

    private void GetUserLikesCount()
    {
        mFirebaseMethods.GetUserLikesCount(user.getUser_id(), new IGetNumberListener() {
            @Override
            public void getNumber(int numb) {
                _likesText.setText(String.valueOf(numb));

                mFirebaseMethods.GetUserDislikesCount(user.getUser_id(), new IGetNumberListener() {
                    @Override
                    public void getNumber(int numb) {
                        _dislikeText.setText(String.valueOf(numb));

                    }
                });
            }
        });
    }
}