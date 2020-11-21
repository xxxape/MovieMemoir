package com.zzx.mymoviememoir.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieDetails implements Parcelable {

    private String movieName;
    private String releaseDate;
    private String movieId;
    private String imagePath;
    private String isInWatchList;

    public MovieDetails(String movieName, String releaseDate, String movieId, String imagePath, String isInWatchList) {
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.movieId = movieId;
        this.imagePath = imagePath;
        this.isInWatchList = isInWatchList;
    }

    public MovieDetails(Parcel source) {
        this.movieName = source.readString();
        this.releaseDate = source.readString();
        this.movieId = source.readString();
        this.imagePath = source.readString();
        this.isInWatchList = source.readString();
    }

    public String getMovieName() {
        return movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIsInWatchList() {
        return isInWatchList;
    }

    public void setIsInWatchList(String isInWatchList) {
        this.isInWatchList = isInWatchList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieName);
        dest.writeString(releaseDate);
        dest.writeString(movieId);
        dest.writeString(imagePath);
        dest.writeString(isInWatchList);
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[0];
        }
    };
}
