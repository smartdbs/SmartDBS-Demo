package com.zkteco.dbs.common.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;

/**
 * AES工具类
 */
public class AES256Utils {
    // 密钥
    public static final String SECURITY_KEY = "c09eb7463827cda245f3a398bd453c42";
    private static final String CHARSET = "utf-8";
    /**
     * 设置偏移量
     * 本例采用ECB加密模式，不需要设置偏移量
     */
    private static final int offset = 0;
    private static final String transformation = "AES/ECB/PKCS5Padding";
    private static final String algorithm = "AES";

    /****
     * 使用默认密钥进行 AES256加密，输出 base64字符串加密结果
     *
     * @param content 加密内容
     * @return  加密后的base64字符串
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:45
     * @since 1.0.0
     */
    public static String encryptToBase64(String content) {
        return encryptToBase64(content, SECURITY_KEY);
    }

    /****
     * 使用指定key进行AES256加密，输出 base64字符串加密结果
     *
     * @param content 加密内容
     * @return 加密后的base64字符串
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:45
     * @since 1.0.0
     */
    public static String encryptToBase64(String content, String key) {
        byte[] result = encrypt(content, key);
        return Base64.getEncoder().encodeToString(result);
    }


    /****
     * 解密  encryptToBase64
     *
     * @param content  加密后的base64
     * @return 加密前的明文
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:47
     * @since 1.0.0
     */
    public static String decryptByBase64(String content) {
        return decryptByBase64(content, SECURITY_KEY);
    }


    /****
     * 解密  encryptToBase64
     *
     * @param content  加密后的base64
     * @param key  加密密钥
     * @return 加密前的明文
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:47
     * @since 1.0.0
     */
    public static String decryptByBase64(String content, String key) {
        try {
            byte[] contentByte =  Base64.getDecoder().decode(content);
            return decrypt(contentByte, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 使用默认密钥进行 AES256加密，输出 16进制字符串加密字符串
     *
     * @param content 加密内容
     * @return  加密后的 16进制字符串
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:45
     * @since 1.0.0
     */
    public static String encryptToHex(String content) {
        return encryptToHex(content, SECURITY_KEY);
    }


    /****
     * 使用指定key进行AES256加密，输出 16进制字符串加密字符串
     *
     * @param content 加密内容
     * @return 加密后的 16进制字符串
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:45
     * @since 1.0.0
     */
    public static String encryptToHex(String content, String key) {
        byte[] result = encrypt(content, key);
        return toHex(result);
    }


    /****
     * 解密  encryptToHex
     *
     * @param content  加密后的 16进制字符串
     * @return 加密前的明文
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:47
     * @since 1.0.0
     */
    public static String decryptByHex(String content) { return decryptByHex(content, SECURITY_KEY); }


    /****
     * 解密  encryptToHex
     *
     * @param content  加密后的 16进制字符串
     * @param key  加密密钥
     * @return 加密前的明文
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:47
     * @since 1.0.0
     */
    public static String decryptByHex(String content, String key) {
        byte[] contentByte = fromHex(content);
        return decrypt(contentByte, key);
    }


    /****
     * encrypt 加密
     *
     * @param content 需要加密的内容
     * @param key   加密密钥
     * @return byte[]
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:16
     * @since 1.0.0
     */
    private static byte[] encrypt(String content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            byte[] byteContent = content.getBytes(CHARSET);
            cipher.init(Cipher.ENCRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 解密
     *
     * @param content 需要解密的内容
     * @param key 加密密钥
     * @return java.lang.String
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:07
     * @since 1.0.0
     */
    private static String decrypt(byte[] content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(content);
            return new String(result, "UTF-8"); // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /****
     * 使用默认密钥 加密二进制流
     *
     * @param content 需要加密的内容
     * @return byte[]
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:16
     * @since 1.0.0
     */
    public static byte[] encryptByStream(byte [] content) {
       return  encryptByStream(content,SECURITY_KEY);
    }

    /****
     * 使用指定密钥 加密二进制流
     *
     * @param content 需要加密的内容
     * @param key   加密密钥
     * @return byte[]
     * @throws
     * @author howard.liu
     * @date 2020/12/1 14:16
     * @since 1.0.0
     */
    public static byte[] encryptByStream(byte [] content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 使用默认密钥 解密二进制流
     *
     * @param content   需要加密的内容
     * @return byte[]
     * @throws
     * @author howard.liu
     * @date 2020/12/1 19:12
     * @since 1.0.0
     */
    public  static byte[]   decryptByStream(byte[] content) {
        return decryptByStream(content,SECURITY_KEY);
    }


    /****
     * 使用指定密钥 解密二进制流
     *
     * @param content   需要加密的内容
     * @param key   加密密钥
     * @return byte[]
     * @throws
     * @author howard.liu
     * @date 2020/12/1 19:12
     * @since 1.0.0
     */
    public  static byte[]   decryptByStream(byte[] content, String key) {
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), algorithm);
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, skey);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 解密
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 二进制字节转十六进制字符串
     *
     * @param array 二进制字节数组
     * @return java.lang.String 十六进制字符串
     * @author howard.liu
     * @date 2020/9/22 10:32
     * @since 1.0.0
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex 十六进制字符串
     * @return byte[] 二进制字节
     * @author Larry
     * @date 2020-06-12 16:10
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }





    public static void main(String[] args) {
//        String content = "李振辉";
//        String s = encryptToBase64(content, "c09eb7463827cda245f3a398bd453c42");
        String s2 = decryptByBase64("4f5UqNioCWzq3SPZhVnb1hvwK9rG6PIFmdI5xMEWjOBp/28JhgrixtNvtVZFEhk8o73/JRJ0haVzejMRBEi4L0kYY1jMAYK5nY74iPNGZ9mkR3vz/JfefYmm/imvaPTudJO5Ay6KRCG4rYfcNxS5hwbVaC+BMdF0Lfwz8DT84CA=", "c09eb7463827cda245f3a398bd453c42");
//
//        String s3 = encryptToHex(content, "c09eb7463827cda245f3a398bd453c42");
//
//        String s4 = decryptByHex(s3, "c09eb7463827cda245f3a398bd453c42");
//
//        System.out.println("加密前内容:  " + content);
//
//        System.out.println("加密后base64:  " + s);
        System.out.println("base64解密后:   " + s2);
//
//        System.out.println("加密后hex:   " + s3);
//        System.out.println("hex解密后:   " + s4);

    }

}