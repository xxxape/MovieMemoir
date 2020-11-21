package com.zzx.mymoviememoir.memoir;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.model.MovieDetails;
import com.zzx.mymoviememoir.server.Cinema;
import com.zzx.mymoviememoir.server.Memoir;
import com.zzx.mymoviememoir.server.ReUseData;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MemoirAddActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private TextView tvWelcome, tvCurrentDate, tvMovieName, tvReleaseDate, tvNewCinema;
    private ImageView ivPoster;
    private EditText etWatchDate, etWatchTime, etComment;
    private MovieDetails movieDetails;
    private NetworkConnection networkConnection;
    private Spinner spMaCinema;
    private Calendar calendar;
    private ArrayAdapter<String> spinnerAdapter;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private String movName, movRelDate, userWDate, userWTime, userComment;
    private float ratingStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memoir_add);

        final Bundle bundle = getIntent().getExtras();
        movieDetails = bundle.getParcelable("movie_details");
        networkConnection = new NetworkConnection();
        GetMaxCinemaId getMaxCinemaId = new GetMaxCinemaId();
        getMaxCinemaId.execute();
        GetMaxMemoirId getMaxMemoirId = new GetMaxMemoirId();
        getMaxMemoirId.execute();

        // add the toolbar
        Toolbar toolbar = findViewById(R.id.maToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvWelcome = findViewById(R.id.tvMaWelcomeUser);
        tvCurrentDate = findViewById(R.id.tvMaCurrentDate);
        tvWelcome.setText("Welcome " + UserInfo.getPerFname() + "!");
        SimpleDateFormat s = new SimpleDateFormat("E dd/MM/yyyy");
        tvCurrentDate.setText(s.format(new Date(System.currentTimeMillis())));

        // set movie name and release date
        movName = movieDetails.getMovieName();
        movRelDate = movieDetails.getReleaseDate();
        String poster_path = movieDetails.getImagePath();
        tvMovieName = findViewById(R.id.tvMaMovieName);
        tvMovieName.setText(movName);
        tvReleaseDate = findViewById(R.id.tvMaReleaseDate);
        tvReleaseDate.setText(movRelDate);
        ivPoster = findViewById(R.id.ivMaPoster);
        Picasso.get()
                .load(IMAGE_URL + poster_path)
                .error(R.drawable.ic_no_image_available_600_x_450)
                .resize(500, 500)
                .centerInside()
                .into(ivPoster);

        // set watch date editText
        calendar = Calendar.getInstance();
        etWatchDate = findViewById(R.id.etMaWatchDate);
        etWatchDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MemoirAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                etWatchDate.setText(new StringBuilder()
                                        .append(year)
                                        .append("-")
                                        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                        .append("-")
                                        .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
                            }
                        },
                        "".equals(etWatchDate.getText().toString())?calendar.get(Calendar.YEAR):Integer.valueOf(etWatchDate.getText().toString().substring(0,4)),
                        "".equals(etWatchDate.getText().toString())?calendar.get(Calendar.MONTH):Integer.valueOf(etWatchDate.getText().toString().substring(5,7))-1,
                        "".equals(etWatchDate.getText().toString())?calendar.get(Calendar.DAY_OF_MONTH):Integer.valueOf(etWatchDate.getText().toString().substring(8,10))
                ).show();
            }
        });
        // set watch time editText
        etWatchTime = findViewById(R.id.etMaWatchTime);
        etWatchTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MemoirAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                etWatchTime.setText(new StringBuilder()
                                        .append(hourOfDay < 10 ? "0" + hourOfDay : hourOfDay)
                                        .append(":")
                                        .append(minute < 10 ? "0" + minute : minute));
                            }
                        },
                        "".equals(etWatchTime.getText().toString())?calendar.get(Calendar.HOUR):Integer.valueOf(etWatchTime.getText().toString().substring(0, 2)),
                        "".equals(etWatchTime.getText().toString())?calendar.get(Calendar.MINUTE):Integer.valueOf(etWatchTime.getText().toString().substring(3, 5)),
                        true).show();
            }
        });

        // set cinemas spinner
        spMaCinema = findViewById(R.id.spMaCinema);
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        spMaCinema.setAdapter(spinnerAdapter);
        GetAllCinemas getAllCinemas = new GetAllCinemas();
        getAllCinemas.execute();

        // set add new cinema
        tvNewCinema = findViewById(R.id.tvMaNewCinema);
        tvNewCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Create a new cinema info");
                final View view = View.inflate(v.getContext(), R.layout.new_cinema, null);
                builder.setView(view);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText etNcName = view.findViewById(R.id.etNcName);
                        EditText etNcPostcode = view.findViewById(R.id.etNcPostcode);
                        String cinName = etNcName.getText().toString();
                        String cinPostcode = etNcPostcode.getText().toString();
                        boolean flag = true;
                        for (Cinema cinema: ReUseData.getCinemas()) {
                            if (cinName.equals(cinema.getCinname()) && cinPostcode.equals(cinema.getCinpostcode())) {
                                flag = false;
                                Toast.makeText(getBaseContext(), "This cinema is existed!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (flag) {
                            Cinema cinema = new Cinema(ReUseData.getMaxCinId(), cinName, cinPostcode);
                            AddCinema addCinema = new AddCinema();
                            addCinema.execute(cinema);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

        // set rating bar and comment
        ratingBar = findViewById(R.id.maRating);
        etComment = findViewById(R.id.etMaComment);

        btnSubmit = findViewById(R.id.btnMaSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!"".equals(etWatchDate.getText().toString()) && !"".equals(etWatchTime.getText().toString()) && !"".equals(etComment.getText().toString())) {
                    userWDate = etWatchDate.getText().toString() + "T00:00:00+08:00";
                    userWTime = "1970-01-01T" + etWatchTime.getText().toString() + ":00+08:00";
                    userComment = etComment.getText().toString();
                    ratingStar = ratingBar.getRating();
                    String releaseDate = movRelDate + "T00:00:00+08:00";
                    int position = spMaCinema.getSelectedItemPosition();
                    int perId = Integer.parseInt(UserInfo.getPerId());
                    int cinId = ReUseData.getCinemas().get(position).getCinid();
                    Memoir memoir = new Memoir(ReUseData.getMaxMemoId(), movName, releaseDate, userWDate, userWTime, userComment, ratingStar);
                    memoir.setPerid(perId);
                    memoir.setCinid(cinId);
                    AddMemoir addMemoir = new AddMemoir();
                    addMemoir.execute(memoir);
                } else {
                    Toast.makeText(v.getContext(), "Please enter all the information first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetAllCinemas extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllCinemas();
        }

        @Override
        protected void onPostExecute(String s) {
            JsonArray cinemasJsonArray = new JsonParser().parse(s).getAsJsonArray();
            for (int i = 0; i < cinemasJsonArray.size(); i++) {
                String strCinId = cinemasJsonArray.get(i).getAsJsonObject().get("cinid").getAsString();
                int cinId = Integer.valueOf(strCinId);
                String cinName = cinemasJsonArray.get(i).getAsJsonObject().get("cinname").getAsString();
                String cinPostcode = cinemasJsonArray.get(i).getAsJsonObject().get("cinpostcode").getAsString();
                Cinema cinema = new Cinema(cinId, cinName, cinPostcode);
                ReUseData.addCinema(cinema);
                spinnerAdapter.add(cinName + " " + cinPostcode);
                spinnerAdapter.notifyDataSetChanged();
            }
            super.onPostExecute(s);
        }
    }

    private class GetMaxCinemaId extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return networkConnection.getMaxCinemaId();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ReUseData.setMaxCinId(integer);
        }
    }

    private class GetMaxMemoirId extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return networkConnection.getMaxMemoirId();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            ReUseData.setMaxMemoId(integer);
        }
    }

    private class AddCinema extends AsyncTask<Cinema, Void, HashMap<Integer, Cinema>> {

        @Override
        protected HashMap<Integer, Cinema> doInBackground(Cinema... cinemas) {
            return networkConnection.addCinema(cinemas[0]);
        }

        @Override
        protected void onPostExecute(HashMap<Integer, Cinema> integerCinemaHashMap) {
            Cinema cinema = null;
            if (integerCinemaHashMap.get(204) != null) {
                cinema = integerCinemaHashMap.get(204);
                ReUseData.addCinema(cinema);
                String item = cinema.getCinname() + " " + cinema.getCinpostcode();
                spinnerAdapter.add(item);
                spinnerAdapter.notifyDataSetChanged();
                spMaCinema.setSelection(spinnerAdapter.getPosition(item));
            }
        }
    }

    private class AddMemoir extends AsyncTask<Memoir, Void, Integer> {

        @Override
        protected Integer doInBackground(Memoir... memoirs) {
            return networkConnection.addMemoir(memoirs[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 204) {
                //insert successfully
                Toast.makeText(getApplicationContext(), "Add to memoir successfully!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Fail to add to memoir! Please try it again later!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
