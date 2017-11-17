package com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.Activities.NavigationActivity;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.DatabaseUtil;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.util.PreferenceUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "LoginFragment";
    private FirebaseAuth mAuth;
    private static SignupListener mSignupListener;

    public interface SignupListener{
        void toSignup();
    }

    public static LoginFragment newInstance(SignupListener signupListener) {
        mSignupListener = signupListener;
        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        TextView signupLink = (TextView) getView().findViewById(R.id.toSignupLink);
        signupLink.setOnClickListener(this);
        Button loginBtn = (Button) getView().findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.loginBtn:
                EditText emailInput = (EditText) getView().findViewById(R.id.emailInput);
                EditText passwordInput = (EditText) getView().findViewById(R.id.passwordInput);
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (email.isEmpty()){
                    emailInput.setError("Can not be empty");
                    return;
                }

                if (password.isEmpty()){
                    passwordInput.setError("Can not be empty");
                    return;
                }

                loginUser(email, password);
                break;
            case R.id.toSignupLink:
                mSignupListener.toSignup();
                break;
        }
    }

    private void loginUser(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    PreferenceUtil.saveUserAccount(getActivity(), email, password);
                    new DatabaseUtil(getActivity()).getUserData();

                        // To Home
                        Intent feedIntent = new Intent(getActivity(), NavigationActivity.class);
                        startActivity(feedIntent);
                        getActivity().finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Whoops");
                if (e.getLocalizedMessage().equals("There is no user record corresponding to this " +
                        "identifier. The user may have been deleted.")){
                    builder.setMessage("Account not found");
                }else if (e.getLocalizedMessage().equals("The password is invalid or the user does " +
                        "not have a password.")){
                    builder.setMessage("Incorrect Password");
                }else if (e.getLocalizedMessage().equals("The email address is badly formatted.")){
                    builder.setMessage("Must have a valid email address");
                }

                builder.show();
            }
        });
    }
}
