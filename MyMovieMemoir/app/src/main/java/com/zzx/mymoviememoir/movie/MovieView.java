package com.zzx.mymoviememoir.movie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.zzx.mymoviememoir.memoir.MemoirAddActivity;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.UserInfo;
import com.zzx.mymoviememoir.watchlist.WatchList;
import com.zzx.mymoviememoir.watchlist.WatchListViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MovieView extends AppCompatActivity {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private TextView tvWelcome, tvCurrentDate, tvMovieName, tvMovieReleaseDate, tvSummary, tvMovieGenre, tvCountries, tvDirectors;
    private ImageView ivMoviePoster;
    private RatingBar mvRatingStar;
    private NetworkConnection networkConnection;
    private MovieDetails movieDetails;
    private ListView mvCastList;
    private Button btnAddWatchList, btnAddMemoir;
    private List<HashMap<String, String>> castListArray;
    private SimpleAdapter myListAdapter;
    private WatchListViewModel watchListViewModel;
    private WatchList watchList;

    String[] colHead = new String[] {"character", "name"};
    int[] dataCell = new int[] {R.id.clCharacter, R.id.clName};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);

        networkConnection = new NetworkConnection();
        final Bundle bundle = getIntent().getExtras();
        movieDetails = bundle.getParcelable("movie_details");

        // get widget
        tvMovieName = findViewById(R.id.mvMovieName);
        tvMovieReleaseDate = findViewById(R.id.mvMovieReleaseDate);
        tvSummary = findViewById(R.id.mvSummary);
        tvMovieGenre = findViewById(R.id.mvMovieGenre);
        tvCountries = findViewById(R.id.mvCountries);
        tvDirectors = findViewById(R.id.mvDirectors);
        ivMoviePoster = findViewById(R.id.mvMoviePoster);
        mvRatingStar = findViewById(R.id.mvRatingStar);
        tvMovieName.setText(movieDetails.getMovieName());
        tvMovieReleaseDate.setText(movieDetails.getReleaseDate());
        mvCastList = findViewById(R.id.mvCastList);
        castListArray = new ArrayList<>();

        // add the toolbar
        Toolbar toolbar = findViewById(R.id.mvToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvWelcome = findViewById(R.id.tvMvWelcomeUser);
        tvCurrentDate = findViewById(R.id.tvMvCurrentDate);
        tvWelcome.setText("Welcome " + UserInfo.getPerFname() + "!");
        SimpleDateFormat s = new SimpleDateFormat("E dd/MM/yyyy");
        tvCurrentDate.setText(s.format(new Date(System.currentTimeMillis())));

        watchListViewModel = new ViewModelProvider(this).get(WatchListViewModel.class);
        watchListViewModel.initalizeVars(getApplication());
        btnAddWatchList = findViewById(R.id.mvBtnAddWatchList);
        // if come from watchList page, disable the button
        if ("1".equals(movieDetails.getIsInWatchList())) {
            btnAddWatchList.setEnabled(false);
        }
        btnAddWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check watchListId first
                CheckWatchListId checkWatchListId = new CheckWatchListId();
                checkWatchListId.execute(movieDetails.getMovieId());
            }
        });

        // set add to memoir button
        btnAddMemoir = findViewById(R.id.mvBtnAddMemoir);
        btnAddMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieView.this, MemoirAddActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("movie_details", movieDetails);
                intent.putExtras(bundle1);
                startActivity(intent);
            }
        });

        GetMovieDetails getMovieDetails = new GetMovieDetails();
        getMovieDetails.execute(movieDetails.getMovieId());
        GetMovieCredits getMovieCredits = new GetMovieCredits();
        getMovieCredits.execute(movieDetails.getMovieId());

    }

    // action bar back button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetMovieDetails extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.getMovieDetails(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
            // poster
            String poster_path = "";
            if (!"null".equals(jsonObject.get("poster_path").toString()))
                poster_path = jsonObject.get("poster_path").getAsString();
            movieDetails.setImagePath(poster_path);
            Picasso.get()
                    .load(IMAGE_URL + poster_path)
                    .error(R.drawable.ic_no_image_available_600_x_450)
                    .resize(500, 500)
                    .centerInside()
                    .into(ivMoviePoster);
            // overview
            String overview = jsonObject.get("overview").getAsString();
            tvSummary.setText(overview);
            // country
            String countries = "";
            if (jsonObject.getAsJsonArray("production_countries").size() != 0)
                countries = jsonObject.getAsJsonArray("production_countries").get(0).getAsJsonObject().get("name").getAsString();
            tvCountries.setText(countries);
            // genres
            JsonArray genresJsonArray = jsonObject.getAsJsonArray("genres");
            String genres = "";
            for (int i = 0; i < genresJsonArray.size(); i++) {
                String genre = genresJsonArray.get(i).getAsJsonObject().get("name").getAsString();
                genres = genres + genre + ", ";
            }
            if (!"".equals(genres))
                genres = genres.substring(0, genres.length()-2);
            tvMovieGenre.setText(genres);
            float rating = jsonObject.get("vote_average").getAsFloat();
            mvRatingStar.setRating(rating);
        }
    }

    // get movie casts and crews
    private class GetMovieCredits extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.getMovieCredits(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            JsonObject jsonObject = new JsonParser().parse(s).getAsJsonObject();
            // cast
            JsonArray castJsonArray = jsonObject.getAsJsonArray("cast");
            for (int i = 0; i < castJsonArray.size(); i++) {
                String character = castJsonArray.get(i).getAsJsonObject().get("character").getAsString();
                String name = castJsonArray.get(i).getAsJsonObject().get("name").getAsString();
                HashMap<String, String> map = new HashMap<>();
                map.put("character", character);
                map.put("name", name);
                castListArray.add(map);
            }
            myListAdapter = new SimpleAdapter(getApplicationContext(), castListArray, R.layout.cast_list, colHead, dataCell);
            mvCastList.setAdapter(myListAdapter);

            // directors
            JsonArray crewJsonArray = jsonObject.getAsJsonArray("crew");
            String directors = "";
            for (int i = 0; i < crewJsonArray.size(); i++) {
                String job = crewJsonArray.get(i).getAsJsonObject().get("job").getAsString();
                if ("Director".equals(job)) {
                    directors = directors + crewJsonArray.get(i).getAsJsonObject().get("name").getAsString() + ", ";
                }
            }
            directors = directors.substring(0, directors.length()-2);
            tvDirectors.setText(directors);
        }
    }

    private class CheckWatchListId extends AsyncTask<String, Void, WatchList> {

        @Override
        protected WatchList doInBackground(String... strings) {
            return watchListViewModel.findById(strings[0]);
        }

        @Override
        protected void onPostExecute(WatchList w) {
            watchList = w;
            if (w != null) {
                // if exist, do nothing
                Toast.makeText(getApplicationContext(), "You have already added this movie into the watch list! Please check it in Watchlist page", Toast.LENGTH_LONG).show();
            } else {
                // if not exist, add to watchList
                SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateAndTime = s.format(new Date(System.currentTimeMillis()));
                final String date = dateAndTime.substring(0, 10);
                final String time = dateAndTime.substring(11, 19);
                watchList = new WatchList(movieDetails.getMovieId(), movieDetails.getMovieName(), movieDetails.getReleaseDate(), date, time);
                watchListViewModel.insert(watchList);
                Toast.makeText(getApplicationContext(), "Add successfully", Toast.LENGTH_SHORT).show();
            }
            btnAddWatchList.setEnabled(false);
        }
    }
}
