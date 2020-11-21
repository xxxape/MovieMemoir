package com.zzx.mymoviememoir.tools;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzx.mymoviememoir.model.MemoirList;
import com.zzx.mymoviememoir.server.Cinema;
import com.zzx.mymoviememoir.server.Memoir;
import com.zzx.mymoviememoir.server.Person;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {

    private static final String SERVER_URL = "http://192.168.0.106:8080/MovieMemoir/webresources/";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String TMDB_URL = "https://api.themoviedb.org/3/";
    private static final String TMDB_API_KEY = "#TMDB_API_KEY";
    private static final String GOOGLE_MAP_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private static final String MAP_API_KEY = "#MAP_API_KEY";

    private OkHttpClient client;
    private String result;

    public NetworkConnection() {
        client = new OkHttpClient();
    }

    /**
     * verify user
     * @param username username
     * @param passwordEnter password
     * @return sign in flag
     */
    public String checkUser(String username, String passwordEnter) {

        String credId = "";
        final String methodPath = "restws.credential/checkUser/" + username + "/" + passwordEnter;

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"".equals(result)) {
            credId = new JsonParser().parse(result).getAsJsonArray().get(0).getAsJsonObject().get("credid").getAsString();
        }
        return credId;
    }

    /**
     * get person by id
     * @param id, flag flag: 0: perId; 1: credId
     * @return person details
     */
    public String getPersonById(String id, String flag) {
        String methodPath = "restws.person/";
        if ("0".equals(flag))
            // perId
            methodPath = methodPath + id;
        else if ("1".equals(flag)) {
            // credid
            methodPath = methodPath + "3a.findByCredID/" + id;
        }

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get the max credential id to manage the id
     * @return max credential id
     */
    public int getMaxCredId() {
        final String methodPath = "restws.credential/getMaxCredid/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(result);
    }

    /**
     * get the max person id to manage the id
     * @return max person id
     */
    public int getMaxPersonId() {
        final String methodPath = "restws.person/getMaxPersonId/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(result);
    }

    /**
     * get max cinema id
     * @return max cinema id
     */
    public int getMaxCinemaId() {
        final String methodPath = "restws.cinema/getMaxCinemaId/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(result);
    }

    /**
     * get max memoir id
     * @return max memoir id
     */
    public int getMaxMemoirId() {
        final String methodPath = "restws.memoir/getMaxMemoirId/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(result);
    }

    /**
     * check is username (email) exist
     * @param username username
     * @return is user's email existed
     */
    public String isEmailExist(String username) {
        final String methodPath = "restws.credential/3a.findByUsername/" + username;

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * sign up, add the credential first
     * @param person person
     * @return responseCode (204: successful)
     */
    public int addCredential(Person person) {
        Gson gson = new Gson();
        String credentialJson = gson.toJson(person.getCred());

        int responseCode = 0;
        // test how the json looks like in logcat
        Log.i("jsonCredential ", credentialJson);
        final String methodPath = "restws.credential/";
        RequestBody body = RequestBody.create(credentialJson, JSON);
        Request request = new Request.Builder()
                .url(SERVER_URL + methodPath)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();
            responseCode = response.code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    /**
     * sign up, add the person
     * @param person person
     * @return responseCode (204: successful)
     */
    public int addPerson(Person person) {
        Gson gson = new Gson();
        String personJson = gson.toJson(person);

        int responseCode = 0;
        // test how the json looks like in logcat
        Log.i("jsonPerson ", personJson);
        final String methodPath = "restws.person/";
        RequestBody body = RequestBody.create(personJson, JSON);
        Request request = new Request.Builder()
                .url(SERVER_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responseCode = response.code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    /**
     * add new cinema
     * @param cinema cinema
     * @return responseCode (204: successful)
     */
    public HashMap<Integer, Cinema> addCinema(Cinema cinema) {
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(cinema);

        int responseCode = 0;
        // test how the json looks like in logcat
        Log.i("jsonCinema ", cinemaJson);
        final String methodPath = "restws.cinema/";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder()
                .url(SERVER_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responseCode = response.code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashMap<Integer, Cinema> map = new HashMap<>();
        map.put(responseCode, cinema);
        return map;
    }

    /**
     * add new memoir
     * @param memoir memoir
     * @return responseCode (204: successful)
     */
    public int addMemoir(Memoir memoir) {
        Gson gson = new Gson();
        String memoirJson = gson.toJson(memoir);

        int responseCode = 0;
        // test how the json looks like in logcat
        Log.i("jsonMemoir ", memoirJson);
        final String methodPath = "restws.memoir/";
        RequestBody body = RequestBody.create(memoirJson, JSON);
        Request request = new Request.Builder()
                .url(SERVER_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responseCode = response.code();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseCode;
    }

    /**
     * find recent top movies by person id
     * @param perid person id stored in NetBeans
     * @return movies details
     */
    public JsonArray findRecentTopMovies(String perid) {
        JsonArray recordings = new JsonArray();
        final String methodPath = "restws.memoir/4f.findRecentTopMovies/" + perid;

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!"".equals(result)) {
            recordings = new JsonParser().parse(result).getAsJsonArray();
        }
        return recordings;
    }

    /**
     * search movie by TMDB
     * @param movieName movie name
     * @return movie info
     */
    public JsonArray movieSearchByTMDB(String movieName) {
        movieName = movieName.replace(" ", "%20");
        movieName = movieName.replace(":", "%3A");

        Request.Builder builder = new Request.Builder();
        builder.url(TMDB_URL + "search/movie?api_key=" + TMDB_API_KEY + "&query=" + movieName);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonParser().parse(result).getAsJsonObject().getAsJsonArray("results");
    }

    /**
     * search movie by TMDB
     * @param memoirList memoirList
     * @return movie info
     */
    public MemoirList movieSearchByTMDB(MemoirList memoirList) {
        String movieName = memoirList.getMemoir().getMovname();
        movieName = movieName.replace(" ", "%20");
        movieName = movieName.replace(":", "%3A");
        String year = memoirList.getMemoir().getMovreldate().substring(0, 4);

        Request.Builder builder = new Request.Builder();
        builder.url(TMDB_URL + "search/movie?api_key=" + TMDB_API_KEY + "&query=" + movieName + "&year=" + year);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonArray jsonArray = new JsonParser().parse(result).getAsJsonObject().getAsJsonArray("results");
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        memoirList.setMovieId(id);
        String poster = jsonObject.get("poster_path").getAsString();
        memoirList.setPosterPath(poster);
        float rating = jsonObject.get("vote_average").getAsFloat();
        memoirList.setPublicRating(rating);
        JsonArray genresJson = jsonObject.get("genre_ids").getAsJsonArray();
        String strGenres = genresJson.get(0).getAsString();
        for (int i = 1; i < genresJson.size(); i++)
            strGenres = strGenres + "," + genresJson.get(i).getAsString();
        String[] genres = strGenres.split(",");
        memoirList.setGenres(genres);
        return memoirList;
    }

    /**
     * get movie details by movie id
     * @param movieId movie id stored in TMDB
     * @return movie details
     */
    public String getMovieDetails(String movieId) {

        Request.Builder builder = new Request.Builder();
        builder.url(TMDB_URL + "movie/" + movieId + "?api_key=" + TMDB_API_KEY);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get movie cast and crew by movie id
     * @param movieId movie id stored in TMDB
     * @return movie cast and crew
     */
    public String getMovieCredits(String movieId) {
        Request.Builder builder = new Request.Builder();
        builder.url(TMDB_URL + "movie/" + movieId + "/credits?api_key=" + TMDB_API_KEY);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * count number by postcode from starting date to ending date
     * @param strings start date and end date
     * @return number of movies watched in different postcodes
     */
    public String countNumByPostcode(String... strings) {
        String perId = strings[0];
        String startingDate = strings[1];
        String endingDate = strings[2];

        final String methodPath = "restws.memoir/4a.countNumByPostcode/" + perId + "/" + startingDate + "/" + endingDate;

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param strings year
     * @return number of every month
     */
    public String countNumByMonth(String... strings) {
        String perId = strings[0];
        String year = strings[1];

        final String methodPath = "restws.memoir/4b.countNumByMonth/" + perId + "/" + year;

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get lat and lan by address
     * @param strings
     * strings[0]: address
     * string[1]: user/cinema flag  user: "u"  cinema: name
     * @return lat and lng
     */
    public String[] getLatLngByAddress(String... strings) {
        String address = strings[0];
        final String methodPath = address + "&key=" + MAP_API_KEY;

        Request.Builder builder = new Request.Builder();
        builder.url(GOOGLE_MAP_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] latLng = new String[] {"null", "null", strings[1]};
        String status = new JsonParser().parse(result).getAsJsonObject().get("status").getAsString();
        if ("OK".equals(status)) {
            JsonArray results = new JsonParser().parse(result).getAsJsonObject().get("results").getAsJsonArray();
            JsonObject geometry = results.get(0).getAsJsonObject().get("geometry").getAsJsonObject();
            JsonObject location = geometry.get("location").getAsJsonObject();
            latLng[0] = location.get("lat").getAsString();
            latLng[1] = location.get("lng").getAsString();
        }
        return latLng;
    }

    /**
     * get all cinemas
     * @return all cinemas
     */
    public String getAllCinemas() {
        final String methodPath = "restws.cinema/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get all memoirs
     * @return all memoirs
     */
    public String getAllMemoirs() {
        final String methodPath = "restws.memoir/";

        Request.Builder builder = new Request.Builder();
        builder.url(SERVER_URL + methodPath);
        Request request = builder.build();

        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * get all genres from TMDB
     * @return all genres
     */
    public String getAllGenres() {
        Request.Builder builder = new Request.Builder();
        builder.url(TMDB_URL + "genre/movie/list?api_key=" + TMDB_API_KEY);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
