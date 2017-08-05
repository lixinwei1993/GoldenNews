package com.lixinwei.www.goldennews.likedlist;

import android.content.Context;
//TODO 使用support库中的popoupMenu会出错。
import android.view.ContextThemeWrapper;
import android.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.LikedListViewHolder> {
    private List<StoryLikedForRealm> mLikedStories = new ArrayList<>();//TODO 这种初始化的实机是？

    @Inject
    Context mContext;
    @Inject
    LikedListContract.Presenter mPresenter;
    @Inject
    public LikedAdapter() {

    }

    public LikedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_liked_list, parent, false
        );

        LikedListViewHolder likedListViewHolder = new LikedListViewHolder(view);
        setupClickableViews(view, likedListViewHolder);

        return likedListViewHolder;
    }

    private void setupClickableViews(final View view, final LikedListViewHolder likedListViewHolder) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = likedListViewHolder.getAdapterPosition();
                long id = mLikedStories.get(position).getId();
                mPresenter.startDetailActivity(id);
            }
        });

        likedListViewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(mContext, R.style.popupMenuStyle);
                PopupMenu popupMenu = new PopupMenu(wrapper, view);
                popupMenu.inflate(R.menu.menu_popup_liked);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    int position;

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.item_share:
                                position = likedListViewHolder.getAdapterPosition();
                                StoryLikedForRealm story = mLikedStories.get(position);
                                mPresenter.shareItemClicked(story);
                                break;
                            case R.id.item_comments:
                                position = likedListViewHolder.getAdapterPosition();
                                mPresenter.commentsItemClicked(mLikedStories.get(position).getId());
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public void onBindViewHolder(LikedListViewHolder holder, int position) {
        StoryLikedForRealm storyLikedForRealm = mLikedStories.get(position);

        holder.bind(storyLikedForRealm);
    }

    @Override
    public int getItemCount() {
        return mLikedStories.size();
    }

    public void updateStoriesList(List<StoryLikedForRealm> stories) {
        mLikedStories.clear();
        mLikedStories.addAll(stories);
        notifyDataSetChanged();
    }

    public static class LikedListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liked_item_image)
        ImageView mImageView;
        @BindView(R.id.liked_item_title)
        TextView mTitle;
        @BindView(R.id.button_more)
        ImageButton mImageButton;
        private Context mContext;

        public LikedListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();   //TODO view.getContext 是哪一种context，声明周期怎样的？
        }


        public void bind(StoryLikedForRealm storyLikedForRealm) {
            mTitle.setText(storyLikedForRealm.getTitle());

            Picasso.with(mContext)
                    .load(storyLikedForRealm.getImage())
                    .into(mImageView);
        }
    }
}
