package com.example.tt.rasp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by tt on 20.09.17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
