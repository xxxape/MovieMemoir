package com.zzx.mymoviememoir.watchlist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WatchList {

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    @NonNull
    private String movieId;

    @ColumnInfo(name = "movie_name")
    private String movieName;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    @ColumnInfo(name = "add_date")
    private String addDate;

    @ColumnInfo(name = "add_time")
    private String addTime;

    public WatchList(@NonNull String movieId, String movieName, String releaseDate, String addDate, String addTime) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addDate = addDate;
        this.addTime = addTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
