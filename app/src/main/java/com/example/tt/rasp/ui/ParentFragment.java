package com.example.tt.rasp.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tt.rasp.ExcelUtil;
import com.example.tt.rasp.MyLoader;
import com.example.tt.rasp.R;
import com.example.tt.rasp.model.Constants;
import com.example.tt.rasp.model.EdDay;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by tt on 14.10.17.
 */

public class ParentFragment extends Fragment {
    public static final String TAG = ParentFragment.class.getName();
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private static final int REQUEST_EXTERNAL_STORAGE = 1;


    private final static int LOADER_ID = 2;


    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.pb)
    ProgressBar pb;

    @BindView(R.id.refresh_button)
    Button btnRefresh;

    private Realm mRealm;
    private ExcelUtil mEu;


    public static ParentFragment newInstance() {
        ParentFragment fragment = new ParentFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEu = new ExcelUtil();

        verifyStoragePermissions(getActivity());
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.parent_fragment, container, false);
        verifyStoragePermissions(getActivity());

        ButterKnife.bind(this,v);
        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        mViewPager.setCurrentItem(mEu.getCurrentDay() - 2);
        setHasOptionsMenu(true);


        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.open_xlsx) {
            File file = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + "/rasp.xlsx");
            Intent intent = new Intent();
            intent.setAction(android.content.Intent.ACTION_VIEW);
            String mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(".XLSX");
            intent.setDataAndType(Uri.fromFile(file), mime);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        loadSchedule(false);
    }

    private void loadSchedule(boolean restart) {
        LoaderManager.LoaderCallbacks<String> callbacks = new ParentFragment.RequestCallback();
        if (restart) {
            getActivity().getSupportLoaderManager().restartLoader(LOADER_ID, Bundle.EMPTY, callbacks);
        } else {
            getActivity().getSupportLoaderManager().initLoader(LOADER_ID, Bundle.EMPTY, callbacks);
        }
    }

    private class RequestCallback implements LoaderManager.LoaderCallbacks<String> {

        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new MyLoader(getActivity());
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            File file = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + "/rasp.xlsx");
            if (file.exists()) {
                Toast.makeText(getActivity(), "file downloaded", Toast.LENGTH_SHORT).show();
                btnRefresh.setEnabled(true);
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    }

    @OnClick(R.id.refresh_button)
    public void onLookBtnClick() {
        new MyTask().execute();
    }



    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btnRefresh.setEnabled(false);
            pb.setVisibility(View.GONE);
        }


        @Override
        protected Void doInBackground(Void... params) {
            File file = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + "/rasp.xlsx");
            try {
                mEu.readFromExcel(file.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.d("myLog", "" + mRealm.where(EdDay.class)
                    .equalTo("day", Constants.weekDay.get(mEu.getCurrentDay())).findAll());
            Log.d("MyLog", "mRealm open");
            getChildFragmentManager().findFragmentByTag(ChildFragment.TAG);
//            mAdapter.notifyDataSetChanged();
//
//            tvDay.setVisibility(View.VISIBLE);
//            pbCentral.setVisibility(View.GONE);
//            mRecyclerView.setVisibility(View.VISIBLE);
            btnRefresh.setEnabled(true);
            pb.setVisibility(View.VISIBLE);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }




}
