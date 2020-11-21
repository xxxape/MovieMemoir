package com.zzx.mymoviememoir.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.model.MemoirList;
import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.movie.MovieView;

import java.util.List;

public class MemoirListAdapter extends RecyclerView.Adapter<MemoirListAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivPoster;
        public TextView tvMovieName;
        public TextView tvReleaseDate;
        public TextView tvUserWDate;
        public TextView tvPostcode;
        public TextView tvComment;
        public RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMlPoster);
            tvMovieName = itemView.findViewById(R.id.tvMlMovieName);
            tvReleaseDate = itemView.findViewById(R.id.tvMlReleaseDate);
            tvUserWDate = itemView.findViewById(R.id.tvMlUserWDate);
            tvPostcode = itemView.findViewById(R.id.tvMlPostcode);
            tvComment = itemView.findViewById(R.id.tvMlComment);
            ratingBar = itemView.findViewById(R.id.rbMl);
        }
    }

    List<MemoirList> memoirLists;

    public MemoirListAdapter(List<MemoirList> memoirLists) {
        this.memoirLists = memoirLists;
    }

    public void setMemoir(List<MemoirList> memoirs) {
        this.memoirLists = memoirs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View memoirView = inflater.inflate(R.layout.memoir_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(memoirView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MemoirList memoirList = this.memoirLists.get(position);
        ImageView ivPoster = holder.ivPoster;
        Picasso.get()
                .load(memoirList.getPosterPath())
                .error(R.drawable.ic_no_image_available_600_x_450)
                .resize(360, 360)
                .centerInside()
                .into(ivPoster);
        TextView tvMovieName = holder.tvMovieName;
        tvMovieName.setText(memoirList.getMemoir().getMovname());
        TextView tvReleaseDate = holder.tvReleaseDate;
        tvReleaseDate.setText(memoirList.getMemoir().getMovreldate());
        TextView tvUserWDate = holder.tvUserWDate;
        tvUserWDate.setText(memoirList.getMemoir().getUserwdate());
        TextView tvPostcode = holder.tvPostcode;
        tvPostcode.setText(memoirList.getMemoir().getCinid().getCinpostcode());
        TextView tvComment = holder.tvComment;
        tvComment.setText(memoirList.getMemoir().getUsercomment());
        RatingBar ratingBar = holder.ratingBar;
        ratingBar.setRating(memoirList.getMemoir().getRatingstar());

        // add click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetails movieDetails = new MovieDetails(memoirList.getMemoir().getMovname(),
                        memoirList.getMemoir().getMovreldate(),
                        memoirList.getMovieId(),
                        memoirList.getPosterPath(),
                        "0");
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
        return memoirLists.size();
    }
}
