package com.example.kaanb.masrafmain.Mainthings;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.kaanb.masrafmain.Database.Database_dao;
import com.example.kaanb.masrafmain.Database.Databasehelper;
import com.example.kaanb.masrafmain.Edit.Islembildirim;
import com.example.kaanb.masrafmain.Edit.editislemler;
import com.example.kaanb.masrafmain.Edit.editislemlerbildirim;
import com.example.kaanb.masrafmain.Edit.myadapter;
import com.example.kaanb.masrafmain.Edit.myadapterbildirim;
import com.example.kaanb.masrafmain.Islemler.bildirimler;
import com.example.kaanb.masrafmain.Islemler.gider;
import com.example.kaanb.masrafmain.R;
import com.viewpagerindicator.CirclePageIndicator;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class masrafmain extends AppCompatActivity {

    private Button gelir,gider,bildirimbutton;
    private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter,
            textdate,textinfo,textprice,bildirimtxt,kalanguntxt,gunkaldıtxt,bildirimnotxt,
            islemlernotxt,gelirtxt,gidertxt,totaltxt,slidertext,slidertext2,taksittxt;
    private ScrollView myscroll,scrollbildirim,scroll;
    private ConstraintLayout constraint,constraintbildirim,constaintmain,constraintana,sliderlayout;
    private LinearLayout linear;
    private Databasehelper db,db2,db3,db4,db5,db6;
    private ImageView imageislemedit,gelirimagebtn,giderimagebutton,bildirimimagebutton,imagebildirimedit;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static int max = 0;
    private static int stringcounter = 0;
    private static double taksitcounter = 0.0;

    private int[] urls = new int[] {0,0,0,0,0};
    private String[]text = new String[]{"Kaan"};

    private String[]label = new String[]{"Diğer","Maaş","Yemek","Eğlence","Yol","Araba","Sağlık","Giyim","Eğitim","Sigara","Ev"
            ,"Fatura","Market","Hobiler","Telefon"};
    private int[]labelcounter = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    String datex = "",datebildirim = "",infobildirim = "",datebildirim2 = "",infobildirim2 = "",gunkaldi = "",infox = "",pricex = "",pricex2 = "";
    String []arrays;
    int[] array;
    int counter = 0,arraycounter = 0;
    double totalgelir = 0.0,totalgider=0.0,totaltotal=0.0,priceoflast = 0.0,priceofbefore = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_masrafmain);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        addID();

        array = new int[500];
        arrays = new String[500];


        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        float density = displayMetrics.density;

        //constraint.setBackgroundColor(Color.parseColor("#FFE2E1E3")); //C5E1A5 B1C282 96B478  C5E1A5
        //constaintmain.setBackgroundColor(Color.parseColor("#FFE2E1E3"));
        //constraintbildirim.setBackgroundColor(Color.parseColor("#FFE2E1E3"));
        sliderlayout.setBackgroundColor(Color.parseColor("#FFE2E1E3"));
        constraintana.setBackgroundColor(Color.parseColor("#1DCC01FF"));



        constaintmain.getLayoutParams().height = (int)density * 60;
        sliderlayout.getLayoutParams().height = (int)density * 140;

        constraintbildirim.getLayoutParams().height = (int)density * 140;
        scrollbildirim.getLayoutParams().height = (int)density * 140;

        constraint.getLayoutParams().height = (int)density * 160;
        scroll.getLayoutParams().height = (int)density * 160;




        constaintmain.requestLayout();

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
                                    ,m.getInt(m.getColumnIndex("price")),"YES",m.getString(m.getColumnIndex("label")),m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")),m.getInt(m.getColumnIndex("taksitcounter")),"NO");
                            dbm5 = db5.getReadableDatabase();
                        }
                        else
                        {
                            new Database_dao().adding(db5, m.getString(m.getColumnIndex("type")), m.getString(m.getColumnIndex("info")),m.getInt(m.getColumnIndex("day")),m.getInt(m.getColumnIndex("month"))+mm,m.getInt(m.getColumnIndex("year"))
                                    ,m.getInt(m.getColumnIndex("price")),"NO",m.getString(m.getColumnIndex("label")),m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")),m.getInt(m.getColumnIndex("taksitcounter")),"NO");
                            dbm5 = db5.getReadableDatabase();



                        }

                    }

                }
                ////Taksit için////



                if((m.getString(m.getColumnIndex("taksitactive")).equals("YES")) &&(Calendar.getInstance().get(Calendar.MONTH)+1 >m.getInt(m.getColumnIndex("month"))) && Calendar.getInstance().get(Calendar.DATE) >=m.getInt(m.getColumnIndex("day")) )
                {
                    ContentValues cv = new ContentValues();
                    int mn = m.getInt(m.getColumnIndex("taksitcounter"));

                    cv.put("taksitcounter",mn );
                    cv.put("taksitactive","NO");
                    if(m.getInt(m.getColumnIndex("taksit")) == m.getInt(m.getColumnIndex("taksitcounter")))
                    {
                        cv.put("taksitactive","NO");
                    }
                    dbm5.update("holder", cv, "processid=" + m.getInt(m.getColumnIndex("processid")), null);
                    dbm5 = db5.getReadableDatabase();
                    int monthcounter = (Calendar.getInstance().get(Calendar.MONTH)+1) - m.getInt(m.getColumnIndex("month"));

                    for(int mm=1;mm<= monthcounter;mm++) {


                        if (m.getInt(m.getColumnIndex("taksitcounter")) <= m.getInt(m.getColumnIndex("taksit"))) {
                            if (mm == monthcounter) {
                                new Database_dao().adding(db5, m.getString(m.getColumnIndex("type")), m.getString(m.getColumnIndex("info")), m.getInt(m.getColumnIndex("day")), m.getInt(m.getColumnIndex("month")) + mm, m.getInt(m.getColumnIndex("year"))
                                        , m.getInt(m.getColumnIndex("price")), "YES", m.getString(m.getColumnIndex("label")), m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")), ++mn,"YES");
                                dbm5 = db5.getReadableDatabase();
                            } else {
                                new Database_dao().adding(db5, m.getString(m.getColumnIndex("type")), m.getString(m.getColumnIndex("info")), m.getInt(m.getColumnIndex("day")), m.getInt(m.getColumnIndex("month")) + mm, m.getInt(m.getColumnIndex("year"))
                                        , m.getInt(m.getColumnIndex("price")), "NO", m.getString(m.getColumnIndex("label")), m.getString(m.getColumnIndex("pricetype")), m.getInt(m.getColumnIndex("taksit")), ++mn,"NO");
                                dbm5 = db5.getReadableDatabase();


                            }
                        }


                    }

                }





            } while (m.moveToPrevious());
            m.close();

        }


        /////////////////////////////Slidercontraint///////////////////////////////



        mPager = findViewById(R.id.pager);
        mPager.setAdapter(new imageadaptor(masrafmain.this,urls));

        CirclePageIndicator indicator = findViewById(R.id.indicator);

        indicator.setViewPager(mPager);



