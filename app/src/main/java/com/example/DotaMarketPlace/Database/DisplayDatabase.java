package com.example.DotaMarketPlace.Database;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DotaMarketPlace.DBHelper;
import com.example.DotaMarketPlace.R;

public class DisplayDatabase extends AppCompatActivity {

    TextView showDB, showItem, showHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_db_layout);

        DBHelper db = new DBHelper(this);

        showDB = findViewById(R.id.idShow);
        showItem = findViewById(R.id.txtItem);
        showHistory = findViewById(R.id.txtHistory);

        // Top Up Balance
        Cursor cursor = db.viewDota();

        StringBuilder showAll = new StringBuilder();

        //Show Database
        while (cursor.moveToNext()){
            showAll.append("\nRoll Number : " +cursor.getInt(0)
                    +"\nPasword: "+cursor.getString(1)+
                    "\nUsername: "+cursor.getString(2)+
                    "\nPhoneNumber: "+cursor.getString(3)+
                    "\nGender: "+cursor.getString(4)+
                    "\nBalance: "+cursor.getInt(5)+"\n");

        }

        showDB.setText(showAll);

        // Show Item
        Cursor gursor = db.viewItem();
        StringBuilder allItem = new StringBuilder();

        while(gursor.moveToNext()){

            allItem.append(
                "\nNama : "+gursor.getString(0)+
                "\nPrice: "+gursor.getString(1)+
                "\nStock: "+gursor.getString(2)+
                 "\n\n"
            );
        }

        showItem.setText(allItem);

        //Show History
        Cursor hcursor = db.viewHistory();
        StringBuilder viewHistory = new StringBuilder();

        while (hcursor.moveToNext()){
            viewHistory.append(
                    "\nNama Item: " + hcursor.getString(0)+
                    "\nDate: "+ hcursor.getString(1)+
                    "\nStock: "+ hcursor.getString(2)+
                    "\nHarga: "+ hcursor.getString(3)+
                    "\n\n"
            );
        }

        showHistory.setText(viewHistory);

    }
}
