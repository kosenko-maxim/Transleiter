package aaa.bbb.ccc.transleiter.screen.history;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import aaa.bbb.ccc.transleiter.R;
import aaa.bbb.ccc.transleiter.data.DataBase;
import aaa.bbb.ccc.transleiter.util.RecyclerAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

public class HistoryFragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.up)
    ImageView upButton;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    DataBase dataBase;
    @BindView(R.id.textClear)
    TextView textClear;
    Realm realm;


    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        dataBase = new DataBase();
        recyclerView.setNestedScrollingEnabled(false);
        if (dataBase.getDataBaseList().size() > 0) textClear.setText("");

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        RecyclerAdapter adapter = new RecyclerAdapter(dataBase.getDataBaseList(), getContext(), getFragmentManager());
      realm = Realm.getDefaultInstance();

        recyclerView.setAdapter(adapter);
        upButton.setOnClickListener(v ->scrollView.smoothScrollTo(0, 0));
        upButton.setVisibility(View.GONE);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (scrollView.getScrollY() > 10) {
                upButton.setVisibility(View.VISIBLE);
            } else {
                upButton.setVisibility(View.GONE);
            }
        });
        return view;
    }

}
