/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Person;

/**
 *
 * @author xxxape
 */
@Stateless
@Path("restws.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "MovieMemoirPU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    /**********************************************/
    /******************* Task 3 *******************/
    /**********************************************/    
    
    /********************* a **********************/ 
    @GET
    @Path("3a.findByPerfname/{perfname}")
    @Produces({"application/json"})
    public List<Person> findByPerfname(@PathParam("perfname") String perfname){
        Query q = em.createNamedQuery("Person.findByPerfname");
        q.setParameter("perfname", perfname);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerlname/{perlname}")
    @Produces({"application/json"})
    public List<Person> findByPerlname(@PathParam("perlname") String perlname){
        Query q = em.createNamedQuery("Person.findByPerlname");
        q.setParameter("perlname", perlname);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPergender/{pergender}")
    @Produces({"application/json"})
    public List<Person> findByPergender(@PathParam("pergender") String pergender){
        Query q = em.createNamedQuery("Person.findByPergender");
        q.setParameter("pergender", pergender);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerdob/{perdob}")
    @Produces({"application/json"})
    public List<Person> findByPerdob(@PathParam("perdob") Date perdob){
        Query q = em.createNamedQuery("Person.findByPerdob");
        q.setParameter("perdob", perdob);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerstno/{perstno}")
    @Produces({"application/json"})
    public List<Person> findByPerstno(@PathParam("perstno") String perstno){
        Query q = em.createNamedQuery("Person.findByPerstno");
        q.setParameter("perstno", perstno);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerstname/{perstname}")
    @Produces({"application/json"})
    public List<Person> findByPerstname(@PathParam("perstname") String perstname){
        Query q = em.createNamedQuery("Person.findByPerstname");
        q.setParameter("perstname", perstname);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerstate/{perstate}")
    @Produces({"application/json"})
    public List<Person> findByPerstate(@PathParam("perstate") String perstate){
        Query q = em.createNamedQuery("Person.findByPerstate");
        q.setParameter("perstate", perstate);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerpostcode/{perpostcode}")
    @Produces({"application/json"})
    public List<Person> findByPerpostcode(@PathParam("perpostcode") String perpostcode){
        Query q = em.createNamedQuery("Person.findByPerpostcode");
        q.setParameter("perpostcode", perpostcode);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByCredID/{credid}")
    @Produces({"application/json"})
    public Person findByCredID(@PathParam("credid") Integer credid) {
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.credid.credid = :credid", Person.class);
        q.setParameter("credid", credid);
        return q.getResultList().get(0);
    }

    /********************* a **********************/
    
    
    /********************* b **********************/
    @GET
    @Path("3b.findByNameAndDob/{perfname}/{perlname}/{perdob}")
    @Produces({"application/json"})
    public Person findByNameAndDob(@PathParam("perfname") String perfname, @PathParam("perlname") String perlname, 
                @PathParam("perdob") Date perdob){
        TypedQuery<Person> q = em.createQuery("SELECT p FROM Person p WHERE p.perfname = :perfname AND p.perlname = :perlname AND p.perdob = :perdob", Person.class);
        q.setParameter("perfname", perfname);
        q.setParameter("perlname", perlname);
        q.setParameter("perdob", perdob);
        return q.getResultList().get(0);
    }
    /********************* b **********************/
    
    /**********************************************/
    /******************* Task 3 *******************/
    /**********************************************/
    
    @GET
    @Path("test/{perfname}")
    @Produces({"application/json"})
    public String test(@PathParam("perfname") String perfname){
        TypedQuery q = em.createQuery(
                "SELECT p.credid.username "
                        + "FROM Person p WHERE p.perfname = :perfname "
                , Object[].class);
        q.setParameter("perfname", perfname);
        List<String> list =  q.getResultList();
        return list.get(0);
    }
    
    @GET
    @Path("getMaxPersonId")
    @Produces({"application/json"})
    public String getMaxPersonId(){
        Query q = em.createNamedQuery("Person.getMaxPersonId");
        List<Integer> result = q.getResultList();
        return result.get(0).toString();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
