package com.cleartax.cleartaxtweets.ui.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cleartax.cleartaxtweets.R;
import com.cleartax.cleartaxtweets.data.api.GetBestTweetService;
import com.cleartax.cleartaxtweets.data.api.GetBestTweetServiceImpl;
import com.cleartax.cleartaxtweets.data.models.BestTweetRequest;
import com.cleartax.cleartaxtweets.data.models.Tweet;
import com.cleartax.cleartaxtweets.ui.activity.MainActivity;
import com.cleartax.cleartaxtweets.ui.adapter.TweetsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import rx.functions.Action1;
import rx.schedulers.Schedulers;
import twitter4j.QueryResult;
import twitter4j.Status;

/**
 * A Fragment containing the Recycler view of most Frequent Tweets
 */
public class MainActivityFragment extends Fragment {

    private TweetsAdapter tweetsAdapter;
    private ArrayList<Tweet> tweetList = new ArrayList<>();
    private RecyclerView tweetRecyclerView;
    private View mainLayout;
    private static SharedPreferences sharedPreferences;

    private GetBestTweetService getBestTweetService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mainLayout = (RelativeLayout)inflater.inflate(R.layout.fragment_main, container, false);
        return mainLayout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView tweetRecyclerView = (RecyclerView) mainLayout.findViewById(R.id.lvTweets);
        tweetsAdapter = new TweetsAdapter(this.tweetList);
        FloatingActionButton fab = (FloatingActionButton) mainLayout.findViewById(R.id.fab);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshTweets();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        tweetRecyclerView.setLayoutManager(mLayoutManager);
        tweetRecyclerView.setItemAnimator(new DefaultItemAnimator());
        tweetRecyclerView.setAdapter(tweetsAdapter);
        fetchAndDisplayTweets(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void fetchAndDisplayTweets(final boolean shouldshowCachedContent){
        if(getBestTweetService == null)
            getBestTweetService = new GetBestTweetServiceImpl();

        // show the previous tweets Untill we get the latest tweets and then refresh
        if(shouldshowCachedContent)
            showCachedContent();

        getBestTweetService.getBestTweetService(new BestTweetRequest("ClearTax", 100))
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<QueryResult>() {
                    @Override
                    public void call(QueryResult queryResult) {
                        Map<String, Integer> tweetsMap = new HashMap<>();
                        String mostFrequentTweet[] = new String [3];
                        int j = 0;
                        for (Status status : queryResult.getTweets()) {
                            String tweet = status.getText();
                            if(tweet != null && (tweet.toLowerCase().contains("cleartax")
                                             || tweet.toLowerCase().contains("clear tax"))){
                                if(tweetsMap.containsKey(tweet)) {
                                    tweetsMap.put(tweet, tweetsMap.get(tweet) + 1);
                                } else {
                                    tweetsMap.put(tweet, 1);
                                }
                            }
                        }
                        addMostFrequentTweetsToList(tweetsMap, 3);
                        updateView();
                    }
                });
    }

    /**
     *
     * @param tweetsMap : Map containing the last 100 tweets
     * @param n : no of Tweets to show (As of now its 3)
     */

    private void addMostFrequentTweetsToList(Map<String, Integer> tweetsMap, int n) {
        String [] mostFrequentTweet = new String[n];
        Set<String > mostFrequentStringSet = new HashSet<>();
        int j = 0;
        tweetList.clear();
        for(int i = 0; i < n; i++) {
            int max = -1;
            Iterator it = tweetsMap.entrySet().iterator();
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                if (max < ((Integer)pair.getValue())){
                    mostFrequentTweet[j] = (String)pair.getKey();
                    max = (Integer)pair.getValue();
                }
            }
            tweetsMap.remove(mostFrequentTweet[j]);
            tweetList.add( new Tweet(mostFrequentTweet[j]));
            mostFrequentStringSet.add(mostFrequentTweet[j]);
            j++;
        }
        sharedPreferences.edit().putStringSet("most_frequent_tweet", mostFrequentStringSet).commit();
        return;
    }

    /**
     *  on Clicking on FAB it refreshes the tweets with the real time values
     *  of frequencies and update the recycler view
     */
    public void refreshTweets(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "Fetching new Tweets!!", Toast.LENGTH_SHORT).show();
            }
        });
        tweetList.clear();
        fetchAndDisplayTweets(false);
    }

    /**
     *  It adds the most frequent tweets to list
     *  and update the view accordingly
     */
    private void updateView(){
        if(getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tweetsAdapter.setList(tweetList);
                    tweetsAdapter.notifyDataSetChanged();
                    if (getActivity() != null && getActivity() instanceof MainActivity) {
                        ((MainActivity) getActivity()).hideProgressBar();
                    }
                    Toast.makeText(getContext(), "Content is refreshed !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void showCachedContent(){
        Set<String > mostFrequentStringSet = new HashSet<>();
        mostFrequentStringSet = sharedPreferences.getStringSet("most_frequent_tweet", null);
        if(mostFrequentStringSet != null){
            for (Iterator<String> it = mostFrequentStringSet.iterator(); it.hasNext(); ) {
                String tweetMsg = it.next();
                tweetList.add(new Tweet(tweetMsg));
            }
            if(getActivity() != null && getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).hideProgressBar();
            }
            tweetsAdapter.notifyDataSetChanged();
        } else{
            if(getActivity() != null && getActivity() instanceof MainActivity) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((MainActivity) getActivity()).showProgressBar();
                    }
                });
            }
        }
    }
}
