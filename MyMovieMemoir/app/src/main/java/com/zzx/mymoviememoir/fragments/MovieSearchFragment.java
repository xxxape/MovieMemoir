package com.zzx.mymoviememoir.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.adapters.MovieSearchAdapter;
import com.zzx.mymoviememoir.tools.NetworkConnection;

import java.util.ArrayList;
import java.util.List;

public class MovieSearchFragment extends Fragment {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private EditText etSearchMovie;
    private ImageView btnSearchMovie;
    private NetworkConnection networkConnection;
    private RecyclerView rvMovieList;
    private MovieSearchAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<MovieDetails> movies;

    public MovieSearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_search, container, false);
        networkConnection = new NetworkConnection();
        rvMovieList = view.findViewById(R.id.rvMovieSearch);

        etSearchMovie = view.findViewById(R.id.etSearchMovie);
        btnSearchMovie = view.findViewById(R.id.btnSearchMovie);
        btnSearchMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(etSearchMovie.getText().toString())) {
                    Toast.makeText(getContext(), "Please enter movie name first!", Toast.LENGTH_SHORT).show();
                } else {
                    etSearchMovie.clearFocus();
                    final String movieName = etSearchMovie.getText().toString();
                    SearchMovie searchMovie = new SearchMovie();
                    searchMovie.execute(movieName);
                    movies = new ArrayList<>();
                    myAdapter = new MovieSearchAdapter(movies);
                    rvMovieList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                    rvMovieList.setAdapter(myAdapter);
                    layoutManager = new LinearLayoutManager(getContext());
                    rvMovieList.setLayoutManager(layoutManager);
                }
            }
        });
        return view;
    }

    private class SearchMovie extends AsyncTask<String, Void, JsonArray> {
        @Override
        protected JsonArray doInBackground(String... strings) {
            return networkConnection.movieSearchByTMDB(strings[0]);
        }

        @Override
        protected void onPostExecute(JsonArray jsonElements) {
            int a = jsonElements.size();
            for (int i = 0; i < jsonElements.size(); i++) {
                JsonObject jsonObject = jsonElements.get(i).getAsJsonObject();
                // get poster path，movie name and release date
                String movieId = jsonObject.get("id").getAsString();
                String movieImage = "";
                if (!"null".equals(jsonObject.get("poster_path").toString()))
                    movieImage = jsonObject.get("poster_path").getAsString();
                String imageUrl = IMAGE_URL + movieImage;
                String movieTitle = jsonObject.get("title").getAsString();
                String movieRelsDate = "";
                if (jsonObject.get("release_date") != null)
                    movieRelsDate = jsonObject.get("release_date").getAsString();
                saveData(movieTitle, movieRelsDate, movieId, imageUrl);
            }
        }
    }

    private void saveData(String movieName, String releaseDate, String movieId, String imagePath) {
        MovieDetails movie = new MovieDetails(movieName, releaseDate, movieId, imagePath, "0");
        movies.add(movie);
        myAdapter.addMovie(movies);
    }
}
