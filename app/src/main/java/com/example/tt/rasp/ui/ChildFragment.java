package com.example.tt.rasp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tt.rasp.ExcelUtil;
import com.example.tt.rasp.R;
import com.example.tt.rasp.model.Constants;
import com.example.tt.rasp.model.EdDay;
import com.example.tt.rasp.model.Lesson;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ChildFragment extends Fragment {
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    public static final String POSITION_KEY = "FragmentPositionKey";
    public static final String TAG = ChildFragment.class.getName();

    int pageNumber;


    @BindView(R.id.text_view_2)
    TextView tvDay;


    @BindView(R.id.pb)
    ProgressBar pbCentral;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private Realm mRealm;
    private ExcelUtil mEu = new ExcelUtil();
    private DataAdapter mAdapter;
    private EdDay mEdDay = new EdDay();
    private RealmList<Lesson> mRmLessons = new RealmList<>();
    private LinearLayoutManager mLayoutManager;

    public static ChildFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(ChildFragment.POSITION_KEY, position);
        ChildFragment f = new ChildFragment();
        f.setArguments(args);
        return f;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRealm = Realm.getDefaultInstance();
        pageNumber = getArguments().getInt(POSITION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.content_main, container, false);
        ButterKnife.bind(this,v);
        updateEdDay();

        mAdapter = new DataAdapter(getActivity(), mRmLessons);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity(), false));

        mRecyclerView.setAdapter(mAdapter);
        tvDay.setText(mEdDay.getDay());
        tvDay.setVisibility(View.VISIBLE);

        return v;
    }

    private void updateEdDay() {
        Log.d("myLog","page number: " + pageNumber);
        RealmResults<EdDay> query = mRealm.where(EdDay.class)
                .equalTo("day", Constants.weekDay.get(pageNumber + 2)).findAll();

        if (query.isEmpty()) {
            mEdDay.setDay(Constants.weekDay.get(pageNumber + 2));
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mEdDay = query.get(0);
            mRmLessons.clear();
            mRmLessons.addAll(mEdDay.getLessons());
        }
    }
}

