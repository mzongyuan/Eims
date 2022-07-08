package com.eims.common;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description MD5工具类
 * @Auth zongyuan.ma
 * @Date 2022/7/7 10:08
 * @Version V 1.0.0
 */
public class Md5Utils {

    /**
     * 将字符串转成MD5值
     *
     * @param string
     * @return
     */
    public static String md5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String MD5(String s) {
        try {
            StringBuilder sb = new StringBuilder();
            byte[] data = s.getBytes("utf-8");
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            MD5.update(data);
            data = MD5.digest();
            for (int i = 0; i < data.length; i++) {
                sb.append(HEX_DIGITS[(data[i] & 0xf0) >> 4] + "" + HEX_DIGITS[data[i] & 0xf]);
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
