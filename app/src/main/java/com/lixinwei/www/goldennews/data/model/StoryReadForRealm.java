package com.lixinwei.www.goldennews.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by welding on 2017/7/3.
 */

public class StoryReadForRealm extends RealmObject {
    @PrimaryKey
    private Long mId;

    public StoryReadForRealm() { }

    public StoryReadForRealm(Long id) {
        this.mId = id;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }
}
