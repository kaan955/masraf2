package com.example.kaanb.masrafmain.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Database_dao extends AppCompatActivity {

    public void adding (Databasehelper dh, String my_type, String my_info, int my_day, int my_month, int my_year, double my_price, String my_repeat, String my_label, String my_pricetype, int my_taksit ){


        SQLiteDatabase dbm = dh.getWritableDatabase();
        ContentValues mycontent = new ContentValues();

        //mycontent.put("processid",process_id);

         mycontent.put("type",my_type);
         mycontent.put("info",my_info);
         mycontent.put("day",my_day);
        mycontent.put("month",my_month);
        mycontent.put("year",my_year);
       mycontent.put("price",my_price);
         mycontent.put("repeat",my_repeat);
        mycontent.put("label",my_label);
        mycontent.put("pricetype",my_pricetype);
        mycontent.put("taksit",my_taksit);




        dbm.insertOrThrow("holder",null,mycontent);
        dbm.close();

    }

    public ArrayList<Mydatabase> veriler(Databasehelper dh)
    {
        ArrayList<Mydatabase>myveri = new ArrayList<>();
        SQLiteDatabase dbm = dh.getWritableDatabase();
        Cursor c = dbm.rawQuery("SELECT * FROM  holder",null );


        while(c.moveToNext())
        {
            Mydatabase verilerim = new Mydatabase(
                    c.getInt(c.getColumnIndex("processid")),
                    c.getString(c.getColumnIndex("type")),
                    c.getString(c.getColumnIndex("info")),
                  c.getInt(c.getColumnIndex("month")),
                   c.getInt(c.getColumnIndex("day")),
                  c.getInt(c.getColumnIndex("year")),
                   c.getDouble(c.getColumnIndex("price")),
                    c.getString(c.getColumnIndex("repeat")),
                    c.getString(c.getColumnIndex("label")),
                    c.getString(c.getColumnIndex("pricetype")),
                   c.getInt(c.getColumnIndex("taksit")));

            myveri.add(verilerim);
        }
        return myveri;
    }

    public void addingalarm(Databasehelper dh,String my_alarminfo,int my_day,int my_month,int my_year,String my_repeat)
    {
        SQLiteDatabase dbm = dh.getWritableDatabase();
        ContentValues mycontent = new ContentValues();

        mycontent.put("informationx",my_alarminfo);
        mycontent.put("day",my_day);
        mycontent.put("month",my_month);
        mycontent.put("year",my_year);
        mycontent.put("repeat",my_repeat);

        dbm.insertOrThrow("bildirim",null,mycontent);
        dbm.close();


    }

    public ArrayList<Mydatabase> verileralarm(Databasehelper dh)
    {
        ArrayList<Mydatabase>myveri2 = new ArrayList<>();
        SQLiteDatabase dbm = dh.getWritableDatabase();
        Cursor d = dbm.rawQuery("SELECT * FROM  bildirim",null );


        while(d.moveToNext())
        {
            Mydatabase verilerimalarm = new Mydatabase(
                    d.getInt(d.getColumnIndex("processidx")),
                    d.getString(d.getColumnIndex("informationx")),
                    d.getInt(d.getColumnIndex("month")),
                    d.getInt(d.getColumnIndex("day")),
                    d.getInt(d.getColumnIndex("year")),
                    d.getString(d.getColumnIndex("repeat")));

            myveri2.add(verilerimalarm);
        }
        return myveri2;
    }

    public void databaseguncelle(Databasehelper dh,int my_id)
    {
        SQLiteDatabase dbx = dh.getWritableDatabase();
        dbx.delete("holder","my_id=?",new String[]{String.valueOf(my_id)});
        dbx.close();
    }
    public void bildirimguncelle(Databasehelper dh,int my_id)
    {
        SQLiteDatabase dbx = dh.getWritableDatabase();
        dbx.delete("bildirim","my_id=?",new String[]{String.valueOf(my_id)});
        dbx.close();
    }



}
