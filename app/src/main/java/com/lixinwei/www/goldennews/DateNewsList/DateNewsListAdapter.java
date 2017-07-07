package com.lixinwei.www.goldennews.DateNewsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.Story;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListAdapter extends RecyclerView.Adapter<DateNewsListAdapter.DateNewsListViewHolder> {
    private List<Story> mStoryList = new ArrayList<>();

    @Inject
    Context mContext;
    @Inject
    DateNewsListContract.Presenter mPresenter;
    @Inject
    public DateNewsListAdapter() {

    }

    public DateNewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_liked_list, parent, false
        );

        DateNewsListViewHolder dateNewsListViewHolder = new DateNewsListViewHolder(view);
        setupClickableViews(view, dateNewsListViewHolder);

        return dateNewsListViewHolder;
    }

    private void setupClickableViews(final View view, final DateNewsListViewHolder viewHolder) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                long id = mStoryList.get(position).getId();
                mPresenter.startDetailActivity(id);
            }
        });

    }

    @Override
    public void onBindViewHolder(DateNewsListViewHolder holder, int position) {
        Story story = mStoryList.get(position);

        holder.bind(story);
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public void updateStoriesList(List<Story> stories) {
        mStoryList.clear();
        mStoryList.addAll(stories);
        notifyDataSetChanged();
    }

    public static class DateNewsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liked_item_image)
        ImageView mImageView;
        @BindView(R.id.liked_item_title)
        TextView mTitle;

        private Context mContext;

        public DateNewsListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();   //TODO view.getContext 是哪一种context，声明周期怎样的？
        }


        public void bind(Story story) {
            mTitle.setText(story.getTitle());

            //Glide can automatically cache image in memory&disk, if you want to customize the cache pattern, read api
            Glide.with(mContext)
                    .load(story.getImages().get(0))
                    .into(mImageView);
        }
    }
}
