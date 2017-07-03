package com.lixinwei.www.goldennews.data.Realm;

import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;

import java.util.List;

/**
 * Created by welding on 2017/7/1.
 */

public interface RealmService {


    //store&&delete liked story
    void insertLikedStory(StoryForNewsList story);
    void deleteLikedStory(Long id);
    boolean queryLikedStory(Long id);

    void insertStoryRead(Long id);
    boolean queryStoryRead(Long id);
    List<StoryLikedForRealm> getLikedList();
    void changeLikeTime(Long id ,Long time, boolean isPlus);

}
