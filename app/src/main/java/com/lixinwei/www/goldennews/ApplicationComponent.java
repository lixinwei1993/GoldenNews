package com.lixinwei.www.goldennews;

import android.content.Context;

import com.lixinwei.www.goldennews.data.domain.ZhihuApiModule;
import com.lixinwei.www.goldennews.data.domain.ZhihuService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by welding on 2017/6/29.
 */
@Singleton
@Component (
        modules = {
                ApplicationModule.class,
                ZhihuApiModule.class
        }
)
public interface ApplicationComponent {
    ZhihuService getZhihuService();
    Context getContext();
}
