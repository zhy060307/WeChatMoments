package com.zhy.wechatmoments.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit
 * <p>
 * 创建时间: 16/5/3 上午10:50 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class RetrofitUtils {

    private static final String BASE_URL = "http://thoughtworks-ios.herokuapp.com";
    private static Retrofit instance;


    public static <T> T createService(Class<T> clazz) {

        if (null == instance) {
            synchronized (RetrofitUtils.class) {
                if (null == instance) {

                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(60 * 3, TimeUnit.SECONDS)
                            .build();
                    instance = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .build();
                }
            }
        }

        return instance.create(clazz);
    }


}
