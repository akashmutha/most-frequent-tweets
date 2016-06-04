package com.cleartax.cleartaxtweets.data.api;

import com.cleartax.cleartaxtweets.data.models.BestTweetRequest;

import rx.Observable;
import twitter4j.QueryResult;

/**
 * Created by mutha on 03/06/16.
 */
public interface GetBestTweetService {
    Observable<QueryResult> getBestTweetService(BestTweetRequest bestTweetRequest);
}
