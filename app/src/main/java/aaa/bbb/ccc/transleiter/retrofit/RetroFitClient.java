package aaa.bbb.ccc.transleiter.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
//https://www.googleapis.com/language/translate/v2/detect?key=AIzaSyBBjovpvIbOCsbvzvtG1FGF6pS6T3mEb_0&q=текст
//https://translation.googleapis.com/language/translate/v2/detect?key=AIzaSyBBjovpvIbOCsbvzvtG1FGF6pS6T3mEb_0&q=текст
// https://translation.googleapis.com/language/translate/v2?key=AIzaSyBBjovpvIbOCsbvzvtG1FGF6pS6T3mEb_0&source=ru&target=en&q=текст;

    private static final String OPEN_URL = "https://translation.googleapis.com/language/translate/";

    public static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(OPEN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static SearchGoogle getApiService(){
        return getRetrofitInstance().create(SearchGoogle.class);
    }
}
