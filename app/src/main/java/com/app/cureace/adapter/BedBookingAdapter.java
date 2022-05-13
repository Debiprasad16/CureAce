package com.app.cureace.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cureace.R;
import com.app.cureace.activity.bedbooking.MyBedBookingDetailsActivity;
import com.app.cureace.activity.doctor.AppointmentDetailsActivity;
import com.app.cureace.model.AppointmentModel;
import com.app.cureace.model.BedBookingModel;
import com.app.cureace.utils.AppUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BedBookingAdapter extends RecyclerView.Adapter<BedBookingAdapter.ViewHolder> {

    List<BedBookingModel> userList;
    Context context;

    public BedBookingAdapter(List<BedBookingModel> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public BedBookingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.appointment_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull BedBookingAdapter.ViewHolder holder, int position) {

        holder.appointmentid_tv.setText("ID :" + userList.get(position).getBed_booking_id());
        holder.name_tv.setText("Hospital Name : "+userList.get(position).getHospital_name());
        holder.expertise_tv.setText("Address : "+userList.get(position).getHospital_address());
        holder.h_name_tv.setText("No.of days  : "+userList.get(position).getNo_of_days());
        holder.date_tv.setText("Start date of Booking  : "+userList.get(position).getStart_date());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyBedBookingDetailsActivity.class);
                intent.putExtra("data",userList.get(position));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAppointment(position,holder);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         public TextView appointmentid_tv,name_tv,expertise_tv,h_name_tv,date_tv;

         public AppCompatButton delete_btn;
        public ViewHolder(View itemView) {
            super(itemView);
            appointmentid_tv = (TextView) itemView.findViewById(R.id.appointmentid_tv);
             name_tv = (TextView) itemView.findViewById(R.id.name_tv);
            expertise_tv = (TextView) itemView.findViewById(R.id.expertise_tv);
            h_name_tv = (TextView) itemView.findViewById(R.id.h_name_tv);
            date_tv = (TextView) itemView.findViewById(R.id.date_tv);
            delete_btn =  itemView.findViewById(R.id.delete_btn);

        }
    }


    private void deleteAppointment(int position,ViewHolder viewHolder)
    {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference()
                .child(AppUtil.BED_BOOKING_TABLE_KEY)
                .child(userList.get(position).getUser_id()).child(userList.get(position).getId());
        mDatabaseRef.removeValue();
        viewHolder.itemView.setVisibility(View.GONE);
        Toast.makeText(context, "bed booking has been deleted!", Toast.LENGTH_SHORT).show();
        userList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, userList.size());



    }
}
