package aaa.bbb.ccc.transleiter.presenter.tranclete;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import aaa.bbb.ccc.transleiter.screen.tranclete.TransleteView;


public interface TrancletePresenter extends MvpPresenter<TransleteView> {
    void getResultsSearch(String key, String source, String target,String text);
    void getResultsDetectedSearch(String key, String text);
}
