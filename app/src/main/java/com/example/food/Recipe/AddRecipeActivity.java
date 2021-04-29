package com.example.food.Recipe;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.food.Interfaces.ICompleteListener;
import com.example.food.R;
import com.example.food.Utils.FirebaseMethods;

public class AddRecipeActivity extends AppCompatActivity {
    private ImageView _saveImage;
    private FirebaseMethods mFirebaseMethods;

    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_recipe_layout);
         
        mFirebaseMethods = new FirebaseMethods(this);
         
        FindViews();
        SetupSaveImage();
    }
 
    private void FindViews()
     {
         _saveImage = findViewById(R.id.saveChanges);
         //TODO ale find all objects
     }
 
     private void SetupSaveImage()
     {
         _saveImage.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 SaveRecipe();
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
