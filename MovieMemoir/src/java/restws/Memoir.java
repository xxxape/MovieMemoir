/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author xxxape
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMemoid", query = "SELECT m FROM Memoir m WHERE m.memoid = :memoid")
    , @NamedQuery(name = "Memoir.findByMovname", query = "SELECT m FROM Memoir m WHERE m.movname = :movname")
    , @NamedQuery(name = "Memoir.findByMovreldate", query = "SELECT m FROM Memoir m WHERE m.movreldate = :movreldate")
    , @NamedQuery(name = "Memoir.findByUserwdate", query = "SELECT m FROM Memoir m WHERE m.userwdate = :userwdate")
    , @NamedQuery(name = "Memoir.findByUserwtime", query = "SELECT m FROM Memoir m WHERE m.userwtime = :userwtime")
    , @NamedQuery(name = "Memoir.findByUsercomment", query = "SELECT m FROM Memoir m WHERE m.usercomment = :usercomment")
    , @NamedQuery(name = "Memoir.findByRatingstar", query = "SELECT m FROM Memoir m WHERE m.ratingstar = :ratingstar")
    /******************* Task 3d *******************/
    , @NamedQuery(name = "Memoir.findByMovienameAndCinemaname2", query = "SELECT m FROM Memoir m WHERE m.movname = :movname and m.cinid.cinname = :cinname")
    /******************* Task 3d *******************/
    , @NamedQuery(name = "Memoir.getMaxMemoirId", query = "SELECT MAX(m.memoid) FROM Memoir m")})

public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEMOID")
    private Integer memoid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MOVNAME")
    private String movname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOVRELDATE")
    @Temporal(TemporalType.DATE)
    private Date movreldate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERWDATE")
    @Temporal(TemporalType.DATE)
    private Date userwdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USERWTIME")
    @Temporal(TemporalType.TIME)
    private Date userwtime;
    @Size(max = 200)
    @Column(name = "USERCOMMENT")
    private String usercomment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATINGSTAR")
    private double ratingstar;
    @JoinColumn(name = "CINID", referencedColumnName = "CINID")
    @ManyToOne(optional = false)
    private Cinema cinid;
    @JoinColumn(name = "PERID", referencedColumnName = "PERID")
    @ManyToOne(optional = false)
    private Person perid;

    public Memoir() {
    }

    public Memoir(Integer memoid) {
        this.memoid = memoid;
    }

    public Memoir(Integer memoid, String movname, Date movreldate, Date userwdate, Date userwtime, double ratingstar) {
        this.memoid = memoid;
        this.movname = movname;
        this.movreldate = movreldate;
        this.userwdate = userwdate;
        this.userwtime = userwtime;
        this.ratingstar = ratingstar;
    }

    public Integer getMemoid() {
        return memoid;
    }

    public void setMemoid(Integer memoid) {
        this.memoid = memoid;
    }

    public String getMovname() {
        return movname;
    }

    public void setMovname(String movname) {
        this.movname = movname;
    }

    public Date getMovreldate() {
        return movreldate;
    }

    public void setMovreldate(Date movreldate) {
        this.movreldate = movreldate;
    }

    public Date getUserwdate() {
        return userwdate;
    }

    public void setUserwdate(Date userwdate) {
        this.userwdate = userwdate;
    }

    public Date getUserwtime() {
        return userwtime;
    }

    public void setUserwtime(Date userwtime) {
        this.userwtime = userwtime;
    }

    public String getUsercomment() {
        return usercomment;
    }

    public void setUsercomment(String usercomment) {
        this.usercomment = usercomment;
    }

    public double getRatingstar() {
        return ratingstar;
    }

    public void setRatingstar(double ratingstar) {
        this.ratingstar = ratingstar;
    }

    public Cinema getCinid() {
        return cinid;
    }

    public void setCinid(Cinema cinid) {
        this.cinid = cinid;
    }

    public Person getPerid() {
        return perid;
    }

    public void setPerid(Person perid) {
        this.perid = perid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memoid != null ? memoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoid == null && other.memoid != null) || (this.memoid != null && !this.memoid.equals(other.memoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Memoir[ memoid=" + memoid + " ]";
    }
    
}
