package com.zhy.wechatmoments.view.impl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.wechatmoments.R;
import com.zhy.wechatmoments.domain.User;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/11/4.
 */

public class UserInfoView extends RelativeLayout {

    @BindView(R.id.iv_profile_image)
    ImageView profileImageView;
    @BindView(R.id.iv_avatar)
    ImageView avatarImageIiew;
    @BindView(R.id.tv_username)
    TextView userNameView;
    private View rootView;
    private Context context;


    public UserInfoView(Context context) {
        this(context, null);
    }

    public UserInfoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_view_user_info, this, true);

        ButterKnife.bind(rootView, this);
    }

    public void loadUserInfo(User user) {

        userNameView.setText(user.getUsername());
        Glide.with(context).load(user.getAvatar()).placeholder(R.drawable.default_image_show_square).into(avatarImageIiew);
        Glide.with(context).load(user.getProfileImage()).placeholder(R.drawable.default_image_show_square).into(profileImageView);
    }
}
