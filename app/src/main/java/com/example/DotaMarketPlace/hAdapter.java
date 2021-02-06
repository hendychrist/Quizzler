package com.example.DotaMarketPlace;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class hAdapter extends RecyclerView.Adapter<hAdapter.MyViewHolder> {

    Context context;
   private  ArrayList  arr_history_date, arr_history_name, arr_history_stock, arr_history_minus;

    hAdapter( Context context, ArrayList history_date, ArrayList history_name, ArrayList history_stock, ArrayList history_minus){
      this.context = context;
      this.arr_history_date = history_date;
      this.arr_history_name = history_name;
      this.arr_history_stock = history_stock;
      this.arr_history_minus = history_minus;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.baris, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.history_date.setText(String.valueOf(arr_history_date.get(position)));
        holder.history_name.setText(String.valueOf(arr_history_name.get(position)));
        holder.history_stock.setText(String.valueOf(arr_history_stock.get(position)));
        holder.history_minus.setText(String.valueOf(arr_history_minus.get(position)));
    }

    @Override
    public int getItemCount() {
        return arr_history_name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView history_name, history_date, history_stock, history_minus;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            history_date = itemView.findViewById(R.id.text_view_date);
            history_name = itemView.findViewById(R.id.valueName);
            history_stock = itemView.findViewById(R.id.valueQty);
            history_minus = itemView.findViewById(R.id.valueMinusBalance);

        }
    }
}
