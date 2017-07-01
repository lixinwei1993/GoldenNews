package com.lixinwei.www.goldennews.data.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class ZhihuApiModule {
    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(60*1000, TimeUnit.MILLISECONDS)
                .readTimeout(60*1000, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    /**
     *
    @Provides
    @Singleton
    public GsonConverterFactory provideGsonConverterFactory() {
        final Gson gson = new GsonBuilder().setDateFormat("yyyyMMdd").serializeNulls().create();

        return GsonConverterFactory.create(gson);
    }
     */

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.client(okHttpClient)
                .baseUrl("https://news-at.zhihu.com/api/4/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

    @Provides
    @Singleton
    public ZhihuService provideZhihuService(Retrofit retrofit) {
        return retrofit.create(ZhihuService.class);
    }

}
