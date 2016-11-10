package com.zhy.wechatmoments.utils.recyclerview.base;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhy.wechatmoments.R;


/**
 * TODO 功能描述
 * <p>
 * 创建时间: 16/7/8 下午4:53 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class LoadMoreFooter extends FooterView {

    ProgressBar progressBar;
    TextView textView;

    public LoadMoreFooter(Context context) {
        super(context);
        footer = View.inflate(context, R.layout.list_item_footer, null);
        footer.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        initView();
    }

    private void initView() {
        progressBar = (ProgressBar) footer.findViewById(R.id.progressbar);
        textView = (TextView) footer.findViewById(R.id.text);
    }

    @Override
    protected void loadComplete() {
        footer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setText(R.string.loading_no_more);

    }

    @Override
    protected void loading() {
        footer.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setText(R.string.loading);
    }

    @Override
    public void init() {
        footer.setVisibility(View.GONE);
    }
}
