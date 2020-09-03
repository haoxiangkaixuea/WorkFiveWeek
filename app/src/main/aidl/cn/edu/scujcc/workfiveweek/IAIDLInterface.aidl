// IAIDLInterface.aidl
package cn.edu.scujcc.workfiveweek;

// Declare any non-default types here with import statements
import cn.edu.scujcc.workfiveweek.Student;

interface IAIDLInterface {
    List<Student> getStudent();
    void setStudent(in Student student);
}
