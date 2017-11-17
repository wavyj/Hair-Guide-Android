package com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.util.List;

public class CreatePostActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    private static final String TAG = "CreatePostActivity";
    private static final String IMAGEURI = "selectedImage";

    private ImagePicker imagePicker;
    private CameraImagePicker cameraImagePicker;
    private ImagePickerCallback imagePickerCallback;
    private ChosenImage selectedImage;
    private ImageView imageView;
    private String outputPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button cameraBtn = (Button) findViewById(R.id.cameraBtn);
        Button libraryBtn =  (Button) findViewById(R.id.libraryBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
        cameraBtn.setOnClickListener(this);
        libraryBtn.setOnClickListener(this);

        toolbar.inflateMenu(R.menu.camera_menu);
        toolbar.setOnMenuItemClickListener(this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0112);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cameraBtn:
                openCamera();
                break;
            case R.id.libraryBtn:
                openLibrary();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putString("picker_path", outputPath);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null){
            outputPath = savedInstanceState.getString("picker_path");
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && requestCode == 0x0112 && grantResults[0] == PackageManager.
                PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Permission Required");
            builder.setMessage("Access to your external storage is required to save and load images.");
            builder.setNeutralButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(CreatePostActivity.this, new String[]{Manifest
                            .permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0x0112);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == Picker.PICK_IMAGE_DEVICE){
                if (imagePicker == null){
                    imagePicker = new ImagePicker(CreatePostActivity.this);
                    imagePicker.setImagePickerCallback(imagePickerCallback);
                }
                imagePicker.submit(data);
            }

            if (requestCode == Picker.PICK_IMAGE_CAMERA){
                if (cameraImagePicker == null){
                    cameraImagePicker = new CameraImagePicker(CreatePostActivity.this);
                    cameraImagePicker.reinitialize(outputPath);
                    cameraImagePicker.setImagePickerCallback(imagePickerCallback);
                }
                cameraImagePicker.submit(data);
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

    private void openCamera(){
        cameraImagePicker = new CameraImagePicker(this);
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
        cameraImagePicker.setImagePickerCallback(imagePickerCallback);
        outputPath = cameraImagePicker.pickImage();
    }

    private void imageSelected(){
        imageView.setImageURI(Uri.parse(selectedImage.getQueryUri()));
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.postContinue:
                if (selectedImage != null) {
                    Intent postSetupIntent = new Intent(CreatePostActivity.this, PostSetupActivity.class);
                    postSetupIntent.putExtra(IMAGEURI, selectedImage.getQueryUri());
                    startActivity(postSetupIntent);
                    finish();
                }else{
                    Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }
}
