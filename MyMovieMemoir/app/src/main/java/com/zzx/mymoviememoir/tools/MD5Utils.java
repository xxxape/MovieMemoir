package com.zzx.mymoviememoir.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Hash encrypt
 */
public class MD5Utils {

    public static String stringToMD5(String plainText) {
        byte[] secretByte = null;
        try {
            secretByte = MessageDigest.getInstance("md5").digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String md5Text = new BigInteger(1, secretByte).toString(16);
        for (int i = 0; i < 32 - md5Text.length(); i++) {
            md5Text = "0" + md5Text;
        }
        return md5Text;
    }
}
