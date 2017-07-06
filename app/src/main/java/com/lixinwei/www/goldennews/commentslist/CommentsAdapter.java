package com.lixinwei.www.goldennews.commentslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.Comment;

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

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
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

        public void bindView(Comment comment) {
            mContent.setText(comment.getContent());
            mUserName.setText(comment.getAuthor());

            Glide.with(mContext)
                    .load(comment.getAvatar())
                    .apply(RequestOptions.circleCropTransform())
                    .into(mUserAvatar);
        }
    }
}
