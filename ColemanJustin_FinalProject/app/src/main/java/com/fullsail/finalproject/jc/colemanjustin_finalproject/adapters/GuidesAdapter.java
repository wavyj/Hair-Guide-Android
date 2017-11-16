package com.fullsail.finalproject.jc.colemanjustin_finalproject.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.R;
import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.Guide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Justin on 11/16/17.
 */

public class GuidesAdapter extends RecyclerView.Adapter {

    private ArrayList<Guide> guides;

    public GuidesAdapter(ArrayList<Guide> guides){
        this.guides = guides;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView usernameLabel;
        private ImageView guideImage;
        private TextView guideTitle;
        private TextView viewsLabel;

        ViewHolder(View v){
            super(v);
            profileImage = (CircleImageView) v.findViewById(R.id.profilePic);
            usernameLabel = (TextView) v.findViewById(R.id.usernameLabel);
            guideImage = (ImageView) v.findViewById(R.id.guideImage);
            guideTitle = (TextView) v.findViewById(R.id.guideTitle);
            viewsLabel = (TextView) v.findViewById(R.id.viewsLabel);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = ((ViewHolder) holder);
        Guide current = guides.get(position);

        viewHolder.guideTitle.setText(current.getTitle());
        viewHolder.usernameLabel.setText(current.getGuideUser().getUsername().toLowerCase());
        Picasso.with(holder.itemView.getContext()).load(current.getGuideUser().getProfilePicUrl())
                .into(viewHolder.profileImage);
        viewHolder.viewsLabel.setText(String.valueOf(current.getViews()));
    }

    @Override
    public int getItemCount() {
        return guides.size();
    }
}
