package com.example.DotaMarketPlace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    //LayoutInflater inflater;
    List<Item> item;
    private Context ctx;
   // ConstraintLayout parentLayout;

   private  ArrayList book_name, book_price, book_stock;

    DBHelper db;
    private static final String TAG = "Adapter";

    // CONSTRUCTOR

 /*   public Adapter(Context ctx, List<Item> item){
        this.inflater = LayoutInflater.from(ctx);
        this.item = item;
        this.ctx = ctx;
    } */

    public Adapter(Context ctx, List<Item> item, ArrayList book_name, ArrayList book_price, ArrayList book_stock) {
        this.ctx = ctx;
        this.item = item;
        this.book_name = book_name;
        this.book_price = book_price;
        this.book_stock = book_stock;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      //  View view = inflater.inflate(R.layout.row,parent,false);
      //  return new ViewHolder(view);

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.row, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        //  Bind the data || Show from JSON > Item model > RecyclerView

        final Item items = item.get(position);
//        items.getIname();
//        items.getIstock();
//        items.getIprice();

    /*  holder.nameTitle.setText(items.getIname());
        holder.stockTitle.setText(items.getIstock());
        holder.priceTitle.setText(items.getIprice()); */

        // Tampilin row.xml cardView lewat SQLite > ArrayList > RecyclerView
        holder.nameTitle.setText(String.valueOf(book_name.get(position)));
        holder.priceTitle.setText(String.valueOf(book_price.get(position)));
        holder.stockTitle.setText(String.valueOf(book_stock.get(position)));

        // - Button Buy
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, buyItem.class);
                intent.putExtra("name", item.get(position).getIname());
                intent.putExtra("price", item.get(position).getIprice());
                intent.putExtra("stock", String.valueOf(book_stock.get(position)));
                intent.putExtra("latitude", item.get(position).getLat());
                intent.putExtra("longitude", item.get(position).getLng());

                Bundle bundle = new Bundle();
                bundle.putSerializable("Item", items);
                intent.putExtra("Item", bundle);

                //Be aware if you are using flags that you change the history stack as Alex Volovoy's answer says:
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                
                ctx.startActivity(intent);

               // Toast.makeText(ctx, "Buy Item Has been Clicked", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return item.size();
       // return book_name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameTitle,stockTitle,priceTitle;
        Button btnBuy;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            nameTitle = itemView.findViewById(R.id.txtName);
            stockTitle = itemView.findViewById(R.id.txtStock);
            priceTitle = itemView.findViewById(R.id.txtPrice);
            btnBuy = itemView.findViewById(R.id.btnBuy);

        }

    }

}
