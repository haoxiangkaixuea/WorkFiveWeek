package cn.edu.scujcc.workfiveweek.util;

import android.util.Log;

/**
 * @author Administrator
 */
public class LogUtils {

    /**
     * 默认的TAG标签名称
     */

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }
}
