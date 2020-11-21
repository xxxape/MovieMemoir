package com.zzx.mymoviememoir.server;

public class Memoir {
    private int memoid;
    private String movname;
    private String movreldate;
    private String userwdate;
    private String userwtime;
    private String usercomment;
    private float ratingstar;
    private Person perid;
    private Cinema cinid;

    public Memoir(int memoid, String movname, String movreldate, String userwdate, String userwtime, String usercomment, float ratingstar) {
        this.memoid = memoid;
        this.movname = movname;
        this.movreldate = movreldate;
        this.userwdate = userwdate;
        this.userwtime = userwtime;
        this.usercomment = usercomment;
        this.ratingstar = ratingstar;
    }

    public Memoir() {
    }

    public void setPerid(int perid) {
        this.perid = new Person(perid);
    }

    public void setCinid(int cinid) {
        this.cinid = new Cinema(cinid);
    }

    public void setCinid(int cinid, String cinpostcode) {
        this.cinid = new Cinema(cinid, cinpostcode);
    }

    public Cinema getCinid() {
        return cinid;
    }

    public void setMemoid(int memoid) {
        this.memoid = memoid;
    }

    public String getMovname() {
        return movname;
    }

    public void setMovname(String movname) {
        this.movname = movname;
    }

    public String getMovreldate() {
        return movreldate;
    }

    public void setMovreldate(String movreldate) {
        this.movreldate = movreldate;
    }

    public String getUserwdate() {
        return userwdate;
    }

    public void setUserwdate(String userwdate) {
        this.userwdate = userwdate;
    }

    public String getUserwtime() {
        return userwtime;
    }

    public void setUserwtime(String userwtime) {
        this.userwtime = userwtime;
    }

    public String getUsercomment() {
        return usercomment;
    }

    public void setUsercomment(String usercomment) {
        this.usercomment = usercomment;
    }

    public float getRatingstar() {
        return ratingstar;
    }

    public void setRatingstar(float ratingstar) {
        this.ratingstar = ratingstar;
    }
}
