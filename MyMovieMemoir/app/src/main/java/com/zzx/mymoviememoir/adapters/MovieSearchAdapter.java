package com.zzx.mymoviememoir.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.movie.MovieView;
import com.zzx.mymoviememoir.R;

import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView tvMovieName;
        public TextView tvReleaseDate;
        public TextView tvViewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.msIvMovie);
            tvMovieName = itemView.findViewById(R.id.msMovieName);
            tvReleaseDate = itemView.findViewById(R.id.msReleaseDate);
            tvViewMore = itemView.findViewById(R.id.msViewMore);
        }
    }

    private List<MovieDetails> movieDetails;

    public MovieSearchAdapter(List<MovieDetails> movieDetails) {
        this.movieDetails = movieDetails;
    }

    public void addMovie(List<MovieDetails> movieDetails) {
        this.movieDetails = movieDetails;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View movieView = inflater.inflate(R.layout.movie_search_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(movieView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieDetails movieDetails = this.movieDetails.get(position);
        ImageView ivMovie = holder.imageView;
        TextView tvMovieName = holder.tvMovieName;
        tvMovieName.setText(movieDetails.getMovieName());
        TextView tvReleaseDate = holder.tvReleaseDate;
        String year = "";
        if (!"".equals(movieDetails.getReleaseDate()))
            year = movieDetails.getReleaseDate().substring(0, 4);
        tvReleaseDate.setText(year);
        String url = movieDetails.getImagePath();
        // get picture
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_no_image_available_600_x_450)
                .resize(200, 200)
                .centerInside()
                .into(ivMovie);
        holder.tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        return movieDetails.size();
    }
}
