package com.example.nerdeyesem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdeyesem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.example.nerdeyesem.Model.RestaurantModel;

public class RecyclerAdapterRestaurants extends RecyclerView.Adapter<RecyclerAdapterRestaurants.RecyclerViewHolder> {

    private List<RestaurantModel> mrstCards;
    private AdapterView.OnItemClickListener mListener;
    private Context mContext;

    public RecyclerAdapterRestaurants(Context context,List<RestaurantModel> rstCards) {
        mContext = context;
        mrstCards = rstCards;
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        public TextView name,rating,address,webPage;
        public ImageView imageRes;

        public RecyclerViewHolder(@NonNull View itemView, final AdapterView.OnItemClickListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.textName);
            rating = itemView.findViewById(R.id.textRating);
            address = itemView.findViewById(R.id.textAddress);
            webPage = itemView.findViewById(R.id.textWeb);
            imageRes = itemView.findViewById(R.id.imageViewRes);

        }
    }



    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_card, parent,false);
        RecyclerViewHolder rvh = new RecyclerViewHolder(v, mListener);
        mContext = parent.getContext();
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        RestaurantModel currents_rstCards = mrstCards.get(position);
        holder.name.setText(currents_rstCards.getName());
        holder.rating.setText(currents_rstCards.getUserRating());
        holder.webPage.setText(currents_rstCards.getUrl());
        holder.address.setText(currents_rstCards.getAddress());
        Picasso.get().load(currents_rstCards.getFeaturedImage()).into(holder.imageRes);
    }

    @Override
    public int getItemCount() {
        return mrstCards.size();
    }
}
