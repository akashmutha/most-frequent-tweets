package com.cleartax.cleartaxtweets.ui.adapter;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleartax.cleartaxtweets.R;
import com.cleartax.cleartaxtweets.data.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mutha on 03/06/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetViewHolder> {

    private ArrayList<Tweet> tweetsList;

    public TweetsAdapter(ArrayList<Tweet> tweetsList) {
        this.tweetsList = tweetsList;
    }

    @Override
    public TweetsAdapter.TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.tweet, parent, false);
        return new TweetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.TweetViewHolder holder, int position) {
        Tweet tweet = tweetsList.get(position);
        holder.tweetMesssage.setText(tweet.getMessage());
        holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public int getItemCount() {
        return tweetsList != null ? tweetsList.size() : 0;
    }

    // if viewholder has two many view We can also put this class
    // in another file

    public class TweetViewHolder extends RecyclerView.ViewHolder{
        public TextView tweetMesssage;

        public TweetViewHolder(View itemView) {
            super(itemView);
            tweetMesssage = (TextView) itemView.findViewById(R.id.tvTweet);
        }
    }

    public void setList(ArrayList<Tweet> tweetsList){
        this.tweetsList = tweetsList;
    }
}
