package com.example.food.Recipe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.R;
import com.example.food.SelectPhotos.SelectPhotoActivity;
import com.example.food.Utils.FirebaseMethods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {
    private ImageView _save, backArrow, photo;
    TextView category;
    private EditText name, preparation_time, serving_size, description, calorii, proteine, carbo, grasimi;
    boolean[] selectedCategory;
    ArrayList<Integer> categoryList = new ArrayList<>();
    String[] categoryArray = {"Desert", "Vegan", "Sosuri"};
    private FirebaseMethods mFirebaseMethods;

    LinearLayout layoutList;
    Button buttonAddIngredient;
    List<String> measurementsList = new ArrayList<>();


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

        EditText numeIngredient = (EditText) ingredientView.findViewById(R.id.nume_ingredient);
        EditText cantitateIngredient = (EditText) ingredientView.findViewById(R.id.cantitate_ingredient);
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
                    categoryList.add(which);
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
                    stringBuilder.append(categoryArray[categoryList.get(j)]);
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
 
    private void FindViews()
     {
         category = findViewById(R.id.category);
         _save = findViewById(R.id.saveChanges);
         backArrow = findViewById(R.id.backArrow);
         photo = findViewById(R.id.profile_photo);
         name = findViewById(R.id.nume_reteta);
         preparation_time = findViewById(R.id.timp_preparare);
         serving_size = findViewById(R.id.nr_portii);
         description = findViewById(R.id.descriere);
         calorii = findViewById(R.id.cantitate_calorii);
         proteine = findViewById(R.id.cantitate_proteine);
         carbo = findViewById(R.id.cantitate_carbo);
         grasimi = findViewById(R.id.cantitate_grasimi);
         layoutList = findViewById(R.id.layout_list);
         buttonAddIngredient = findViewById(R.id.add_ingredient_button);
         //TODO ale find all objects
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
                Intent intent = new Intent(AddRecipeActivity.this, SelectPhotoActivity.class);
                startActivity(intent);
            }
        });
    }

     private void SaveRecipe()
     {
         //verfici sa fie adaugate toate campurile
         //TODO construiesti un obiect de tipul reteta...
         //string name = nameText.Text;
         //Reteta reteta = new Reteta(nume,categorii);
         Recipe testRecipe = new Recipe("Cartofi prajiti");//la misto
         mFirebaseMethods.AddRecipe(testRecipe, new ICompleteListener() {
             @Override
             public void OnComplete(boolean isSuccessfulCompleted) {
                 if(isSuccessfulCompleted)
                 {
                     Toast.makeText(getApplicationContext(),"Reteta a fost adaugata cu succes",Toast.LENGTH_LONG).show();
                 }
                 else {
                     Toast.makeText(getApplicationContext(),"Reteta nu a fost adaugata cu succes",Toast.LENGTH_LONG).show();
                 }
             }
         });
     }
}
