package com.zhy.wechatmoments.utils.recyclerview.base;

import android.content.Context;
import android.view.View;

/**
 * <p>
 * 创建时间: 16/7/8 下午4:16 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public abstract class FooterView {

    public static final int STATE_INIT = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_LOAD_COMPLETE = 2;
    View footer;
    private Context context;

    public FooterView(Context context) {
        this.context = context;
    }

    public View getFooter() {
        return footer;
    }

    public void setFooter(View footer) {
        this.footer = footer;
    }


    public void setFooterState(int footerState) {

        switch (footerState) {
            case STATE_INIT:
                init();
                break;
            case STATE_LOADING:
                loading();
                break;
            case STATE_LOAD_COMPLETE:
                loadComplete();
        }

    }

    protected abstract void loadComplete();

    protected abstract void loading();

    public abstract void init();

}
