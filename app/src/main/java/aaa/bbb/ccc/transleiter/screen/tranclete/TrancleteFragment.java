package aaa.bbb.ccc.transleiter.screen.tranclete;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.rw.keyboardlistener.KeyboardUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import aaa.bbb.ccc.transleiter.R;
import aaa.bbb.ccc.transleiter.data.DataBase;
import aaa.bbb.ccc.transleiter.presenter.tranclete.TrancletePresenterImpl;
import aaa.bbb.ccc.transleiter.retrofit.RetroFitClient;
import aaa.bbb.ccc.transleiter.retrofit.SearchGoogle;
import aaa.bbb.ccc.transleiter.screen.main.MainActivity;
import aaa.bbb.ccc.transleiter.util.EditTextEx;
import aaa.bbb.ccc.transleiter.util.Language;
import aaa.bbb.ccc.transleiter.util.LanguageCode;
import aaa.bbb.ccc.transleiter.util.SpinnerArrayAdapter;
import aaa.bbb.ccc.transleiter.util.TextToSpeachManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static aaa.bbb.ccc.transleiter.screen.main.MainActivity.checkVoice;
import static android.content.Context.CLIPBOARD_SERVICE;

public class TrancleteFragment extends MvpFragment<TransleteView, TrancletePresenterImpl> implements TransleteView {
    String API = "trnsl.1.1.20190117T125234Z.1571cc32fed20a9c.ad84ae1d7f04fd2e969a82a27505edbb36f202b4";

    String API_GOOGLE = "AIzaSyBBjovpvIbOCsbvzvtG1FGF6pS6T3mEb_0";
    // https://translation.googleapis.com/language/translate/v2?key=AIzaSyBBjovpvIbOCsbvzvtG1FGF6pS6T3mEb_0&source=ru&target=en&q=текст;
    //private final int REQ_CODE_SPEECH_OUTPUT = 143;
    SearchGoogle searchGoogle;
    @BindView(R.id.editText)
    EditTextEx editText;
    @BindView(R.id.trancleitedText)
    TextView trancleitedTextView;
    @BindView(R.id.spinnerSource)
    Spinner spinnerSource;
    @BindView(R.id.spinnerTarget)
    Spinner spinnerTarget;
    @BindView(R.id.languageNameSourse)
    TextView languageNameSourse;
    @BindView(R.id.languageNameTarget)
    TextView languageNameTarget;
    ArrayList<Integer> flagList = new ArrayList<>();
    ArrayList<String> languageList = new ArrayList<>();
    ClipboardManager clipboardManager;
    ClipData clipData;
    DataBase dataBase;
    TextToSpeachManager textSourseToSpeachManager = null;
    TextToSpeachManager textTargetToSpeachManager = null;


    private SharedPreferences Sharedprefernces_EditText;
    private static SharedPreferences Sharedprefernces_LnTarget;
    private final String App_Prefences = "aaa.bbb.ccc.transleiter.screen.tranclete";
    private final String App_Prefences_EditText = "editText";
    private final String App_Prefences_TransleteText = "transleteText";
    private final String App_Prefences_LnSourse = "lnSourse";
    private final String App_Prefences_LnTarget = "lnTarget";

    public boolean checkKlaviature = true;
    public boolean checkMaster = false;
    InputMethodManager imm;
    int soursePosition = 8;

    //int targetPosition = 1;
    // https://translation.googleapis.com/language/translate/v2?key=AIzaSyAaUB2eeH1uxYegkjlXTJ6VnZYm0ZwBIH4&source=ru&target=en&q=текст;
    //https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190117T125234Z.1571cc32fed20a9c.ad84ae1d7f04fd2e969a82a27505edbb36f202b4&lang=en&text=Перевод%20текста

