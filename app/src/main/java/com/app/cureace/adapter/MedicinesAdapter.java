package com.app.cureace.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cureace.R;
import com.app.cureace.activity.ambulance.AmbulanceDetailsActivity;
import com.app.cureace.activity.medicines.MedicinesDetailsActivity;
import com.app.cureace.model.AmbulanceModel;
import com.app.cureace.model.MedicineModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MedicinesAdapter extends RecyclerView.Adapter<MedicinesAdapter.ViewHolder> {

    List<MedicineModel> ambulanceModelList;
    Context context;

    public MedicinesAdapter(List<MedicineModel> ambulanceModelList, Context context) {
        this.ambulanceModelList = ambulanceModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicinesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.medicine_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicinesAdapter.ViewHolder holder, int position) {

        holder.name_tv.setText(ambulanceModelList.get(position).getM_title());
        holder.price_tv.setText("Price : "+ambulanceModelList.get(position).getM_price()+" Rs");
        holder.date_tv.setText("Expiry Date : " +ambulanceModelList.get(position).getM_exp_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicinesDetailsActivity.class);
                intent.putExtra("data",ambulanceModelList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


        Picasso.with(context).load(ambulanceModelList.get(position).getM_image()).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return ambulanceModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public TextView name_tv,price_tv,date_tv;
         public ImageView imageView;
         public ViewHolder(View itemView) {
            super(itemView);
             name_tv = (TextView) itemView.findViewById(R.id.name_tv);
             price_tv = (TextView) itemView.findViewById(R.id.price_tv);
             date_tv = (TextView) itemView.findViewById(R.id.date_tv);
             imageView =   itemView.findViewById(R.id.imageView);

        }
    }



}
