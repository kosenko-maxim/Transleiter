
package aaa.bbb.ccc.transleiter.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detection {

    @SerializedName("confidence")
    @Expose
    private Float confidence;
    @SerializedName("isReliable")
    @Expose
    private Boolean isReliable;
    @SerializedName("language")
    @Expose
    private String language;

    public Float getConfidence() {
        return confidence;
    }

    public void setConfidence(Float confidence) {
        this.confidence = confidence;
    }

    public Boolean getIsReliable() {
        return isReliable;
    }

    public void setIsReliable(Boolean isReliable) {
        this.isReliable = isReliable;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
