package com.example.food.Utils;

import android.app.usage.NetworkStats;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food.Interfaces.IGetUserSettings;
import com.example.food.R;
import com.example.food.User.User;
import com.example.food.User.UserSettings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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


        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
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

    /**
     * update username in the 'users'
     *
     * @param username
     */
    public void updateUsername(String username) {
        Log.d(TAG, "updateUsername: upadting username to: " + username);

        userRef = db.collection("Users")
                .document(mAuth.getUid());

        userRef.update("username", username
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

//    public boolean checkIfUsernameExists(String username, DataSnapshot dataSnapshot){
//        Log.d(TAG, "checkIfUsernameExists: checking if" + username +"already exists");
//
//        User user = new User();
//
//        for(DataSnapshot ds : dataSnapshot.child(userID).getChildren()){ //iterate nodes from firebase(user_account & user)
//            Log.d(TAG, "checkIfUsernameExists: datasnapshot " + ds);
//
//            user.setUsername(ds.getValue(User.class).getUsername());
//            Log.d(TAG, "checkIfUsernameExists: username " + user.getUsername());
//
//            if(StringManipulation.expandUsername(user.getUsername()).equals(username)){
//                Log.d(TAG, "checkIfUsernameExists: FOUND A MATCH: " + user.getUsername());
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * Register a new email and password to Firebase Authentication
     **/
    public void registerNewEmail(final String email, String password, final String username, final String phoneNumber) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete: " + task.isSuccessful());
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(mContext, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        } else if (task.isSuccessful()) {
                            sendVerificationEmail();

                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: authstate changed" + userID);
                        }
                    }
                });
    }

    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                            } else {
                                Toast.makeText(mContext, "Couldn't send email verification", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    //add info to the users and user_account_settings nodes
    public void addNewUser(String email, String username, String phoneNumber, String profile_photo) {
        User user = new User(userID, phoneNumber, email, StringManipulation.condenseUsername(username), username, profile_photo);

        db.collection("Users").document(userID).set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added with ID: ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
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
}
