
package aaa.bbb.ccc.transleiter.retrofit.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("translations")
    @Expose
    private List<Translation> translations = null;

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
    @SerializedName("detections")
    @Expose
    private List<List<Detection>> detections = null;

    public List<List<Detection>> getDetections() {
        return detections;
    }

    public void setDetections(List<List<Detection>> detections) {
        this.detections = detections;
    }
}
