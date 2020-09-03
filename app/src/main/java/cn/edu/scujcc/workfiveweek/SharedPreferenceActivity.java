package cn.edu.scujcc.workfiveweek;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class SharedPreferenceActivity extends AppCompatActivity {
    private static final String TAG = "SecretSharedPreference";
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSubmit;
    private TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preference);

        inputUsername = findViewById(R.id.editText_username);
        inputPassword = findViewById(R.id.editText_password);
        tvShow = findViewById(R.id.show_message);
        btnSubmit = findViewById(R.id.button_submit);
        btnSubmit.setOnClickListener(v -> {
            saveInSecurityPreference();
        });
    }

    private void saveInSecurityPreference() {
        SecuritySharedPreference securitySharedPreference = new SecuritySharedPreference(getApplicationContext(), "security_prefs", Context.MODE_PRIVATE);
        SecuritySharedPreference.SecurityEditor securityEditor = securitySharedPreference.edit();
        securityEditor.putString("username", inputUsername.getText().toString());
        securityEditor.putString("password", inputPassword.getText().toString());
        securityEditor.apply();
        Map a = securitySharedPreference.getAll();
        Log.d(TAG, String.valueOf(a));
        tvShow.setText(String.valueOf(a));
        SecuritySharedPreference.clear(getApplicationContext(), "security_prefs");
    }
}