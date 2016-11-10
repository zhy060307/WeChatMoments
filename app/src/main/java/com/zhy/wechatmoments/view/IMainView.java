package com.zhy.wechatmoments.view;

import com.zhy.wechatmoments.domain.User;

import java.util.List;

/**
 * Created by lenovo on 2016/11/4.
 */

public interface IMainView {
    void showUserInfo(User user);

    void showTweets(List tweets);
}
