package com.app.cureace.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cureace.R;
import com.app.cureace.activity.bedbooking.BedDetailsActivity;
import com.app.cureace.activity.doctor.DoctorDetailsActivity;
import com.app.cureace.model.BedModel;
import com.app.cureace.model.DoctorModel;

import java.util.List;

public class BedListAdapter extends RecyclerView.Adapter<BedListAdapter.ViewHolder> {

    List<BedModel> bedList;
    Context context;

    public BedListAdapter(List<BedModel> bedList, Context context) {
        this.bedList = bedList;
        this.context = context;
    }

    @NonNull
    @Override
    public BedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.doctor_list_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull BedListAdapter.ViewHolder holder, int position) {

        holder.name_tv.setText(bedList.get(position).getHospital_name() + " Hospital");
        holder.expertise_tv.setText("address : "+bedList.get(position).getHospital_address());
        holder.h_name_tv.setText("Available Beds  : " +bedList.get(position).getAvailable_beds());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BedDetailsActivity.class);
                intent.putExtra("data",bedList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return bedList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public TextView name_tv,expertise_tv,h_name_tv;
         public ViewHolder(View itemView) {
            super(itemView);
             name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            expertise_tv = (TextView) itemView.findViewById(R.id.expertise_tv);
            h_name_tv = (TextView) itemView.findViewById(R.id.h_name_tv);

        }
    }



}
