package com.example.kaanb.masrafmain;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class masrafmain extends AppCompatActivity {

    private Button gelir,gider,bildirimbutton;
    private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,textdate,textinfo,textprice,bildirimtxt,kalanguntxt,gunkaldıtxt,bildirimnotxt,islemlernotxt,gelirtxt,gidertxt,totaltxt;
    private ScrollView myscroll;
    private ConstraintLayout constraint,constraintbildirim,constaintmain,constraintana;
    private LinearLayout linear;
    private Databasehelper db,db2,db3,db4,db5;
    private ImageView imageislemedit,gelirimagebtn,giderimagebutton,bildirimimagebutton,imagebildirimedit;


    String datex = "",datebildirim = "",infobildirim = "",datebildirim2 = "",infobildirim2 = "",gunkaldi = "",infox = "",pricex = "",pricex2 = "";
    String []arrays;
    int[] array;
    int counter = 0,arraycounter = 0;
    double totalgelir = 0.0,totalgider=0.0,totaltotal=0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masrafmain);
        init();
        addID();

        array = new int[500];
        arrays = new String[500];


        constraint.setBackgroundColor(Color.parseColor("#FFE2E1E3")); //C5E1A5 B1C282 96B478  C5E1A5
        constaintmain.setBackgroundColor(Color.parseColor("#FFE2E1E3"));
        constraintbildirim.setBackgroundColor(Color.parseColor("#FFE2E1E3"));
        constraintana.setBackgroundColor(Color.parseColor("#FF726C6C"));
        db5 = new Databasehelper(this);
        new Database_dao().veriler(db5);
        SQLiteDatabase dbm5 = db5.getReadableDatabase();
        Cursor m = dbm5.rawQuery("SELECT * FROM holder", null);
        m.moveToFirst();


        if (m.getCount() <= 0) {
            m.close();

        } else {
            m.moveToLast();
            do {
                if((m.getString(m.getColumnIndex("repeat")).equals("YES")) &&(Calendar.getInstance().get(Calendar.MONTH)+1 >m.getInt(m.getColumnIndex("month"))) && Calendar.getInstance().get(Calendar.DATE) >=m.getInt(m.getColumnIndex("day")) )
                {
                    ContentValues cv = new ContentValues();
                    cv.put("repeat", "NO");
                    dbm5.update("holder", cv, "processid=" + m.getInt(m.getColumnIndex("processid")), null);
                    dbm5 = db5.getReadableDatabase();
                    int monthcounter = (Calendar.getInstance().get(Calendar.MONTH)+1) - m.getInt(m.getColumnIndex("month"));

                    for(int mm=1;mm<= monthcounter;mm++) {
                        if(mm == monthcounter) {
                            new Database_dao().adding(db5, m.getString(m.getColumnIndex("type")), m.getString(m.getColumnIndex("info")),m.getInt(m.getColumnIndex("day")),m.getInt(m.getColumnIndex("month"))+mm,m.getInt(m.getColumnIndex("year"))
                                    ,m.getInt(m.getColumnIndex("price")),"YES",m.getString(m.getColumnIndex("label")),m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")));
                            dbm5 = db5.getReadableDatabase();
                        }
                        else
                        {
                            new Database_dao().adding(db5, m.getString(m.getColumnIndex("type")), m.getString(m.getColumnIndex("info")),m.getInt(m.getColumnIndex("day")),m.getInt(m.getColumnIndex("month"))+mm,m.getInt(m.getColumnIndex("year"))
                                    ,m.getInt(m.getColumnIndex("price")),"NO",m.getString(m.getColumnIndex("label")),m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")));
                            dbm5 = db5.getReadableDatabase();



                        }

                    }

                }
            } while (m.moveToPrevious());
            m.close();

        }





        new Database_dao().veriler(db);
        SQLiteDatabase dbm = db.getReadableDatabase();
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);

        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToFirst();

        if (c.getCount() <= 0) {
            c.close();
            islemlernotxt.setText("Gelir & gider giriniz..");
        } else {
            c.moveToLast();
            do {

                if (counter <= 20) {
                    datex = datex + c.getInt(c.getColumnIndex("day")) + "." +
                            c.getInt(c.getColumnIndex("month")) + "." +
                            c.getInt(c.getColumnIndex("year")) + "\n";
                    infox = infox + c.getString(c.getColumnIndex("info")) + "\n";
                    String firstNumberAsString = String.format ("%.2f", c.getDouble(c.getColumnIndex("price")));
                    pricex +="₺" + firstNumberAsString + "\n";
                    counter++;
                }
                if (counter >= 21) {
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
        db4 = new Databasehelper(this);

        final Calendar e = Calendar.getInstance();
        int mYear = e.get(Calendar.YEAR);
        int mMonth = e.get(Calendar.MONTH);
        int mDay = e.get(Calendar.DAY_OF_MONTH);

        new Database_dao().veriler(db4);


        SQLiteDatabase dbm4 = db4.getReadableDatabase();

        Cursor f = dbm4.rawQuery("SELECT * FROM bildirim", null);


        f.moveToFirst();


        if ( f.getCount() <= 0 ) {

            f.close();
            bildirimnotxt.setText("Bildirim giriniz..");

        } else {
            f.moveToFirst();

            do {
                if((f.getString(f.getColumnIndex("repeat")).equals("YES")) &&(Calendar.getInstance().get(Calendar.MONTH)+1 >f.getInt(f.getColumnIndex("month"))) && Calendar.getInstance().get(Calendar.DATE) >=f.getInt(f.getColumnIndex("day")) )
                {
                    ContentValues cv = new ContentValues();
                    cv.put("repeat", "NO");
                    dbm4.update("bildirim", cv, "processid=" + f.getInt(f.getColumnIndex("processid")), null);
                    dbm4 = db4.getReadableDatabase();
                    int monthcounter = (Calendar.getInstance().get(Calendar.MONTH)+1) - f.getInt(f.getColumnIndex("month"));

                    for(int mm=1;mm<= monthcounter;mm++) {
                        if(mm == monthcounter) {
                            new Database_dao().addingalarm(db4, f.getString(f.getColumnIndex("informationx")), f.getInt(f.getColumnIndex("day")), f.getInt(f.getColumnIndex("month")) + mm, f.getInt(f.getColumnIndex("year")), "YES");
                            dbm4 = db4.getReadableDatabase();
                        }
                        else
                        {
                            new Database_dao().addingalarm(db4, f.getString(f.getColumnIndex("informationx")), f.getInt(f.getColumnIndex("day")), f.getInt(f.getColumnIndex("month")) + mm, f.getInt(f.getColumnIndex("year")), "NO");
                            dbm4 = db4.getReadableDatabase();



                        }

                    }

                }


            } while (f.moveToNext());

            f.close();

        }


        new Database_dao().veriler(db2);
        SQLiteDatabase dbm2 = db2.getReadableDatabase();
        Cursor d = dbm2.rawQuery("SELECT * FROM bildirim", null);
        d.moveToFirst();


        /// bildirim

        if ( d.getCount() <= 0 ) {

            d.close();
            bildirimnotxt.setText("Bildirim giriniz..");

        } else {
            d.moveToLast();

            do {

                if ((mYear == d.getInt(d.getColumnIndex("year"))) && (((mMonth + 1) == d.getInt(d.getColumnIndex("month"))) || ((mMonth + 2) == d.getInt(d.getColumnIndex("month"))))) {

                    if (((mMonth + 1) == d.getInt(d.getColumnIndex("month"))) && (mDay <= d.getInt(d.getColumnIndex("day")))) {

                        int day_counter = (d.getInt(d.getColumnIndex("day"))) - mDay;

                        datebildirim = datebildirim + day_counter + "\n";
                        infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                        array[arraycounter] = day_counter;
                        arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                        arraycounter++;
                        gunkaldi = gunkaldi + " gün kaldı." + "\n";
                    } else if (((mMonth + 2) == d.getInt(d.getColumnIndex("month")))) {
                        int res = e.getActualMaximum(Calendar.DATE);
                        int day_counter = (d.getInt(d.getColumnIndex("day"))) + (res - mDay);

                        if (day_counter <= 30) {
                            datebildirim = datebildirim + day_counter + "\n";
                            infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                            array[arraycounter] = day_counter;
                            arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                            arraycounter++;
                            gunkaldi = gunkaldi + " gün kaldı." + "\n";
                        }

                    }

                }else if((d.getInt(d.getColumnIndex("month")) == 0) && (mMonth == 11))
                {
                    int res = e.getActualMaximum(Calendar.DATE);
                    int day_counter = (d.getInt(d.getColumnIndex("day"))) + (res - mDay);

                    if (day_counter <= 30) {
                        datebildirim = datebildirim + day_counter + "\n";
                        infobildirim = infobildirim + d.getString(d.getColumnIndex("informationx")) + "\n";
                        array[arraycounter] = day_counter;
                        arrays[arraycounter] = d.getString(d.getColumnIndex("informationx"));
                        arraycounter++;
                        gunkaldi = gunkaldi + " gün kaldı." + "\n";
                    }


                }


            } while (d.moveToPrevious());


            ///////Array Sort //////////
            for (int i = 0; i < arraycounter - 1; i++) {

                int sayi = array[i];
                int temp = i;
                String r = arrays[i];

                for (int j = i + 1; j < arraycounter; j++) {
                    if (array[j] < sayi) {
                        sayi = array[j];
                        r = arrays[j];
                        temp = j;
                    }
                }

                if (temp != i) {
                    array[temp] = array[i];
                    arrays[temp] = arrays[i];
                    arrays[i] = r;
                    array[i] = sayi;
                }

            }
            if (arraycounter == 0) {
                bildirimnotxt.setText("30 gün içerisinde yaklaşan bildiriminiz yok.");
            } else {

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


        gelirimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ser.setText("Kaan");

                Intent intent = new Intent(masrafmain.this, gelir.class);
                startActivity(intent);

            }

        });


        gider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, gider.class);
                startActivity(intent);
            }
        });

        bildirimbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, bildirimler.class);
                startActivity(intent);
            }
        });


        /////////Gelir Gider Total hesaplama ////////////


        db3 = new Databasehelper(this);
        new Database_dao().veriler(db3);
        SQLiteDatabase dbm3 = db3.getReadableDatabase();


        Cursor g = dbm3.rawQuery("SELECT type,price FROM holder", null);
        g.moveToFirst();

        if (g.getCount() <= 0) {

            g.close();
            gelirtxt.setText("₺" + "0");
            gidertxt.setText("₺" + "0");
            totaltxt.setText("₺" + "0");

        } else {
            do {
                String typer = g.getString(g.getColumnIndex("type"));
                Double pricer = g.getDouble(g.getColumnIndex("price"));

                if(typer.equals("gelir"))
                {
                    totalgelir += pricer;
                }
                else if(typer.equals("gider"))
                {
                    totalgider += pricer;
                }
                else
                {
                    totalgelir += 0;
                }

                totaltotal = totalgelir - totalgider;



            } while (g.moveToNext());


            String firstNumberAsString = String.format ("%.2f", totalgelir);
            String firstNumberAsString2 = String.format ("%.2f", totalgider);
            String firstNumberAsString3 = String.format ("%.2f", totaltotal);

            totaltxt.setText("" + "₺" + firstNumberAsString);
            gelirtxt.setText("" +"₺" + firstNumberAsString2 );
            gidertxt.setText("" +"₺"+ firstNumberAsString3);


        }

        imageislemedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, editislemler.class);
                startActivity(intent);
            }


        });
        lasttenshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, editislemler.class);
                startActivity(intent);
            }
        });

        giderimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, gider.class);
                startActivity(intent);
            }
        });

        bildirimimagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, bildirimler.class);
                startActivity(intent);
            }
        });
        imagebildirimedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, editislemlerbildirim.class);
                startActivity(intent);
            }
        });
    }

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
        gelirimagebtn = new ImageView(this);
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
        db = new Databasehelper(this);
        constraint = new ConstraintLayout(this);
        constraintbildirim = new ConstraintLayout(this);
        constaintmain = new ConstraintLayout(this);
        imageislemedit = new ImageView(this);
        giderimagebutton = new ImageView(this);
        constraintana = new ConstraintLayout(this);
        bildirimimagebutton = new ImageView(this);
        imagebildirimedit = new ImageView(this);
    }
    public void addID()
    {

        lastprocessshow = findViewById(R.id.lastprocessshow);
        lasttenshow = findViewById(R.id.lasttenshow);
        lastprocesswriter = findViewById(R.id.lastprocesswriter);
        textdate = findViewById(R.id.textdate);
        myscroll = findViewById(R.id.scroll);
        textinfo = findViewById(R.id.textinfo);
        textprice = findViewById(R.id.textprice);
        bildirimtxt = findViewById(R.id.bildirimtxt);
        kalanguntxt = findViewById(R.id.kalanguntxt);
        gunkaldıtxt = findViewById(R.id.gunkaldıtxt);
        bildirimnotxt = findViewById(R.id.bildirimnotxt);
        islemlernotxt = findViewById(R.id.islemlernotxt);
        constraintbildirim = findViewById(R.id.constraintbildirim);
        constaintmain = findViewById(R.id.constaintmain);
        gelirtxt = findViewById(R.id.topofgelir);
        gidertxt = findViewById(R.id.gidertxt);
        totaltxt = findViewById(R.id.totaltxt);
        constraint = findViewById(R.id.constraint);
        constraintbildirim = findViewById(R.id.constraintbildirim);
        constaintmain = findViewById(R.id.constaintmain);
        imageislemedit = findViewById(R.id.imageislemedit);
        gelirimagebtn = findViewById(R.id.gelirimagebtn);
        constraintana = findViewById(R.id.constraintana);
        giderimagebutton = findViewById(R.id.giderimagebutton);
        bildirimimagebutton = findViewById(R.id.bildirimimagebutton);
        imagebildirimedit = findViewById(R.id.imagebildirimedit);
    }
    }
