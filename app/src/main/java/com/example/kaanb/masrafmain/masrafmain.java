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
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,textdate,textinfo,textprice,bildirimtxt,kalanguntxt,gunkaldıtxt,bildirimnotxt,islemlernotxt,gelirtxt,gidertxt,totaltxt;
private ScrollView myscroll;
private ConstraintLayout constraint,constraintbildirim,constaintmain;
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
        constraintbildirim = new ConstraintLayout(this);
        constaintmain = new ConstraintLayout(this);
        textinfo = new TextView(this);
        textprice = new TextView(this);
        kalanguntxt = new TextView(this);
        bildirimtxt = new TextView(this);
        bildirimbutton = new Button(this);
        gunkaldıtxt = new TextView(this);
        bildirimnotxt = new TextView(this);
        islemlernotxt = new TextView(this);
        gelirtxt = new TextView(this);
        gidertxt = new TextView(this);
        totaltxt = new TextView(this);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masrafmain);
        init();

        gelir = findViewById(R.id.gelir);
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
        gunkaldıtxt = findViewById(R.id.gunkaldıtxt);
        bildirimnotxt = findViewById(R.id.bildirimnotxt);
        islemlernotxt = findViewById(R.id.islemlernotxt);
        constraintbildirim = findViewById(R.id.constraintbildirim);
        constaintmain = findViewById(R.id.constaintmain);
        gelirtxt = findViewById(R.id.gelirtxt);
        gidertxt = findViewById(R.id.gidertxt);
        totaltxt = findViewById(R.id.totaltxt);



        array = new int[500];
        arrays = new String[500];


        db = new Databasehelper(this);

        new Database_dao().veriler(db);
        SQLiteDatabase dbm = db.getReadableDatabase();


        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);

        c.moveToFirst();


        if(c != null && c.getCount()<=0){
          // String num = c.getString(c.getColumnIndex("day"));
            c.close();
            islemlernotxt.setText("Gelir & gider giriniz.." );
        }
        else{
            c.moveToLast();
            do {
                if (counter <= 15) {
                    datex = datex + c.getInt(c.getColumnIndex("day")) + "." +
                            c.getInt(c.getColumnIndex("month")) + "." +
                            c.getInt(c.getColumnIndex("year")) + "\n";
                    infox = infox + c.getString(c.getColumnIndex("info"))+"\n";
                    pricex2 = Double.toString(c.getDouble(c.getColumnIndex("price")));
                    pricex = pricex + pricex2 +"₺" + "\n";
                    counter++;

                }
                if (counter >= 16) {
                    c.moveToFirst();
                    counter = 0;
                }

            }
            while (c.moveToPrevious());
            islemlernotxt.setText("");
            textdate.setText("" + datex);
            textinfo.setText("" + infox);
            textprice.setText("" + pricex);

        }



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

         if(d != null && d.getCount()<=0) {

             d.close();
             bildirimnotxt.setText("Bildirim giriniz..");

         }
         else {
             do {

                 if ((mYear == d.getInt(d.getColumnIndex("year"))) && (((mMonth + 1) == d.getInt(d.getColumnIndex("month"))) || ((mMonth + 2) == d.getInt(d.getColumnIndex("month"))))) {

                     if (((mMonth + 1) == d.getInt(d.getColumnIndex("month"))) && (mDay <= d.getInt(d.getColumnIndex("day")))) {


                         int day_counter = (d.getInt(d.getColumnIndex("day"))) - mDay;
                         datebildirim = datebildirim + day_counter + "\n";
                         infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                         array[arraycounter] = day_counter;
                         arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                         arraycounter++;
                         gunkaldi = gunkaldi+ " gün kaldı." + "\n";
                     } else if (((mMonth + 2) == d.getInt(d.getColumnIndex("month")))) {
                         int res = e.getActualMaximum(Calendar.DATE);
                         int day_counter = (d.getInt(d.getColumnIndex("day"))) + (res - mDay);

                         if (day_counter <= 30) {
                             datebildirim = datebildirim + day_counter + "\n";
                             infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                             array[arraycounter] = day_counter;
                             arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                             arraycounter++;
                             gunkaldi = gunkaldi+ " gün kaldı." + "\n";
                         }


                     }

                 }


             } while (d.moveToPrevious());




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
        if(arraycounter == 0)
        {
            bildirimnotxt.setText("30 gün içerisinde yaklaşan bildiriminiz yok.");
        }
        else {

            for (int u = 0; u <= arraycounter - 1; u++) {
                datebildirim2 = datebildirim2 + array[u] + "\n";
            }

            for (int p = 0; p <= arraycounter - 1; p++) {
                infobildirim2 = infobildirim2 + arrays[p] + "\n";
            }

            islemlernotxt.setText("");
            gunkaldıtxt.setText("" + gunkaldi);
            bildirimtxt.setText("" + infobildirim2);
            kalanguntxt.setText("" + datebildirim2);
        }

     }



        gelirtxt.setText("123213" + "₺");
         gidertxt.setText("-" + "1232131" +"₺");
         totaltxt.setText("" +"23123" + "₺");



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
