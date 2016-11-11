package com.zhy.wechatmoments.view.impl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhy.wechatmoments.R;
import com.zhy.wechatmoments.domain.Tweet;
import com.zhy.wechatmoments.domain.User;
import com.zhy.wechatmoments.presenter.TweetPresenter;
import com.zhy.wechatmoments.utils.ListUtils;
import com.zhy.wechatmoments.utils.recyclerview.base.LoadMoreFooter;
import com.zhy.wechatmoments.utils.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.wechatmoments.utils.recyclerview.wrapper.LoadMoreWrapper;
import com.zhy.wechatmoments.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IMainView {


    @BindView(R.id.swipe_refresh_view)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private Unbinder unbinder;
    private RecyclerView.Adapter adapter;
    private HeaderAndFooterWrapper headerWrapper;
    private LoadMoreWrapper loadMoreWrapper;
    private UserInfoView userInfoView;

    private List<Tweet> tweets = new ArrayList<>();

    private TweetPresenter presenter;
    private boolean isLoadMore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        presenter = new TweetPresenter(this);
        initView();

        presenter.getTweets();
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = getRecyclerAdapter();

        headerWrapper = getHeader();
        loadMoreWrapper = new LoadMoreWrapper(headerWrapper);

        loadMoreWrapper.setLoadMoreView(new LoadMoreFooter(this));
        loadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        recyclerView.setAdapter(loadMoreWrapper);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    private void refresh() {
        isLoadMore = false;
        presenter.getRefreshTweets();
    }

    private void loadMore() {
        isLoadMore = true;
        presenter.getLoadMoreTweets();
    }

    private HeaderAndFooterWrapper getHeader() {
        headerWrapper = new HeaderAndFooterWrapper(adapter);
        userInfoView = new UserInfoView(this);
        headerWrapper.addHeaderView(userInfoView);

        return headerWrapper;
    }

    private RecyclerView.Adapter getRecyclerAdapter() {
        return new TweetAdapter(this, tweets);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != unbinder) {
            unbinder.unbind();
        }

        if (null != presenter) {
            presenter.detachView();
        }
    }

    @Override
    public void showUserInfo(User user) {

        if (null != userInfoView) {
            userInfoView.loadUserInfo(user);
        }
    }

    @Override
    public void showTweets(final List tweets) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshTweets(tweets);
            }
        }, 1000);

    }

    private void refreshTweets(List tweets) {
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }

        if (!isLoadMore) {
            this.tweets.clear();
        }

        if (ListUtils.isNotEmpty(tweets)) {

            if (tweets.size() < 5) {
                loadMoreWrapper.setPageState(LoadMoreWrapper.STATE_NO_MORE);
            } else {
                loadMoreWrapper.setPageState(LoadMoreWrapper.STATE_LOAD_MORE);
            }
            this.tweets.addAll(tweets);
            loadMoreWrapper.notifyDataSetChanged();
        } else {
            loadMoreWrapper.setPageState(LoadMoreWrapper.STATE_NO_MORE);
        }
    }

}
