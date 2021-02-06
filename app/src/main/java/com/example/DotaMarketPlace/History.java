package com.example.DotaMarketPlace;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.DotaMarketPlace.Kontroller.MenuForm;

import java.util.ArrayList;
import java.util.Date;

public class History extends AppCompatActivity {

    RecyclerView reecyclerView;
    hAdapter haadapter;
    Button btnClear;

    DBHelper db;
    ArrayList<String> history_name, history_date,history_stock, history_minus;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baris);

    history_name = new ArrayList<>();
    history_date = new ArrayList<>();
    history_stock = new ArrayList<>();
    history_minus = new ArrayList<>();

    reecyclerView = findViewById(R.id.historyList);
    btnClear = findViewById(R.id.btnClearData);
    storeHistoryInArrays();

        haadapter = new hAdapter(History.this, history_date, history_name, history_stock, history_minus);
        reecyclerView.setAdapter(haadapter);
        reecyclerView.setLayoutManager(new LinearLayoutManager(History.this));

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    db.deleteAllHistory();
                    Toast.makeText(History.this, "History has been remove", Toast.LENGTH_SHORT).show();
                    Toast.makeText(History.this, "Please go Back to MenuForm to reload", Toast.LENGTH_SHORT).show();

                }catch (SQLException e){
                    e.printStackTrace();
                    Toast.makeText(History.this, "Fail to delete data History", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }//Close onCreate

    public void storeHistoryInArrays(){
        db = new DBHelper(History.this);
        Cursor cursor = db.viewHistory();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No History data", Toast.LENGTH_SHORT).show();
        }else{

            while(cursor.moveToNext()){
                history_name.add(cursor.getString(0));
                history_date.add(cursor.getString(1));
                history_stock.add(cursor.getString(2));
                history_minus.add(cursor.getString(3));
            }
        }
    }

}

/*
    Reference :
    https://youtu.be/VQKq9RHMS_0
 */


