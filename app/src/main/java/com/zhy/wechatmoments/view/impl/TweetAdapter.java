package com.zhy.wechatmoments.view.impl;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.zhy.wechatmoments.R;
import com.zhy.wechatmoments.domain.Tweet;
import com.zhy.wechatmoments.domain.User;
import com.zhy.wechatmoments.utils.ListUtils;
import com.zhy.wechatmoments.utils.recyclerview.CommonAdapter;
import com.zhy.wechatmoments.utils.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by lenovo on 2016/11/4.
 */

public class TweetAdapter extends CommonAdapter<Tweet> {


    /**
     * 存放图片样式的9个View的id
     */
    private Integer[] photoStyleIds = {
            R.layout.list_item_tweet_photo1,
            R.layout.list_item_tweet_photo2,
            R.layout.list_item_tweet_photo3,
            R.layout.list_item_tweet_photo4,
            R.layout.list_item_tweet_photo5,
            R.layout.list_item_tweet_photo6,
            R.layout.list_item_tweet_photo7,
            R.layout.list_item_tweet_photo8,
            R.layout.list_item_tweet_photo9};
    /**
     * 存放加载图片的9个ImageView的id
     */
    private Integer[] imageViewItemids =
            {R.id.imageViewItem1, R.id.imageViewItem2, R.id.imageViewItem3,
                    R.id.imageViewItem4, R.id.imageViewItem5, R.id.imageViewItem6,
                    R.id.imageViewItem7, R.id.imageViewItem8, R.id.imageViewItem9};

    public TweetAdapter(Context context, List<Tweet> tweets) {
        super(context, R.layout.list_item_tweet, tweets);
    }

    @Override
    protected void convert(ViewHolder holder, Tweet tweet, int position) {

        holder.setText(R.id.tv_content, tweet.getContent());
        showSenderInfo(tweet, holder);
        showPhotos(tweet, holder);
        showComments(tweet, holder);
    }

    private void showComments(Tweet tweet, ViewHolder holder) {

        CommentView commentView = holder.getView(R.id.comment_view);
        List<Tweet.Comment> comments = tweet.getComments();
        if (ListUtils.isNotEmpty(comments)) {
            commentView.setVisibility(View.VISIBLE);
            commentView.loadComments(comments);
        } else {
            commentView.setVisibility(View.GONE);
        }
    }

    private void showSenderInfo(Tweet tweet, ViewHolder holder) {
        User sender = tweet.getSender();

        if (null != sender) {
            holder.setImageUrl(R.id.iv_avatar, sender.getAvatar())
                    .setText(R.id.tv_nike, sender.getNick());
        }
    }

    private void showPhotos(Tweet tweet, ViewHolder holder) {
        View photoView = holder.getView(R.id.photo_display_view);

        List<Tweet.ImageInfo> images = tweet.getImages();
        photoView.setVisibility(ListUtils.isEmpty(images) ? View.GONE : View.VISIBLE);
        if (ListUtils.isNotEmpty(images)) {
            LinearLayout photoContainerView = holder.getView(R.id.ll_photo_container);
            showPhotoStyleView(photoContainerView, images);
        }
    }


    private void showPhotoStyleView(LinearLayout viewPhotoContainer, List<Tweet.ImageInfo> images) {
        viewPhotoContainer.removeAllViews();


        int intPhotoSize = images.size();
        if (intPhotoSize > 9) {
            intPhotoSize = 9;
        }
        // 动态获取图片加载样式View
        View viewPhotoStyles = View.inflate(mContext, photoStyleIds[intPhotoSize - 1], null);
        for (int photoIndex = 0; photoIndex < intPhotoSize; photoIndex++) {
            ImageView imageViewItem = (ImageView) viewPhotoStyles.findViewById(imageViewItemids[photoIndex]);

            Tweet.ImageInfo imageInfo = images.get(photoIndex);
            if (null != imageInfo) {
                Glide.with(mContext).load(imageInfo.getUrl()).placeholder(R.drawable.default_image_show_square).into(imageViewItem);
            }
        }
        // 设置图片加载样式View的大小
        final int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        int imageSize = Math.round(screenWidth / (intPhotoSize == 9 ? 6.0f : 5.0f));
        imageSize = Math.round(imageSize * (intPhotoSize == 9 ? 3f : 2f));
        LinearLayout.LayoutParams linearParamsPhotos = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);// (LinearLayout.LayoutParams)
        linearParamsPhotos.height = imageSize;
        linearParamsPhotos.width = LinearLayout.LayoutParams.MATCH_PARENT;
        viewPhotoStyles.setLayoutParams(linearParamsPhotos); // 使设置好的布局参数应用到控件
        viewPhotoContainer.addView(viewPhotoStyles);

    }

}
