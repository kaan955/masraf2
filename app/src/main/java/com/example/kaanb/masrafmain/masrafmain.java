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
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,textdate,textinfo,textprice,bildirimtxt,kalanguntxt,gunkaldıtxt,textView7;
private ScrollView myscroll;
private ConstraintLayout constraint;
private LinearLayout linear;
private Databasehelper db,db2;

    String datex = "";
    String datebildirim = "";
    String infobildirim = "";
    String datebildirim2 = "";
    String infobildirim2 = "";
    String gunkaldi = "";
    String infox = "";
    String pricex = "";
    String pricex2 = "";
    int counter = 0;
    int []array;
    String []arrays;
    int arraycounter = 0;


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
        gunkaldıtxt = new TextView(this);
        textView7 = new TextView(this);

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
        textdate = findViewById(R.id.textdate);
        myscroll = findViewById(R.id.scroll);
        constraint = findViewById(R.id.constraint);
        bildirimbutton = findViewById(R.id.bildirimbutton);
        textinfo = findViewById(R.id.textinfo);
        textprice = findViewById(R.id.textprice);
        bildirimtxt = findViewById(R.id.bildirimtxt);
        kalanguntxt = findViewById(R.id.kalanguntxt);
        gunkaldıtxt = findViewById(R.id.textView6);
        textView7 = findViewById(R.id.textView7);

        array = new int[500];
        arrays = new String[500];


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
        /*
        d.moveToLast();
        do
        {
            if(counter < 100) {

                if((mYear == d.getInt(d.getColumnIndex("year"))) && (((mMonth+1) ==  d.getInt(d.getColumnIndex("month"))) || ((mMonth) ==  d.getInt(d.getColumnIndex("month")))) && (mDay <=  d.getInt(d.getColumnIndex("day")) ))
                {


                    int day_counter = (d.getInt(d.getColumnIndex("day"))) - mDay;
                    datebildirim = datebildirim +  day_counter +"\n";
                    infobildirim =  infobildirim + d.getString(d.getColumnIndex("informationx"))+ "\n";

                }




                counter++;

            }
            if(counter >= 100)
            {
                d.moveToFirst();
                counter = 0;
            }

        }
        while (d.moveToPrevious());


        bildirimtxt.setText(""+ infobildirim);
        kalanguntxt.setText("" + datebildirim);

        */

        d.moveToLast();

        do {
            int monthstopper = d.getInt(d.getColumnIndex("month"));
            int daystopper = d.getInt(d.getColumnIndex("day"));



         //   if((mYear == d.getInt(d.getColumnIndex("year"))) && (((mMonth+1) ==  d.getInt(d.getColumnIndex("month"))) || ((mMonth+2) ==  d.getInt(d.getColumnIndex("month")))) && (mDay <=  d.getInt(d.getColumnIndex("day")) ))
            if((mYear == d.getInt(d.getColumnIndex("year"))) && (((mMonth+1) ==  d.getInt(d.getColumnIndex("month"))) || ((mMonth+2) ==  d.getInt(d.getColumnIndex("month")))))
            {


                if(((mMonth+1) ==  d.getInt(d.getColumnIndex("month"))) && (mDay <=  d.getInt(d.getColumnIndex("day")) ) ) {


                    int day_counter = (d.getInt(d.getColumnIndex("day"))) - mDay;
                    datebildirim = datebildirim + day_counter + "\n";
                    infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                    array[arraycounter] = day_counter;
                    arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                    arraycounter++;
                }
                else if(((mMonth+2) ==  d.getInt(d.getColumnIndex("month"))))
                {
                    int res = e.getActualMaximum(Calendar.DATE);
                    int day_counter = (d.getInt(d.getColumnIndex("day"))) + (res - mDay);

                    if(day_counter <=30) {
                        datebildirim = datebildirim + day_counter + "\n";
                        infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                        array[arraycounter] = day_counter;
                        arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                        arraycounter++;
                    }



                }

            }



        }while(d.moveToPrevious());


        ///////Array Sort //////////
        for (int i = 0; i < arraycounter-1; i++) {

            int sayi = array[i];
            int temp = i;
            String r = arrays[i];

            for (int j = i+1; j < arraycounter ; j++) {
                if(array[j]<sayi){
                    sayi = array[j];
                    r = arrays[j];
                    temp = j;
                }
            }

            if(temp != i){
                array[temp] = array[i];
                arrays[temp] = arrays[i];
                arrays[i] = r;
                array[i] = sayi;
            }

        }




        for (int sayi:array) {
            datebildirim2 = datebildirim2 + sayi + "\n";

        }

        for (String s:arrays)
        {
            infobildirim2 = infobildirim2 + s + "\n";
        }



        //textView6.setText("" + datebildirim);
        //textView7.setText("" + infobildirim);

        gunkaldıtxt.setText("" + "deneme");
        bildirimtxt.setText(""+ infobildirim2);
        kalanguntxt.setText("" + datebildirim2);







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
