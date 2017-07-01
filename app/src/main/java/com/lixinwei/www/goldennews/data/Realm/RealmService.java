package com.lixinwei.www.goldennews.data.Realm;

import com.lixinwei.www.goldennews.data.model.StoryForRealm;

import java.util.List;

/**
 * Created by welding on 2017/7/1.
 */

public interface RealmService {


    //used to store liked story
    void insertStory(StoryForRealm story);

    void deleteStory(long id);

    //TODO used for cache latest daily top stories
    void insertStories(List<StoryForRealm> stories);
}
