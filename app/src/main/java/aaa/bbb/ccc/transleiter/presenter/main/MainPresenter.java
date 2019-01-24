package aaa.bbb.ccc.transleiter.presenter.main;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import aaa.bbb.ccc.transleiter.screen.main.MainView;

public interface MainPresenter extends MvpPresenter<MainView> {
    void checkInternetConection();
}
