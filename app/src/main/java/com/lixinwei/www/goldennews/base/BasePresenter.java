package com.lixinwei.www.goldennews.base;

/**
 * Created by welding on 2017/6/30.
 */

public interface BasePresenter<T> {

    void bindView(T view);
    void unbindView();

}
