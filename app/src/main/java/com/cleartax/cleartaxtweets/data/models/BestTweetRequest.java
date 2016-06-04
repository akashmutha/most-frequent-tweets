package com.cleartax.cleartaxtweets.data.models;

/**
 * Created by mutha on 03/06/16.
 */
public class BestTweetRequest {

    private String searchText;
    private int msgCount;

    public BestTweetRequest(String searchText, int msgCount) {
        this.searchText = searchText;
        this.msgCount = msgCount;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public int getMsgCount() {
        return this.msgCount;
    }

    public void setMsgCount(int msgCount) {
        this.msgCount = msgCount;
    }
}
