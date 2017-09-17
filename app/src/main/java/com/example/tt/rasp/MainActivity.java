package com.example.tt.rasp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private final static int MESSAGE_CODE = 1;
    private final static int LOADER_ID = 2;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    @BindView(R.id.look_button)
    TextView btnHelloWorld;
    @BindView(R.id.text_view_2)
    TextView tvSchedule;
    @BindView(R.id.toolbar)
    Toolbar tbOne;
    @BindView(R.id.pb)
    ProgressBar pbCentral;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Handler handler;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_CODE:
                        pbCentral.setVisibility(View.GONE);
                        tvSchedule.setText(msg.obj.toString());
                        break;
                }
            }
        };
        btnHelloWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnHelloWorld.setVisibility(View.GONE);
                pbCentral.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        File file = new File(Environment
                                .getExternalStorageDirectory().toString()
                                + "/rasp.xlsx");
                        try {
                            EdDay result = ExcelUtil.readFromExcel(file.toString());
                            Message msg = handler.obtainMessage(MESSAGE_CODE, result);
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        setSupportActionBar(tbOne);
        Log.d("myLog", Environment
                .getExternalStorageDirectory().toString());
        verifyStoragePermissions(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSchedule(false);
            }
        });
    }

    private void loadSchedule(boolean restart) {
        LoaderManager.LoaderCallbacks<String> callbacks = new MyCallbacks();
        if (restart) {
            getSupportLoaderManager().restartLoader(LOADER_ID, Bundle.EMPTY, callbacks);
        } else {
            getSupportLoaderManager().initLoader(LOADER_ID, Bundle.EMPTY, callbacks);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class MyCallbacks implements LoaderManager.LoaderCallbacks<String> {

        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return new MyLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            File file = new File(Environment
                    .getExternalStorageDirectory().toString()
                    + "/rasp.xlsx");
            if (file.exists()) {
                Toast.makeText(getApplicationContext(), "file downloaded", Toast.LENGTH_SHORT).show();
                btnHelloWorld.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    }


}

