package com.fullsail.finalproject.jc.colemanjustin_finalproject.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.fullsail.finalproject.jc.colemanjustin_finalproject.data.User;

import java.util.ArrayList;

public class PreferenceUtil {

    private static String EMAIL = "email";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";
    private static String BIO = "bio";
    private static String FOLLOWERS = "followers";
    private static String FOLLOWING = "following";
    private static String PROFILEPIC = "profilePic";
    private static String GENDER = "gender";
    private static String FBUSER = "fbuser";


    private PreferenceUtil(){

    }

    public static SharedPreferences getSharedPreference(Context context){
        return context.getSharedPreferences("hairGuide", Context.MODE_PRIVATE);
    }

    public static void saveUserAccount(Context context, String email, String password){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(EMAIL, email);
        editor.putString(PASSWORD, password);
        editor.apply();
    }

    public static String loadUserEmail(Context context){
        return getSharedPreference(context).getString(EMAIL, "");
    }

    public static String loadUserPassword(Context context){
        return getSharedPreference(context).getString(PASSWORD, "");
    }

    public static void saveUserData(Context context, User user){
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USERNAME, user.getUsername());
        editor.putString(BIO, user.getBio());
        editor.putInt(FOLLOWERS, user.getFollowers());
        editor.putInt(FOLLOWING, user.getFollowing());
        editor.putString(PROFILEPIC, user.getProfilePicUrl());
        editor.putString(GENDER, user.getGender());
        editor.putString(FBUSER, user.getFbUser());
        editor.apply();
    }

    public static User loadUserData(Context context){
        SharedPreferences prefs = getSharedPreference(context);
        String username = prefs.getString(USERNAME, "");
        String bio = prefs.getString(BIO, "");
        String profilePic = prefs.getString(PROFILEPIC, "");
        int followers = prefs.getInt(FOLLOWERS, 0);
        int following = prefs.getInt(FOLLOWING, 0);
        String gender = prefs.getString(GENDER, "");
        String fbUser = prefs.getString(FBUSER, "");

        return new User(username, profilePic, bio, followers, following, new ArrayList<String>(),
                loadUserEmail(context), gender, fbUser);
    }

    public static void deleteUserData(Context context){
        SharedPreferences prefs = getSharedPreference(context);
        prefs.edit().clear().apply();
    }

}
