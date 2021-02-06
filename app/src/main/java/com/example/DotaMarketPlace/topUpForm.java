package com.example.DotaMarketPlace;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DotaMarketPlace.Kontroller.MenuForm;
import com.example.DotaMarketPlace.Model.User;

public class  topUpForm extends AppCompatActivity {

    ImageView btnImg;
    View rLayout;
    EditText aa, aPassword;
    Button bBuy;

    String sUsername, passw;
    int uBalance , doet;
    TextView saldo;

    DBHelper db;
    Cursor cursor , kursor;
    StringBuilder passBuilder, balanceBuild;

    private static final String TAG = "topUpForm";

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topupform);

        saldo = (TextView) findViewById(R.id.txtTValue);

        rLayout = (View) findViewById(R.id.hilang);
        btnImg = (ImageView) findViewById(R.id.btnTplus);

        bBuy = (Button) findViewById(R.id.btnBeli);
        aa = (EditText) findViewById(R.id.txtAmountValue);
        aPassword = (EditText) findViewById(R.id.txtTpassword);

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   Start  Button Plus ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

   /*     btnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rLayout.setVisibility(View.VISIBLE);

            }
        }); */

        // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ End Button PLus ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

      //  if( rLayout.getVisibility() == View.VISIBLE ){

            // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   Start  Button BUY ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        sUsername = ApplicationClass.mMyAppsBundle.getString("key");
        Log.i(TAG,"topUpForm Username VALUE : " + sUsername);

        //Get Password sesuai Username yang login
        db = new DBHelper(this);
        cursor = db.ViewData(sUsername);
         passBuilder = new StringBuilder();
         balanceBuild = new StringBuilder();

        while(cursor.moveToNext()){
            // Karena dia cuman SELECT username FROM usertable jadi cuman 1 index [0]
            passBuilder.append(cursor.getString(1));
            balanceBuild.append(cursor.getString(5));
        }

       // BalanceTextValue.setText(passBuilder.toString());
        saldo.setText(balanceBuild.toString());


        uBalance = Integer.parseInt(balanceBuild.toString());
        Log.i(TAG,"uBalance value : " + balanceBuild);

            bBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText( topUpForm.this , " Buy button has been Click " , Toast.LENGTH_SHORT).show();

                    // Ini punya AmountBalance
                    String bb = aa.getText().toString();


                    if (TextUtils.isEmpty(bb)) {

                        aa.setError("Amount of balance must be filled");
                        aa.requestFocus();

                    } else {

                        int amount = Integer.parseInt(bb);

                        if (amount < 50000) {

                            aa.setError(" Amount balance must be more than equals 50000");
                            aa.requestFocus();

                        } else if (TextUtils.isEmpty(aPassword.getText())) {

                            aPassword.setError("Password must be filled");
                            aPassword.requestFocus();

                        } else if( !(aPassword.getText().toString().equals(passBuilder.toString()))){

                            Log.i(TAG,"topUpForm tPass VALUE : " + aPassword.getText().toString());
                            Log.i(TAG,"topUpForm passbuilder VALUE : " + passBuilder.toString());

                            aPassword.setError("Password Do not match");
                            aPassword.requestFocus();

                        }else {

                            doet = uBalance + amount;
                                Toast.makeText(topUpForm.this, " Done  Top Up Success : " + doet, Toast.LENGTH_SHORT).show();
                                Log.i(TAG," VALUE DOET setelah ditambahkan : " + doet);


                            // String tPass = passBuilder.toString();
                           // Log.i(TAG,"topUpForm Password VALUE : " + tPass);

                            kursor = db.updtBalance(doet,sUsername);

                            while(kursor.moveToNext()){
                                Log.i(TAG," Kursor moveToNext success " + doet);
                            }

                            Intent wew = new Intent(topUpForm.this , MenuForm.class);
                          //  wew.putExtra("test1", doet);
                            startActivity(wew);
                            finish();

                            Toast.makeText(topUpForm.this, " All validation Complete", Toast.LENGTH_SHORT).show();

                        }
                    }


                } //Close onClick
            });

            btnImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    closeKeyboard();
                }

            });

    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if( view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

}
