package aaa.bbb.ccc.transleiter.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import aaa.bbb.ccc.transleiter.R;

public class SpinnerArrayAdapter extends ArrayAdapter {
    private Context context;
    private ArrayList<Integer> imageResourse;
    private ArrayList<String> language;


    public SpinnerArrayAdapter(@NonNull Context context, ArrayList<Integer> imageResourse, ArrayList<String> language) {
        super(context, R.layout.spinner);
        this.imageResourse = imageResourse;
        this.language = language;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageResourse.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner, parent, false);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.flagSpinner);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.imageView.setImageResource(imageResourse.get(position));


        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.spinner_dropdown_item, parent, false);
            mViewHolder.imageView = (ImageView) convertView.findViewById(R.id.spinner_image);
            mViewHolder.textView = (TextView) convertView.findViewById(R.id.spinner_text);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.imageView.setImageResource(imageResourse.get(position));
        mViewHolder.textView.setText(language.get(position));

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
