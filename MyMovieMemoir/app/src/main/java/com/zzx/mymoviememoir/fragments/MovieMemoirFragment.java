package com.zzx.mymoviememoir.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.model.MemoirList;
import com.zzx.mymoviememoir.adapters.MemoirListAdapter;
import com.zzx.mymoviememoir.server.Memoir;
import com.zzx.mymoviememoir.tools.NetworkConnection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MovieMemoirFragment extends Fragment {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private Button btnSortByDate, btnSortByUserRate, btnSortByPublicRate;
    private NetworkConnection networkConnection;
    private RecyclerView rvMemoirList;
    private MemoirListAdapter memoirListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<MemoirList> memoirLists;
    private Spinner spinnerGenre;
    private List<String> genreList;
    private List<HashMap<String, String>> genreMap;
    private ArrayAdapter<String> spinnerAdapter;
    private Button btnFilter, btnClear;

    public MovieMemoirFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_memoir, container, false);
        networkConnection = new NetworkConnection();

        // get all genres
        spinnerGenre = view.findViewById(R.id.spinnerGenre);
        genreList = new ArrayList<>();
        genreMap = new ArrayList<>();
        GetAllGenres getAllGenres = new GetAllGenres();
        getAllGenres.execute();

        // set recycler view
        rvMemoirList = view.findViewById(R.id.rvMemoirList);
        memoirLists = new ArrayList<>();
        memoirListAdapter = new MemoirListAdapter(memoirLists);
        rvMemoirList.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rvMemoirList.setAdapter(memoirListAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        rvMemoirList.setLayoutManager(layoutManager);
        GetAllMemoirs getAllMemoirs = new GetAllMemoirs();
        getAllMemoirs.execute();

        // filter and clear button
        btnFilter = view.findViewById(R.id.btnFilter);
        btnClear = view.findViewById(R.id.btnClear);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String genreSelected = spinnerGenre.getSelectedItem().toString();
                String genreId = "";
                for (int i = 0; i < genreMap.size(); i++) {
                    if (genreMap.get(i).containsKey(genreSelected)) {
                        genreId = genreMap.get(i).get(genreSelected);
                        break;
                    }
                }
                List<MemoirList> filterMemoirs = new ArrayList<>();
                for (int i = 0; i < memoirLists.size(); i++) {
                    String[] genresId = memoirLists.get(i).getGenres();
                    if (Arrays.asList(genresId).contains(genreId))
                        filterMemoirs.add(memoirLists.get(i));
                }
                setData(filterMemoirs);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setData(memoirLists);
            }
        });

        // sort button
        btnSortByDate = view.findViewById(R.id.btnSortByDate);
        btnSortByUserRate = view.findViewById(R.id.btnSortByUserRate);
        btnSortByPublicRate = view.findViewById(R.id.btnSortByPublicRate);
        // sort by date
        btnSortByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(memoirLists, new Comparator<MemoirList>() {
                    @Override
                    public int compare(MemoirList o1, MemoirList o2) {
                        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date d1 = s.parse(o1.getMemoir().getMovreldate());
                            Date d2 = s.parse(o2.getMemoir().getMovreldate());
                            if (d1.getTime() > d2.getTime())
                                return -1;
                            else if (d1.getTime() < d2.getTime())
                                return 1;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return 0;
                    }
                });
                setData(memoirLists);
            }
        });

        // sort by user rate
        btnSortByUserRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(memoirLists, new Comparator<MemoirList>() {
                    @Override
                    public int compare(MemoirList o1, MemoirList o2) {
                        float diff = o1.getMemoir().getRatingstar() - o2.getMemoir().getRatingstar();
                        if (diff < 0)
                            return 1;
                        else if (diff > 0)
                            return -1;
                        return 0;
                    }
                });
                setData(memoirLists);
            }
        });

        // sort by public rate
        btnSortByPublicRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(memoirLists, new Comparator<MemoirList>() {
                    @Override
                    public int compare(MemoirList o1, MemoirList o2) {
                        float diff = o1.getPublicRating() - o2.getPublicRating();
                        if (diff < 0)
                            return 1;
                        else if (diff > 0)
                            return -1;
                        return 0;
                    }
                });
                setData(memoirLists);
            }
        });

        return view;
    }

    private class GetAllMemoirs extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllMemoirs();
        }

        @Override
        protected void onPostExecute(String s) {
            JsonArray memoirsJsonArray = new JsonParser().parse(s).getAsJsonArray();
            for (int i = 0; i < memoirsJsonArray.size(); i++) {
                JsonObject memoirJsonObject = memoirsJsonArray.get(i).getAsJsonObject();
                Memoir memoir = new Memoir();
                memoir.setMemoid(memoirJsonObject.get("memoid").getAsInt());
                memoir.setMovname(memoirJsonObject.get("movname").getAsString());
                memoir.setMovreldate(memoirJsonObject.get("movreldate").getAsString().substring(0, 10));
                memoir.setUserwdate(memoirJsonObject.get("userwdate").getAsString().substring(0, 10));
                memoir.setUserwtime(memoirJsonObject.get("userwtime").getAsString().substring(11, 16));
                memoir.setUsercomment(memoirJsonObject.get("usercomment").getAsString());
                memoir.setRatingstar(memoirJsonObject.get("ratingstar").getAsFloat());
                memoir.setPerid(memoirJsonObject.get("perid").getAsJsonObject().get("perid").getAsInt());
                memoir.setCinid(memoirJsonObject.get("cinid").getAsJsonObject().get("cinid").getAsInt(),
                                memoirJsonObject.get("cinid").getAsJsonObject().get("cinpostcode").getAsString());
                MemoirList currentMemoir = new MemoirList();
                currentMemoir.setMemoir(memoir);
                MovieSearchByTMDB movieSearchByTMDB = new MovieSearchByTMDB();
                movieSearchByTMDB.execute(currentMemoir);
            }
            super.onPostExecute(s);
        }
    }

    private class MovieSearchByTMDB extends AsyncTask<MemoirList, Void, MemoirList> {

        @Override
        protected MemoirList doInBackground(MemoirList... memoirLists) {
            return networkConnection.movieSearchByTMDB(memoirLists[0]);
        }

        @Override
        protected void onPostExecute(MemoirList memoirList) {
            String poster_path = IMAGE_URL + memoirList.getPosterPath();
            memoirList.setPosterPath(poster_path);
            addData(memoirList);
        }
    }

    private class GetAllGenres extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllGenres();
        }

        @Override
        protected void onPostExecute(String s) {
            JsonArray genresJson = new JsonParser().parse(s).getAsJsonObject().get("genres").getAsJsonArray();
            for (int i = 0; i < genresJson.size(); i++) {
                String id = genresJson.get(i).getAsJsonObject().get("id").getAsString();
                String genre = genresJson.get(i).getAsJsonObject().get("name").getAsString();
                HashMap<String, String> map = new HashMap<>();
                map.put(genre, id);
                genreMap.add(map);
                genreList.add(genre);
            }
            spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, genreList);
            spinnerGenre.setAdapter(spinnerAdapter);
        }
    }

    private void setData(List<MemoirList> memoirList) {
        memoirListAdapter.setMemoir(memoirList);
    }

    private void addData(MemoirList memoirList) {
        memoirLists.add(memoirList);
        memoirListAdapter.setMemoir(memoirLists);
    }
}
