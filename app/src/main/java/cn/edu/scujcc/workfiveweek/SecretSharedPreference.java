package cn.edu.scujcc.workfiveweek;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import cn.edu.scujcc.workfiveweek.sp.SecuritySharedPreference;

public class SecretSharedPreference extends AppCompatActivity {
    private EditText inputUsername;
    private EditText inputPassword;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_shared_preference);

        inputUsername = findViewById(R.id.editText_username);
        inputPassword = findViewById(R.id.editText_password);
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
    }
}