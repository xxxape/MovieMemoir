/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.util.List;
import javax.ejb.Stateless;
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
import restws.Cinema;

/**
 *
 * @author xxxape
 */
@Stateless
@Path("restws.cinema")
public class CinemaFacadeREST extends AbstractFacade<Cinema> {

    @PersistenceContext(unitName = "MovieMemoirPU")
    private EntityManager em;

    public CinemaFacadeREST() {
        super(Cinema.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Cinema entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Cinema entity) {
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
    public Cinema find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Cinema> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cinema> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("3a.findByCinname/{cinname}")
    @Produces({"application/json"})
    public List<Cinema> findByCinname(@PathParam("cinname") String cinname){
        Query q = em.createNamedQuery("Cinema.findByCinname");
        q.setParameter("cinname", cinname);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByCinpostcode/{cinpostcode}")
    @Produces({"application/json"})
    public List<Cinema> findByCinpostcode(@PathParam("cinpostcode") String cinpostcode){
        Query q = em.createNamedQuery("Cinema.findByCinpostcode");
        q.setParameter("cinpostcode", cinpostcode);
        return q.getResultList();
    }

    /********************* a **********************/
    
    /**********************************************/
    /******************* Task 3 *******************/
    /**********************************************/

    @GET
    @Path("getMaxCinemaId")
    @Produces({"application/json"})
    public String getMaxCinemaId(){
        Query q = em.createNamedQuery("Cinema.getMaxCinemaId");
        List<Integer> result = q.getResultList();
        return result.get(0).toString();
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
