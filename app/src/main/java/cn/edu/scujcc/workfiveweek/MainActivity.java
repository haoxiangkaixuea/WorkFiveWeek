package cn.edu.scujcc.workfiveweek;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.scujcc.workfiveweek.util.AesUtils;
import cn.edu.scujcc.workfiveweek.util.LogUtils;
import cn.edu.scujcc.workfiveweek.util.Md5Utils;

/**
 * @author Administrator
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Md5Utils md5Utils = new Md5Utils();
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
        Button btnMd5 = findViewById(R.id.button_md5);
        Button btnAes = findViewById(R.id.button_aes);
        Button btnRsa = findViewById(R.id.button_rsa);
        Button btnSecretSharedPreference = findViewById(R.id.button_SecretSharedPreference);

        btnSecretSharedPreference.setOnClickListener(v -> {
            Intent startSpIntent = new Intent(MainActivity.this, SharedPreferenceActivity.class);
            startActivity(startSpIntent);
        });
        btnAidl.setOnClickListener(v -> {
            Intent startAidlIntent = new Intent(MainActivity.this, AidlActivity.class);
            startActivity(startAidlIntent);
        });
        btnMd5.setOnClickListener(v -> setMd5());
        btnAes.setOnClickListener(v -> {
            try {
                setAes();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        btnRsa.setOnClickListener(v -> {
            Intent startRsaIntent = new Intent(MainActivity.this, RsaActivity.class);
            startActivity(startRsaIntent);
        });
    }

    private void setAes() throws Exception {
        String key = "1234567890123456";
        //加密明文--> 密文
        String aesLock = getResources().getString(R.string.lock_before);
        aesLock = AesUtils.encrypt(aesLock, key);
        LogUtils.d(TAG, getResources().getString(R.string.lock_after) + aesLock);
        tvLock.setText(aesLock);

        String aesUnlock = AesUtils.decrypt(aesLock, key);
        LogUtils.d(TAG, getResources().getString(R.string.unlock_after) + aesUnlock);
        tvUnlock.setText(aesUnlock);
    }

    private void setMd5() {
        String getData = inputLock.getText().toString().trim();
        LogUtils.d(TAG, getResources().getString(R.string.lock_before) + getData);
        String md5 = Md5Utils.string2Md5(getData);
        LogUtils.d(TAG, getResources().getString(R.string.md5_after) + md5);
        String md5Lock = md5Utils.encrypt(getData);
        LogUtils.d(TAG, getResources().getString(R.string.lock_after) + md5Lock);
        tvLock.setText(md5Lock);
        String md5Unlock = md5Utils.decode(md5Lock);
        tvUnlock.setText(md5Unlock);
        LogUtils.d(TAG, getResources().getString(R.string.public_unlock) + md5Unlock);
    }
}