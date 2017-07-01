package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.StoryForRealm;
import com.lixinwei.www.goldennews.data.model.StoryForRealm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/6/30.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {
    private final List<StoryForRealm> mStories = new ArrayList<>();

    @Inject
    public NewsListAdapter() {

    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //这个parent指的是什么？getContext又指的是什么，LayoutInflater又有哪些地方可以用呢？
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
        return new NewsListViewHolder(view);
    }


    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        StoryForRealm storyForRealm = mStories.get(position);

        holder.bind(storyForRealm);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void updateStoriesList(List<StoryForRealm> stories) {
        mStories.clear();
        mStories.addAll(stories);
        notifyDataSetChanged();
    }

    public class NewsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.story_card_image)
        ImageView mImage;

        @BindView(R.id.story_card_title)
        TextView mTitle;

        private Context mContext;

        public NewsListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bind(StoryForRealm storyForRealm) {
            mTitle.setText(storyForRealm.getTitle() + storyForRealm.getComments());

            Glide.with(mContext)
                    .load(storyForRealm.getImage())
                    .into(mImage);
        }
    }
}