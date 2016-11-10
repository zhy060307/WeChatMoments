package com.zhy.wechatmoments.presenter;

import com.zhy.wechatmoments.domain.Tweet;
import com.zhy.wechatmoments.domain.User;
import com.zhy.wechatmoments.model.TweetModel;
import com.zhy.wechatmoments.utils.ListUtils;
import com.zhy.wechatmoments.utils.RetrofitUtils;
import com.zhy.wechatmoments.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by lenovo on 2016/11/4.
 */

public class TweetPresenter {

    public static final int PAGE_SIZE = 5;
    private IMainView mainView;
    private TweetModel tweetModel;
    private CompositeSubscription compositeSubscription;
    private List<Tweet> allTweets = new ArrayList<>();

    public TweetPresenter(IMainView mainView) {
        this.mainView = mainView;
        this.tweetModel = RetrofitUtils.createService(TweetModel.class);
    }


    protected void unSubscribe() {
        if (compositeSubscription != null) {
            compositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void detachView() {

        if (null != mainView) {
            mainView = null;
        }
        unSubscribe();
    }

    public void getTweets() {
        Observable<User> userObservable = tweetModel.queryUserInfo();
        Observable<List<Tweet>> tweetsObservable =
                tweetModel.queryTweets().flatMap(new Func1<List<Tweet>, Observable<List<Tweet>>>() {
                    @Override
                    public Observable<List<Tweet>> call(List<Tweet> tweets) {
                        return Observable.just(filter(tweets));
                    }
                });

        Subscription subscription = Observable.merge(userObservable, tweetsObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                        if (o instanceof User) {
                            mainView.showUserInfo((User) o);
                        } else if (o instanceof ArrayList) {
                            allTweets = (ArrayList) o;
                            getTweetsByPage(0);
                        }
                    }
                });

        addSubscribe(subscription);
    }

    private List<Tweet> filter(List<Tweet> tweets) {
        List<Tweet> tweetList = new ArrayList<>();
        if (ListUtils.isNotEmpty(tweets)) {
            for (Tweet tweet : tweets) {
                if (tweet.getSender() != null &&
                        (tweet.getContent() != null || tweet.getImages() != null)) {
                    tweetList.add(tweet);
                }
            }

        }
        return tweetList;
    }

    public void getTweetsByPage(int pageIndex) {

        List<Tweet> tweetList;
        if (ListUtils.isNotEmpty(allTweets)) {
            int size = allTweets.size();
            int nextPageIndex = (pageIndex + 1) * PAGE_SIZE;
            int endIndex = nextPageIndex <= size ? nextPageIndex : size;
            tweetList = allTweets.subList(pageIndex * PAGE_SIZE, endIndex);
            mainView.showTweets(tweetList);
        }
    }
}