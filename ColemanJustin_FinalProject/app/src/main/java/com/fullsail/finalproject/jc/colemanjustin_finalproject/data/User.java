package com.fullsail.finalproject.jc.colemanjustin_finalproject.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Justin on 11/13/17.
 */

public class User {
    private String email = "";
    private String username = "";
    private String profilePicUrl = "";
    private int followers = 0;
    private int following = 0;
    private ArrayList<String> hairTypes = new ArrayList<String>();
    private String bio = "";
    private String gender = "";
    private String fbuser = "";

    public User(){

    }
    public User(String username, String profilePicUrl, String bio, int followers, int
            following, ArrayList<String> hairTypes, String email, String gender, String fbuser){
        this.username = username;
        this.profilePicUrl = profilePicUrl;
        this.bio = bio;
        this.followers = followers;
        this.following = following;
        this.hairTypes = hairTypes;
        this.email = email;
        this.fbuser = fbuser;
    }

    public String getEmail(){
        return email;
    }

    public String getUsername(){
        return username.toLowerCase();
    }

    public String getProfilePicUrl(){
        return profilePicUrl;
    }

    public String getBio(){
        return bio;
    }

    public int getFollowers(){
        return followers;
    }

    public int getFollowing(){
        return following;
    }

    public String getGender(){
        return gender;
    }

    public ArrayList<String> getHairTypes(){
        return hairTypes;
    }

    public void setHairTypes(ArrayList<String> types){
        hairTypes = types;
    }

    public String getFbUser(){
      return fbuser;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> data = new HashMap<>();
        data.put("username", username);
        data.put("bio", bio);
        data.put("gender", gender);
        data.put("email", email);
        data.put("followers", followers);
        data.put("following", following);
        data.put("hairTypes", hairTypes);
        data.put("profilePicUrl", profilePicUrl);
        return data;
    }

    @Override
    public String toString() {
        return email + username + followers + following + profilePicUrl + bio;
    }
}
