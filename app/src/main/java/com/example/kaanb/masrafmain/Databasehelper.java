package com.example.kaanb.masrafmain;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Databasehelper extends SQLiteOpenHelper {

    public Databasehelper(Context context) {
        super(context, "helper", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE \" holder\" (\n" +
                "\t\"processid\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\t\"Info\"\tTEXT,\n" +
                "\t\"Day\"\tINTEGER,\n" +
                "\t\"Month\"\tINTEGER,\n" +
                "\t\"Year\"\tINTEGER,\n" +
                "\t\"Price\"\tINTEGER,\n" +
                "\t\"Repeat\"\tTEXT,\n" +
                "\t\"Label\"\tTEXT,\n" +
                "\t\"Payingtype\"\tTEXT,\n" +
                "\t\"Taksit\"\tINTEGER\n" +
                ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE  IF EXISTS holder");
        onCreate(db);

    }
}

