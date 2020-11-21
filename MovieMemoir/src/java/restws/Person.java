/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author xxxape
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")
    , @NamedQuery(name = "Person.findByPerid", query = "SELECT p FROM Person p WHERE p.perid = :perid")
    , @NamedQuery(name = "Person.findByPerfname", query = "SELECT p FROM Person p WHERE p.perfname = :perfname")
    , @NamedQuery(name = "Person.findByPerlname", query = "SELECT p FROM Person p WHERE p.perlname = :perlname")
    , @NamedQuery(name = "Person.findByPergender", query = "SELECT p FROM Person p WHERE p.pergender = :pergender")
    , @NamedQuery(name = "Person.findByPerdob", query = "SELECT p FROM Person p WHERE p.perdob = :perdob")
    , @NamedQuery(name = "Person.findByPerstno", query = "SELECT p FROM Person p WHERE p.perstno = :perstno")
    , @NamedQuery(name = "Person.findByPerstname", query = "SELECT p FROM Person p WHERE p.perstname = :perstname")
    , @NamedQuery(name = "Person.findByPerstate", query = "SELECT p FROM Person p WHERE p.perstate = :perstate")
    , @NamedQuery(name = "Person.findByPerpostcode", query = "SELECT p FROM Person p WHERE p.perpostcode = :perpostcode")
    , @NamedQuery(name = "Person.getMaxPersonId", query = "SELECT MAX(p.perid) FROM Person p")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERID")
    private Integer perid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PERFNAME")
    private String perfname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PERLNAME")
    private String perlname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PERGENDER")
    private String pergender;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERDOB")
    @Temporal(TemporalType.DATE)
    private Date perdob;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "PERSTNO")
    private String perstno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PERSTNAME")
    private String perstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PERSTATE")
    private String perstate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "PERPOSTCODE")
    private String perpostcode;
    @JoinColumn(name = "CREDID", referencedColumnName = "CREDID")
    @OneToOne(optional = false)
    private Credential credid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "perid")
    private Collection<Memoir> memoirCollection;

    public Person() {
    }

    public Person(Integer perid) {
        this.perid = perid;
    }

    public Person(Integer perid, String perfname, String perlname, String pergender, Date perdob, String perstno, String perstname, String perstate, String perpostcode) {
        this.perid = perid;
        this.perfname = perfname;
        this.perlname = perlname;
        this.pergender = pergender;
        this.perdob = perdob;
        this.perstno = perstno;
        this.perstname = perstname;
        this.perstate = perstate;
        this.perpostcode = perpostcode;
    }

    public Integer getPerid() {
        return perid;
    }

    public void setPerid(Integer perid) {
        this.perid = perid;
    }

    public String getPerfname() {
        return perfname;
    }

    public void setPerfname(String perfname) {
        this.perfname = perfname;
    }

    public String getPerlname() {
        return perlname;
    }

    public void setPerlname(String perlname) {
        this.perlname = perlname;
    }

    public String getPergender() {
        return pergender;
    }

    public void setPergender(String pergender) {
        this.pergender = pergender;
    }

    public Date getPerdob() {
        return perdob;
    }

    public void setPerdob(Date perdob) {
        this.perdob = perdob;
    }

    public String getPerstno() {
        return perstno;
    }

    public void setPerstno(String perstno) {
        this.perstno = perstno;
    }

    public String getPerstname() {
        return perstname;
    }

    public void setPerstname(String perstname) {
        this.perstname = perstname;
    }

    public String getPerstate() {
        return perstate;
    }

    public void setPerstate(String perstate) {
        this.perstate = perstate;
    }

    public String getPerpostcode() {
        return perpostcode;
    }

    public void setPerpostcode(String perpostcode) {
        this.perpostcode = perpostcode;
    }

    public Credential getCredid() {
        return credid;
    }

    public void setCredid(Credential credid) {
        this.credid = credid;
    }

    @XmlTransient
    public Collection<Memoir> getMemoirCollection() {
        return memoirCollection;
    }

    public void setMemoirCollection(Collection<Memoir> memoirCollection) {
        this.memoirCollection = memoirCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (perid != null ? perid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.perid == null && other.perid != null) || (this.perid != null && !this.perid.equals(other.perid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Person[ perid=" + perid + " ]";
    }
    
}
