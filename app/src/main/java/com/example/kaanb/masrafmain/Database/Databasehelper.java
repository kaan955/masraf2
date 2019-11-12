package com.example.kaanb.masrafmain.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {


    public Databasehelper(Context context) {
        super(context, "veritutar9", null, 9);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


            db.execSQL("CREATE TABLE holder(processid INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,info TEXT,day INTEGER,month INTEGER,year INTEGER,price DOUBLE,repeat TEXT,label TEXT,pricetype TEXT,taksit INTEGER,taksitcounter INTEGER,taksitactive TEXT);");
            db.execSQL("CREATE TABLE bildirim(processid INTEGER PRIMARY KEY AUTOINCREMENT,informationx TEXT,day INTEGER,month INTEGER,year INTEGER,repeat TEXT);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS holder");
        db.execSQL("DROP TABLE  IF EXISTS bildirim");
        onCreate(db);

    }
}

