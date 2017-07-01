package com.lixinwei.www.goldennews.data.Realm;

import android.content.Context;

import com.lixinwei.www.goldennews.data.model.StoryForRealm;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
    public void insertStory(StoryForRealm story) {
        Realm realm = null;
        try { // I could use try-with-resources here
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
    public void deleteStory(long id) {
        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            StoryForRealm story = realm.where(StoryForRealm.class).equalTo("mId", id).findFirst();
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
    public void insertStories(List<StoryForRealm> stories) {

        Realm realm = null;
        try { // I could use try-with-resources here
            realm = Realm.getDefaultInstance();
            realm.beginTransaction();
            for (StoryForRealm story : stories)
                realm.copyToRealmOrUpdate(story);
            realm.commitTransaction();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }


}
