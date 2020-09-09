package com.example.nerdeyesem.Fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nerdeyesem.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.nerdeyesem.Model.RestaurantModel;

public class FragmentMap extends Fragment implements OnMapReadyCallback {


    private final static String BASE_URL = "https://developers.zomato.com/api/v2.1/search?count=5&lat=";
    private double latitude, longitude;
    List<RestaurantModel> restaurantCards;
    List<LatLng> locationList = new ArrayList<>();
    List<MarkerOptions> markerOptionsList;
    @Nullable
    private GoogleMap mMap;
    private MapView mMapView;
    private final static String TAG = "EDA LOG";


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_map, container, false);

        markerOptionsList = new ArrayList<MarkerOptions>();
        restaurantCards = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("long");
        }


        final SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        getData();
        mapFragment.getMapAsync(this);

        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("permissionDenied", "no permission");
            return;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (mMap != null && markerOptionsList != null) {
            for (int i = 0; i < markerOptionsList.size(); i++) {
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.addMarker(markerOptionsList.get(i));
                builder.include(markerOptionsList.get(i).getPosition());
                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 300, 200, 5);
                mMap.animateCamera(cu);
                mMap.moveCamera(cu);
            }

        }

    }

    private void createMarker(String name, LatLng newLoc) {
        markerOptionsList.add(new MarkerOptions().position(newLoc).title(name).icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

    }

    private void getData(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = BASE_URL + latitude + "&lon=" + longitude;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("restaurants");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectRes = jsonArray.getJSONObject(i);


                                JSONObject jsonObject = jsonObjectRes.getJSONObject("restaurant");
                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String url = jsonObject.getString("url");
                                String imgUrl = jsonObject.getString("featured_image");

                                JSONObject jsonObjectLoc = jsonObject.getJSONObject("location");
                                String latitudeSt = jsonObjectLoc.getString("latitude");
                                String longitudeSt = jsonObjectLoc.getString("longitude");
                                String address = jsonObjectLoc.getString("address");

                                JSONObject jsonObjectRtng = jsonObject.getJSONObject("user_rating");
                                String rating = jsonObjectRtng.getString("aggregate_rating") + " - " + jsonObjectRtng.getString("rating_text");


                                Double latitudeRes = Double.parseDouble(latitudeSt);
                                Double longitudeRes = Double.parseDouble(longitudeSt);
                                restaurantCards.add(new RestaurantModel(id, name, url, latitudeSt, longitudeSt, imgUrl, rating, address));
                                LatLng newLoc = new LatLng(latitudeRes, longitudeRes);
                                createMarker(name, newLoc);
                                Log.i(TAG, name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "6b2efadcf65943310e10aa34f43cd343");
                params.put("content-type", "application/json");
                return params;
            }
        };

        requestQueue.add(request);
    }
}
