package aaa.bbb.ccc.transleiter.screen;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import aaa.bbb.ccc.transleiter.R;
import aaa.bbb.ccc.transleiter.data.DataBase;
import aaa.bbb.ccc.transleiter.data.ItemTransleted;
import aaa.bbb.ccc.transleiter.dialog.TextDialog;
import aaa.bbb.ccc.transleiter.util.LanguageCode;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.CLIPBOARD_SERVICE;


public class SettingsFragment extends Fragment {
    String shareText;
    DataBase dataBase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        dataBase = new DataBase();
        if (!dataBase.getDataBaseList().isEmpty()) {
            ItemTransleted sourseTranclete = dataBase.getDataBaseList().get(dataBase.getDataBaseList().size() - 1);
            ItemTransleted targetTranclete = dataBase.getDataBaseList().get(dataBase.getDataBaseList().size() - 2);
            shareText = "Translate\n" + getLanguageCode(sourseTranclete.getLanguageCode())
                    + " : \n" +
                    sourseTranclete.getTransletedText()
                    + "\n to " +
                    getLanguageCode(targetTranclete.getLanguageCode())
                    + " : \n" +
                    targetTranclete.getTransletedText();
        }
        return view;
    }

    @OnClick({R.id.share, R.id.privacyPolicy, R.id.writeToTechSupport, R.id.clearHistory})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share:
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case R.id.privacyPolicy:
                getFromAssets();
                break;
            case R.id.writeToTechSupport:
                Intent shar = new Intent(Intent.ACTION_SEND);
                shar.setType("text/plain");
                shar.putExtra(android.content.Intent.EXTRA_TEXT, "Возникла Ошибочка");
                startActivity(Intent.createChooser(shar, "Share using"));
                break;
            case R.id.clearHistory:
                dataBase.deleteData();
                Toast.makeText(getContext(), "History deleted..", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public String getLanguageCode(int position) {
        LanguageCode[] languageCodes = LanguageCode.values();
        return languageCodes[position].toString();
    }


    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append("  ").append(line).append("\n ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public InputStream getFromAssets() {
        InputStream is = null;
        try {

            is = getContext().getAssets().open("policy.txt");
            TextDialog dialog = new TextDialog();
            dialog.binData(convertStreamToString(is));
            dialog.show(getFragmentManager(), "");
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }
}
