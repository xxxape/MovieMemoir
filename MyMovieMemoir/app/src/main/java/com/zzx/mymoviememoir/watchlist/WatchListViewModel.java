package com.zzx.mymoviememoir.watchlist;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class WatchListViewModel extends ViewModel {

    private WatchListRepository watchListRepository;
//    private MutableLiveData<List<WatchList>> allWatchLists;

//    public void setWatchList(List<WatchList> watchList) {
//        allWatchLists.setValue(watchList);
//    }

    public LiveData<List<WatchList>> getAllWatchLists() {
        return watchListRepository.getAllWatchLists();
    }

    public void initalizeVars(Application application) {
        watchListRepository = new WatchListRepository(application);
    }

    public WatchList findById(String movieId) {
        return  watchListRepository.findById(movieId);
    }

    public void insert(WatchList watchList) {
        watchListRepository.insert(watchList);
    }

    public void delete(WatchList watchList) {
        watchListRepository.delete(watchList);
    }

    public void updateWatchList(WatchList watchList) {
        watchListRepository.updateWatchList(watchList);
    }
}
