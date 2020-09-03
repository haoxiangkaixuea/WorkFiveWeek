package cn.edu.scujcc.workfiveweek;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.scujcc.workfiveweek.util.AesUtils;
import cn.edu.scujcc.workfiveweek.util.MD5Utils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MD5Utils md5Utils = new MD5Utils();
    private TextView tvLock, tvUnlock;
    private EditText inputLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLock = findViewById(R.id.text_lock);
        tvUnlock = findViewById(R.id.text_unlock);
        inputLock = findViewById(R.id.editText_input);
        Button btnAidl = findViewById(R.id.button_aidl);
        Button btnMD5 = findViewById(R.id.button_md5);
        Button btnAES = findViewById(R.id.button_aes);
        Button btnRSA = findViewById(R.id.button_rsa);
        Button btnSecretSharedPreference = findViewById(R.id.button_SecretSharedPreference);
        btnSecretSharedPreference.setOnClickListener(v -> {
            Intent startSpIntent = new Intent(MainActivity.this, SharedPreferenceActivity.class);
            startActivity(startSpIntent);
        });
        btnAidl.setOnClickListener(v -> {
            Intent startAidlIntent = new Intent(MainActivity.this, AidlActivity.class);
            startActivity(startAidlIntent);
        });
        btnMD5.setOnClickListener(v -> setMD5());
        btnAES.setOnClickListener(v -> {
            try {
                setAES();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnRSA.setOnClickListener(v -> {
            Intent startRsaIntent = new Intent(MainActivity.this, RsaActivity.class);
            startActivity(startRsaIntent);
        });
    }

    private void setAES() throws Exception {
        String key = "1234567890123456";
        //加密明文--> 密文
        String aesLock = "这是加密前的数据";
        aesLock = AesUtils.encrypt(aesLock, key);
        Log.d(TAG, "这是加密后的数据" + aesLock);
        tvLock.setText(aesLock);

        String aesUnlock = "这是加密前的数据";
        aesUnlock = AesUtils.decrypt(aesLock, key);
        Log.d(TAG, "这是解密后的数据" + aesUnlock);
        tvUnlock.setText(aesUnlock);
    }

    private void setMD5() {
        String getData = inputLock.getText().toString().trim();
        Log.d(TAG, "这是加密前的数据" + getData);
        String md5 = MD5Utils.string2MD5(getData);
        Log.d(TAG, "这是MD5后的数据" + md5);
        String md5Lock = md5Utils.encrypt(getData);
        Log.d(TAG, "这是加密后的数据" + md5Lock);
        tvLock.setText(md5Lock);
        String md5Unlock = md5Utils.decode(md5Lock);
        tvUnlock.setText(md5Unlock);
        Log.d(TAG, "这是解密后的数据" + md5Unlock);
    }
}