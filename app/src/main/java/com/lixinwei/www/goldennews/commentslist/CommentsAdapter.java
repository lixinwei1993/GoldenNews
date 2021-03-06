package com.lixinwei.www.goldennews.commentslist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.Comment;
import com.lixinwei.www.goldennews.util.PicassoTransforms;
import com.lixinwei.www.goldennews.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private List<Comment> mComments = new ArrayList<>();

    @Inject
    Context mContext;
    @Inject
    CommentsContract.Presenter mPresenter;

    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 10;

    @Inject
    public CommentsAdapter() {

    }

    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_comments, parent, false);

        CommentsViewHolder commentsViewHolder = new CommentsViewHolder(view);

        return commentsViewHolder;
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(true ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                           // animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        Comment comment = mComments.get(position);

        holder.bindView(comment);
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void updateComments(List<Comment> comments) {
        mComments.clear();
        mComments.addAll(comments);
        notifyDataSetChanged();
    }

    public static class CommentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_avatar)
        ImageView mUserAvatar;
        @BindView(R.id.user_name)
        TextView mUserName;
        @BindView(R.id.comment_content)
        TextView mContent;

        private Context mContext;

        public CommentsViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bindView(final Comment comment) {
            mContent.setText(comment.getContent());
            mUserName.setText(comment.getAuthor());

            /*Picasso.with(mContext)
                    .load(comment.getAvatar())
                    //.transform(new PicassoTransforms.CircleTransform())
                    .into(mUserAvatar);*/

            Picasso.with(mContext)
                    .load(comment.getAvatar())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mUserAvatar, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(comment.getAvatar())
                                    .error(R.mipmap.ic_launcher)
                                    .into(mUserAvatar, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });
        }
    }
}
