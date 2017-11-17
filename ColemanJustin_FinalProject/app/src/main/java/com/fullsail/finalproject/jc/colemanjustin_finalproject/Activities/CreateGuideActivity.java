package com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Guide;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateGuideActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private static final String TAG = "CreateGuideActivity";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_guide);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.postsetup_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.doneBtn:
                createGuide();
                break;
        }
        return true;
    }

    private void createGuide(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Guide...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

        EditText titleInput = (EditText) findViewById(R.id.titleInput);
        EditText textInput = (EditText) findViewById(R.id.textInput);

        String title = titleInput.getText().toString();
        String text = textInput.getText().toString();

        if (!title.isEmpty() && !text.isEmpty()){
            Guide newGuide = new Guide(0, 0, "", text, title,
                    PreferenceUtil.loadUserReference(this), 0);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("guides").add(newGuide).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){
                        progressDialog.cancel();
                        finish();
                    }
                }
            });

        }else {
            if (title.isEmpty()){
                titleInput.setError("Must have a title");
            }

            if (text.isEmpty()){
                textInput.setError("Must have content");
            }
        }
    }
}
