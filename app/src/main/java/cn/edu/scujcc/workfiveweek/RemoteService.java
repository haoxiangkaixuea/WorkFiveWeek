package cn.edu.scujcc.workfiveweek;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RemoteService extends Service {

    private List<Student> studentList = new ArrayList<>();
    IAIDLInterface.Stub stub = new IAIDLInterface.Stub() {
        @Override
        public List<Student> getStudent() throws RemoteException {
            return studentList;
        }

        @Override
        public void setStudent(Student student) throws RemoteException {
            studentList.add(student);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
