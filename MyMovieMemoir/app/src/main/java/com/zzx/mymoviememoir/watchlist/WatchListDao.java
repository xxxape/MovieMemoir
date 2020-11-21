package com.zzx.mymoviememoir.watchlist;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WatchListDao {

    @Query("SELECT * FROM watchlist")
    LiveData<List<WatchList>> getAll();

    @Query("SELECT * FROM watchlist WHERE movie_id = :movieId")
    WatchList findById(String movieId);

    @Insert
    long insert(WatchList watchList);

    @Delete
    void delete(WatchList watchList);

    @Update(onConflict = REPLACE)
    void updateWatchList(WatchList watchList);
}
