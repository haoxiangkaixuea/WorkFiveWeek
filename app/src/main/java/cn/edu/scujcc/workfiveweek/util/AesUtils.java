package cn.edu.scujcc.workfiveweek.util;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import cn.edu.scujcc.workfiveweek.constant.Constants;

/**
 * 安卓端AES加密
 *
 * @author Administrator
 */
public class AesUtils {
    /**
     * 向量
     */
    private static String ivParameter = "1234567890123456";
    private static AesUtils instance = null;
    /**
     *     加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     */
    private String sKey;

    private AesUtils(String sKey) {
        this.sKey = sKey;
    }

    public static AesUtils getInstance(String sKey) {
        if (instance == null) {
            instance = new AesUtils(sKey);
        }
        return instance;
    }

    /**
     * 加密
     */
    public static String encrypt(String encData, String secretKey) throws Exception {

        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != Constants.LENGTH) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skyeSpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skyeSpec, iv);
        byte[] encrypted = cipher.doFinal(encData.getBytes(StandardCharsets.UTF_8));
        // 此处使用BASE64做转码。（处于android.util包）
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * 解密
     */
    public static String decrypt(String sSrc, String key) {
        try {
            byte[] raw = key.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skyeSpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skyeSpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 加密
     */
    public String encrypt(String sSrc) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = sKey.getBytes();
        SecretKeySpec skyeSpec = new SecretKeySpec(raw, "AES");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skyeSpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        // 此处使用BASE64做转码。
        return Base64.encodeToString(encrypted, Base64.DEFAULT);
    }

    /**
     * 解密
     */
    public String decrypt(String sSrc) {
        try {
            byte[] raw = sKey.getBytes(StandardCharsets.US_ASCII);
            SecretKeySpec skyeSpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skyeSpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            return null;
        }
    }
}
