package com.zhy.wechatmoments.model;

import com.zhy.wechatmoments.domain.Tweet;
import com.zhy.wechatmoments.domain.User;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by lenovo on 2016/11/3.
 */

public interface TweetModel {

    @GET("user/jsmith")
    Observable<User> queryUserInfo();


    @GET("user/jsmith/tweets")
    Observable<List<Tweet>> queryTweets();
}
