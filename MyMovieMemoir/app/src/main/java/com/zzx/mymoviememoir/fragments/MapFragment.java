package com.zzx.mymoviememoir.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.zzx.mymoviememoir.R;
import com.zzx.mymoviememoir.tools.NetworkConnection;
import com.zzx.mymoviememoir.user.UserInfo;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private LatLng currentLocation;
    private NetworkConnection networkConnection;
    private String perId;

    public MapFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        networkConnection = new NetworkConnection();

        perId = UserInfo.getPerId();
        GetPersonById getPersonById = new GetPersonById();
        getPersonById.execute(perId);
        GetAllCinemas getAllCinemas = new GetAllCinemas();
        getAllCinemas.execute();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            FragmentManager fm= getFragmentManager();
            FragmentTransaction ft= fm.beginTransaction();
            mapFragment= SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        /*Zoom levels
        1: World
        5: Landmass/continent
        10: City
        15: Streets
        20: Buildings*/
    }

    private class GetPersonById extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            // get person info by perId
            return networkConnection.getPersonById(strings[0], "0");
        }

        @Override
        protected void onPostExecute(String s) {
            JsonObject person = new JsonParser().parse(s).getAsJsonObject();
            String addrStNo = person.get("perstno").getAsString();
            String addrStName = person.get("perstname").getAsString();
            String addrState = person.get("perstate").getAsString();
            String addrPostcode = person.get("perpostcode").getAsString();
            String address = addrStNo.replace(" ", "%20") + addrStName.replace(" ", "%20") + addrState.replace(" ", "%20") + addrPostcode;
            GetLatLngByAddress getLatLngByAddress = new GetLatLngByAddress();
            getLatLngByAddress.execute(address, "u");
        }
    }

    private class GetLatLngByAddress extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... strings) {
            return networkConnection.getLatLngByAddress(strings);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (!"null".equals(strings[0])) {
                double lat = Double.valueOf(strings[0]);
                double lng = Double.valueOf(strings[1]);
                currentLocation = new LatLng(lat, lng);
                float zoomLevel = (float)15.0;
                if ("u".equals(strings[2])) {
                    mMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title("My location")
                            .icon(BitmapDescriptorFactory.defaultMarker(29)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, zoomLevel));
                } else {
                    mMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title(strings[2])
                            .icon(BitmapDescriptorFactory.defaultMarker(210)));
                }
            }
        }
    }

    private class GetAllCinemas extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return networkConnection.getAllCinemas();
        }

        @Override
        protected void onPostExecute(String s) {
            JsonArray cinemas = new JsonParser().parse(s).getAsJsonArray();
            for (int i = 0; i < cinemas.size(); i++) {
                String cinName = cinemas.get(i).getAsJsonObject().get("cinname").getAsString();
                String cinPostcode = cinemas.get(i).getAsJsonObject().get("cinpostcode").getAsString();
                String address = cinName.replace(" ", "%20") + "+" + cinPostcode + "+Australia";
                GetLatLngByAddress getLatLngByAddress = new GetLatLngByAddress();
                getLatLngByAddress.execute(address, cinName);
            }
        }
    }
}
