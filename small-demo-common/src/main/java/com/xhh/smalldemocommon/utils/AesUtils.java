package com.xhh.smalldemocommon.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesUtils {
    /**加密的Key*/
    private static String  AESKEY = "+hKt3Kr7bf/cxfabc/bLbA==";
    /**同意是用的编码*/
    private static String AESCODE  = "UTF-8";

    public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding))
                finalKey[i++%16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**AES 解密
     * data : 待解密的数据
     * */
    public static String decrpt(String data)  throws Exception {
        // Decrypt
        final Cipher decryptCipher = Cipher.getInstance("AES");
        decryptCipher.init(Cipher.DECRYPT_MODE, generateMySQLAESKey(AESKEY, AESCODE));
        return new String(decryptCipher.doFinal(Hex.decodeHex(data.toCharArray())));
    }

    /**AES加密
     * data : 待加密 的数据
     * */
    public static String encrpt(String data)  throws Exception {
        // Encrypt
        final Cipher encryptCipher = Cipher.getInstance("AES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, generateMySQLAESKey(AESKEY, AESCODE));
        char[] code=  Hex.encodeHex(encryptCipher.doFinal(data.getBytes(AESCODE)));
        StringBuilder builder = new StringBuilder();
        for(char d:code) {
            builder.append(d);
        }
        String string = builder.toString();
        return string;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrpt("8107"));
    }

    /**
     * 生成AES秘钥
     * @param length
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateKey(int length) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //设置密钥长度
        kgen.init(128);
        //生成密钥
        SecretKey skey = kgen.generateKey();
        //返回密钥的二进制编码
        return Base64.getEncoder().encodeToString(skey.getEncoded());
    }

    /**
     * 加密
     * @param content
     * @param keyStr
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String keyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");

        cipher.init(Cipher.ENCRYPT_MODE, key);
        //将加密并编码后的内容解码成字节数组
        return Base64.getEncoder().encodeToString(cipher.doFinal(content.getBytes()));
    }

    public static String decrypt(String encodeStr, String keyStr) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] keyBytes = Base64.getDecoder().decode(keyStr);
        byte[] encodeBytes = Base64.getDecoder().decode(encodeStr);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        //初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(encodeBytes));
    }
    
}
