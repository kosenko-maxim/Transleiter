package aaa.bbb.ccc.transleiter.data;

import io.realm.Realm;
import io.realm.RealmResults;

public class DataBase {
    private Realm realm;


    public DataBase() {
        realm = Realm.getDefaultInstance();
    }

    public void createData(byte[] byteArray, String text, int languageCode) {
        realm.beginTransaction();
        ItemTransleted itemTransleted = realm.createObject(ItemTransleted.class);
        itemTransleted.setFlagBitMap(byteArray);
        itemTransleted.setTransletedText(text);
        itemTransleted.setLanguageCode(languageCode);
        realm.commitTransaction();
    }

    public RealmResults<ItemTransleted> getDataBaseList() {
        RealmResults<ItemTransleted> list = realm.where(ItemTransleted.class).findAll();
        return list;
    }

    public void deleteData() {
        realm.executeTransaction(realm -> realm.deleteAll());
    }

    public void close() {
        realm.close();
    }
}
