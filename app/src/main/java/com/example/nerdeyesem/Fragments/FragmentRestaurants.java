package com.example.nerdeyesem.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.nerdeyesem.Adapter.RecyclerAdapterRestaurants;
import com.example.nerdeyesem.Model.RestaurantModel;
import com.example.nerdeyesem.R;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentRestaurants extends Fragment {

    private final static String TAG="EDA LOG";
    private final static String BASE_URL ="https://developers.zomato.com/api/v2.1/search?count=5&lat=";
    private double latitude, longitude;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<RestaurantModel> restaurantCards;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_restaurants, container, false);
        restaurantCards = new ArrayList<>();
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerRestaurants);
        mRecyclerView.setHasFixedSize(true);
        mManager = new LinearLayoutManager(getContext());

        Bundle bundle = getArguments();
        if(bundle != null){
            latitude = bundle.getDouble("lat");
            longitude = bundle.getDouble("long");
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = BASE_URL + latitude+ "&lon=" + longitude;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("restaurants");
                            for (int i = 0; i < jsonArray.length(); i++){
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

                                addRestaurants(id, name, url, latitudeSt, longitudeSt, imgUrl, rating, address);

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
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user-key", "6b2efadcf65943310e10aa34f43cd343");
                params.put("content-type", "application/json");
                return params;
            }
        };
        requestQueue.add(request);
        return v;
    }

    private void addRestaurants(String id, String name, String url, String latitudeSt, String longitudeSt, String imgUrl, String rating, String address){
        restaurantCards.add(new RestaurantModel(id, name, url, latitudeSt, longitudeSt, imgUrl, rating, address));
        mAdapter = new RecyclerAdapterRestaurants(getContext(),restaurantCards);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(mManager);
        mRecyclerView.setAdapter(mAdapter);

    }
}
