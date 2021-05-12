package com.example.food.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.food.Interfaces.ICompleteListener;
import com.example.food.Interfaces.IExistsListener;
import com.example.food.Interfaces.IGetNumberListener;
import com.example.food.Interfaces.IGetStringListener;
import com.example.food.Interfaces.IGetUserSettings;
import com.example.food.Models.LikeDislikeModel;
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
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private FirebaseStorage firebaseStorage;
    DocumentReference userRef;
    FirebaseAuth firebaseAuth;

    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mContext = context;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        db = FirebaseFirestore.getInstance();
        myRef = mFirebaseDatabase.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
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

    public void UpdateProfilePhoto(String photo) {

        Log.d(TAG, "updateUserAccountSettings: updating user account settings.");


        userRef = db.collection("Users")
                .document(userID);

        userRef.update("profile_photo", photo
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
                        } else {
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
                    }
                });
    }

    public void RetrieveUserSettings(String userId, IGetUserSettings userSettings) {
        Log.d(TAG, "getUserAccountSettings: retrieving user account settings from firebase");


        try {
            userRef = db.collection("Users")
                    .document(userId);

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

    public void AddRecipe(Recipe recipe, String recipeUID, ICompleteListener onCompleteListener) {

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

    public void UploadImage(String receipeId, Bitmap bitmap, IGetStringListener listener) {
        StorageReference riversRef = firebaseStorage.getReference().child(receipeId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByteArray = baos.toByteArray();
        UploadTask task = riversRef.putBytes(imageByteArray);
        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            listener.GetString(url);
                            Log.d("TESTLOG", url);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.GetString("");
                        }
                    });
                }
            }
        });
    }

    public void UploadProfileImage(String userId, Bitmap bitmap, IGetStringListener listener) {
        StorageReference riversRef = firebaseStorage.getReference().child("profilePhoto/" + userId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageByteArray = baos.toByteArray();
        UploadTask task = riversRef.putBytes(imageByteArray);
        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String url = uri.toString();
                            listener.GetString(url);
                            Log.d("TESTLOG", url);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            listener.GetString("");
                        }
                    });
                }
            }
        });
    }

    public void LikeRecipe(String recipeId, ICompleteListener listener) {
        RemoveLDislike(recipeId, new ICompleteListener() {
            @Override
            public void OnComplete(boolean isSuccessful) {
                CheckIfILikedRecipe(recipeId, exists -> {
                    if (exists) {
                        RemoveLike(recipeId, isSuccessfulCompleted -> {
                            listener.OnComplete(isSuccessfulCompleted);
                        });
                    } else {
                        AddLikeToRecipe(recipeId, isSuccessfulCompleted -> {
                            listener.OnComplete(isSuccessfulCompleted);
                        });
                    }
                });
            }
        });


    }

    public void CheckIfILikedRecipe(String recipeId, IExistsListener listener) {
        String userID = mAuth.getCurrentUser().getUid();

        db.collection("Recipes").document(recipeId).collection("Likes").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AtomicBoolean found = new AtomicBoolean(false);
                        task.getResult().getDocuments().forEach(documentSnapshot -> {
                            LikeDislikeModel model = documentSnapshot.toObject(LikeDislikeModel.class);
                            if (model.getUserID().equals(userID)) {
                                listener.Exists(true);
                                found.set(true);
                                return;
                            }

                        });
                        if (!found.get())
                            listener.Exists(false);
                    } else listener.Exists(false);
                });
    }

    private void RemoveLike(String recipeId, ICompleteListener listener) {
        String userID = mAuth.getCurrentUser().getUid();

        db.collection("Recipes").document(recipeId).collection("Likes").document(userID).delete()
                .addOnCompleteListener(task -> {
                    UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {

                            UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                                @Override
                                public void OnComplete(boolean isSuccessfulCompleted) {
                                    listener.OnComplete(task.isSuccessful());
                                }
                            });
                        }
                    });
                });
    }

    private void AddLikeToRecipe(String recipeId, ICompleteListener listener) {
        LikeDislikeModel likeDislikeModel = new LikeDislikeModel(userID);
        db.collection("Recipes").document(recipeId).collection("Likes")
                .document(userID).set(likeDislikeModel).addOnCompleteListener(task -> {

            UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                @Override
                public void OnComplete(boolean isSuccessfulCompleted) {

                    UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {
                            listener.OnComplete(task.isSuccessful());
                        }
                    });
                }
            });
        });
    }

    public void DislikeRecipe(String recipeId, ICompleteListener listener) {
        RemoveLike(recipeId, isSuccessful -> {

            CheckIfIDislikedRecipe(recipeId, exists -> {
                if (exists) {
                    RemoveLDislike(recipeId, isSuccessfulCompleted -> {

                        UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                            @Override
                            public void OnComplete(boolean isSuccessfulCompleted) {

                                UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                                    @Override
                                    public void OnComplete(boolean isSuccessfulCompleted) {
                                        listener.OnComplete(isSuccessfulCompleted);
                                    }
                                });
                            }
                        });

                    });
                } else {
                    AddDislikeToRecipe(recipeId, isSuccessfulCompleted -> {

                        UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                            @Override
                            public void OnComplete(boolean isSuccessfulCompleted) {

                                UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                                    @Override
                                    public void OnComplete(boolean isSuccessfulCompleted) {
                                        listener.OnComplete(isSuccessfulCompleted);
                                    }
                                });
                            }
                        });
                    });
                }
            });
        });
    }

    public void CheckIfIDislikedRecipe(String recipeId, IExistsListener listener) {
        String userID = mAuth.getCurrentUser().getUid();

        db.collection("Recipes").document(recipeId).collection("Dislikes").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        AtomicBoolean found = new AtomicBoolean(false);
                        task.getResult().getDocuments().forEach(documentSnapshot -> {
                            LikeDislikeModel model = documentSnapshot.toObject(LikeDislikeModel.class);
                            if (model.getUserID().equals(userID)) {
                                listener.Exists(true);
                                found.set(true);
                                return;
                            }

                        });
                        if (!found.get())
                            listener.Exists(false);
                    } else listener.Exists(false);
                });
    }

    private void RemoveLDislike(String recipeId, ICompleteListener listener) {
        String userID = mAuth.getCurrentUser().getUid();

        db.collection("Recipes").document(recipeId).collection("Dislikes").document(userID).delete()
                .addOnCompleteListener(task -> {
                    UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {

                            UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                                @Override
                                public void OnComplete(boolean isSuccessfulCompleted) {
                                    listener.OnComplete(isSuccessfulCompleted);
                                }
                            });
                        }
                    });
                });
    }

    private void AddDislikeToRecipe(String recipeId, ICompleteListener listener) {
        LikeDislikeModel likeDislikeModel = new LikeDislikeModel(userID);
        db.collection("Recipes").document(recipeId).collection("Dislikes")
                .document(userID).set(likeDislikeModel).addOnCompleteListener(task -> {
            UpdateRecipeLikesCount(recipeId, new ICompleteListener() {
                @Override
                public void OnComplete(boolean isSuccessfulCompleted) {

                    UpdateRecipeDislikesCount(recipeId, new ICompleteListener() {
                        @Override
                        public void OnComplete(boolean isSuccessfulCompleted) {
                            listener.OnComplete(isSuccessfulCompleted);
                        }
                    });
                }

            });
        });
    }

    public void UpdateRecipeLikesCount(String recipeId, ICompleteListener listener) {
        GetRecipeLikesCount(recipeId, new IGetNumberListener() {
            @Override
            public void getNumber(int numb) {
                db.collection("Recipes").document(recipeId).update("LikesCount", numb).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                listener.OnComplete(task.isSuccessful());
                            }
                        }
                );
            }
        });
    }

    public void UpdateRecipeDislikesCount(String recipeId, ICompleteListener listener) {
        GetRecipeDislikesCount(recipeId, new IGetNumberListener() {
            @Override
            public void getNumber(int numb) {
                db.collection("Recipes").document(recipeId).update("DislikesCount", numb).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                listener.OnComplete(task.isSuccessful());
                            }
                        }
                );
            }
        });
    }

    public void GetRecipeLikesCount(String recipeId, IGetNumberListener listener) {
        db.collection("Recipes").document(recipeId).collection("Likes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listener.getNumber(task.getResult().getDocuments().size());
                    }
                });
    }

    public void GetRecipeDislikesCount(String recipeId, IGetNumberListener listener) {
        db.collection("Recipes").document(recipeId).collection("Dislikes").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listener.getNumber(task.getResult().getDocuments().size());
                    }
                });

    }
}
