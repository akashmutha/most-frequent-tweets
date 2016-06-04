package com.cleartax.cleartaxtweets.data.models;

/**
 * Created by mutha on 3/06/16.
 */
public class Tweet {

    private String userName;
    private String message;
    private String imageUrl;

    public Tweet(final String message) {
        this.message = message;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
