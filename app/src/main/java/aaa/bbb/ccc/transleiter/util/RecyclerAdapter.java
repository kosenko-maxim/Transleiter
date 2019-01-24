package aaa.bbb.ccc.transleiter.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.reactivestreams.Subscriber;

import java.util.ArrayList;

import aaa.bbb.ccc.transleiter.R;
import aaa.bbb.ccc.transleiter.data.DataBase;
import aaa.bbb.ccc.transleiter.data.ItemTransleted;
import aaa.bbb.ccc.transleiter.dialog.TextDialog;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private RealmResults<ItemTransleted> itemTransleteds;
    private Context context;
    private Realm realm;
    FragmentManager fragmentManager;


    public RecyclerAdapter(RealmResults<ItemTransleted> itemTransleteds, Context context, FragmentManager fragmentManager) {
        this.itemTransleteds = itemTransleteds;
        this.context = context;
        this.fragmentManager = fragmentManager;
        realm = Realm.getDefaultInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_recycler_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        //if (itemTransleteds.isLoaded()) {
            ItemTransleted student = itemTransleteds.get(itemTransleteds.size() - 1 - i);
            Bitmap photoBitmap = BitmapFactory.decodeByteArray(student.getFlagByte(), 0, student.getFlagByte().length);
            viewHolder.photo.setImageBitmap(photoBitmap);
            if (student.getTransletedText().length() > 11) {
                String value = student.getTransletedText().substring(0, 8) + "...";
                viewHolder.name.setText(value);
            } else viewHolder.name.setText(student.getTransletedText());
            DataBase dataBase = new DataBase();
            viewHolder.linearLayout.setOnClickListener(v -> {
                TextDialog dialog = new TextDialog();
                dialog.binData(dataBase.getDataBaseList().get(itemTransleteds.size() - 1 - i).getTransletedText());
                dialog.show(fragmentManager, "");
            });
       // }
/*
        Observable.from(arrayList).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if(s.contains("https:/")) {
                    System.out.println("url is " + s);
                    list.add(new ImageModel(s));
                }
            }

        });*/
    }


    @Override
    public int getItemCount() {
        return itemTransleteds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView name;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.recyclerImage);
            name = itemView.findViewById(R.id.recyclerText);
            linearLayout = itemView.findViewById(R.id.recycler_item);
        }
    }

   /* public void removeItem(int position) {
        itemTransleteds.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemTransleteds.size());
    }*/
}
