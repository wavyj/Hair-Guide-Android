package com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.auth.ProfileSetupActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class SignupFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "SignupFragment";
    private FirebaseAuth mAuth;
    private static LoginListener mLoginListener;

    public interface LoginListener{
        void toLogin();
    }

    public static SignupFragment newInstance(LoginListener loginListener) {
        mLoginListener = loginListener;
        Bundle args = new Bundle();

        SignupFragment fragment = new SignupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        Button signupBtn = (Button) getView().findViewById(R.id.signupBtn);
        TextView loginLink = (TextView) getView().findViewById(R.id.toLoginLink);
        signupBtn.setOnClickListener(this);
        loginLink.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupBtn:
                EditText emailInput = (EditText) getView().findViewById(R.id.emailInput);
                EditText passwordInput = (EditText) getView().findViewById(R.id.passwordInput);
                EditText confirmPasswordInput = (EditText) getView().findViewById(R.id.confirmPasswordInput);

                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                //Check Email
                if (email.equals("")){
                    emailInput.setError("Must have an email e.g. hello@email.com");
                    return;
                }

                //Check password
                if (password.equals("")){
                    passwordInput.setError("Must have a password. Anything 6-14 letters");
                    return;
                }else if (password.toCharArray().length < 6){
                    passwordInput.setError("Too Short");
                    return;
                }else if (password.toCharArray().length > 14){
                    passwordInput.setError("Too Long");
                    return;
                }

                //Check passwords match
                if (!confirmPassword.equals(password)){
                    confirmPasswordInput.setError("Passwords must match");
                    return;
                }

                signupUser(email, password);
                break;
            case R.id.toLoginLink:
                mLoginListener.toLogin();
                break;
        }
    }

    private void signupUser(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    PreferenceUtil.saveUserAccount(getActivity(),email,password);

                    //Create User Data
                    Intent profileSetupIntent = new Intent(getActivity(), ProfileSetupActivity.class);
                    startActivity(profileSetupIntent);
                    getActivity().finish();

                }
            }
        });
    }
}
