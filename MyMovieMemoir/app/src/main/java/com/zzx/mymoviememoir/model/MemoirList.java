package com.zzx.mymoviememoir.model;

import com.zzx.mymoviememoir.server.Memoir;

public class MemoirList {

    private Memoir memoir;
    private String movieId;
    private String posterPath;
    private float publicRating;
    private String[] genres;

    public MemoirList() {
    }

    public Memoir getMemoir() {
        return memoir;
    }

    public void setMemoir(Memoir memoir) {
        this.memoir = memoir;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public float getPublicRating() {
        return publicRating;
    }

    public void setPublicRating(float publicRating) {
        this.publicRating = publicRating;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