//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = 5;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
                //textView.setText("Bu bir çalışma" + currentPage);

            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
                //textView.setText("Bu bir çalışma" + currentPage);
            }
        }, 3000, 3000);


        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

                if(currentPage==0) {
                    sliderlayout.setBackgroundColor(Color.parseColor("#FFF3E5F5"));

                    slidertext.setText("Harcalamalarınızı kontrol altına alın !");
                    slidertext2.setText("Gelir - Giderlerinizi buradan takip edin.");



                }
                else if(currentPage == 1) {
                    sliderlayout.setBackgroundColor(Color.parseColor("#FFC4A8E9"));
                    priceoflast = money("gelir","current",0,-2);
                    priceofbefore = money("gelir","before",0,-2);
                    double fark = priceofbefore - priceoflast;
                    slidertext.setText("Bu ay ₺" + priceoflast + " gelir elde ettiniz.");
                    slidertext2.setText("");
                    if(fark >0){
                        slidertext2.setText("Bu geçen aya göre ₺" + fark + " daha az gelir demek !" );
                    }
                    else if(fark < 0)
                    {
                        slidertext2.setText("Geçen aya göre ₺" + (-fark) + " daha fazla gelir demek !" );
                    }
                    else
                    {
                        slidertext2.setText("");
                    }

                }
                else if(currentPage == 2)
                {
                    sliderlayout.setBackgroundColor(Color.parseColor("#FFBA97E8"));
                    priceoflast = money("gider","current",0,-2);
                    priceofbefore = money("gider","before",0,-2);
                    double fark = priceofbefore - priceoflast;
                    slidertext.setText("Bu ay ₺" + priceoflast + " harcadınız.");
                    slidertext2.setText("");
                    if(fark >0){
                        slidertext2.setText("Bu geçen aya göre ₺" + fark + "daha AZ harcadınız. " );
                    }
                    else if(fark < 0)
                    {
                        slidertext2.setText("Geçen aya göre ₺" + fark + " daha FAZLA harcadınız !" );
                    }
                    else
                    {
                        slidertext2.setText("");
                    }
                }
                else if(currentPage == 3)
                {
                    sliderlayout.setBackgroundColor(Color.parseColor("#FFB38AE8"));
                    double labelprice = money("gider","current",1,-2);
                    double labelsıra = money("gider","current",2,-2);
                    int sıra =(int)labelsıra;

                    slidertext2.setText("");

                    if(labelprice == 0)
                    {
                        slidertext.setText("Harcamalarınıza etiket seçmeyi unutmayın :)" );
                        slidertext2.setText("");
                    }
                    else
                    {
                        slidertext.setText("Bu ay en çok " +"'" + label[sıra] +"'" + " kategorisinde ₺" + labelprice + " harcadınız." );
                        slidertext.setTextSize(14);
                        if(label[sıra].equals("Diğer")) {
                            slidertext2.setText("Kategori özelliğimizi pek kullanmıyor gibisiniz. :(");
                        }
                    }
                }
                else if(currentPage == 4)
                {
                    double z = money("gider","current",0,2);
                    sliderlayout.setBackgroundColor(Color.parseColor("#FFA978E9"));
                    slidertext.setTextSize(16);
                    slidertext.setText("Bu ay " +"'₺"+ z + "'" + " taksitlere ödeme yapacaksınız..");
                    slidertext2.setText("");
                    if(z>100) {
                        slidertext2.setText("Taksitli harcamalara dikkat !");
                        slidertext2.setText("");
                    }
                }
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });


















        ////////////////////////////////////////////////////////////////////////////////////

        new Database_dao().veriler(db);
        SQLiteDatabase dbm = db.getReadableDatabase();
        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);

        Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToFirst();

        if (c.getCount() <= 0) {
            c.close();
            islemlernotxt.setText("Gelir & gider giriniz..");
            islemlernotxt.setTextColor(Color.parseColor("#FFD8D0DA"));
        } else {
            c.moveToLast();
            do {

                if (counter <= 30) {
                    datex = datex + c.getInt(c.getColumnIndex("day")) + "." +
                            c.getInt(c.getColumnIndex("month")) + "." +
                            c.getInt(c.getColumnIndex("year")) + "\n";



                    if(c.getInt(c.getColumnIndex("taksit")) >= 2) {
                        infox = infox + c.getString(c.getColumnIndex("info")) + " (" +c.getInt(c.getColumnIndex("taksitcounter")) + "/" + c.getInt(c.getColumnIndex("taksit")) + ")" + "\n";
                    }
                    else
                    {
                        infox = infox + c.getString(c.getColumnIndex("info")) + "\n";
                    }



                    String firstNumberAsString = String.format ("%.2f", c.getDouble(c.getColumnIndex("price")));


                    if(c.getString(c.getColumnIndex("type")).equals("gelir")) {
                        pricex += "₺" + firstNumberAsString + "\n";
                    }
                    else if(c.getString(c.getColumnIndex("type")).equals("gider")) {
                        pricex += "₺ -" + firstNumberAsString + "\n";
                    }


                    counter++;
                }
                if (counter >= 31) {
                    c.moveToFirst();
                    counter = 0;
                }

            }
            while (c.moveToPrevious());

            islemlernotxt.setTextColor(Color.parseColor("#FFD8D0DA"));
            islemlernotxt.setText("");

            textdate.setTextSize(16);
            textdate.setTextColor(Color.parseColor("#FFD8D0DA"));
            textdate.setText("" + datex);

            textinfo.setTextSize(16);
            textinfo.setTextColor(Color.parseColor("#FFD8D0DA"));
            textinfo.setText("" + infox);


            textprice.setTextSize(16);
            textprice.setTextColor(Color.parseColor("#FFD8D0DA"));
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
            bildirimnotxt.setTextColor(Color.parseColor("#FFD8D0DA"));

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
            bildirimnotxt.setTextColor(Color.parseColor("#FFD8D0DA"));

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
                bildirimnotxt.setTextColor(Color.parseColor("#FFD8D0DA"));
            } else {

                for (int u = 0; u <= arraycounter - 1; u++) {
                    datebildirim2 = datebildirim2 + array[u] + "\n";
                }

                for (int p = 0; p <= arraycounter - 1; p++) {
                    infobildirim2 = infobildirim2 + arrays[p] + "\n";
                }

                islemlernotxt.setTextSize(16);
                islemlernotxt.setTextColor(Color.parseColor("#FFD8D0DA"));
                islemlernotxt.setText("");

                gunkaldıtxt.setTextSize(16);
                gunkaldıtxt.setTextColor(Color.parseColor("#FFD8D0DA"));
                gunkaldıtxt.setText("" + gunkaldi);

                bildirimtxt.setTextSize(16);
                bildirimtxt.setTextColor(Color.parseColor("#FFD8D0DA"));
                bildirimtxt.setText("" + infobildirim2);

                kalanguntxt.setTextSize(16);
                kalanguntxt.setTextColor(Color.parseColor("#FFD8D0DA"));
                kalanguntxt.setText("" + datebildirim2);
            }

        }


        gelirimagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ser.setText("Kaan");

                Intent intent = new Intent(masrafmain.this, com.example.kaanb.masrafmain.Islemler.gelir.class);
                startActivity(intent);

            }

        });


        gider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, com.example.kaanb.masrafmain.Islemler.gider.class);
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




            } while (g.moveToNext());
            totaltotal = totalgelir - totalgider;



            String firstNumberAsString = String.format ("%.2f", totalgelir);
            String firstNumberAsString2 = String.format ("%.2f", -totalgider);
            String firstNumberAsString3 = String.format ("%.2f", totaltotal);


            totaltxt.setText("" + "₺" + firstNumberAsString3);
            gelirtxt.setText("" +"₺" + firstNumberAsString );
            gidertxt.setText("" +"₺"+ firstNumberAsString2);


        }

        imageislemedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, myadapter.class);
                startActivity(intent);
            }


        });
        lasttenshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(masrafmain.this, myadapterbildirim.class);
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
                Intent intent = new Intent(masrafmain.this, myadapterbildirim.class);
                startActivity(intent);
            }
        });
    }




    public double money(String s,String update,int labelx,int taksitx) {

        final Calendar e = Calendar.getInstance();
        int mYear = e.get(Calendar.YEAR);
        int mMonth = e.get(Calendar.MONTH);
        int mDay = e.get(Calendar.DAY_OF_MONTH);
        double price = 0.0;
        double beforeprice = 0.0;
        double labelprice = 0.0;
        taksitcounter = 0.0;

        db6 = new Databasehelper(this);
        new Database_dao().veriler(db6);
        SQLiteDatabase dbm9 = db6.getReadableDatabase();

        Cursor q = dbm9.rawQuery("SELECT * FROM holder", null);
        q.moveToFirst();

        if (q.getCount() <= 0) {
            q.close();
            islemlernotxt.setText("Gelir & gider giriniz..");
            islemlernotxt.setTextColor(Color.parseColor("#FFD8D0DA"));
        } else {
            q.moveToLast();
            do {

                if(q.getString(q.getColumnIndex("type")).equals(""+s) && (mYear == q.getInt(q.getColumnIndex("year"))) && (((mMonth + 1) == q.getInt(q.getColumnIndex("month")))) && (taksitx == -2))
                {
                    labelcounter = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                    for(int j=0 ; j<=14;j++)
                    {
                        if(q.getString(q.getColumnIndex("type")).equals("gider")  && label[j].equals(q.getString(q.getColumnIndex("label"))))
                        {
                            if(q.getString(q.getColumnIndex("type")).equals("gelir"))
                            {
                                j=14;
                            }
                            labelcounter[j]+= q.getDouble(q.getColumnIndex("price"));
                            j=14;

                        }
                    }
                    price += q.getDouble(q.getColumnIndex("price"));

                }
                else if(q.getString(q.getColumnIndex("type")).equals(""+s) && ((mYear == q.getInt(q.getColumnIndex("year"))) && (((mMonth + 1) == q.getInt(q.getColumnIndex("month"))+1))) && (taksitx == -2))
                {

                    beforeprice+= q.getDouble(q.getColumnIndex("price"));
                }
                else if(q.getString(q.getColumnIndex("type")).equals(""+s) && ((mYear == q.getInt(q.getColumnIndex("year"))) && (((mMonth + 1) == q.getInt(q.getColumnIndex("month"))))) && (q.getInt(q.getColumnIndex("taksit")) > 1 ) )
                {
                    taksitcounter += q.getDouble(q.getColumnIndex("price"));
                }

            } while (q.moveToPrevious());

            for(int l= 0; l<=14;l++)
            {


                if(labelcounter[l]>max)
                {
                    max = labelcounter[l];
                    stringcounter = l;
                }
            }

            if(update.equals("current") && labelx == 0 && taksitx == -2) {
                return price;
            }
            else if(update.equals("before") && labelx == 0 && taksitx == -2)
            {
                return beforeprice;
            }
            else if(labelx == 1 && taksitx == -2)
            {
                return max;
            }
            else if(labelx == 2 && taksitx == -2)
            {
                return stringcounter;
            }
            else if(taksitx> 1)
            {
                return taksitcounter;
            }

        }

        return 0;

    }

    public void init()
    {
        gelir = new Button(this);
        gider = new Button(this);
        ser = new TextView(this);
        taksittxt = new TextView(this);
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
        sliderlayout = new ConstraintLayout(this);
        slidertext = new TextView(this);
        slidertext2 = new TextView(this);
    }
    public void addID()
    {

        lastprocessshow = findViewById(R.id.lastprocessshow);
        scrollbildirim= findViewById(R.id.scrollbildirim);
        scroll = findViewById(R.id.scroll);
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
        sliderlayout = findViewById(R.id.sliderlayout);
        slidertext = findViewById(R.id.slidertext);
        slidertext2=findViewById(R.id.slidertext2);
        taksittxt = findViewById(R.id.taksittxt);
    }
}