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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Credential;

/**
 *
 * @author xxxape
 */
@Stateless
@Path("restws.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "MovieMemoirPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credential entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Credential find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("3a.findByUsername/{username}")
    @Produces({"application/json"})
    public List<Credential> findByUsername(@PathParam("username") String username){
        Query q = em.createNamedQuery("Credential.findByUsername");
        q.setParameter("username", username);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPassword/{password}")
    @Produces({"application/json"})
    public List<Credential> findByPassword(@PathParam("password") String password){
        Query q = em.createNamedQuery("Credential.findByPassword");
        q.setParameter("password", password);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findBySignupdate/{signupdate}")
    @Produces({"application/json"})
    public List<Credential> findBySignupdate(@PathParam("signupdate") Date signupdate){
        Query q = em.createNamedQuery("Credential.findBySignupdate");
        q.setParameter("signupdate", signupdate);
        return q.getResultList();
    }

    /********************* a **********************/
    
    /**********************************************/
    /******************* Task 3 *******************/
    /**********************************************/
    
    @GET
    @Path("checkUser/{username}/{password}")
    @Produces({"application/json"})
    public Object checkUser(@PathParam("username") String username, @PathParam("password") String password){
        Query q = em.createNamedQuery("Credential.checkUser");
        q.setParameter("username", username);
        q.setParameter("password", password);
        List<Integer> result = q.getResultList();
        if (result.size() == 0) {
            return "";
        }
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        JsonObject queryObjecy = Json.createObjectBuilder().add("credid", result.get(0).toString()).build();
        arrayBuilder.add(queryObjecy);
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    
    @GET
    @Path("getMaxCredid")
    @Produces({"application/json"})
    public String getMaxCredid(){
        Query q = em.createNamedQuery("Credential.getMaxCredid");
        List<Integer> result = q.getResultList();
        return result.get(0).toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
