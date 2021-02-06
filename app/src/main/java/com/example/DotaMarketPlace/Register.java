package com.example.DotaMarketPlace;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DotaMarketPlace.Model.User;

import java.io.Serializable;
import java.util.ArrayList;

public class Register extends Activity implements Serializable{

    EditText first, last, password, username, cPassword, nomorHP;
    Button btn;

    RadioGroup radioGroup;
    RadioButton radioButton;

    TextView register;

    CheckBox chcBox;
    ImageView imgDown;

    DBHelper DB;

    // private static String URL_REGIST = "http://192.168.100.3/Dota_2/register.php";

    public ArrayList<User> mUsers = new ArrayList<>();
     public final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        imgDown = (ImageView) findViewById(R.id.btnKeyDown);
        imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tutup();
            }

        });

        register = (TextView) findViewById(R.id.goToLogin);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }

        });

        first = (EditText) findViewById(R.id.txtFirstName);
        last = (EditText) findViewById(R.id.txtLastName);

        username = (EditText) findViewById(R.id.txtUsername);

        password = (EditText) findViewById(R.id.txtPassword);
        cPassword = (EditText) findViewById(R.id.txtCpassword);

        nomorHP = (EditText) findViewById(R.id.txtPhoneNumber);

        radioGroup = (RadioGroup) findViewById(R.id.rgid);
        chcBox = (CheckBox) findViewById(R.id.ckBox);

        DB = new DBHelper(this);

        btn = (Button) findViewById(R.id.btnRegister);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                radioGroup = (RadioGroup) findViewById(R.id.rgid);

                if (TextUtils.isEmpty(first.getText())) {

                    first.setError("First name must be filled");
                    first.requestFocus();

                } else if (TextUtils.isEmpty(last.getText())) {

                    last.setError("Last name must be filled");
                    last.requestFocus();

                } else if (TextUtils.isEmpty(username.getText())) {

                    username.setError(" username must be filled");
                    username.requestFocus();

                } else if (username.getText().toString().length() < 5 || username.getText().toString().length() > 25) {

                    username.setError(" Username Length Must 5 - 25 Char");
                    username.requestFocus();

                } else if (TextUtils.isEmpty(password.getText())) {

                    password.setError("Password must be filled");
                    password.requestFocus();

                } else if (password.getText().toString().length() < 8 || password.getText().toString().length() > 25) {

                    password.setError("Password Length Must 8 - 25 Char");
                    password.requestFocus();

                } else if (isAlphaNumeric() == false) {

                    password.setError("Password must be alphanumeric. \n Password Contains 1 Uppercase Character. \n  Password Contains 1 Special Character.");
                    password.requestFocus();

                } else if (cekSpecial(password.getText().toString()) == false ){

                    password.setError("Password must contains 1 Special Character");
                    password.requestFocus();

                } else if (TextUtils.isEmpty(cPassword.getText())) {

                    cPassword.setError("Confirmation Password must be filled");
                    cPassword.requestFocus();

                } else if (!(password.getText().toString().equals(cPassword.getText().toString()))) {

                    cPassword.setError("Your password and confirmation password do not match.");
                    cPassword.requestFocus();

                } else if (TextUtils.isEmpty(nomorHP.getText())) {

                    nomorHP.setError("Phone Number must be filled");
                    nomorHP.requestFocus();

                } else if (nomorHP.getText().length() < 12) {

                    nomorHP.setError("Phone Number must be more than 12 Digit");
                    nomorHP.requestFocus();

                } else if (!(nomorHP.getText().toString().startsWith("+62"))) {

                    nomorHP.setError("Phone Number must starts with +62");
                    nomorHP.requestFocus();

                } else if (validation() == false) {

                    Toast.makeText(Register.this, "Gender Must be Filled", Toast.LENGTH_SHORT).show();

                } else if( !(chcBox.isChecked()) ){

                    chcBox.setError("Agreement must be Cheked");
                    chcBox.requestFocus();

                }else {

                    Toast.makeText(Register.this, "Registration Success", Toast.LENGTH_SHORT).show();

                    String Fname = first.getText().toString();
                    String Sname = last.getText().toString();
                    String User = username.getText().toString();
                    String pass = password.getText().toString();
                    String cPass = cPassword.getText().toString();
                    String hp = nomorHP.getText().toString();

                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    radioButton = (RadioButton) findViewById(selectedId);

                    String rd = radioButton.getText().toString();

                    String fullname = Fname + Sname;

                    Log.d(TAG, "User: Register Page: First Name : " + Fname);
                    Log.d(TAG, "User: Register Page: Last Name : " + Sname);
                    Log.d(TAG, "User: Register Page: Username : " + User);
                    Log.d(TAG, "User: Register Page: Password : " + pass);
                    Log.d(TAG, "User: Register Page: Confirmation Password :  " + cPass);
                    Log.d(TAG, "User: Register Page: Phone Number :  " + hp);
                    Log.d(TAG, "User: Register Page: Gender : " + rd);
                    Log.d(TAG, " ");

                    mUsers.add(new User(Fname, Sname, User,  pass, cPass, hp , rd));

                    Log.d(TAG, " ");
                    Log.d(TAG, " ");

                    for (int i = 0; i < mUsers.size(); i++) {

                        Log.d(TAG, " On ArrayList<> FirstName : " + mUsers.get(i).getFirstName());
                        Log.d(TAG, " On ArrayList<> LastName : " + mUsers.get(i).getLastName());
                        Log.d(TAG, " On ArrayList<> Username : " + mUsers.get(i).getUsername());
                        Log.d(TAG, " On ArrayList<> Password : " + mUsers.get(i).getPassword());
                        Log.d(TAG, " On ArrayList<> Confirmation Password : " + mUsers.get(i).getcPassword());
                        Log.d(TAG, " On ArrayList<> PhoneNumber : " + mUsers.get(i).getPhoneNumber());
                        Log.d(TAG, " On ArrayList<> Gender: " + mUsers.get(i).getGender());

                    }

                    Boolean checkuser = DB.checkUsername(User);
                    if(checkuser == false){
                        Boolean insert = DB.insertData(User,pass,fullname,hp,rd,0);
                        if(insert == true){

                            Toast.makeText(Register.this, "Register Successfully DBHelper", Toast.LENGTH_SHORT).show();

                            Intent intentx = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intentx);
                            finish();

                        }else{ // If insert == false
                            Toast.makeText(Register.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{ // If checkUser ==  True
                        username.setError("User already exists!");
                        username.requestFocus();
                    }

                  /*  Intent intent = new Intent(Register.this, MainActivity.class);

                    for (int i = 0; i < mUsers.size(); i++) {

                        intent.putExtra("Username" , mUsers.get(i).getUsername() );
                        intent.putExtra("Password" , mUsers.get(i).getPassword() );
                    }

                    startActivity(intent);
                    finish(); */

                } // Close last else if
            }
        });

    }

    public boolean validation() {
        int isSelected = radioGroup.getCheckedRadioButtonId();
        if (isSelected == -1) {
            return false;
        }
        return true;
    }

    public boolean isAlphaNumeric() {

        Boolean status = true;

        // o Validate the password must contains at least 1 special character
        for (int i = 0; i < password.length(); i++) {
            if (password.getText().toString().charAt(i) >= '!' && password.getText().toString().charAt(i) <=  '/' ||
                    password.getText().toString().charAt(i) >= ':' && password.getText().toString().charAt(i) <= '@' ||
                        password.getText().toString().charAt(i) >= '['  && password.getText().toString().charAt(i) <= '`' ||
                             password.getText().toString().charAt(i) >= '{'  && password.getText().toString().charAt(i) <= '~') {

                //Toast.makeText(Register.this, "Special Character Found", Toast.LENGTH_SHORT).show();
                status = true;
                break;

            } else{

                //Toast.makeText(Register.this, "At least contains at least 1 Special Charater", Toast.LENGTH_SHORT).show();
                status = false;

            }
        }

        //o	Validate the password must contains at least 1 uppercase character
        for (int i = 0; i < password.length(); i++) {
            if (password.getText().toString().charAt(i) >= 'A' && password.getText().toString().charAt(i) <= 'Z') {

                status = true;
                break;

            }else{

               // Toast.makeText(Register.this, "At least contains at least 1 Uppercase Character", Toast.LENGTH_SHORT).show();
                status = false;

            }
        }

       // o	Apakah ada Angka
        if (status) {
            for (int i = 0; i < password.length(); i++) {
                if (password.getText().toString().charAt(i) >= '0' && password.getText().toString().charAt(i) <= '9') {
                    status = true;
                    break;
                } else {
                    status = false;
                }
            }
        }

        //o	Apakah ada huruf
        if (status) {
            for (int i = 0; i < password.length(); i++) {
                if (password.getText().toString().charAt(i) >= 'A' && password.getText().toString().charAt(i) <= 'Z' || password.getText().toString().charAt(i) >= 'a' && password.getText().toString().charAt(i) <= 'z') {
                    status = true;
                    break;
                } else {
                    status = false;
                }
            }
        }
        return status;
    }

    public boolean cekSpecial( String password ){

        String sc = "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

        boolean cek = false;

        for (int i = 0; i < password.length(); i++) {

            if(sc.contains(String.valueOf(password.charAt(i)))){
                cek = true;
            }else {
                cek = false;
            }

        }
        return cek;
    }

    private void tutup(){
        View view = this.getCurrentFocus();
        if( view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

}

/*

Source IMG :
- https://www.pngitem.com/download/ixmwRxJ_icon-symbol-hd-png-download/ (Welcome Logo)
- https://id.pinterest.com/pin/854346991784252530/ (Background UI)
- https://youtu.be/AOaYvRQMNSw
- https://www.tutorialspoint.com/how-to-use-charat-in-android-textview

SQL Lite DB helper:
https://youtu.be/8obgNNlj3Eo

 */