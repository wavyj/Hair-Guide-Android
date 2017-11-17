package com.fullsail.finalproject.jc.colemanjustin_finalproject.auth;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities.CreatePostActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities.NavigationActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProfileSetupActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener
        , View.OnClickListener{

    private static final String TAG = "ProfileSetupActivity";

    private ChosenImage selectedImage;
    private ImagePicker imagePicker;
    private ImagePickerCallback imagePickerCallback;
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private boolean isUsernameValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.camera_menu);
        toolbar.setOnMenuItemClickListener(this);

        imageView = (ImageView) findViewById(R.id.profileImage);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && requestCode == 0x0112 && grantResults[0] == PackageManager.
                PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                openLibrary();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Required");
            builder.setMessage("Access to your external storage is required to save and load images.");
            builder.setNeutralButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(ProfileSetupActivity.this, new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0112);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.postContinue:
                checkUsername();
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profileImage){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0112);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == Picker.PICK_IMAGE_DEVICE){
                if (imagePicker == null){
                    imagePicker = new ImagePicker(ProfileSetupActivity.this);
                    imagePicker.setImagePickerCallback(imagePickerCallback);
                }
                imagePicker.submit(data);
            }
        }
    }

    private void openLibrary(){
        imagePicker = new ImagePicker(this);
        imagePickerCallback = new ImagePickerCallback() {
            @Override
            public void onImagesChosen(List<ChosenImage> list) {
                selectedImage = list.get(0);
                imageSelected();
            }

            @Override
            public void onError(String s) {
                Log.d(TAG, s);
            }
        };
        imagePicker.setImagePickerCallback(imagePickerCallback);
        imagePicker.pickImage();
    }

    private void imageSelected(){
        imageView.setImageURI(Uri.parse(selectedImage.getQueryUri()));
    }

    private void uploadImage(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String formatted = dateFormat.format(new Date());

        String imageName = PreferenceUtil.loadUserReference(this) + formatted;
        final StorageReference imageRef = storageReference.child("images/" + imageName);

        UploadTask uploadTask = imageRef.putFile(Uri.parse(selectedImage.getQueryUri()));
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        createUserData(uri.toString());
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
        });
    }

    private void createUserData(String uri){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating User...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
        }else{
            progressDialog.setMessage("Creating User...");
        }
        EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        EditText bioInput = (EditText) findViewById(R.id.bioInput);

        final User u = new User(usernameInput.getText().toString(), uri,bioInput.getText().toString(), 0, 0
        , new ArrayList<String>(), PreferenceUtil.loadUserEmail(this), "", "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").add(u).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    PreferenceUtil.saveUserData(ProfileSetupActivity.this, u);
                    Intent feedIntent = new Intent(ProfileSetupActivity.this, NavigationActivity.class);
                    startActivity(feedIntent);
                    finish();
                }
            }
        });
    }

    private void checkUsername(){
        final EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        String username = usernameInput.getText().toString();

        if (username.isEmpty()){
            usernameInput.setError("Must have a username");
        }

        //Check Username
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").whereEqualTo("username", username).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null){
                    Log.d(TAG, e.getLocalizedMessage());
                }else {
                    if (documentSnapshots.size() > 0){
                        usernameInput.setError("Username is taken");
                    }else{
                        isUsernameValid = true;
                        if (selectedImage != null){
                            uploadImage();
                        }else {
                            createUser();
                        }
                    }
                }
            }
        });


    }

    private void createUser(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating User...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        EditText usernameInput = (EditText) findViewById(R.id.usernameInput);
        EditText bioInput = (EditText) findViewById(R.id.bioInput);

        final User u = new User(usernameInput.getText().toString(), "",bioInput.getText()
                .toString(), 0, 0
                , new ArrayList<String>(), PreferenceUtil.loadUserEmail(this), "",
                "");
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").add(u).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    PreferenceUtil.saveUserData(ProfileSetupActivity.this, u);
                    Intent feedIntent = new Intent(ProfileSetupActivity.this, NavigationActivity.class);
                    startActivity(feedIntent);
                    finish();
                }
            }
        });
    }
}
