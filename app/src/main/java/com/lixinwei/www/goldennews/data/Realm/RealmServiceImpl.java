package com.lixinwei.www.goldennews.data.Realm;

import android.content.Context;

import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.data.model.StoryReadForRealm;

import java.util.Calendar;
import java.util.List;
import java.util.Timer;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by welding on 2017/7/1.
 */

//TODO How this singleton Scope annotation decide the scope of the injected object of this class
    //应该是由scope注解的类其具体的Dager2为其生成的factory代码位于同一scope的component中
    //component是dagger2生成代码的入口，可以认为dagger只生成component对应的impl，所有的provide或@inject注解constructor
    //的类对应的生成代码都位于同域component中，但是对于没有scope注解的provide又位于哪里呢？有空看一下Dagger2生成的代码以及youtube上的讲解视频
    //再添加了RealmServiceModule后其实就用不到这里的@inject注解constructor了，但是为了理解还是scope，暂时先放在这里

public class RealmServiceImpl implements RealmService {


    public RealmServiceImpl(Context context) {
        Realm.init(context);
    }

    @Override
    public void insertLikedStory(StoryForNewsList story) {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();

            StoryLikedForRealm s = new StoryLikedForRealm();
            s.setId(story.getId());
            s.setImage(story.getImage());
            s.setTitle(story.getTitle());
            s.setLikedime((Calendar.getInstance().getTimeInMillis()));

            realm.beginTransaction();
            realm.copyToRealmOrUpdate(s);
            realm.commitTransaction();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void deleteLikedStory(Long id) {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            StoryLikedForRealm story = realm.where(StoryLikedForRealm.class).equalTo("mId", id).findFirst();
            realm.beginTransaction();
            if(story != null) {
                story.deleteFromRealm();
            }
            realm.commitTransaction();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public boolean queryLikedStory(Long id) {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            StoryLikedForRealm story = realm.where(StoryLikedForRealm.class).equalTo("mId", id).findFirst();
            if (story == null) {
                return false;
            } else {
                return true;
            }
        } finally {
            if(realm != null) {
                realm.close();
            }
        }

    }

    @Override
    public void insertStoryRead(Long id) {
        Realm realm = null;
        try { // I could use try-with-resources here
            StoryReadForRealm story = new StoryReadForRealm(id);
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(story);
            realm.commitTransaction();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public boolean queryStoryRead(Long id) {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            StoryReadForRealm story = realm.where(StoryReadForRealm.class).equalTo("mId", id).findFirst();
            if (story == null) {
                return false;
            } else {
                return true;
            }
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public List<StoryLikedForRealm> getLikedList() {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            RealmResults<StoryLikedForRealm> results = realm.where(StoryLikedForRealm.class).findAllSorted("mLikedTime");
            return realm.copyFromRealm(results);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    @Override
    public void changeLikeTime(Long id, Long time, boolean isPlus) {

    }
}
