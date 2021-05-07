package com.example.food.Profile;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.food.Interfaces.IGetStringListener;
import com.example.food.Interfaces.IGetUserSettings;
import com.example.food.Recipe.AddRecipeActivity;
import com.example.food.Utils.ConfirmPasswordDialog;
import com.example.food.R;
import com.example.food.Utils.FirebaseMethods;
import com.example.food.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.food.Utils.UniversalImageLoader;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditProfileFragment extends Fragment implements
        ConfirmPasswordDialog.OnConfirmPasswordListener{

    @Override
    public void onConfirmPassword(String password) {
        Log.d(TAG, "onConfirmPassword: got the password: " + password);
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(mAuth.getCurrentUser().getEmail(), password);

        ///////////////////// Prompt the user to re-provide their sign-in credentials
        mAuth.getCurrentUser().reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "User re-authenticated.");

                            ///////////////////////check to see if the email is not already present in the database
                            mAuth.fetchSignInMethodsForEmail (mEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    if(task.isSuccessful()){
                                        try{
                                            if(task.getResult().getSignInMethods().size() == 1){
                                                Log.d(TAG, "onComplete: that email is already in use.");
                                                Toast.makeText(getActivity(), "That email is already in use", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                Log.d(TAG, "onComplete: That email is available.");

                                                //////////////////////the email is available so update it
                                                mAuth.getCurrentUser().updateEmail(mEmail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    //mAuth.getCurrentUser().sendVerificationEmail();
                                                                    Log.d(TAG, "User email address updated.");
                                                                    Toast.makeText(getActivity(), "email updated", Toast.LENGTH_SHORT).show();
                                                                    mFirebaseMethods.updateEmail(mEmail.getText().toString());
                                                                }
                                                            }
                                                        });

                                            }
                                        }catch (NullPointerException e){
                                            Log.e(TAG, "onComplete: NullPointerException: "  +e.getMessage() );
                                        }
                                    }
                                }
                            });
                        }else{
                            Log.d(TAG, "onComplete: re-authentication failed.");
                        }

                    }
                });
    }

    private static final String TAG = "EditProfileFragment";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    //EditProfile Fragment widgets
    private EditText mDisplayName, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;

    private Bitmap ReceipeBitmap;

    //var
    private User mUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName = (EditText) view.findViewById(R.id.display_name);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPhoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(getActivity());


        //setProfileImage();
        SetupChoosePhoto();
        setupFirebaseAuth();

        //back arrow for navigating back to ProfileActivity
        ImageView backArrow = (ImageView) view.findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();
            }
        });

        ImageView checkmark = (ImageView) view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to save changes.");
                saveProfileSettings();
                Toast.makeText(getActivity(), "account setting saved", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /**
     * Retrieves the data contained in the widgets and submits it to the database
     * Before donig so it chekcs to make sure the username chosen is unqiue
     */
    private void saveProfileSettings(){
        final String displayName = mDisplayName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phoneNumber = mPhoneNumber.getText().toString();
        if(email == null || email.isEmpty()){
            Toast.makeText(getContext(), "add an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(displayName == null || displayName.isEmpty()){
            Toast.makeText(getContext(), "displayName is empty or null", Toast.LENGTH_SHORT).show();
            return;
        }

        /**
         * change the rest of the settings that do not require uniqueness
         */
        if(!mUser.getDisplay_name().equals(displayName)){
            //update displayname
            mFirebaseMethods.updateDisplayName(displayName);
        }
        if(!mUser.getPhone_number().equals(phoneNumber)){
            //update phoneNumber
            mFirebaseMethods.updatePhoneNumber(phoneNumber);
        }

        if(ReceipeBitmap != null)
        {
            mFirebaseMethods.UploadProfileImage(mAuth.getCurrentUser().getUid(), ReceipeBitmap, new IGetStringListener() {
                @Override
                public void GetString(String photoUrl) {
                    if(!photoUrl.isEmpty())
                    {
                        if(!mUser.getProfile_photo().equals(photoUrl)){
                            mFirebaseMethods.UpdateProfilePhoto(photoUrl);
                        }

                    }
                    else Log.d(TAG, "onComplete: upload profile failed ");
                }
            });
        }
        else
        {
            mFirebaseMethods.UpdateProfilePhoto("");
        }
    }

    private void setProfileWidgets(User user){

        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + user.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + user.getEmail());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + user.getPhone_number());

        UniversalImageLoader.setImage(user.getProfile_photo(), mProfilePhoto, null, "");
        mDisplayName.setText(user.getDisplay_name());
        mEmail.setText(user.getEmail());
        mPhoneNumber.setText(user.getPhone_number());
    }

    private void SetupChoosePhoto()
    {
        mProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage(getContext());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        //camera
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ReceipeBitmap = selectedImage;
                        mProfilePhoto.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        //galerie
                        Uri selectedimageUri = data.getData();
                        mProfilePhoto.setImageURI(selectedimageUri);

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
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
        }
        else
        {
            ImageDecoder.Source source = ImageDecoder.createSource(
                    getActivity().getContentResolver(),imageUri);
            bitmap = ImageDecoder.decodeBitmap(source);
        }

        return bitmap;
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
        userID = mAuth.getCurrentUser().getUid();

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
                mUser = userSettings;
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
