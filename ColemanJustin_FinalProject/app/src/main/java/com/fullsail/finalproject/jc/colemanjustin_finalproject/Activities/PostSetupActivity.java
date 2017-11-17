package com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Post;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostSetupActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{

    private static final String TAG = "PostSetupActivity";
    private static final String IMAGEURI = "selectedImage";

    private Uri selectedImage;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_setup);

        Intent receivedIntent = getIntent();
        if (receivedIntent.getExtras() != null){
            selectedImage = Uri.parse(receivedIntent.getExtras().getString(IMAGEURI));
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageURI(selectedImage);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.postsetup_menu);
        toolbar.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.doneBtn:
                uploadImage();
                break;
        }
        return true;
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

        UploadTask uploadTask = imageRef.putFile(selectedImage);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        createPost(uri.toString());
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

    private void createPost(String url){
        progressDialog.setMessage("Creating Post");
        EditText caption = (EditText) findViewById(R.id.captionInput);

        Post newPost = new Post(caption.getText().toString(), 0, new Date(), url, 0, new ArrayList<String>(),
                PreferenceUtil.loadUserReference(this));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(newPost).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    progressDialog.cancel();
                    finish();
                }
            }
        });
    }
}
