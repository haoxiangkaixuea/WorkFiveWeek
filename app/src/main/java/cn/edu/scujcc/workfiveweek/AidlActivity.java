package cn.edu.scujcc.workfiveweek;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class AidlActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn;
    private EditText codeEt;
    private EditText nameEt;
    private Button addBtn;
    private Button getBtn;
    private TextView studentListText;
    private IAIDLInterface proxy;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxy = IAIDLInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        startBtn = findViewById(R.id.btn_start);
        codeEt = findViewById(R.id.et_code);
        nameEt = findViewById(R.id.et_name);
        addBtn = findViewById(R.id.btn_add);
        getBtn = findViewById(R.id.btn_get);
        studentListText = findViewById(R.id.text_student_list);
        initListener();
    }

    private void initListener() {
        startBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        getBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                bindService(new Intent(this, RemoteService.class), connection, BIND_AUTO_CREATE);
                break;

            case R.id.btn_add:
                int code = Integer.valueOf(codeEt.getText().toString().trim());
                String name = nameEt.getText().toString().trim();
                try {
                    proxy.setStudent(new Student(code, name));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btn_get:
                try {
                    List<Student> students = proxy.getStudent();
                    if (students != null && students.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (Student student : students) {
                            sb.append(student.toString());
                            sb.append("\n");
                        }
                        studentListText.setText(sb.toString());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            default:
                break;
        }
    }
}
