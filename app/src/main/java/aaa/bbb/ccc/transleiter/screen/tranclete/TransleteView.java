package aaa.bbb.ccc.transleiter.screen.tranclete;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface TransleteView extends MvpView {
    void showErrorMessage();

    void showText(String trancletedText);

    void detectLanguageCode(String languageCode);

}
