package aaa.bbb.ccc.transleiter.screen.main;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface MainView extends MvpView {
    boolean isOnline();
    void showMessage(String text);
}
