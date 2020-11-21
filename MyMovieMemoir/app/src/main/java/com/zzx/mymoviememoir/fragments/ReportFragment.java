package com.zzx.mymoviememoir.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportFragment extends Fragment {

    private NetworkConnection networkConnection;

    private PieChart pieChart;
    private BarChart barChart;
    private EditText etStartDate, etEndDate;
    private Calendar calendar;
    private Button btnPie, btnBar;
    private Spinner yearSpinner;
    private String perId;
    private int[] resColors;

    public ReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        resColors = new int[]{R.color.color1, R.color.color2, R.color.color3, R.color.color4};
        perId = UserInfo.getPerId();
        networkConnection = new NetworkConnection();

        calendar = Calendar.getInstance();
        etStartDate = view.findViewById(R.id.rpEtStartDate);
        etEndDate = view.findViewById(R.id.rpEtEndDate);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                etStartDate.setText(new StringBuilder()
                                        .append(year)
                                        .append("-")
                                        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                        .append("-")
                                        .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
                            }
                        },
                        "".equals(etStartDate.getText().toString())?calendar.get(Calendar.YEAR):Integer.valueOf(etStartDate.getText().toString().substring(0,4)),
                        "".equals(etStartDate.getText().toString())?calendar.get(Calendar.MONTH):Integer.valueOf(etStartDate.getText().toString().substring(5,7))-1,
                        "".equals(etStartDate.getText().toString())?calendar.get(Calendar.DAY_OF_MONTH):Integer.valueOf(etStartDate.getText().toString().substring(8,10))
                ).show();
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                etEndDate.setText(new StringBuilder()
                                        .append(year)
                                        .append("-")
                                        .append((month + 1) < 10 ? "0" + (month + 1) : (month + 1))
                                        .append("-")
                                        .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
                            }
                        },
                        "".equals(etEndDate.getText().toString())?calendar.get(Calendar.YEAR):Integer.valueOf(etEndDate.getText().toString().substring(0,4)),
                        "".equals(etEndDate.getText().toString())?calendar.get(Calendar.MONTH):Integer.valueOf(etEndDate.getText().toString().substring(5,7))-1,
                        "".equals(etEndDate.getText().toString())?calendar.get(Calendar.DAY_OF_MONTH):Integer.valueOf(etEndDate.getText().toString().substring(8,10))
                ).show();
            }
        });

        btnPie = view.findViewById(R.id.rpBtnPie);
        btnPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strStartDate = etStartDate.getText().toString();
                String strEndDate = etEndDate.getText().toString();
                if (!"".equals(strStartDate) && !"".equals(strEndDate)) {
                    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date startDate = s.parse(strStartDate);
                        Date endDate = s.parse(strEndDate);
                        if (startDate.getTime() < endDate.getTime()) {
                            // date range valid
                            CountNumByPostcode countNumByPostcode = new CountNumByPostcode();
                            countNumByPostcode.execute(perId, strStartDate, strEndDate);
                        } else
                            // date range invalid
                            Toast.makeText(getContext(), "The selected date range is not valid. Please select again!", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // date is empty
                    Toast.makeText(getContext(), "Please select date first!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        yearSpinner = view.findViewById(R.id.rpYearSpinner);
        btnBar = view.findViewById(R.id.rpBtnBar);
        btnBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountNumByMonth countNumByMonth = new CountNumByMonth();
                countNumByMonth.execute(perId, yearSpinner.getSelectedItem().toString());
            }
        });

        // pie
        pieChart = view.findViewById(R.id.pieChart);
        // bar
        barChart = view.findViewById(R.id.barChart);
        return view;
    }

    private class CountNumByPostcode extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.countNumByPostcode(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            DrawPie(s);
        }
    }

    private class CountNumByMonth extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            return networkConnection.countNumByMonth(strings);
        }

        @Override
        protected void onPostExecute(String s) {
            DrawBar(s);
        }
    }

    /**
     * draw pie chart
     * @param rawData
     */
    private void DrawPie(String rawData) {
        JsonArray jsonArray = new JsonParser().parse(rawData).getAsJsonArray();
        int total_number = 0;
        for (int i = 0; i < jsonArray.size(); i++) {
            int number = jsonArray.get(i).getAsJsonObject().get("total_number").getAsInt();
            total_number += number;
        }
        List<PieEntry> entries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            float percent = jsonArray.get(i).getAsJsonObject().get("total_number").getAsFloat() / total_number;
            String postcode = jsonArray.get(i).getAsJsonObject().get("cinemapostcode").getAsString();
            entries.add(new PieEntry(percent, postcode));
            colors.add(getResources().getColor(resColors[i<4?i:i%4]));
        }
        PieDataSet set = new PieDataSet(entries, "Total number of movies watched per postcode");
        set.setColors(colors);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(13f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);
        Description description = new Description();
        description.setEnabled(false);
        pieChart.setDescription(description);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();
    }

    /**
     * draw bar chart
     * @param rawData
     */
    private void DrawBar(String rawData) {
        JsonArray jsonArray = new JsonParser().parse(rawData).getAsJsonArray();
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 1; i <= jsonArray.size(); i++) {
            float total_number = jsonArray.get(i-1).getAsJsonObject().get("total_number").getAsFloat();
            entries.add(new BarEntry(i, total_number));
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(13f);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String[] month = new String[] {"", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", ""};
                return month[(int)value];
            }
        });
        xAxis.setDrawLabels(true);
        xAxis.setLabelCount(12);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setGranularity(1f);
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        BarDataSet set = new BarDataSet(entries, "Total number of movies watched per month in " + yearSpinner.getSelectedItem().toString());
        set.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                String str = String.valueOf(value);
                if (str.length() == 0)
                    return str;
                return str.substring(0, str.indexOf("."));
            }
        });
        set.setColor(getResources().getColor(R.color.color4));
        set.setValueTextSize(13f);
        BarData data = new BarData(set);
        data.setBarWidth(0.8f); // set custom bar width
        barChart.setData(data);
        Description description = new Description();
        description.setEnabled(false);
        barChart.setDescription(description);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleXEnabled(false);
        barChart.setScaleYEnabled(false);
        barChart.invalidate(); // refresh
    }
}
