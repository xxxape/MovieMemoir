package com.zzx.mymoviememoir.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.adapters.WatchListAdapter;
import com.zzx.mymoviememoir.watchlist.WatchList;
import com.zzx.mymoviememoir.watchlist.WatchListViewModel;

import java.util.List;

public class WatchlistFragment extends Fragment {

    private RecyclerView wlRecyclerView;
    private WatchListAdapter watchListAdapter;
    private WatchListViewModel watchListViewModel;

    public WatchlistFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);

        wlRecyclerView = view.findViewById(R.id.wlRecyclerView);
        wlRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        watchListAdapter = new WatchListAdapter(this, getActivity().getApplication());
        wlRecyclerView.setAdapter(watchListAdapter);

        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getActivity().getApplication());
        watchListViewModel.getAllWatchLists().observe(this, new Observer<List<WatchList>>() {
            @Override
            public void onChanged(List<WatchList> watchLists) {
                watchListAdapter.setWatchList(watchLists);
                watchListAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
