package cn.edu.scujcc.workfiveweek.util;

import java.security.MessageDigest;

/**
 * @author Administrator
 */
public class MD5Utils {
    private static final String TAG = "MD5Util";

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        LogUtils.d(TAG, "string2MD5: -------------------------");
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }


    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {
        LogUtils.e(TAG, "convertMD5: ----------------------------------------------------------");
        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);

    }

    public static String MD5(String sourceStr) {
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(sourceStr.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            StringBuffer buf = new StringBuffer();
//            for (int i = 0; i < md.length; i++) {
//                int tmp = md[i];
//                if (tmp < 0) {
//                    tmp += 256;
//                }
//                if (tmp < 16) {
//                    buf.append("0");
//                }
//                buf.append(Integer.toHexString(tmp));
//            }
            for (int b : md) {
                int tmp = b;
                if (tmp < 0) {
                    tmp += 256;
                }
                if (tmp < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(tmp));
            }
            //return buf.toString().substring(8, 24);// 16位加密
            return buf.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * encrypt译成密码
     */
    public String encrypt(String str) {
        // MD5
        String s1 = string2MD5(str);
        //加密
        String s2 = MD5(str);

        String s = str;

        LogUtils.e(TAG, "show: ------------原始：" + s);
        LogUtils.e(TAG, "show: ------------MD5后：" + string2MD5(s));
        LogUtils.e(TAG, "show: ------------加密的：" + convertMD5(s));
        LogUtils.e(TAG, "show: ------------解密的：" + convertMD5(convertMD5(s)));
        // return convertMD5(convertMD5(s));
        return convertMD5(s);
        //return string2MD5(s);
    }

    public String decode(String unlock) {
        LogUtils.e(TAG, "这是解密--------------*****---------" + convertMD5(unlock));
        return convertMD5(unlock);
    }
}
