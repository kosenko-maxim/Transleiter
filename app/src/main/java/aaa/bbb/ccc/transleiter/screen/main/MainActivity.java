package aaa.bbb.ccc.transleiter.screen.main;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import org.jetbrains.annotations.NotNull;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Locale;

import aaa.bbb.ccc.transleiter.R;
import aaa.bbb.ccc.transleiter.presenter.main.MainPresenterImpl;
import aaa.bbb.ccc.transleiter.screen.history.HistoryFragment;
import aaa.bbb.ccc.transleiter.screen.PhrasebookFragment;
import aaa.bbb.ccc.transleiter.screen.SettingsFragment;
import aaa.bbb.ccc.transleiter.screen.tranclete.TrancleteFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends MvpActivity<MainView, MainPresenterImpl> implements MainView {
    BottomifyNavigationView bottomifyNavigationView;
    int number = 0;
    private final int REQ_CODE_SPEECH_OUTPUT = 143;
    TrancleteFragment trancleteFragment;
    @BindView(R.id.back)
    ImageView back;
    public static boolean checkVoice = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        final FragmentManager fragmentManager = getSupportFragmentManager();
        trancleteFragment = new TrancleteFragment();
        fragmentManager.beginTransaction().replace(R.id.container, trancleteFragment).commit();
        bottomifyNavigationView = findViewById(R.id.bottomify_nav);
        back.setVisibility(View.GONE);
        bottomifyNavigationView.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(@NotNull BottomifyNavigationView.NavigationItem navigationItem) {
                switch (navigationItem.getPosition()) {
                    case 0:
                        fragmentManager.beginTransaction().replace(R.id.container, trancleteFragment).commit();
                        back.setVisibility(View.GONE);
                        break;
                    case 1:
                        fragmentManager.beginTransaction().replace(R.id.container, new HistoryFragment()).commit();
                        back.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        fragmentManager.beginTransaction().replace(R.id.container, new PhrasebookFragment()).commit();
                        back.setVisibility(View.GONE);
                        break;
                    case 4:
                        fragmentManager.beginTransaction().replace(R.id.container, new SettingsFragment()).commit();
                        back.setVisibility(View.VISIBLE);
                        break;
                }


            }
        });
    }

    @OnClick({R.id.microfon, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.microfon:
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Пожалуйста говорите...");
                bottomifyNavigationView.setActiveNavigationIndex(0);

                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_OUTPUT);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.back:
                bottomifyNavigationView.setActiveNavigationIndex(0);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_OUTPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> voiceText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Bundle bundle = new Bundle();
                    bundle.putString("voice", voiceText.get(0));
                    trancleteFragment.setArguments(bundle);
                    checkVoice =true;
                }
                break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSharedPreferences("aaa.bbb.ccc.transleiter.screen.tranclete", 0).edit().clear().apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().checkInternetConection();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("save_position", number);
        super.onSaveInstanceState(outState);
    }

    @NonNull
    @Override
    public MainPresenterImpl createPresenter() {
        return new MainPresenterImpl(this);
    }

    @Override
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
