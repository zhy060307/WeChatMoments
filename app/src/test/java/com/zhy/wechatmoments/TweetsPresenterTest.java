package com.zhy.wechatmoments;

import com.zhy.wechatmoments.domain.Tweet;
import com.zhy.wechatmoments.domain.User;
import com.zhy.wechatmoments.model.TweetModel;
import com.zhy.wechatmoments.presenter.TweetPresenter;
import com.zhy.wechatmoments.utils.ListUtils;
import com.zhy.wechatmoments.utils.RetrofitUtils;
import com.zhy.wechatmoments.view.IMainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.mock;

/**
 * Created by lenovo on 2016/11/4.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class TweetsPresenterTest {

    private TweetModel tweetModel = RetrofitUtils.createService(TweetModel.class);


    private TweetModel mockModel;
    private OkHttpClient mMockClient;

    private TweetPresenter presenter;
    private IMainView mainView;

    @Before
    public void before() {

        OkHttpClient client = mock(OkHttpClient.class);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thoughtworks-ios.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        mockModel = retrofit.create(TweetModel.class);
        mainView = mock(IMainView.class);

        presenter = new TweetPresenter(mainView);
    }


    @Test
    public void testQueryUser() {
//        {
//            "profile-image": "http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png",
//                "avatar": "http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png",
//                "nick": "John Smith",
//                "username": "jsmith"
//        }
        Observable<User> userObservable = tweetModel.queryUserInfo();
        User user = userObservable.toBlocking().single();

        assertEquals("jsmith", user.getUsername());
        assertEquals("John Smith", user.getNick());
        assertEquals("http://info.thoughtworks.com/rs/thoughtworks2/images/glyph_badge.png", user.getAvatar());
        assertEquals("http://img2.findthebest.com/sites/default/files/688/media/images/Mingle_159902_i0.png", user.getProfileImage());

    }

    @Test
    public void testQueryTweets() {
        List<Tweet> tweetList = tweetModel.queryTweets().toBlocking().single();

        assertEquals(22, tweetList.size());
    }

    @Test
    public void test_filter_tweets() {
        List<Tweet> tweets = tweetModel.queryTweets().flatMap(new Func1<List<Tweet>, Observable<List<Tweet>>>() {
            @Override
            public Observable<List<Tweet>> call(List<Tweet> tweets) {
                return Observable.just(filter(tweets));
            }
        }).toBlocking().single();

        assertEquals(15, tweets.size());
    }

    @Test
    public void test_getTweets() {
        mainView.showTweets(anyList());
        presenter.getTweets();
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
}
