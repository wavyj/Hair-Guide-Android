package com.fullsail.finalproject.jc.colemanjustin_finalproject.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class DatabaseUtil {
    private static final String TAG = "DatabaseUtil";
    private FirebaseFirestore db;
    private Context mContext;

    public DatabaseUtil(Context context){
        mContext = context;
        db = FirebaseFirestore.getInstance();
    }

    public void getUserData(){
        db.collection("users").whereEqualTo("email", PreferenceUtil.loadUserEmail(mContext)).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null){
                    Log.d(TAG, e.getLocalizedMessage());
                }else {
                    if (documentSnapshots.getDocuments().size() > 0) {
                        User u = documentSnapshots.getDocuments().get(0).toObject(User.class);
                        PreferenceUtil.saveUserData(mContext, u);
                    }
                }
            }
        });
    }

    public void createNewUserData(User u){
        db.collection("users").add(u).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()){
                    Log.d(TAG, "User Created");
                }
            }
        });
    }


}
