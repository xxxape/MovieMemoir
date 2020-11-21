package com.zzx.mymoviememoir.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.UserInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends Fragment {

    private NetworkConnection networkConnection;
    List<HashMap<String, String>> movieListArray;
    SimpleAdapter myListAdapter;
    ListView movieList;

    String[] colHead = new String[] {"id", "movie_name", "release_date", "rating"};
    int[] dataCell = new int[] {R.id.tmId, R.id.tmMovieName, R.id.tmReleaseDate, R.id.tmRatingScore};

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        networkConnection = new NetworkConnection();
        movieList = view.findViewById(R.id.lvTopMovie);
        movieListArray = new ArrayList<>();

        String perId = UserInfo.getPerId();
        // get user memoir data by perId
        GetData getData = new GetData();
        getData.execute(perId);
        return view;
    }

    private class GetData extends AsyncTask<String, Void, JsonArray> {

        @Override
        protected JsonArray doInBackground(String... strings) {
            return networkConnection.findRecentTopMovies(strings[0]);
        }

        @Override
        protected void onPostExecute(JsonArray jsonElements) {
            for (int i = 0; i < jsonElements.size(); i++) {
                JsonObject jsonObject = jsonElements.get(i).getAsJsonObject();
                String movieName = jsonObject.get("movieName").getAsString();
                String movieReleaseDate = jsonObject.get("movieReleaseDate").getAsString();
                movieReleaseDate = movieReleaseDate.substring(7, 11) + movieReleaseDate.substring(4, 8) + movieReleaseDate.substring(24, 28);
                String ratingScore = jsonObject.get("ratingScore").getAsString();
                HashMap<String, String> map = new HashMap<>();
                map.put("id", String.valueOf(i+1));
                map.put("movie_name", movieName);
                map.put("release_date", movieReleaseDate);
                map.put("rating", ratingScore);
                movieListArray.add(map);
            }
            myListAdapter = new SimpleAdapter(getContext(), movieListArray, R.layout.top_movies_list, colHead, dataCell);
            movieList.setAdapter(myListAdapter);
        }
    }
}
