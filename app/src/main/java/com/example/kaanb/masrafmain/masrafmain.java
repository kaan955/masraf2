package com.example.kaanb.masrafmain;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class masrafmain extends AppCompatActivity {

private Button gelir,gider;
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter;
private ScrollView myscroll;
private LinearLayout linear;
private Databasehelper db;

    String s = "Tarih\t\t\t\t\t\t\t |\t Açıklama\t\t\t\t\t\t\t |\t Ucret\n\n";
    String x ="";



    public void init()
    {
        gelir = new Button(this);
        gider = new Button(this);
        ser = new TextView(this);
        lastprocessshow = new TextView(this);
        lasttenshow = new TextView(this);
        lastprocesswriter =  new TextView(this);
        lastprocesswriter2 =  new TextView(this);
        myscroll = new ScrollView(this);
        linear = new LinearLayout(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masrafmain);
        init();


        final TextView ser = findViewById(R.id.editText2);
        Button gelir = findViewById(R.id.gelir);
        gider = findViewById(R.id.gider);
        lastprocessshow = findViewById(R.id.lastprocessshow);
        lasttenshow = findViewById(R.id.lasttenshow);
        lastprocesswriter = findViewById(R.id.lastprocesswriter);
        lastprocesswriter2 = findViewById(R.id.lastprocesswriter2);
        myscroll = findViewById(R.id.scroll);
        linear = findViewById(R.id.linear);

        db = new Databasehelper(this);


        lastprocesswriter.setText(""+ s);
        lastprocesswriter.setTextSize(14);



        new Database_dao().veriler(db);

        SQLiteDatabase dbm = db.getReadableDatabase();
        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToLast();
        do
        {
            x =x +c.getInt(c.getColumnIndex("day"))+"."+
                    c.getInt(c.getColumnIndex("month")) +"." +
                    c.getInt(c.getColumnIndex("year")) +"\t\t\t\t\t\t\t\t\t "+
                    c.getString(c.getColumnIndex("info"))+"\t\t\t\t\t\t\t\t\t "+ c.getDouble(c.getColumnIndex("price")) +"\n";

        }
        while (c.moveToPrevious());
        lastprocesswriter2.setText(""+ x);



       gelir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ser.setText("Kaan");

               Intent intent = new Intent(masrafmain.this,gelir.class);
                startActivity(intent);

           }

       });


       gider.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(masrafmain.this,gider.class);
               startActivity(intent);
           }
       });


    }








}
