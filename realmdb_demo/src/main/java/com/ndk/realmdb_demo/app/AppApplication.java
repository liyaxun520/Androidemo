package com.ndk.realmdb_demo.app;



import com.ndk.realmdb_demo.base.BaseApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * APPLICATION
 */
public class AppApplication extends BaseApplication {
    public static Realm REALM_INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new  RealmConfiguration.Builder()
                .name("TimeLineRealm.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        REALM_INSTANCE = Realm.getInstance(config);
    }
}
