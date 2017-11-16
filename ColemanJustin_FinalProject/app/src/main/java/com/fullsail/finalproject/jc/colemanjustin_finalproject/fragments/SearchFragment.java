package com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
