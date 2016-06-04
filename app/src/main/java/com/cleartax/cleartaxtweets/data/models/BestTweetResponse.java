package com.cleartax.cleartaxtweets.data.models;

/**
 * Created by mutha on 03/06/16.
 */
public class BestTweetResponse {

    private String [] mostFrequentTweets;

    public String[] getMostFrequentTweets() {
        return this.mostFrequentTweets;
    }

    public void setMostFrequentTweets(String[] mostFrequentTweets) {
        this.mostFrequentTweets = mostFrequentTweets;
    }

    public BestTweetResponse(String[] mostFrequentTweets) {
        this.mostFrequentTweets = mostFrequentTweets;
    }
}
