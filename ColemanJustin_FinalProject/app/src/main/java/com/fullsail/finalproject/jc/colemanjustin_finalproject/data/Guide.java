package com.fullsail.finalproject.jc.colemanjustin_finalproject.data;


public class Guide {
    private int bookmarks;
    private int comments;
    private String image;
    private String text;
    private String title;
    private String user;
    private int views;
    public User guideUser;

    public Guide(){

    }

    public Guide(int bookmarks, int comments, String image, String text, String title, String user,
                 int views){
        this.bookmarks = bookmarks;
        this.comments = comments;
        this.image = image;
        this.text = text;
        this.title = title;
        this.user = user;
        this.views = views;
    }

    public int getBookmarks(){
        return bookmarks;
    }

    public int getComments(){
        return comments;
    }

    public String getImage(){
        return image;
    }

    public String getText(){
        return text;
    }

    public String getTitle(){
        return title;
    }

    public String getUser(){
        return user;
    }

    public int getViews(){
        return views;
    }

    public User getGuideUser(){
        return guideUser;
    }
}
