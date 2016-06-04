package com.cleartax.cleartaxtweets.data.api;


import android.util.Log;

import com.cleartax.cleartaxtweets.data.models.BestTweetRequest;
import com.cleartax.cleartaxtweets.util.Constants;

import java.util.HashMap;
import java.util.Iterator;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by mutha on 03/06/16.
 */
public class GetBestTweetServiceImpl implements GetBestTweetService {

    /**
     *
     * @param bestTweetRequest : Tweet Request containing the searchText and Count
     * @return : result (number of tweets defined in the bestTweetRequest
     *
     * This function make connection to the twitter using AccessToken and Secret
     * Then It fetches the result on the basis of the request we have passed to the function
     */

    @Override
    public Observable<QueryResult> getBestTweetService(BestTweetRequest bestTweetRequest) {
        return Observable.just(bestTweetRequest)
                .flatMap(new Func1<BestTweetRequest, Observable<? extends QueryResult>>() {
                    @Override
                    public Observable<? extends QueryResult> call(BestTweetRequest bestTweetRequest) {
                        HashMap<String, Integer> tweetsMap = new HashMap<>();
                        ConfigurationBuilder cb = new ConfigurationBuilder();
                        cb.setDebugEnabled(true)
                                .setOAuthConsumerKey(Constants.CONSUMER_KEY)
                                .setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
                                .setOAuthAccessToken(Constants.O_AUTH_ACCESS_TOKEN)
                                .setOAuthAccessTokenSecret(Constants.O_AUTH_ACCESS_TOKEN_SECRET);

                        TwitterFactory tf = new TwitterFactory(cb.build());
                        Twitter twitter = tf.getInstance();
                        RequestToken requestToken = null;
                        AccessToken accessToken = null;

                        Query query = new Query()
                                .query(bestTweetRequest.getSearchText())
                                .count(bestTweetRequest.getMsgCount());

                        QueryResult result = null;
                        try {
                            result = twitter.search(query);
                        } catch (TwitterException e) {
                            // log the exception in some framework like Crashlytics or something
                            e.printStackTrace();
                        }

                        return Observable.just(result);
                    }
                }).subscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
    }
}
