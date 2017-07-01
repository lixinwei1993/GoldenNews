package com.lixinwei.www.goldennews;

import com.lixinwei.www.goldennews.data.Realm.RealmServiceModule;
import com.lixinwei.www.goldennews.data.domain.ZhihuApiModule;
import com.lixinwei.www.goldennews.newslist.NewsListActivity;
import com.lixinwei.www.goldennews.newslist.NewsListSubComponent;
import com.lixinwei.www.goldennews.newslist.NewsListModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by welding on 2017/6/29.
 */
@Singleton
@Component (
        modules = {
                ApplicationModule.class,
                ZhihuApiModule.class,
                RealmServiceModule.class
        }
)
public interface ApplicationComponent {
    void inject(NewsListActivity activity);

    NewsListSubComponent plus(NewsListModule module);
}
