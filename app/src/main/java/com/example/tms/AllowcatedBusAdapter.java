package com.example.tms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllowcatedBusAdapter extends RecyclerView.Adapter<AllowcatedBusAdapter.ViewHolder>{
    ArrayList<dataset> listdata;
    Context context;

    public AllowcatedBusAdapter(ArrayList<dataset> listdata, Context context) {
        this.listdata = listdata;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.bus_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final dataset datast = listdata.get(position);
        holder.t1.setText(datast.getRoute());
        holder.t2.setText(datast.getBusid());
        holder.rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MapActivity.class);
                i.putExtra("busid", datast.getBusid());
                i.putExtra("route", datast.getRoute());
                context.startActivity(i);
                ((AllowcatedBusActivity) context).finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView t1, t2;
        public LinearLayout rt;
        public ViewHolder(View itemView) {
            super(itemView);
            this.t1 = itemView.findViewById(R.id.t1);
            this.t2 = itemView.findViewById(R.id.t2);
            rt = itemView.findViewById(R.id.rt);
        }
    }
}