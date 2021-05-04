package com.example.food.Utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.Interfaces.IGetRecipeData;
import com.example.food.Interfaces.IGetUserSettings;
import com.example.food.Recipe.Recipe;
import com.example.food.Recipe.UserRecipe;
import com.example.food.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context mContext;
    private String userID;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseFirestore db;
    DocumentReference userRef;
    FirebaseAuth firebaseAuth;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        myRef = mFirebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null)
            userID = mAuth.getCurrentUser().getUid();
    }

    public void updateDisplayName(String displayName) {

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");


        userRef = db.collection("Users")
                .document(userID);

        userRef.update("display_name", displayName
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Alexandra onComplete: update is succesful");
                } else {
                    Log.d(TAG, "Alexandra onComplete: update failed ");
                }

            }
        });
    }

    public void updatePhoneNumber(String phoneNumber) {

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");


        userRef = db.collection("Users")
                .document(userID);

        userRef.update("phone_number", phoneNumber
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: update is succesful");
                } else {
                    Log.d(TAG, "onComplete: update failed ");
                }

            }
        });
    }

    public void updateEmail(String email) {
        Log.d(TAG, "updateEmail: upadting email to: " + email);

        userRef = db.collection("Users")
                .document(mAuth.getUid());

        userRef.update("email", email
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Alexandra onComplete: update is succesful");
                } else {
                    Log.d(TAG, "Alexandra onComplete: update failed ");
                }
            }
        });

    }

    public void registerNewEmail(final String email, String password, final String phoneNumber, ICompleteListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete: " + task.isSuccessful());
                        if (task.isSuccessful()) {
                            sendVerificationEmail(new ICompleteListener() {
                                @Override
                                public void OnComplete(boolean isSuccessfulCompleted) {
                                    listener.OnComplete(isSuccessfulCompleted);
                                }
                            });
                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: authstate changed" + userID);
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mContext, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            listener.OnComplete(false);
                        }
                    }
                });
    }

    public void sendVerificationEmail(ICompleteListener listener) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            listener.OnComplete(task.isSuccessful());
                            if (task.isSuccessful()) {
                                Toast.makeText(mContext, "email verification sent", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Couldn't send email verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //add info to the users and user_account_settings nodes
    public void addNewUser(String email, String phoneNumber, String display_name, String profile_photo, ICompleteListener listener) {
        User user = new User(userID, phoneNumber, email, display_name, profile_photo);

        db.collection("Users").document(userID).set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.OnComplete(task.isSuccessful());
                    }});
    }

    public void RetrieveUserSettings(IGetUserSettings userSettings) {
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase");


        try {
            userRef = db.collection("Users")
                    .document(firebaseAuth.getCurrentUser().getUid());

            userRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User user = documentSnapshot.toObject(User.class);

                            userSettings.getUserSettings(user);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: failed to fetch data");
                        }
                    });
        } catch (NullPointerException e) {
            Log.d(TAG, "getUserAccountSettings: NULLPointerException: " + e.getMessage());
        }
        Log.e(TAG, "getUserAccountSettings: retrieved user_account_settings information: " + userSettings.toString());
    }

    public void AddRecipe(Recipe recipe, ICompleteListener onCompleteListener)
    {
        String recipeUID = UUID.randomUUID().toString();

        db.collection("Recipes").document(recipeUID).set(recipe).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onCompleteListener.OnComplete(task.isSuccessful());
            }
        });

        db.collection("UsersRecipes").document(mAuth.getCurrentUser().getUid())
                .collection("Recipes")
                .document(recipeUID).set(new UserRecipe(recipeUID));
    }

    public void FetchRecipeData(IGetRecipeData recipeData) {
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase");


        try {
            userRef = db.collection("UsersRecipes")
                    .document(firebaseAuth.getCurrentUser().getUid()).collection("Recipes").document();

            userRef.get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Recipe recipe = documentSnapshot.toObject(Recipe.class);

                            recipeData.getRecipeData(recipe);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: failed to fetch data");
                        }
                    });
        } catch (NullPointerException e) {
            Log.d(TAG, "getUserAccountSettings: NULLPointerException: " + e.getMessage());
        }
        Log.e(TAG, "getUserAccountSettings: retrieved recipe information: " + recipeData.toString());
    }
}
