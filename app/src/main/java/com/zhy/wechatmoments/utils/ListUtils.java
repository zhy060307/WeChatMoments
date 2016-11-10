package com.zhy.wechatmoments.utils;

import java.util.List;

/**
 * Created by lenovo on 2016/11/4.
 */

public class ListUtils {


    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<? extends Object> list) {
        if (null != list && !list.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 判断集合不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<? extends Object> list) {
        return !isEmpty(list);

    }
}
