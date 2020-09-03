package cn.edu.scujcc.workfiveweek;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import cn.edu.scujcc.workfiveweek.util.Base64Utils;
import cn.edu.scujcc.workfiveweek.util.LogUtils;
import cn.edu.scujcc.workfiveweek.util.RSAUtils;

public class RsaActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RsaActivity";
    private TextView tvName;
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;
    private String text;
    private String entrySlr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsa);

        tvName = findViewById(R.id.tv_name);
        Button btnPrivateLock = findViewById(R.id.button3);
        Button btnPrivateUnlock = findViewById(R.id.button2);
        Button btnPublicLock = findViewById(R.id.button1);
        Button btnPublicUnlock = findViewById(R.id.button4);

        btnPublicUnlock.setOnClickListener(this);
        btnPublicLock.setOnClickListener(this);
        btnPrivateLock.setOnClickListener(this);
        btnPrivateUnlock.setOnClickListener(this);
        textRSA();
    }

    public void textRSA() {
        text = getResources().getString(R.string.test_RSA);

        KeyPair keyPair = RSAUtils.generateRSAKeyPair(RSAUtils.DEFAULT_KEY_SIZE);
        // 公钥
        publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        privateKey = (RSAPrivateKey) keyPair.getPrivate();
    }

    @Override
    public void onClick(View view) {
        {
            switch (view.getId()) {
                case R.id.button1:
                    //公钥加密
                    byte[] encryptBytes = new byte[0];
                    try {
                        encryptBytes = RSAUtils.encryptByPublicKeyForSpilt(text.getBytes(), publicKey.getEncoded());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    entrySlr = Base64Utils.encode(encryptBytes);
                    LogUtils.d(TAG, getResources().getString(R.string.lock_after) + entrySlr);
                    tvName.setText(entrySlr);
                    break;
                case R.id.button2:
                    //私钥解密
                    byte[] decryptBytes = new byte[0];
                    try {
                        decryptBytes = RSAUtils.decryptByPrivateKeyForSpilt(Base64Utils.decode(entrySlr), privateKey.getEncoded());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    String decryStr = new String(decryptBytes);
                    LogUtils.d(TAG, getResources().getString(R.string.unlock_after) + decryStr);
                    tvName.setText(decryStr);
                    break;
                case R.id.button3:
                    //私钥加密
                    byte[] encryptBytes2 = new byte[0];
                    try {
                        encryptBytes2 = RSAUtils.encryptByPrivateKeyForSpilt(text.getBytes(), privateKey.getEncoded());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    entrySlr = Base64Utils.encode(encryptBytes2);
                    LogUtils.d(TAG, getResources().getString(R.string.lock_after) + entrySlr);
                    tvName.setText(entrySlr);
                    break;
                case R.id.button4:
                    //公钥解密
                    byte[] decryptBytes2 = new byte[0];
                    try {
                        decryptBytes2 = RSAUtils.decryptByPublicKeyForSpilt(Base64Utils.decode(entrySlr), publicKey.getEncoded());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    decryStr = new String(decryptBytes2);
                    LogUtils.d(TAG, getResources().getString(R.string.unlock_after) + decryStr);
                    tvName.setText(decryStr);
                    break;
                default:
            }
        }
    }
}