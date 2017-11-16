package com.fullsail.finalproject.jc.colemanjustin_finalproject.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.adapters.GuidesAdapter;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Guide;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * Created by Justin on 11/15/17.
 */

public class GuidesFragment extends Fragment {
    public static final String TAG = "GuidesFragment";

    private RecyclerView recyclerView;
    private FirebaseFirestore db;
    private ArrayList<Guide> guides = new ArrayList<>();

    public static GuidesFragment newInstance() {

        Bundle args = new Bundle();

        GuidesFragment fragment = new GuidesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.guides_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        getGuides();
    }

    private void getGuides(){
        guides.clear();
        db = FirebaseFirestore.getInstance();
        db.collection("guides").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                for (DocumentSnapshot i: documentSnapshots){
                    if (i.exists()){
                        final Guide g = i.toObject(Guide.class);
                        db.collection("users").document(g.getUser()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                if (e != null){
                                    Log.d(TAG, e.getLocalizedMessage());
                                }else {
                                    if (documentSnapshot.exists()){
                                        User u = documentSnapshot.toObject(User.class);
                                        g.guideUser = u;
                                        guides.add(g);
                                        updateAdapter();
                                    }
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    private void updateAdapter(){
        recyclerView.setAdapter(new GuidesAdapter(guides));
    }
}
