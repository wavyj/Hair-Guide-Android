package com.fullsail.finalproject.jc.colemanjustin_finalproject.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Justin on 11/16/17.
 */

public class Post {

    private String caption;
    private int comments;
    private Date date;
    private String imageUrl;
    private int likes;
    private ArrayList<String> tags;
    private String user;
    public User postUser;

    public Post(){

    }

    public Post(String caption, int comments, Date date, String imageUrl, int likes, ArrayList<String>
            tags, String user){
        this.caption = caption;
        this.comments = comments;
        this.date = date;
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.tags = tags;
        this.user = user;
    }

    public String getCaption(){
        return caption;
    }

    public int getComments(){
        return comments;
    }

    public Date getDate(){
        return date;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public int getLikes(){
        return likes;
    }

    public ArrayList<String> getTags(){
        return tags;
    }

    public String getUser(){
        return user;
    }
}
