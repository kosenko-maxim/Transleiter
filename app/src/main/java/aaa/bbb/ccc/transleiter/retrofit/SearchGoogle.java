package aaa.bbb.ccc.transleiter.retrofit;


import aaa.bbb.ccc.transleiter.retrofit.models.Results;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchGoogle {
      @GET("v2?")
    Single<Results> getTransleitedText(@Query("key")String key, @Query("source") String source, @Query("target") String target, @Query("q")String text);
    @GET("v2/detect?")
    Single<Results> getDetectedText(@Query("key")String key, @Query("q")String text);

}
