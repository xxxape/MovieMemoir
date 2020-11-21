package com.zzx.mymoviememoir.user;

public class UserInfo {

    private static String perId;
    private static String perFname;

    public static String getPerId() {
        return perId;
    }

    public static void setPerId(String perId) {
        UserInfo.perId = perId;
    }

    public static String getPerFname() {
        return perFname;
    }

    public static void setPerFname(String perFname) {
        UserInfo.perFname = perFname;
    }
}
