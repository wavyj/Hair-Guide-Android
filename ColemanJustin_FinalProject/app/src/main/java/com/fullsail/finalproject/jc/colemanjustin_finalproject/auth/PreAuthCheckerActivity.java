package com.fullsail.finalproject.jc.colemanjustin_finalproject.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.Navigation.NavigationActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.DatabaseUtil;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;

public class PreAuthCheckerActivity extends AppCompatActivity {

    private static final String TAG = "AuthenticationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preauth);

        checkLoggedIn();
    }

    //Methods
    private void checkLoggedIn(){
        if (PreferenceUtil.loadUserEmail(this).equals("")){
            // Not Logged In - To Login/Signup
            Intent authIntent = new Intent(this, AuthenticationActivity.class);
            startActivity(authIntent);
            this.finish();
        }else {
            // Logged In - To Home
            new DatabaseUtil(this).getUserData();
            Intent feedIntent = new Intent(this, NavigationActivity.class);
            startActivity(feedIntent);
            this.finish();
        }
    }
}
