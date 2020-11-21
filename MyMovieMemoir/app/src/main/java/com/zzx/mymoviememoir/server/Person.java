package com.zzx.mymoviememoir.server;

public class Person {

    private int perid;
    private String perfname;
    private String perlname;
    private String pergender;
    private String perdob;
    private String perstno;
    private String perstname;
    private String perstate;
    private String perpostcode;
    private Credential credid;

    public Person(int perid) {
        this.perid = perid;
    }

    public Person(){ }

    public void setPerid(int perid) {
        this.perid = perid;
    }

    public void setPerfname(String perfname) {
        this.perfname = perfname;
    }

    public void setPerlname(String perlname) {
        this.perlname = perlname;
    }

    public void setPergender(String pergender) {
        this.pergender = pergender;
    }

    public void setPerdob(String perdob) {
        this.perdob = perdob;
    }

    public void setPerstno(String perstno) {
        this.perstno = perstno;
    }

    public void setPerstname(String perstname) {
        this.perstname = perstname;
    }

    public void setPerstate(String perstate) {
        this.perstate = perstate;
    }

    public void setPerpostcode(String perpostcode) {
        this.perpostcode = perpostcode;
    }

    public void setCredid(int id, String username, String password, String signupdate) {
        this.credid = new Credential(id, username, password, signupdate);
    }

    public Credential getCred() {
        return this.credid;
    }
}
