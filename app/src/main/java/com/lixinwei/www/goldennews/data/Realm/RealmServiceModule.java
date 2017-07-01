package com.lixinwei.www.goldennews.data.Realm;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/7/1.
 */
@Module
public class RealmServiceModule {

    //之所以要建立这个module而不再RealmServiceImpl中注解constructor，是为了将RealmServiceImpl转为RealmService
    //体现了RealmService interface的意义，而且返回interface也更复合solid原则
    @Provides
    @Singleton
    RealmService provideRealmService(Context context) {
        return new RealmServiceImpl(context);
    }
}
