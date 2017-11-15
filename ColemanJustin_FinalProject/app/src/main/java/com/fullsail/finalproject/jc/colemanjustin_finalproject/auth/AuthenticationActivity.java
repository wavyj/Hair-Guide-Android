package com.fullsail.finalproject.jc.colemanjustin_finalproject.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.LoginFragment;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments.SignupFragment;
import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationActivity extends AppCompatActivity implements SignupFragment.LoginListener, LoginFragment.SignupListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        getFragmentManager().beginTransaction().replace(R.id.container, SignupFragment.newInstance(this), SignupFragment.TAG).commit();
    }


    @Override
    public void toLogin() {
        LoginFragment loginFragment = LoginFragment.newInstance(this);
        Slide slide = new Slide(Gravity.RIGHT);
        slide.setDuration(200);
        loginFragment.setEnterTransition(slide);
        loginFragment.setSharedElementEnterTransition(slide);
        loginFragment.setAllowEnterTransitionOverlap(true);
        getFragmentManager().beginTransaction().replace(R.id.container, loginFragment, LoginFragment.TAG).commit();
    }

    @Override
    public void toSignup() {
        SignupFragment signupFragment = SignupFragment.newInstance(this);
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(200);
        signupFragment.setEnterTransition(slide);
        signupFragment.setSharedElementEnterTransition(slide);
        signupFragment.setAllowEnterTransitionOverlap(true);
        getFragmentManager().beginTransaction().replace(R.id.container, signupFragment, LoginFragment.TAG).commit();
    }
}
