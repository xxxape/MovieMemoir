package com.zzx.mymoviememoir.watchlist;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class WatchListRepository {

    private WatchListDao dao;
    private LiveData<List<WatchList>> allWatchLists;
    private WatchList watchList;

    public void setWatchList(WatchList watchList) {
        this.watchList = watchList;
    }

    public WatchListRepository(Application application) {
        WatchListDatabase db = WatchListDatabase.getInstance(application);
        dao = db.watchListDao();
        allWatchLists = dao.getAll();
    }

    public LiveData<List<WatchList>> getAllWatchLists() {
        return allWatchLists;
    }

    public WatchList findById(final String movieId) {
        return dao.findById(movieId);
    }

    public void insert(final WatchList watchList) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchList);
            }
        });
    }

    public void delete(final WatchList watchList) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchList);
            }
        });
    }

    public void updateWatchList(final WatchList watchList) {
        WatchListDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchList(watchList);
            }
        });
    }
}
