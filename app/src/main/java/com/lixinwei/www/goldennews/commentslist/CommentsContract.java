package com.lixinwei.www.goldennews.commentslist;

import com.lixinwei.www.goldennews.base.BasePresenter;
import com.lixinwei.www.goldennews.base.BaseView;
import com.lixinwei.www.goldennews.data.model.Comment;

import java.util.List;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsContract {
    interface View extends BaseView {
        void showShortComments(List<Comment> comments);
        void setLoadingIndicator(final boolean active);
        void showLoadErrorSnackbar();
    }

    interface Presenter extends BasePresenter<View> {
        void loadShortComments(long id);
        void bindView(View view);
        void unbindView();
    }
}
