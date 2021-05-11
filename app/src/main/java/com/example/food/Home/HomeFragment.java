package com.example.food.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.food.Interfaces.IGetRecipeData;
import com.example.food.Interfaces.IGetUserSettings;
import com.example.food.Models.Category;
import com.example.food.Profile.AccountSettingsActivity;
import com.example.food.R;
import com.example.food.Recipe.AddRecipeActivity;
import com.example.food.Recipe.Recipe;
import com.example.food.RecyclerView.RecipeRecyclerViewAdapter;
import com.example.food.User.User;
import com.example.food.Utils.FirebaseMethods;
import com.example.food.Utils.UniversalImageLoader;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private TextView  mDisplayName;
    //private ProgressBar mProgressBar;
    private CircleImageView mProfilePhoto;

    private Toolbar toolbar;
    private AppCompatButton profileMenu, chat;
    private AppCompatButton addRecipeButton;
    private ChipNavigationBar chipNavigationBar;
    private Context mContext;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private RecyclerView recyclerView;
    private RecipeRecyclerViewAdapter adapterRecipe;
    private TextView _warningText;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mDisplayName = view.findViewById(R.id.display_name);
        mProfilePhoto = view.findViewById(R.id.profile_photo);
        toolbar = view.findViewById(R.id.profileToolBar);
        profileMenu = view.findViewById(R.id.buton_setari);
        chipNavigationBar = view.findViewById(R.id.navBar);
        addRecipeButton = view.findViewById(R.id.buton_adaugare_reteta);
        mContext = getActivity();
        mFirebaseMethods = new FirebaseMethods(getActivity());
        recyclerView = view.findViewById(R.id.recyclerView);
        _warningText = view.findViewById(R.id.warning_text);
        Log.d(TAG, "onCreateView: started");

        firebaseFirestore = FirebaseFirestore.getInstance();


        setupToolbar();
        setupAddRecipeButton();
        setupFirebaseAuth();
        RecipeRecyclerViewSetup(view);
        return view;
    }

    public void RecipeRecyclerViewSetup(View view){
        //Query
        Query query = firebaseFirestore.collection("Recipes").whereEqualTo("user_id",
                FirebaseAuth.getInstance().getCurrentUser().getUid()).orderBy("miliseconds", Query.Direction.DESCENDING); //TREBUIE DUPA DATA

        FirestoreRecyclerOptions<Recipe> options = new FirestoreRecyclerOptions.Builder<Recipe>()
                .setQuery(query, Recipe.class)
                .build();

        adapterRecipe = new RecipeRecyclerViewAdapter(getContext(), options);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterRecipe);
        adapterRecipe.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                if(itemCount != 0)
                {
                    _warningText.setVisibility(View.INVISIBLE);
                }
                super.onItemRangeInserted(positionStart, itemCount);
            }

        });
        adapterRecipe.startListening();

    }

    private void setupAddRecipeButton() {
        addRecipeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddRecipeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setProfileWidgets(User user){
        UniversalImageLoader.setImage(user.getProfile_photo(), mProfilePhoto, null, "");

        mDisplayName.setText(user.getDisplay_name());
    }


    private void setupToolbar() {
        profileMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to account settings");
                Intent intent = new Intent(mContext, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });
    }
    /*
    ----------------------------Firebase------------------------------------------
     */

            /**
             * Setup the firebase auth object
             */
            private void setupFirebaseAuth(){
                Log.d(TAG, "setupFirebaseAuth: setting up firebase");
                mAuth = FirebaseAuth.getInstance();
                mFirebaseDatabase = FirebaseDatabase.getInstance();
                myRef = mFirebaseDatabase.getReference();

                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();

                        if(user != null){
                            //User is signed in
                            Log.d(TAG, "onAuthStateChanged: signed in" + user.getUid());
                        } else {
                            //User is signed out
                            Log.d(TAG, "onAuthStateChanged: signed out");
                        }
                    }
                };


                mFirebaseMethods.RetrieveUserSettings(new IGetUserSettings() {
                    @Override
                    public void getUserSettings(User userSettings) {
                        setProfileWidgets(userSettings);
                    }
                });
            }

            @Override
            public void onStart() {
                super.onStart();
                mAuth.addAuthStateListener(mAuthListener);
            }

            @Override
            public void onStop() {
                super.onStop();
                if(mAuthListener != null) {
                    mAuth.removeAuthStateListener(mAuthListener);
                }
            }
}