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
import com.fullsail.finalproject.jc.colemanjustin_finalproject.adapters.PostsAdapter;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Post;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FeedFragment extends Fragment {
    public static final String TAG = "FeedFragment";

    private FirebaseFirestore db;
    private ArrayList<Post> posts = new ArrayList<>();
    private RecyclerView recyclerView;

    public static FeedFragment newInstance() {

        Bundle args = new Bundle();

        FeedFragment fragment = new FeedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.feed_fragment_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        getPosts();
    }

    private void getPosts(){
        posts.clear();

        db = FirebaseFirestore.getInstance();
        db.collection("posts").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener
                (new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (e != null){
                    Log.d(TAG, e.getLocalizedMessage());
                }else{
                    for (DocumentSnapshot i: documentSnapshots.getDocuments()){
                        if (i.exists()){

                            final Post p = i.toObject(Post.class);

                            // Get User
                            db.collection("users").document(p.getUser()).addSnapshotListener
                                    (new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                    if (e != null){
                                        Log.d(TAG, e.getLocalizedMessage());
                                    }else {
                                        if (documentSnapshot.exists()){
                                            User u = documentSnapshot.toObject(User.class);
                                            p.postUser = u;
                                            posts.add(p);
                                            updateGrid();
                                        }
                                    }

                                }
                            });
                        }
                    }
                }

            }
        });
    }

    private void updateGrid(){
        recyclerView.setAdapter(new PostsAdapter(posts));
    }
}
