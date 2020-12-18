package com.zkteco.dbs.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * PBKDF2Utils
 *
 * @author sheldon.wu
 * @date 2020/12/2 11:14
 * @since 1.0.0
 */
public class PBKDF2Utils {

    /**
     * 算法HmacSHA256
     */
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    /**
     * 盐的长度16字节，32字符长度
     */
    public static final int SALT_BYTE_SIZE = 32 / 2;

    /**
     * 生成密文的长度 32字节，64字符长度
     */
    public static final int HASH_BIT_SIZE = 64 * 4;

    /**
     * 迭代次数
     */
    public static final int PBKDF2_ITERATIONS = 1000;


    /**
     * 对输入的密码进行验证
     *
     * @param attemptedPassword 待验证密码
     * @param encryptedPassword 密文
     * @param salt              盐值
     * @return boolean 验证结果
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt) {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * 对输入的密码进行验证
     *
     * @param attemptedPassword 待验证密码
     * @param encryptedPassword 密文
     * @param salt              盐值
     * @param iterations        迭代次数
     * @return boolean 验证结果
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt, int iterations) {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt, iterations);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }

    /**
     * 生成密文(默认迭代一千次)
     *
     * @param password 明文密码
     * @param salt     盐值(16进制)
     * @return java.lang.String 加密过后的密文
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static String getEncryptedPassword(String password, String salt) {
        return getEncryptedPassword(password, salt, PBKDF2_ITERATIONS);
    }


    /****
     * 生成密文,算法： HmacSHA256
     *
     * @param password 原密码
     * @param salt 盐 （十六进制)
     * @param iterations  迭代次数
     * @return java.lang.String
     * @throws
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static String getEncryptedPassword(String password, String salt, int iterations) {
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(password.getBytes(StandardCharsets.UTF_8), fromHex(salt), iterations);
        byte[] dk = ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
        return toHex(dk);
    }


    /**
     * 生成密文 盐值不为16进制字符串
     *
     * @param password 明文密码
     * @param salt     盐值，不为16进制字符串
     * @return java.lang.String 加密过后的密文
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static String getEncryptedPasswordToString(String password, String salt, int iterations)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), iterations, HASH_BIT_SIZE);
        SecretKeyFactory f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return toHex(f.generateSecret(spec).getEncoded());
    }

    /**
     * 通过加密的强随机数生成盐(最后转换为16进制)
     *
     * @return java.lang.String 盐值
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        return toHex(salt);
    }


    /**
     * 十六进制字符串转二进制字符串
     *
     * @param hex 十六进制字符串
     * @return byte[] 二进制字节
     * @author sheldon.wu
     * @date 2020/12/01 10:32
     * @since 1.0.0
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    /**
     * 二进制字节转十六进制字符串
     *
     * @param array 二进制字节数组
     * @return java.lang.String 十六进制字符串
     * @author sheldon.wu
     * @date 2020/12/01 10:32
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

}
