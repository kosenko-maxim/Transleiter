package aaa.bbb.ccc.transleiter.presenter.main;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import aaa.bbb.ccc.transleiter.screen.main.MainView;


public class MainPresenterImpl extends MvpBasePresenter<MainView> implements MainPresenter {
    MainView mainView;
    public MainPresenterImpl(MainView mainView){
        this.mainView = mainView;
    }

    @Override
    public void checkInternetConection() {
        if(!mainView.isOnline()) {
            mainView.showMessage("Check your Internet connection...");
        }
    }
}
