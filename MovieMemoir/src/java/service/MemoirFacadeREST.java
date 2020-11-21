/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
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
import restws.Memoir;

/**
 *
 * @author xxxape
 */
@Stateless
@Path("restws.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MovieMemoirPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
    @Path("3a.findByMovname/{movname}")
    @Produces({"application/json"})
    public List<Memoir> findByMovname(@PathParam("movname") String movname){
        Query q = em.createNamedQuery("Memoir.findByMovname");
        q.setParameter("movname", movname);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByMovreldate/{movreldate}")
    @Produces({"application/json"})
    public List<Memoir> findByMovreldate(@PathParam("movreldate") Date movreldate){
        Query q = em.createNamedQuery("Memoir.findByMovreldate");
        q.setParameter("movreldate", movreldate);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByUserwdate/{userwdate}")
    @Produces({"application/json"})
    public List<Memoir> findByUserwdate(@PathParam("userwdate") Date userwdate){
        Query q = em.createNamedQuery("Memoir.findByUserwdate");
        q.setParameter("userwdate", userwdate);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByUserwtime/{userwtime}")
    @Produces({"application/json"})
    public List<Memoir> findByUserwtime(@PathParam("userwtime") Time userwtime){
        Query q = em.createNamedQuery("Memoir.findByUserwtime");
        q.setParameter("userwtime", userwtime);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByUsercomment/{usercomment}")
    @Produces({"application/json"})
    public List<Memoir> findByUsercomment(@PathParam("usercomment") String usercomment){
        Query q = em.createNamedQuery("Memoir.findByUsercomment");
        q.setParameter("usercomment", usercomment);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByRatingstar/{ratingstar}")
    @Produces({"application/json"})
    public List<Memoir> findByRatingstar(@PathParam("ratingstar") Float ratingstar){
        Query q = em.createNamedQuery("Memoir.findByRatingstar");
        q.setParameter("ratingstar", ratingstar);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByCinID/{cinid}")
    @Produces({"application/json"})
    public List<Memoir> findByCinID(@PathParam("cinid") Integer cinid){
        TypedQuery<Memoir> q = em.createQuery("SELECT m FROM Memoir m WHERE m.cinid.cinid = :cinid", Memoir.class);
        q.setParameter("cinid", cinid);
        return q.getResultList();
    }
    
    @GET
    @Path("3a.findByPerID/{perid}")
    @Produces({"application/json"})
    public List<Memoir> findByPerID(@PathParam("perid") Integer perid){
        TypedQuery<Memoir> q = em.createQuery("SELECT m FROM Memoir m WHERE m.perid.perid = :perid", Memoir.class);
        q.setParameter("perid", perid);
        return q.getResultList();
    }
    /********************* a **********************/
        
    /********************* c **********************/
    @GET
    @Path("3c.findByMovienameAndCinemaname/{movname}/{cinname}")
    @Produces({"application/json"})
    public List<Memoir> findByMovienameAndCinemaname(@PathParam("movname") String movname, @PathParam("cinname") String cinname){
        TypedQuery q = em.createQuery("SELECT m FROM Memoir m WHERE m.movname = :movname and m.cinid.cinname = :cinname", Memoir.class);
        q.setParameter("movname", movname);
        q.setParameter("cinname", cinname);
        return q.getResultList();
    }
    /********************* c **********************/
    
    /********************* d **********************/
    @GET
    @Path("3d.findByMovienameAndCinemaname2/{movname}/{cinname}")
    @Produces({"application/json"})
    public List<Memoir> findByMovienameAndCinemaname2(@PathParam("movname") String movname, @PathParam("cinname") String cinname){
        Query q = em.createNamedQuery("Memoir.findByMovienameAndCinemaname2");
        q.setParameter("movname", movname);
        q.setParameter("cinname", cinname);
        return q.getResultList();
    }
    /********************* d **********************/
    
    /**********************************************/
    /******************* Task 3 *******************/
    /**********************************************/
    
    
    /**********************************************/
    /******************* Task 4 *******************/
    /**********************************************/
    
    /********************* a **********************/
    @GET
    @Path("4a.countNumByPostcode/{perid}/{startingdate}/{endingdate}")
    @Produces({"application/json"})
    public Object countNumByPostcode(@PathParam("perid") Integer perid, 
            @PathParam("startingdate") Date startingdate, @PathParam("endingdate") Date endingdate){
        TypedQuery q = em.createQuery(
                "SELECT m.cinid.cinpostcode, COUNT(m.cinid.cinpostcode) "
                        + "FROM Memoir m WHERE m.perid.perid = :perid "
                        + "AND m.userwdate >= :startingdate "
                        + "AND m.userwdate <= :endingdate "
                        + "GROUP BY m.cinid.cinpostcode"
                , Object[].class);
        q.setParameter("perid", perid);
        q.setParameter("startingdate", startingdate);
        q.setParameter("endingdate", endingdate);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row: list) {
            JsonObject queryObject = Json.createObjectBuilder().
                    add("cinemapostcode", (String)row[0])
                    .add("total_number", row[1].toString()).build();
            arrayBuilder.add(queryObject);            
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* a **********************/
    
    
    /********************* b **********************/
    @GET
    @Path("4b.countNumByMonth/{perid}/{year}")
    @Produces({"application/json"})
    public Object countNumByMonth(@PathParam("perid") Integer perid, @PathParam("year") String year){
        String[] monthStr = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
        TypedQuery q = em.createQuery(
                "SELECT EXTRACT(MONTH FROM m.userwdate) AS MONTH, count(m.memoid) FROM Memoir m WHERE m.perid.perid = :perid "
                        + "AND EXTRACT(YEAR FROM m.userwdate) = :year GROUP BY EXTRACT(MONTH FROM m.userwdate)"
                , Object[].class);
        q.setParameter("perid", perid);
        q.setParameter("year", year);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        int i = 1;
        for(Object[] row: list) {
            while (i != (int)row[0]) {                
                JsonObject queryObject = Json.createObjectBuilder().
                    add("month", monthStr[i])
                    .add("total_number", 0).build();
                arrayBuilder.add(queryObject);
                i++;
            }
            JsonObject queryObject = Json.createObjectBuilder().
                    add("month", monthStr[(int)row[0]])
                    .add("total_number", row[1].toString()).build();
            arrayBuilder.add(queryObject);       
            i++;
        }
        if (i != 13) {
            for (int j = i; j < 13; j++) {
                JsonObject queryObject = Json.createObjectBuilder().
                    add("month", monthStr[j])
                    .add("total_number", 0).build();
                arrayBuilder.add(queryObject);
            }
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* b **********************/
    
    
    /********************* c **********************/
    @GET
    @Path("4c.findHighestRateMovieByUser/{perid}")
    @Produces({"application/json"})
    public Object findHighestRateMovieByUser(@PathParam("perid") Integer perid){
        TypedQuery q = em.createQuery(
                "SELECT m.movname, m.movreldate, m.ratingstar FROM Memoir m WHERE m.perid.perid = :perid "
                        + "AND m.ratingstar = (SELECT MAX(m.ratingstar) FROM Memoir m WHERE m.perid.perid = :perid)"
                , Object[].class);
        q.setParameter("perid", perid);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row: list) {
            JsonObject queryObject = Json.createObjectBuilder().
                    add("movieName", row[0].toString())
                    .add("movieReleaseDate", row[1].toString())
                    .add("ratingStar", row[2].toString())
                    .build();
            arrayBuilder.add(queryObject);            
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* c **********************/
    
    
    /********************* d **********************/
    @GET
    @Path("4d.findMovieBySameReleaseAndWatchedYear/{perid}")
    @Produces({"application/json"})
    public Object findMovieBySameReleaseAndWatchedYear(@PathParam("perid") Integer perid){
        TypedQuery q = em.createQuery(
                "SELECT m.movname, EXTRACT(YEAR FROM m.movreldate) FROM Memoir m WHERE m.perid.perid = :perid "
                        + "AND EXTRACT(YEAR FROM m.userwdate) = EXTRACT(YEAR FROM m.movreldate)"
                , Object[].class);
        q.setParameter("perid", perid);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row: list) {
            JsonObject queryObject = Json.createObjectBuilder().
                    add("movieName", row[0].toString())
                    .add("movieReleaseYear", row[1].toString())
                    .build();
            arrayBuilder.add(queryObject);            
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* d **********************/
    
    
    /********************* e **********************/
    @GET
    @Path("4e.findWatchedRemakedMovie/{perid}")
    @Produces({"application/json"})
    public Object findWatchedRemakedMovie(@PathParam("perid") Integer perid){
        TypedQuery q = em.createQuery(
                "SELECT distinct m1.movname, EXTRACT(YEAR FROM m1.movreldate), EXTRACT(YEAR FROM m2.movreldate) "
                        + "FROM Memoir m1, Memoir m2 "
                        + "WHERE m1.perid.perid = :perid AND m1.movname = m2.movname AND m1.movreldate <> m2.movreldate "
                        + "AND m1.movname IN (SELECT m.movname FROM Memoir m WHERE m.perid.perid = :perid "
                                    + "GROUP BY m.movname having count(distinct m.movreldate) > 1) "
                , Object[].class);
        q.setParameter("perid", perid);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        List<String> movienameList = new ArrayList<>();
        for(Object[] row: list) {
            if (movienameList.contains(row[0].toString())) {
                break;
            }
            movienameList.add(row[0].toString());
            JsonObject queryObject = Json.createObjectBuilder().
                    add("movieName", row[0].toString())
                    .add("movieReleaseYear1", row[1].toString())
                    .add("movieReleaseYear2", row[2].toString())
                    .build();
            arrayBuilder.add(queryObject);            
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* e **********************/
    
    
    /********************* f **********************/
    @GET
    @Path("4f.findRecentTopMovies/{perid}")
    @Produces({"application/json"})
    public Object findRecentTopMovies(@PathParam("perid") Integer perid){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int topNumber = 5;
        TypedQuery q = em.createQuery(
                "SELECT m.movname, m.movreldate, m.ratingstar FROM Memoir m "
                        + "WHERE m.perid.perid = :perid AND EXTRACT(YEAR FROM m.movreldate) = :recentyear "
                        + "ORDER BY m.ratingstar DESC"
                , Object[].class);
        q.setParameter("perid", perid);
        q.setParameter("recentyear", currentYear);
        q.setMaxResults(topNumber);
        List<Object[]> list =  q.getResultList();
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for(Object[] row: list) {
            JsonObject queryObject = Json.createObjectBuilder().
                    add("movieName", row[0].toString())
                    .add("movieReleaseDate", row[1].toString())
                    .add("ratingScore", row[2].toString())
                    .build();
            arrayBuilder.add(queryObject);            
        }
        JsonArray jArray = arrayBuilder.build();
        return jArray;
    }
    /********************* f **********************/
        
    /**********************************************/
    /******************* Task 4 *******************/
    /**********************************************/
    
    @GET
    @Path("getMaxMemoirId")
    @Produces({"application/json"})
    public String getMaxMemoirId(){
        Query q = em.createNamedQuery("Memoir.getMaxMemoirId");
        List<Integer> result = q.getResultList();
        return result.get(0).toString();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
