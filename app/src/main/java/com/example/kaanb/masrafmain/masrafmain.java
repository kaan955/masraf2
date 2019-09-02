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

import java.util.Calendar;

public class masrafmain extends AppCompatActivity {

private Button gelir,gider,bildirimbutton;
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,textdate,textinfo,textprice,bildirimtxt,kalanguntxt;
private ScrollView myscroll;
private ConstraintLayout constraint;
private LinearLayout linear;
private Databasehelper db,db2;


    String datex = "";
    String datebildirim = "";
    String infobildirim = "";
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
        kalanguntxt = new TextView(this);

        bildirimtxt = new TextView(this);

        bildirimbutton = new Button(this);


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

        bildirimbutton = findViewById(R.id.bildirimbutton);
        textinfo = findViewById(R.id.textinfo);
        textprice = findViewById(R.id.textprice);
        bildirimtxt = findViewById(R.id.bildirimtxt);
        kalanguntxt = findViewById(R.id.kalanguntxt);
        db = new Databasehelper(this);




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


        ///////////////YAKLASAN BILDIRIMLER/////////////////////

        db2 = new Databasehelper(this);

        final Calendar e = Calendar.getInstance();
        int mYear = e.get(Calendar.YEAR);
        int mMonth = e.get(Calendar.MONTH);
        int mDay = e.get(Calendar.DAY_OF_MONTH);




        new Database_dao().veriler(db2);

        SQLiteDatabase dbm2 = db2.getReadableDatabase();
        Cursor d = dbm2.rawQuery("SELECT * FROM bildirim", null);
        d.moveToLast();
        do
        {
            if(counter < 30) {
                int counteryear = d.getInt(d.getColumnIndex("year"));
                int countermonth = d.getInt(d.getColumnIndex("month"));
                int counterday = d.getInt(d.getColumnIndex("day"));
                if((mYear == d.getInt(d.getColumnIndex("year"))) && ((mMonth+1) ==  d.getInt(d.getColumnIndex("month"))) && (mDay <=  d.getInt(d.getColumnIndex("day")) ))
                {


                    int day_counter = (d.getInt(d.getColumnIndex("day"))) - mDay;
                    datebildirim = datebildirim +  day_counter +"\n";
                    infobildirim =  infobildirim + d.getString(d.getColumnIndex("informationx"))+ "\n";

                }




                counter++;

            }
            if(counter >= 30)
            {
                d.moveToFirst();
                counter = 0;
            }

        }
        while (d.moveToPrevious());

        bildirimtxt.setText(""+ infobildirim);
        kalanguntxt.setText("" + datebildirim);









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

        bildirimbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this,bildirimler.class);
                startActivity(intent);
            }
        });


    }







}