    ArrayList<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_trancleted, container, false);
        ButterKnife.bind(this, view);
        imm = (InputMethodManager) TrancleteFragment.this.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        Sharedprefernces_EditText = getActivity().getSharedPreferences(App_Prefences, Context.MODE_PRIVATE);
        Sharedprefernces_LnTarget = getActivity().getSharedPreferences(App_Prefences, Context.MODE_PRIVATE);


        dataBase = new DataBase();
        textSourseToSpeachManager = new TextToSpeachManager();
        textTargetToSpeachManager = new TextToSpeachManager();
        searchGoogle = RetroFitClient.getApiService();
        clipboardManager = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
        clipData = clipboardManager.getPrimaryClip();
        addForList();
        SpinnerArrayAdapter arrayAdapter = new SpinnerArrayAdapter(getContext(), flagList, languageList);
        spinnerSource.setAdapter(arrayAdapter);
        spinnerTarget.setAdapter(arrayAdapter);
        spinnerSource.setSelection(8);
        spinnerTarget.setSelection(1);
        spinnersCdsfas();
        texts();
        KeyboardUtils.addKeyboardToggleListener(getActivity(), isVisible -> System.out.println("keyboard visible: " + isVisible));
        return view;
    }


    public void spinnersCdsfas() {
        spinnerSource.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textSourseToSpeachManager.init(getContext(), swichLanguage(position));
                languageNameSourse.setText(languageList.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerTarget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!editText.getText().toString().isEmpty()) {
                    getPresenter().getResultsSearch(API_GOOGLE, swichLanguage(spinnerSource.getSelectedItemPosition()), swichLanguage(position), editText.getText().toString());
                }
                int positionTarget = Sharedprefernces_LnTarget.getInt(App_Prefences_LnTarget, 1);
                System.out.println("+++++++++++++++" + positionTarget);
                System.out.println("+++++++++++++++" + position);
                if (position != positionTarget) {
                    checkMaster = false;
                } else {
                    checkMaster = true;
                }
                textTargetToSpeachManager.init(getContext(), swichLanguage(position));
                languageNameTarget.setText(languageList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public void texts() {

        editText.setOnKeyListener((v, keyCode, event) -> {
            if (imm.isAcceptingText()) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN &&
                        keyCode == KeyEvent.KEYCODE_ENTER) || (event.getAction() == KeyEvent.ACTION_UP &&
                        keyCode == KeyEvent.KEYCODE_BACK)) {

                    checkKlaviature = false;
                    if (!trancleitedTextView.getText().toString().isEmpty() && !editText.getText().toString().isEmpty()) {
                        putOnData(spinnerTarget.getSelectedItemPosition(), trancleitedTextView.getText().toString());
                        putOnData(spinnerSource.getSelectedItemPosition(), editText.getText().toString());
                    }
                    imm.hideSoftInputFromWindow(spinnerTarget.getWindowToken(), 0);
                    getPresenter().getResultsSearch(API_GOOGLE, swichLanguage(spinnerSource.getSelectedItemPosition()), swichLanguage(spinnerTarget.getSelectedItemPosition()), editText.getText().toString());
                    return true;
                }
            }
            return false;
        });

        //String text = trancleitedTextView.getText().toString();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(checkKlaviature);
                soursePosition = spinnerSource.getSelectedItemPosition();
                if (editText.getText().toString().isEmpty())
                    trancleitedTextView.setText("");
                if (checkVoice) {
                    getPresenter().getResultsDetectedSearch(API_GOOGLE, editText.getText().toString());
                    checkVoice = false;
                }
                if (checkKlaviature) {
                    getPresenter().getResultsDetectedSearch(API_GOOGLE, editText.getText().toString());
                    getPresenter().getResultsSearch(API_GOOGLE, swichLanguage(spinnerSource.getSelectedItemPosition()), swichLanguage(spinnerTarget.getSelectedItemPosition()), editText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        trancleitedTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkMaster == false && !s.toString().isEmpty()) {
                    putOnData(spinnerTarget.getSelectedItemPosition(), s.toString());
                    putOnData(spinnerSource.getSelectedItemPosition(), editText.getText().toString());
                }
            }
        });
    }

    @OnClick({R.id.copyText, R.id.deleteText, R.id.reverse, R.id.speachTextSourse, R.id.speachTextTarget, R.id.relativeLayoutMain, R.id.editText})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.copyText:
                if (!trancleitedTextView.getText().toString().isEmpty()) {
                    ClipData clipData = ClipData.newPlainText("text", trancleitedTextView.getText());
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getContext(), "Copping....", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deleteText:
                editText.setText("");
                trancleitedTextView.setText("");
                getContext().getSharedPreferences("aaa.bbb.ccc.transleiter.screen.tranclete", 0).edit().clear().apply();
                break;
            case R.id.reverse:
                checkKlaviature = false;
                int positionSourse = spinnerSource.getSelectedItemPosition();
                int targetPosition = spinnerTarget.getSelectedItemPosition();
                String textTarget = trancleitedTextView.getText().toString();
                String textSourse = editText.getText().toString();
                editText.setText(textTarget);
                trancleitedTextView.setText(textSourse);
                spinnerTarget.setSelection(positionSourse);
                spinnerSource.setSelection(targetPosition);
                textSourseToSpeachManager.init(getContext(), swichLanguage(targetPosition));
                textTargetToSpeachManager.init(getContext(), swichLanguage(spinnerSource.getSelectedItemPosition()));
                break;
            case R.id.speachTextSourse:
                getPresenter().getResultsDetectedSearch(API_GOOGLE, editText.getText().toString());
                textSourseToSpeachManager.initQueue(editText.getText().toString());
                break;
            case R.id.speachTextTarget:
                textTargetToSpeachManager.initQueue(trancleitedTextView.getText().toString());
                break;
            case R.id.relativeLayoutMain:
                if (imm.isAcceptingText()) {
                    if (checkKlaviature) {
                        imm.hideSoftInputFromWindow(spinnerTarget.getWindowToken(), 0);
                        getPresenter().getResultsSearch(API_GOOGLE, swichLanguage(spinnerSource.getSelectedItemPosition()), swichLanguage(spinnerTarget.getSelectedItemPosition()), editText.getText().toString());
                        //checkKlaviature = false;
                        if (!editText.getText().toString().isEmpty()) {
                            putOnData(spinnerTarget.getSelectedItemPosition(), trancleitedTextView.getText().toString());
                            putOnData(spinnerSource.getSelectedItemPosition(), editText.getText().toString());
                        }
                    }
                }
                break;
            case R.id.editText:
                checkKlaviature = true;
                System.out.println(checkKlaviature);
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        checkKlaviature =true;
        Bundle bundle = this.getArguments();
        String text = "";
        if (bundle != null && checkVoice == true) {
            text = bundle.getString("voice");
            Sharedprefernces_EditText.edit().putString(App_Prefences_EditText, text).apply();
            //putOnData(spinnerTarget.getSelectedItemPosition(), text);
        }

        text = Sharedprefernces_EditText.getString(App_Prefences_EditText, "");
        editText.setText(text);
        //trancleitedTextView.setText(Sharedprefernces_EditText.getString(App_Prefences_TransleteText, ""));
        int positionTarget = Sharedprefernces_LnTarget.getInt(App_Prefences_LnTarget, 1);
        int positionSourse = Sharedprefernces_LnTarget.getInt(App_Prefences_LnSourse, 8);
        spinnerTarget.setSelection(positionTarget);
        spinnerSource.setSelection(positionSourse);
        textTargetToSpeachManager.init(getContext(), swichLanguage(positionTarget));
        textSourseToSpeachManager.init(getContext(), swichLanguage(positionSourse));
    }

    @Override
    public void onPause() {
        super.onPause();
        Sharedprefernces_EditText.edit().putString(App_Prefences_EditText, editText.getText().toString()).apply();
        Sharedprefernces_EditText.edit().putString(App_Prefences_TransleteText, trancleitedTextView.getText().toString()).apply();
        Sharedprefernces_LnTarget.edit().putInt(App_Prefences_LnTarget, spinnerTarget.getSelectedItemPosition()).apply();
        Sharedprefernces_LnTarget.edit().putInt(App_Prefences_LnSourse, spinnerSource.getSelectedItemPosition()).apply();
    }

    public void showErrorMessage() {
        trancleitedTextView.setText(editText.getText());
        // Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }


    public void showText(String trancletedText) {
        String s = trancletedText.replaceAll("&#39;", "\'");
        trancleitedTextView.setText(s);

    }


    @Override
    public void detectLanguageCode(String languageCode) {

        int positionSpinner = 0;
        LanguageCode[] languageCodes = LanguageCode.values();
        if (languageCode.equals("tg") || languageCode.equals("be") || languageCode.equals("mn") || languageCode.equals("sr") || languageCode.equals("ky") || languageCode.equals("uk") || languageCode.equals("kk") || languageCode.equals("mk") || editText.getText().toString().isEmpty())
            positionSpinner = 8;
        else positionSpinner = 1;
        for (int i = 0; i < languageCodes.length; i++) {
            String s = languageCodes[i].toString();
            if (s.equals(languageCode)) positionSpinner = i;
        }
        spinnerSource.setSelection(positionSpinner);

    }

    public String swichLanguage(int position) {
        LanguageCode[] languageCodes = LanguageCode.values();
        return languageCodes[position].toString();
    }

    public void putOnData(int position, String text) {
        Bitmap photoBitmap = BitmapFactory.decodeResource(getResources(), flagList.get(position));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        dataBase.createData(byteArray, text, position);
    }

    public void addForList() {
        languageList.clear();
        flagList.clear();
        flagList.add(R.drawable.alb);
        flagList.add(R.drawable.uk);
        //flagList.add(R.drawable.usa);
        flagList.add(R.drawable.bul);
        flagList.add(R.drawable.portu);
        flagList.add(R.drawable.vie);
        flagList.add(R.drawable.nether);
        flagList.add(R.drawable.germ);
        flagList.add(R.drawable.ro);
        flagList.add(R.drawable.ru);
        flagList.add(R.drawable.tur);
        flagList.add(R.drawable.fran);
        flagList.add(R.drawable.jap);
        Language[] languages = Language.values();
        for (Language l : languages) {
            languageList.add(l.toString());
        }
    }


    @Override
    public TrancletePresenterImpl createPresenter() {
        return new TrancletePresenterImpl(this);
    }


}
