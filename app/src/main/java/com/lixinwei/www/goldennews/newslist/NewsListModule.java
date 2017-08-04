package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.util.PerFragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class NewsListModule {

    @PerFragment
    @Provides
    NewsListContract.Presenter provideNewsListContractPresenter(NewsListPresenter presenter) {
        //TODO 面试
        //这里的presenter通过参数传递的形式由dagger2完成presenter的初始化并传进来而不直接使用new NewsListPresenter()
        //的原因是因为new NewsListPresenter()没有通过dagger2机制，对NewsListPresenter内的依赖注解field不会触发dagger2的初始化机制，因此才不用new的，重要，大坑！！
        return presenter;
    }
}
