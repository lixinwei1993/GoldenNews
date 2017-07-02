package com.lixinwei.www.goldennews.data.domain;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.lixinwei.www.goldennews.util.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class ZhihuApiModule {
    private static final String BASE_URL = "https://news-at.zhihu.com/api/4/";
    private static final String HTTP_CACHE_PATH = "http-cache";
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final String PRAGMA = "Pragma";
    private static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    private static final int NETWORK_READ_TIMEOUT = 30; //30 sec
    private static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final int CACHE_MAX_AGE = 2; // 2 min
    private static final int CACHE_MAX_STALE = 7; // 7 day
    private static final int RETRY_COUNT = 3;   // 3 times

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit.Builder builder = new Retrofit.Builder();

        builder.client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }

    @Provides
    @Singleton
    public ZhihuService provideZhihuService(Retrofit retrofit) {
        return retrofit.create(ZhihuService.class);
    }


    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(
            Cache cache,
            @Named("cacheInterceptor") Interceptor cacheInterceptor,
            @Named("offlineInterceptor") Interceptor offlineCacheInterceptor,
            @Named("retryInterceptor") Interceptor retryInterceptor
    ) {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(cacheInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .addInterceptor(retryInterceptor)
                .cache(cache)
                .connectTimeout(NETWORK_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_READ_TIMEOUT, TimeUnit.SECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    @Named("cacheDir")
    File provideCacheDir(Context context) {
        return context.getCacheDir();
    }

    @Provides
    @Singleton
    public Cache provideCache(@Named("cacheDir") File cacheDir) {
        Cache cache = null;

        try {
            cache = new Cache(new File(cacheDir.getPath(), HTTP_CACHE_PATH), CACHE_SIZE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cache;
    }

    @Singleton
    @Provides
    @Named("cacheInterceptor")
    public Interceptor provideCacheInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(CACHE_MAX_AGE, TimeUnit.MINUTES)
                        .build();

                return response.newBuilder()
                        .removeHeader(PRAGMA)
                        .removeHeader(CACHE_CONTROL)
                        .header(CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    @Singleton
    @Provides
    @Named("offlineInterceptor")
    public Interceptor provideOfflineCacheInterceptor(final Context context) {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!Utils.isConnected(context)) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(CACHE_MAX_STALE, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    @Singleton
    @Provides
    @Named("retryInterceptor")
    public Interceptor provideRetryInterceptor() {

        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = null;
                IOException exception = null;

                int tryCount = 0;
                while (tryCount < RETRY_COUNT && (null == response || !response.isSuccessful())) {
                    // retry the request
                    try {
                        response = chain.proceed(request);
                    } catch (IOException e) {
                        exception = e;
                    } finally {
                        tryCount++;
                    }
                }

                // throw last exception
                if (null == response && null != exception)
                    throw exception;

                // otherwise just pass the original response on
                return response;
            }
        };
    }



}
