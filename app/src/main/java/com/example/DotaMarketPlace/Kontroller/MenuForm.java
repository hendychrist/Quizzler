package com.example.DotaMarketPlace.Kontroller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import com.example.DotaMarketPlace.Adapter;
import com.example.DotaMarketPlace.ApplicationClass;
import com.example.DotaMarketPlace.DBHelper;
import com.example.DotaMarketPlace.Database.DisplayDatabase;
import com.example.DotaMarketPlace.History;
import com.example.DotaMarketPlace.Item;
import com.example.DotaMarketPlace.MainActivity;

import com.example.DotaMarketPlace.R;
import com.example.DotaMarketPlace.topUpForm;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuForm  extends AppCompatActivity implements Serializable {

    TextView lbUser , lbUang;
    ImageView btnFesh, btnBuy;

    RecyclerView recyclerView;
    List<Item> items;
    private static String JSON_URL = "https://raw.githubusercontent.com/hendychrist/mata-pelajaran/main/matpel.json";
    Adapter adapter;
    Intent mUser;

    //Inisialisasi DbHelper class
    DBHelper db;
    Cursor cursor;

    ArrayList<String> book_name, book_price;
    ArrayList<Integer> book_stock;

    String labell;

    private static final String TAG = "MenuForm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menuform);

        init();

        //Untuk nampilin Username
        mUser = getIntent();
        labell = mUser.getStringExtra("username");

        lbUser.setText(labell);
        // End

        ApplicationClass.mMyAppsBundle.putString("key",labell);
        Log.i(TAG,"Get Set bundle VALUE : " + labell);

        //JSON to RecyclerView
        recyclerView = findViewById(R.id.songList);
        items = new ArrayList<>();

        extractSongs();
        showBalance();

        //Add Item from SQL to Arraylist
        book_name = new ArrayList<>();
        book_price = new ArrayList<>();
        book_stock = new ArrayList<>();

        storeDataInArrays();

        // Button Fresh
        btnFesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Toast.makeText(MenuForm.this, "Refresh Top Up Pressed", Toast.LENGTH_SHORT).show();
            }
        });
    } // Close on create

    private void extractSongs(){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    String dbName;
                    String dbStock;
                    String dbPrice;

                    try {

                        JSONObject itemObject = response.getJSONObject(i);

                        // Masukan JSON ke SQLite
                        dbName = itemObject.getString("name").toString();
                        dbStock = itemObject.getString("stock").toString();
                        dbPrice = itemObject.getString("price").toString();

                        int vStock = Integer.parseInt(dbStock);
                        Boolean insert = db.insertItem(dbName,dbPrice,vStock);

                        if(insert==true){
                            Toast.makeText( MenuForm.this , " ITEM DATABASE " , Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText( MenuForm.this , " Error input ITEM to ITEMTABLE DB" , Toast.LENGTH_SHORT).show();
                        }

                        Item item = new Item();

                        // Masukan JSON kedalam Item.java adapter
                        item.setIname(itemObject.getString("name").toString());
                        item.setIstock(itemObject.getString("stock").toString());
                        item.setIprice(itemObject.getString("price").toString());
                        item.setLat(itemObject.getString("latitude").toString());
                        item.setLng(itemObject.getString("longitude").toString());

                        items.add(item);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            //Ini adalah adapter recyclerView nampilin dari ArrayList from SQLite table itemTable
                recyclerView.setLayoutManager(new LinearLayoutManager(MenuForm.this));
                adapter = new Adapter(getApplication(), items, book_name, book_price,book_stock);
                recyclerView.setAdapter(adapter);

           /*  Ini adalah adapter RecyclerView untuk nampilin dari JSON bukan database
            Adapter Tampilin datanya dari JSON object to RecyclerView
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter = new Adapter(getApplication(), items);
                recyclerView.setAdapter(adapter);

           */

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"onErrorResponse info : " + error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }

    private void init(){
        lbUser = (TextView) findViewById(R.id.lbUsername);
        btnFesh = findViewById(R.id.btnRefresh);
        lbUang = findViewById(R.id.lbDollar);
    }

    private void showBalance(){

        // Top Up Balance
        db = new DBHelper(this);
        cursor = db.ViewData(labell);
        StringBuilder balanceBuilder = new StringBuilder();

        //Show Database
        while (cursor.moveToNext()){
            balanceBuilder.append(cursor.getInt(5));
        }

        lbUang.setText(balanceBuilder);

    }

    private void storeDataInArrays(){
        Cursor cursor = db.viewItem();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                book_name.add(cursor.getString(0));
                book_price.add(cursor.getString(1));
                book_stock.add(Integer.parseInt(cursor.getString(2)));
            }
        }
    }

    //Navbar Item menu 3 Dot kanan atas
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu,menu);
        return true;
    }

    //Lanjutan Navbar 3 Dot
    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId()) {

            case R.id.idTopUp:

                Toast.makeText( MenuForm.this , "Top Up has pressed" , Toast.LENGTH_SHORT).show();

                Intent iii = new Intent(MenuForm.this , topUpForm.class);
                startActivity(iii);

                return true;

            case R.id.idLogOut:

                Toast.makeText( MenuForm.this , "Log Out has pressed" , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MenuForm.this, MainActivity.class);
                startActivity(intent);

                return true;

            case R.id.idHistory:

                Toast.makeText( MenuForm.this , "History has pressed" , Toast.LENGTH_SHORT).show();

                Intent bbb = new Intent(MenuForm.this, History.class);
                startActivity(bbb);

                return true;

            case R.id.idDB:

                Intent ccc = new Intent(MenuForm.this, DisplayDatabase.class);
                startActivity(ccc);

                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

}

/*
https://youtu.be/SD2t75T5RdY
https://youtu.be/5Tm--PHhbJo

JSON to RecyclerView :
https://youtu.be/e3MDW87mbR8?list=PLlGT4GXi8_8eo127jp2-GeV0B5BlqF8Lf
*/