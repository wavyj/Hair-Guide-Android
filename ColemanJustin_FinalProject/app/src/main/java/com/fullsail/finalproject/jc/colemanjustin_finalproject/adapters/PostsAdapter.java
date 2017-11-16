package com.fullsail.finalproject.jc.colemanjustin_finalproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostsAdapter extends RecyclerView.Adapter {

    private static final String TAG = "PostsAdapter";
    private static final int ID = 0x11001;
    private ArrayList<Post> posts;

    public PostsAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView usernameLabel;
        private ImageView postImage;
        private TextView captionLabel;

        ViewHolder(View v){
            super(v);
            profileImage = (CircleImageView) v.findViewById(R.id.profileImage);
            usernameLabel = (TextView) v.findViewById(R.id.usernameLabel);
            postImage = (ImageView) v.findViewById(R.id.postImage);
            captionLabel = (TextView) v.findViewById(R.id.captionLabel);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = ((ViewHolder) holder);
        Post current = posts.get(position);
        viewHolder.captionLabel.setText(current.getCaption());
        viewHolder.usernameLabel.setText(current.postUser.getUsername());
        Picasso.with(holder.itemView.getContext()).load(current.getImageUrl()).into(((ViewHolder) holder).postImage);
        Picasso.with(holder.itemView.getContext()).load(current.postUser.getProfilePicUrl()).into(((ViewHolder) holder).profileImage);
    }

    @Override
    public long getItemId(int position) {
        return ID + position;
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

}
