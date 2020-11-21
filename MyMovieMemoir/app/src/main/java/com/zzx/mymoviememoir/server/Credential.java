package com.zzx.mymoviememoir.server;

public class Credential {

    private int credid;
    private String username;
    private String password;
    private String signupdate;

    public Credential(int credid, String username, String password, String signupdate) {
        this.credid = credid;
        this.username = username;
        this.password = password;
        this.signupdate = signupdate;
    }

}
