package me.eleme.anubis.sdk.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author liuzhiyong
 */

public class Sha256Util {

    private static final Logger logger = LoggerFactory.getLogger(Sha256Util.class);
    /**
     * 利用java原生的类实现SHA256加密
     * @param str 传入原始字符串
     * @return sha256处理后字符串
     */
    public static String getsha256(String str) {
        MessageDigest messageDigest;
        String encodestr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            encodestr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.info("getSHA256 NoSuchAlgorithmException:{}", e.getMessage());
        }
        return encodestr;
    }

    /**
     * 将bytes转为16进制
     */
    private static String byte2Hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        String temp;
        for (byte aByte : bytes) {
            temp = Integer.toHexString(aByte & 0xFF);
            if (temp.length() == 1) {
                // 只有一位则加0补到两位
                stringBuilder.append("0");
            }
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }
}
