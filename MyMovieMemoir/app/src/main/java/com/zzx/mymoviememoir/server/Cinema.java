package com.zzx.mymoviememoir.server;

public class Cinema {

    private int cinid;
    private String cinname;
    private String cinpostcode;

    public Cinema(int cinid, String cinname, String cinpostcode) {
        this.cinid = cinid;
        this.cinname = cinname;
        this.cinpostcode = cinpostcode;
    }

    public Cinema(int cinid) {
        this.cinid = cinid;
    }

    public Cinema(int cinid, String cinpostcode) {
        this.cinid = cinid;
        this.cinpostcode = cinpostcode;
    }

    public int getCinid() {
        return cinid;
    }

    public String getCinname() {
        return cinname;
    }

    public String getCinpostcode() {
        return cinpostcode;
    }

}
