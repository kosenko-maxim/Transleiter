package aaa.bbb.ccc.transleiter.presenter.tranclete;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import aaa.bbb.ccc.transleiter.screen.tranclete.TransleteView;
import aaa.bbb.ccc.transleiter.retrofit.models.Results;
import aaa.bbb.ccc.transleiter.retrofit.RetroFitClient;
import aaa.bbb.ccc.transleiter.retrofit.SearchGoogle;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class TrancletePresenterImpl extends MvpBasePresenter<TransleteView> implements TrancletePresenter {
    SearchGoogle searchGoogle;
    TransleteView transleteView;


    public TrancletePresenterImpl(TransleteView transleteView) {
        this.transleteView = transleteView;
        searchGoogle = RetroFitClient.getApiService();
    }


    @Override
    public void getResultsSearch(String key, String source, String target, String text) {

            Single<Results> observable = searchGoogle.getTransleitedText(key, source, target, text);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<Results>() {
                        @Override
                        public void onSuccess(Results results) {
                            transleteView.showText(results.getData().getTranslations().get(0).getTranslatedText());
                        }

                        @Override
                        public void onError(Throwable e) {
                            transleteView.showErrorMessage();
                        }
                    });
    }
    @Override
    public void getResultsDetectedSearch(String key, String text) {
        Single<Results> observable = searchGoogle.getDetectedText(key,text);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Results>() {
                    @Override
                    public void onSuccess(Results results) {
                        transleteView.detectLanguageCode(results.getData().getDetections().get(0).get(0).getLanguage());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

}
