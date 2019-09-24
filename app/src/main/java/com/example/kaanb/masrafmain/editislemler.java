package com.example.kaanb.masrafmain;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

public class editislemler extends AppCompatActivity {
    private Databasehelper db5;
    private TextView Islemselect;
    private int counter = 0;
    private String datex = "",infox = "",pricex = "",idx="",firstNumberAsString = "";
    private LinearLayout linear,linear2,linear3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editislemler);
        init();
        db5 = new Databasehelper(this);
        new Database_dao().veriler(db5);
        SQLiteDatabase dbm = db5.getReadableDatabase();
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);

        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToFirst();


        if (c.getCount() <= 0) {
            c.close();
            Islemselect.setText("Öncelikle Gelir & gider giriniz..");
        } else {
            c.moveToLast();
            do {

                TextView tv = new TextView(this);
                TextView tv2 = new TextView(this);
                TextView tv3 = new TextView(this);


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv2.setLayoutParams(lp);
                tv3.setLayoutParams(lp);
                tv3.setSingleLine();
                //tv3.setBackgroundResource(R.drawable.grentxtbar);

                tv.setText(c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText(c.getString(c.getColumnIndex("info")) +"\n");
                tv3.setText("₺" +String.format ("%.2f", c.getDouble(c.getColumnIndex("price"))) + "\n" );

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                linear.addView(tv);
                linear2.addView(tv2);
                linear3.addView(tv3);


            }
            while (c.moveToPrevious());
            //Islemselect.setText("" + datex + infox + pricex+ idx);
        }




    }
    void init()
    {
        linear = new LinearLayout(this);
        linear = findViewById(R.id.linear);
        linear2 = new LinearLayout(this);
        linear2 = findViewById(R.id.linear2);
        linear3 = new LinearLayout(this);
        linear3 = findViewById(R.id.linear3);
        Islemselect = new TextView(this);
        Islemselect = findViewById(R.id.Islemselect);
    }
}


