package com.example.DotaMarketPlace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DotaMarketPlace.Kontroller.MenuForm;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class buyItem extends AppCompatActivity {

    private static final String CHANNEL_ID = "personal_notifications";
    private final int NOTIFICATION_ID = 001;

    TextView  title , stock , price , stockV , priceV , totalV;
    ImageView imageBulet,btnBac;
    Button btn , btn2 , btn3, btn4;
    EditText bQty;

    //btn2 > buy
    //btn3 > show

    String hasil, str, nameGet, currentDate ;
    int jAngka  , totalPrice, vUser;

    private static final String TAG = "buyItem";

    DBHelper db;
    Cursor cursor, kursor, ikursor, viewCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyitem);

        Date tanggal = new Date();

        db = new DBHelper(this);

        Bundle bundle = getIntent().getBundleExtra("Item");
        final Item items = (Item) bundle.getSerializable("Item");

        // Ambil username dari MenuForm.java -> Example : admin
        str = ApplicationClass.mMyAppsBundle.getString("key");
        Log.i(TAG,"Get Set buyItem username  VALUE : " + str);

        requestSendReceiveSMS();

        // Button back
        btnBac =  (ImageView) findViewById(R.id.btnBac);
        btnBac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(buyItem.this, MenuForm.class);
                startActivity(intent);
                finish();

            }
        });

        imageBulet = findViewById(R.id.another_imageView);

        title = (TextView) findViewById(R.id.titleText);
        stock = (TextView)  findViewById(R.id.stockText);
        price = (TextView)  findViewById(R.id.priceText);

        stockV = (TextView)  findViewById(R.id.stockValue);
        priceV = (TextView) findViewById(R.id.priceValue);

        nameGet = getIntent().getStringExtra("name");
        String priceGet = getIntent().getStringExtra("price");
        String stockGet = getIntent().getStringExtra("stock");

        title.setText(items.getIname());
        stockV.setText(stockGet);
        priceV.setText(items.getIprice());

        //String to int
        int vStock = Integer.parseInt(stockGet);
        int vDoet = Integer.parseInt(priceGet);

        // Button Notif
    /*    btn4 = (Button) findViewById(R.id.btnNotif);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Toast.makeText( buyItem.this , " Button Notif Send me Clicked " , Toast.LENGTH_SHORT).show();

              // String message = "Your Location : https://goo.gl/maps/4sxdYcmYL6YeMydZ7";

            }
        }); */

        imageBulet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG,"getIntent " +
                        "\n nama VALUE " + nameGet +
                        " \n priceGet : " + vDoet +
                        " \n stockGet : " + vStock +
                        " \n \n"
                );

            }
        });

        //  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~    Button Position 0   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        bQty = (EditText) findViewById(R.id.txtQty);
        totalV = (TextView) findViewById(R.id.TotalValue);

        btn3 = (Button) findViewById(R.id.btnShow);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( buyItem.this , " Show Location Seller Pressed " , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), SellerLocation.class);

                Bundle bundle = new Bundle();
                bundle.putSerializable("Item", items);
                intent.putExtra("Item", bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        // Top Up Balance
        db = new DBHelper(this);
        cursor = db.ViewData(str);

        StringBuilder balanceBuilder = new StringBuilder();

        //Show Database
        while (cursor.moveToNext()){
            balanceBuilder.append(cursor.getInt(5));
        }

        // Show username from SQLite menuform username by bundle
        vUser = Integer.parseInt(balanceBuilder.toString());
        Log.i(TAG,"Get Set BuyItem Balance VALUE : " + balanceBuilder);

        //Show date Calendar
        Calendar calendar = Calendar.getInstance();

        // Button Total Price
        btn = (Button) findViewById(R.id.btnTotalPrice);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Toast.makeText(buyItem.this, "Button Total Price Clicked", Toast.LENGTH_SHORT).show();

                if(TextUtils.isEmpty(bQty.getText().toString())){
//
                    bQty.setError(" Quantity must be filled");
                    bQty.requestFocus();

                } else {

                    hasil = bQty.getText().toString();
                    jAngka = Integer.parseInt(hasil);

                    if( jAngka > vStock) {

                        bQty.setError(" Not enough stock");
                        bQty.requestFocus();

                    }  else {

                        totalPrice = jAngka * vDoet;
                        totalV.setText(String.valueOf(totalPrice));

                    }
                }

                // Button Buy
                btn2 = (Button) findViewById(R.id.btnBeli);
                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Toast.makeText( buyItem.this , " Buy Item Clicked" , Toast.LENGTH_SHORT).show();

                        // Jika UserBalance Lebih kecik dari totalPrice
                        if( vUser < totalPrice ){

                            bQty.setError(" Unable to process payment: Low card balance ");
                            bQty.requestFocus();

                        } else {

                            Toast.makeText( buyItem.this , " Buy Item Success" , Toast.LENGTH_SHORT).show();
                            Log.i(TAG,"btnBuy BuyItem Success: ");

                            // ~ Update BBalance ke DBHelper
                            int semua = vUser - totalPrice;
                            kursor = db.updtBalance(semua,str);

                            while(kursor.moveToNext()){
                                Log.i(TAG," buyItem Subtracting balance SUCCESS" );
                            }

                            SmsManager.getDefault().sendTextMessage("5554",null,
                                    "Transaction is success",null,null);

                            // ~ Update Quantity || Update data stock ke DBHelper
                            int itemStockDB = vStock - jAngka;
                            ikursor = db.updtStock(itemStockDB,nameGet.toString());

                            while(ikursor.moveToNext()){
                                Log.i(TAG," Quantity berhasil di update");
                            }

                            //  ~ History
                            currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
//                                int selisih = vUser - itemStockDB;

                            Boolean hInsert = db.insertHistory(nameGet,currentDate,jAngka,totalPrice);
                            if(hInsert == true){
                                Toast.makeText(buyItem.this,"History Successfully Inserted", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(buyItem.this,"History Failed ERROR", Toast.LENGTH_SHORT).show();
                            }

                            Intent intent = new Intent(buyItem.this, MenuForm.class);
                            startActivity(intent);
                            finish();

                        }

                    }
                }); // Close button Buy
            }
        }); // Close btn totalPrice
    }// Close onCreate

    public void displayNotification(View view){

        createNotificationChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID);
            builder.setSmallIcon(R.drawable.ic_message);
            builder.setContentTitle("Book Seller Location");
            builder.setContentText("Open Google Maps link in your message");
            builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        SmsManager.getDefault().sendTextMessage("5554",null,
                "https://goo.gl/maps/4sxdYcmYL6YeMydZ7",null,null);

    }

    public void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){

            CharSequence name = "Personal Notifications";
            String description = "Include all the personal personal Notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, importance);

            notificationChannel.setDescription(description);

            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }

    }

    private void requestSendReceiveSMS(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.SEND_SMS},0);
    }

}// CLose class



