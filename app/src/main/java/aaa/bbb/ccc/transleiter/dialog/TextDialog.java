package aaa.bbb.ccc.transleiter.dialog;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Objects;

import aaa.bbb.ccc.transleiter.data.DataBase;
import aaa.bbb.ccc.transleiter.R;


public class TextDialog extends DialogFragment {

    private String text;
    DataBase dataBase = new DataBase();

    public void binData(String text) {
        this.text = text;
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(d.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_text, container, false);
        TextView dialogText = root.findViewById(R.id.dialogTextView);
        ImageView ibClose = root.findViewById(R.id.ibClose);
        dialogText.setText(text);
        final WallpaperManager wallpaperManager =
                WallpaperManager.getInstance(getContext());
        ibClose.setOnClickListener((view) -> dismiss());
        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}


