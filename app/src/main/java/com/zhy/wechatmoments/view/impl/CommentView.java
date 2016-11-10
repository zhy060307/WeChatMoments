package com.zhy.wechatmoments.view.impl;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhy.wechatmoments.R;
import com.zhy.wechatmoments.domain.Tweet.Comment;
import com.zhy.wechatmoments.utils.recyclerview.CommonAdapter;
import com.zhy.wechatmoments.utils.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2016/11/4.
 */

public class CommentView extends RelativeLayout {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private Context context;
    private View rootView;
    private List<Comment> comments = new ArrayList<>();
    private CommonAdapter<Comment> adapter;

    public CommentView(Context context) {
        this(context, null);
    }

    public CommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.item_view_comment, this, true);
        ButterKnife.bind(rootView, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new CommonAdapter<Comment>(context, R.layout.list_item_comment, comments) {
            @Override
            protected void convert(ViewHolder holder, Comment comment, int position) {
                holder.setText(R.id.tv_nike, comment.getSender().getNick() + ": ")
                        .setText(R.id.tv_content, comment.getContent());
            }

        };
        recyclerView.setAdapter(adapter);

    }


    public void loadComments(List<Comment> comments) {

        this.comments.clear();
        this.comments.addAll(comments);
        adapter.notifyDataSetChanged();
    }
}
