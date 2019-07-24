package com.example.kaanb.masrafmain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class Database_dao extends AppCompatActivity {


    public void adding (Databasehelper dh,int process_id){

        //,String type,
        //                        String info,int day,int month,int year,double price,
        //                        String repeat,String label,String payingtype,int taksit

        SQLiteDatabase dbm = dh.getWritableDatabase();
        ContentValues mycontent = new ContentValues();

        mycontent.put("processid",process_id);

        dbm.insertOrThrow("holder",null,mycontent);
        dbm.close();

    }

    public ArrayList<Mydatabase> veriler(Databasehelper dh)
    {
        ArrayList<Mydatabase>myveri = new ArrayList<>();
        SQLiteDatabase dbm = dh.getWritableDatabase();
        Cursor c = dbm.rawQuery("SELECT * FROM  Mydatabase",null );

        while(c.moveToNext())
        {
            Mydatabase verilerim = new Mydatabase(c.getInt(c.getColumnIndex("processid")));
            myveri.add(verilerim);
        }
        return myveri;
    }

}
