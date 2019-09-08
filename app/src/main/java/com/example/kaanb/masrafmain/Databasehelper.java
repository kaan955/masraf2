package com.example.kaanb.masrafmain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {


    public Databasehelper(Context context) {
        super(context, "veritutar5", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


            db.execSQL("CREATE TABLE holder(processid INTEGER PRIMARY KEY AUTOINCREMENT,type TEXT,info TEXT,day INTEGER,month INTEGER,year INTEGER,price DOUBLE,repeat TEXT,label TEXT,pricetype TEXT,taksit INTEGER);");
            db.execSQL("CREATE TABLE bildirim(processid INTEGER PRIMARY KEY AUTOINCREMENT,informationx TEXT,day INTEGER,month INTEGER,year INTEGER);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS holder");
        db.execSQL("DROP TABLE  IF EXISTS bildirim");
        onCreate(db);

    }
}

