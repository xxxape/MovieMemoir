package com.zzx.mymoviememoir.adapters;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.movie.MovieView;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.watchlist.WatchList;
import com.zzx.mymoviememoir.watchlist.WatchListViewModel;

import java.util.ArrayList;
import java.util.List;

public class WatchListAdapter extends RecyclerView.Adapter<WatchListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMovieName;
        public TextView tvReleaseDate;
        public TextView tvAddDate;
        public TextView tvAddTime;
        public ImageView ivDeleteItem;
        public TextView tvView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMovieName = itemView.findViewById(R.id.wlTvMovieName);
            tvReleaseDate = itemView.findViewById(R.id.wlTvReleaseDate);
            tvAddDate = itemView.findViewById(R.id.wlTvAddDate);
            tvAddTime = itemView.findViewById(R.id.wlTvAddTime);
            ivDeleteItem = itemView.findViewById(R.id.wlIvDeleteItem);
            tvView = itemView.findViewById(R.id.wlTvView);
        }
    }

    private List<WatchList> allWatchLists;
    private WatchListViewModel watchListViewModel;
    private Fragment fragment;

    public WatchListAdapter(Fragment fragment, Application application) {
        this.allWatchLists = new ArrayList<>();
        this.fragment = fragment;
        watchListViewModel = new ViewModelProvider(fragment).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(application);
    }

    public void setWatchList(List<WatchList> watchLists) {
        this.allWatchLists = watchLists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View watchListView = inflater.inflate(R.layout.watchlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(watchListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final WatchList watchList = allWatchLists.get(position);
        TextView tvMovieName = holder.tvMovieName;
        tvMovieName.setText(watchList.getMovieName());
        TextView tvReleaseDate = holder.tvReleaseDate;
        tvReleaseDate.setText(watchList.getReleaseDate());
        TextView tvAddDate = holder.tvAddDate;
        tvAddDate.setText(watchList.getAddDate());
        TextView tvAddTime = holder.tvAddTime;
        tvAddTime.setText(" " + watchList.getAddTime().substring(0, 5));
        ImageView ivDeleteItem = holder.ivDeleteItem;
        ivDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
                builder.setMessage("Are you sure to delete this recording?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        allWatchLists.remove(watchList);
                        watchListViewModel.delete(watchList);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
        TextView tvView = holder.tvView;
        tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails movieDetails = new MovieDetails(watchList.getMovieName(), watchList.getReleaseDate(), watchList.getMovieId(), "", "1");
                Intent intent = new Intent(v.getContext(), MovieView.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("movie_details", movieDetails);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allWatchLists.size();
    }
}
