package cn.edu.scujcc.workfiveweek.util;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {

    //明文
    String source = "zachary，加油吧";
    //生成的公钥
    private String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKqs8+U6gJAzmJQeu2IiAttobx67DUs5hYu95Xrc/tWoOu12W+osarqvLNOohaqhoogHj6T+/NUEZxR77sXfm5WZ1lO3RjPjGO9w2Q9SZC6J9mUpwJytIunwEwR8y+e2FIotwc/fbRNzQFYIVJdUBIZkoEBwux3okZskxxrBsvyQIDAQAB";
    //生成的私钥
    private String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIqqzz5TqAkDOYlB67YiIC22hvHrsNSzmFi73letz+1ag67XZb6ixquq8s06iFqqGiiAePpP781QRnFHvuxd+blZnWU7dGM+MY73DZD1JkLon2ZSnAnK0i6fATBHzL57YUii3Bz99tE3NAVghUl1QEhmSgQHC7HeiRmyTHGsGy/JAgMBAAECgYAXgmQGdhpsBL7xdVqoE1sPRP3V8BaXyScQDDHi/ZXd8NWYg+49Bs3V9vKZNs49SM+MhFN+ZKUMUwrOU9KbskcPFDpQFvMIXQYeRxhyMygBR5ZsiFsnp6hpjxY7IZceQDZLAguL+JfVvBQV4sHBtWHo1wtdZ1qmX6pN6gyAa5JB3QJBANQ9kp6KbxpUr2IGWleaeArBgBE4CKu8uWqHW5XTX71AMAK5ZYrq8gWRQwu5D0rZ/GdZLDnwm+6+NyqDB7xOUZsCQQCnQei/ofgCcX+61/N2WZPm7a3hjOxvhAXc826hkE9vtfoisRrXe7OakKxgpg/WZ1oBYNqwDQOuexszhOGhCHxrAkBR7wMvGRoS/CZInVM7BnLZFCIwg4U1Z0HdEiwVBuiq0qC2LIQ6wMB1zcIoQGTa7JQ4AYDFTVGlNOFvE+5kj4eJAkEAgEV+z4DTKGSNFelKMSiv0jnT0Zf3N+rjaDlVThjToxPH2tVChaG78z0ixhh1KvQmRcpWzQ+eFDEbgl5Vf993MwJAASssnHLpAwv3D6DjK6lphMAPexZFHMHYWy9C0S4KHcf+gTGfXT2qPh8gLysAvS1+D1YFmEy9zMS8X77dHMLiTA==";
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public void test() throws Exception {

        // 初始化获得公钥、私钥
        publicKey = RSAUtils.loadPublicKey(publicKeyStr);
        privateKey = RSAUtils.loadPrivateKey(privateKeyStr);

        //加密
        byte[] encryptByte = RSAUtils.encryptDataPublic(source.getBytes(), publicKey);

        //传输数据：加密
        //String afterencrypt = Base64Utils.encode(encryptByte);
        //传输数据：解密
        //byte[] encryptContent = Base64Utils.decode(afterencrypt);

        //解密
        byte[] decryptByte = RSAUtils.decryptDataPrivate(encryptByte, privateKey);

        //获得明文
        String decryptStr = new String(decryptByte);
        System.out.print(decryptStr);

        //私钥加密、公钥解密
        encryptByte = RSAUtils.encryptDataPrivate(source.getBytes(), privateKey);
        decryptByte = RSAUtils.decryptDataPublic(encryptByte, publicKey);

        decryptStr = new String(decryptByte);
        System.out.print(decryptStr);

    }
}