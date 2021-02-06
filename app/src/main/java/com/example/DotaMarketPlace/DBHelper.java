package com.example.DotaMarketPlace;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Login.db";
    public static final String TABLE1 = "usertable";
    public static final String TABLE2 = "itemtable";
    public static final String TABLE3 = "transaksitable";

    public DBHelper(Context context){
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {

        String table1 = "CREATE TABLE "+TABLE1+"(username TEXT primary key, password TEXT, name TEXT, phonenumber TEXT, gender TEXT, balance INTEGER)";
        String table2 = "CREATE TABLE "+TABLE2+"(itemname TEXT primary key, itemprice TEXT, itemstock INTEGER)";
        String table3 = "CREATE TABLE "+TABLE3+"(historyname TEXT primary key, historydate TEXT, historystock INTEGER, historyminus INTEGER)";

        MyDB.execSQL(table1);
        MyDB.execSQL(table2);
        MyDB.execSQL(table3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE if exists "+TABLE1);
        MyDB.execSQL("DROP TABLE if exists "+TABLE2);
        MyDB.execSQL("DROP TABLE if exists "+TABLE3);
        onCreate(MyDB);
    }

    public Boolean insertData(String username, String password,String name,String phonenumber,String gender,Integer balance){

        SQLiteDatabase MyDB = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
            values1.put("username", username);
            values1.put("password", password);
            values1.put("name",name);
            values1.put("phonenumber",phonenumber);
            values1.put("gender",gender);
            values1.put("balance",balance);

        long result = MyDB.insert(TABLE1, null, values1);

        if(result == - 1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean insertItem(String iname, String iprice, Integer istock){

        SQLiteDatabase db = this.getWritableDatabase();

          ContentValues value2 = new ContentValues();
          value2.put("itemname", iname);
          value2.put("itemprice",iprice);
          value2.put("itemstock",istock);

       long resultItem =  db.insert(TABLE2, null, value2);

        if(resultItem == - 1){
            return false;
        }else{
            return true;
        }

    }

    public Boolean insertHistory(String hname, String hdate, Integer hstock, Integer hminus){

        SQLiteDatabase hdb = this.getWritableDatabase();

        ContentValues hValue = new ContentValues();
        hValue.put("historyname", hname);
        hValue.put("historydate",hdate);
        hValue.put("historystock",hstock);
        hValue.put("historyminus",hminus);

        long resultHistory =  hdb.insert(TABLE3, null, hValue);

        if(resultHistory == - 1){
            return false;
        }else{
            return true;
        }

    }

        public Boolean checkUsername(String username){

            SQLiteDatabase MyDB = this.getWritableDatabase();

            Cursor cursor = MyDB.rawQuery("SELECT * FROM usertable WHERE username = ?", new String[] {username});

            // If user exist
            if(cursor.getCount() > 0 )
                return  true;
            else
                return false;
        }

        public Boolean checkUsernamePassword(String username, String password){

            SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("SELECT * FROM usertable where username = ? and password = ?", new String[] {username, password} );

            if(cursor.getCount() > 0 )
                return true;
            else
                return false;

        }

    public Cursor ViewData(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usertable where username = ?", new String[]{username});
        return cursor;
    }

    public Cursor viewDota(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usertable",null);
        return cursor;
    }

    public Cursor viewItem(){
        SQLiteDatabase jh = this.getWritableDatabase();
        Cursor cursor = jh.rawQuery("SELECT * FROM itemtable", null);
        return cursor;
    }

    public Cursor viewHistory(){
        SQLiteDatabase vh = this.getWritableDatabase();
        Cursor cursor = vh.rawQuery("SELECT * FROM transaksitable", null);
        return cursor;
    }

        public Cursor updtBalance(Integer balance, String username){
            SQLiteDatabase update = this.getWritableDatabase();
            Cursor cursor = update.rawQuery("UPDATE usertable SET balance = ? WHERE username = ?", new String[]{balance.toString(),username});
            return cursor;
        }

        public Cursor updtStock(Integer iStock,String iName){
            SQLiteDatabase iItem = this.getWritableDatabase();
            Cursor iCursor = iItem.rawQuery("UPDATE "+TABLE2+" SET itemstock = ? WHERE itemname = ?", new String[]{iStock.toString(),iName});
            return iCursor;
        }

        public void deleteAllHistory(){
            SQLiteDatabase db = this.getWritableDatabase();
             //db.delete(TABLE3,null,null);
             db.execSQL("DELETE FROM "+ TABLE3);
             db.close();
        }

}

/*
    Reference:
    https://youtu.be/J-CP7g_GwpI
 */
