package aaa.bbb.ccc.transleiter.data;

import com.github.vivchar.rendererrecyclerviewadapter.ViewModel;

import io.realm.RealmObject;

public class ItemTransleted extends RealmObject {
    private byte[] flagByte;
    private String transletedText;
    private int languageCode;


    public byte[] getFlagByte() {
        return flagByte;
    }

    public void setFlagBitMap(byte[] flagByte) {
        this.flagByte = flagByte;
    }

    public String getTransletedText() {
        return transletedText;
    }

    public void setTransletedText(String transletedText) {
        this.transletedText = transletedText;
    }


    public int getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(int languageCode) {
        this.languageCode = languageCode;
    }
}
