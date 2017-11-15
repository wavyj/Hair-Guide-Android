package com.fullsail.finalproject.jc.colemanjustin_finalproject.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Justin on 11/13/17.
 */

public class User {
    private String mUsername = "";
    private String mProfilePic = "";
    private int mFollowers = 0;
    private int mFollowing = 0;
    private ArrayList<String> mHairTypes = new ArrayList<String>();
    private String mBio = "";

    public User(String username, String profilePic, String bio, int followers, int
            following, ArrayList<String> hairTypes){
        mUsername = username;
        mProfilePic = profilePic;
        mBio = bio;
        mFollowers = followers;
        mFollowing = following;
        mHairTypes = hairTypes;
    }

    public String getUsername(){
        return mUsername.toLowerCase();
    }

    public String getProfilePic(){
        return mProfilePic;
    }

    public String getBio(){
        return mBio;
    }

    public int getFollowers(){
        return mFollowers;
    }

    public int getFollowing(){
        return mFollowing;
    }

    public ArrayList<String> getHairTypes(){
        return mHairTypes;
    }

    public void setHairTypes(ArrayList<String> types){
        mHairTypes = types;
    }
}
