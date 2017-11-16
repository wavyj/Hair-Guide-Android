package com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;

/**
 * Created by Justin on 11/15/17.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    private static User mCurrentUser;

    public static ProfileFragment newInstance(User u) {
        mCurrentUser = u;

        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
