package aaa.bbb.ccc.transleiter;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("language").build();
        Realm.setDefaultConfiguration(config);
    }
}
