package cn.edu.scujcc.workfiveweek;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.scujcc.workfiveweek.util.MD5Util;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MD5Util md5Util = new MD5Util();
    private String md5Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        md5Test = "Hello World!!!";
        Log.d(TAG, "这是加密前的数据" + md5Test);
        String md5 = md5Util.encrypt(md5Test);
        Log.d(TAG, "这是加密后的数据" + md5);
    }
}