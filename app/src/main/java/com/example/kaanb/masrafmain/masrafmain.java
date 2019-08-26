package com.example.kaanb.masrafmain;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class masrafmain extends AppCompatActivity {

private Button gelir,gider;
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,textdate,textinfo,textprice;
private ScrollView myscroll;
private ConstraintLayout constraint;
private LinearLayout linear;
private Databasehelper db;

    String s = "Tarih\t\t\t\t\t\t\t |\t Açıklama\t\t\t\t\t\t\t |\t Ucret\n\n";
    String datex = "";
    String infox = "";
    String pricex = "";
    String pricex2 = "";
    int counter = 0;



    public void init()
    {
        gelir = new Button(this);
        gider = new Button(this);
        ser = new TextView(this);
        lastprocessshow = new TextView(this);
        lasttenshow = new TextView(this);
        lastprocesswriter =  new TextView(this);
        textdate = new TextView(this);
        lastprocesswriter2 =  new TextView(this);
        myscroll = new ScrollView(this);
        linear = new LinearLayout(this);
        textinfo = new TextView(this);
        textprice = new TextView(this);


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
        //lastprocesswriter2 = findViewById(R.id.lastprocesswriter2);
        textdate = findViewById(R.id.textdate);
        myscroll = findViewById(R.id.scroll);
       // linear = findViewById(R.id.linear);
        constraint = findViewById(R.id.constraint);

        textinfo = findViewById(R.id.textinfo);
        textprice = findViewById(R.id.textprice);

        db = new Databasehelper(this);

        lastprocesswriter.setText(""+ s);
        lastprocesswriter.setTextSize(14);



        new Database_dao().veriler(db);

        SQLiteDatabase dbm = db.getReadableDatabase();
        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToLast();
        do
        {
            if(counter <= 20) {
                datex = datex + c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n";
                infox = infox + c.getString(c.getColumnIndex("info")) + "\n";
                pricex2 = Double.toString(c.getDouble(c.getColumnIndex("price")));
                pricex = pricex +  pricex2 + "\n";
                counter++;

            }
            if(counter >= 20)
            {
                c.moveToFirst();
                counter = 0;
            }

        }
        while (c.moveToPrevious());
        textdate.setText(""+ datex);
        textinfo.setText("" + infox);
        textprice.setText("" + pricex);


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
