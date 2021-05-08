package com.example.food.Recipe;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.Interfaces.IGetStringListener;
import com.example.food.R;
import com.example.food.Utils.FirebaseMethods;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddRecipeActivity extends AppCompatActivity {
    private static final String TAG = "NextActivity";
    private View _loadingView;
    private String userID;
    private ImageView _save, backArrow, photo;
    private TextView category;
    private EditText mRecipeName, mPreparationTime, mServingSize, mDescription, mCalorii, mProteine, mCarbo, mGrasimi;
    private Bitmap ReceipeBitmap;

    boolean[] selectedCategory;
    ArrayList<String> categoryList = new ArrayList<>();
    String[] categoryArray = {"Appetizers", "BBQ", "Breakfast", "Soups", "Snacks","Salads", "Indian", "Chinese", "Thai","Greek","Mexican", "Sweets", "Vegan","Pasta","Pizza", "Others"};


    LinearLayout layoutList;
    Button buttonAddIngredient;
    List<String> measurementsList = new ArrayList<>();
    ArrayList<Ingredients> ingredientsList = new ArrayList<>();

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;


    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_layout);

        mFirebaseMethods = new FirebaseMethods(this);

        FindViews();
        initializeSelectedCategory();
        SetupAddIngredientButton();
        SetupSave();
        SetupChoosePhoto();
        setupBackButton();
        setupFirebaseAuth();
    }

    private void FindViews()
    {
        category = findViewById(R.id.category);
        _save = findViewById(R.id.saveChanges);
        backArrow = findViewById(R.id.backArrow);
        photo = findViewById(R.id.profile_photo);
        mRecipeName = findViewById(R.id.nume_reteta);
        mPreparationTime = findViewById(R.id.timp_preparare);
        mServingSize = findViewById(R.id.nr_portii);
        mDescription = findViewById(R.id.descriere);
        mCalorii = findViewById(R.id.cantitate_calorii);
        mProteine = findViewById(R.id.cantitate_proteine);
        mCarbo = findViewById(R.id.cantitate_carbo);
        mGrasimi = findViewById(R.id.cantitate_grasimi);
        layoutList = findViewById(R.id.layout_list);
        buttonAddIngredient = findViewById(R.id.add_ingredient_button);
        _loadingView = findViewById(R.id.loadingView);
        //TODO ale find all objects
    }

    private void SetupAddIngredientButton(){
        buttonAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        setupSpinner();
    }

    private void setupSpinner(){
        measurementsList.add("gr");
        measurementsList.add("l");
        measurementsList.add("kg");
        measurementsList.add("buc");
    }

    private void addView(){
        View ingredientView = getLayoutInflater().inflate(R.layout.add_row_ingredients, null, false);

        AppCompatSpinner spinnerMeasurements = (AppCompatSpinner) ingredientView.findViewById(R.id.spinner_measurements);
        ImageView removeIngr = (ImageView) ingredientView.findViewById(R.id.image_remove);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, measurementsList);
        spinnerMeasurements.setAdapter(arrayAdapter);

        removeIngr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(ingredientView);
            }
        });
        layoutList.addView(ingredientView);
    }

    private void removeView(View view){
        layoutList.removeView(view);
    }

    //============================CATEGORY===============================//

    private void initializeSelectedCategory(){
        selectedCategory = new boolean[categoryArray.length];

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initAlertDialog();
            }
        });
    }

    private void initAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRecipeActivity.this);
        builder.setTitle("Alege o categorie");
        builder.setCancelable(false);

        builder.setMultiChoiceItems(categoryArray, selectedCategory, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked){
                    //When checkbox selected, add position in cateogryList
                    categoryList.add(categoryArray[which]);
                    Collections.sort(categoryList);
                } else {
                    //When checkbox unselected, remove position in cateogryList
                    categoryList.remove(which);
                }
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for( int j=0; j < categoryList.size(); j++){
                    stringBuilder.append(categoryList.get(j));
                    if(j != categoryList.size()-1){
                        stringBuilder.append(", ");
                    }
                }
                category.setText(stringBuilder.toString());
            }
        });

        builder.setNegativeButton("Anuleaza", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setNeutralButton("Sterge tot", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for( int j=0; j < selectedCategory.length; j++){
                    selectedCategory[j] = false;
                    categoryList.clear();
                    category.setText("");
                }
            }
        });

        builder.show();

    }
 
     private void SetupSave()
     {
         _save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SaveRecipe();
             }
         });
     }

     private void setupBackButton(){
         backArrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
     }

    private void SetupChoosePhoto()
    {
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(AddRecipeActivity.this);
            }
        });
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);//one can be replaced with any action code

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        //camera
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ReceipeBitmap = selectedImage;
                        photo.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        //galerie
                        Uri selectedimageUri = data.getData();
                        photo.setImageURI(selectedimageUri);

                        try {
                            ReceipeBitmap = GetBitmapFromUri(selectedimageUri);
                        } catch (IOException e) {
                            Log.d("Error",e.getMessage());
                        }
                    }
            }
        }
    }

    public Bitmap GetBitmapFromUri(Uri imageUri) throws IOException {
        Bitmap bitmap;
        if(Build.VERSION.SDK_INT < 28)
        {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        }
        else
        {
            ImageDecoder.Source source = ImageDecoder.createSource(
                    this.getContentResolver(),imageUri);
            bitmap = ImageDecoder.decodeBitmap(source);
        }

        return bitmap;
    }



    private boolean checkIngredients() {
        boolean result = true;

        for(int i=1;i<layoutList.getChildCount();i++){

            View ingredientView = layoutList.getChildAt(i);

            EditText numeIngredient = (EditText) ingredientView.findViewById(R.id.nume_ingredient);
            EditText cantitateIngredient = (EditText) ingredientView.findViewById(R.id.cantitate_ingredient);
            AppCompatSpinner spinnerMeasurements = (AppCompatSpinner) ingredientView.findViewById(R.id.spinner_measurements);

            String nume = numeIngredient.getText().toString();
            String cantitate = cantitateIngredient.getText().toString();
            String spinner = measurementsList.get(spinnerMeasurements.getSelectedItemPosition());
            Ingredients ingredient = new Ingredients(nume, cantitate, spinner);

            ingredientsList.add(ingredient);
        }

        if(ingredientsList.size()==0){
            result = false;
            Toast.makeText(this, "Add Ingredients First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
            Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    public List<Ingredients> getIngredientList(){
        return ingredientsList;
    }

     private void SaveRecipe()
     {
         final String recipeName = mRecipeName.getText().toString();
         final String description = mDescription.getText().toString();
         final String preparationTime = mPreparationTime.getText().toString();
         final String servingSize = mServingSize.getText().toString();
         final String calorii = mCalorii.getText().toString();
         final String proteine = mProteine.getText().toString();
         final String carbo = mCarbo.getText().toString();
         final String grasimi = mGrasimi.getText().toString();
         final String category = categoryList.toString();
         userID = mAuth.getCurrentUser().getUid();

         if(!checkIngredients() || ingredientsList == null || ingredientsList.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga ingrediente", Toast.LENGTH_SHORT).show();
         }

         if(recipeName == null || recipeName.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga titlu", Toast.LENGTH_SHORT).show();
             return;
         }

         if(description == null || description.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga descriere", Toast.LENGTH_SHORT).show();
             return;
         }

         if(preparationTime == null || preparationTime.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga preparationTime", Toast.LENGTH_SHORT).show();
             return;
         }

         if(servingSize == null || servingSize.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga servingSize", Toast.LENGTH_SHORT).show();
             return;
         }

         if(calorii == null || calorii.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga calorii", Toast.LENGTH_SHORT).show();
             return;
         }

         if(proteine == null || proteine.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga proteine", Toast.LENGTH_SHORT).show();
             return;
         }

         if(carbo == null || carbo.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga carbo", Toast.LENGTH_SHORT).show();
             return;
         }

         if(grasimi == null || grasimi.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga grasimi", Toast.LENGTH_SHORT).show();
             return;
         }

         if(category == null || category.isEmpty()){
             Toast.makeText(getApplicationContext(), "adauga category", Toast.LENGTH_SHORT).show();
             return;
         }
         Macronutrient macro = new Macronutrient(calorii, proteine, carbo, grasimi);
         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
         String recipeUID = UUID.randomUUID().toString();

         _loadingView.setVisibility(View.VISIBLE);
         if(ReceipeBitmap != null)
         {
             mFirebaseMethods.UploadImage(recipeUID, ReceipeBitmap, new IGetStringListener() {
                 @Override
                 public void GetString(String photoUrl) {
                     if(!photoUrl.isEmpty())
                     {
                         AddRecipe(recipeUID,recipeName,category,description,preparationTime,servingSize,photoUrl,macro);
                     }
                     else finish();
                 }
             });
         }
         else
         {
             AddRecipe(recipeUID,recipeName,category,description,preparationTime,servingSize,"",macro);
         }
     }

    private void AddRecipe(String recipeUID,String recipeName,String category,String description,String preparationTime,String servingSize,String photoUrl,Macronutrient macro)
    {
         Recipe testRecipe = new Recipe(userID, recipeName, category, description, preparationTime, servingSize, photoUrl, macro, ingredientsList);
        mFirebaseMethods.AddRecipe(testRecipe,recipeUID ,new ICompleteListener() {
             @Override
             public void OnComplete(boolean isSuccessfulCompleted) {
                 if(isSuccessfulCompleted)
                 {
                     Toast.makeText(getApplicationContext(),"Reteta a fost adaugata cu succes",Toast.LENGTH_LONG).show();
                 }
                 else {
                     Toast.makeText(getApplicationContext(),"Reteta nu a fost adaugata cu succes",Toast.LENGTH_LONG).show();
                 }
                 _loadingView.setVisibility(View.GONE);
                 finish();
             }
         });
     }

      /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
