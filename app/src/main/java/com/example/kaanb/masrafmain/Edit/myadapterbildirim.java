package com.example.kaanb.masrafmain.Edit;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.kaanb.masrafmain.Database.Database_dao;
import com.example.kaanb.masrafmain.Database.Databasehelper;
import com.example.kaanb.masrafmain.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class myadapterbildirim extends AppCompatActivity {
    private ArrayList<String> dataveriler,dataveriler2,dataveriler3,dataveriler4;
    private RecyclerView rv;
    private Islembildirim adapter,adapter2,adapter3,adapter4;
    private static final String TAG = "MyActivity";

    private Databasehelper db5,db6,db7,db8;
    private TextView Islemselect;
    private int counter = 0;
    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt,taksittxt;
    private Button tarihpicker,kayıtgelirbtn,deletegelirbtn;
    private ImageView infoerror,tariherror,tutarerror;
    private Spinner myspinner;
    private CheckBox monthrepeat;
    static int currentday,currentmonth,currentyear,my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;
    static String myrepeat ="NO";
    private LinearLayout linear,linear2,linear3;
    private ConstraintLayout constraintana,consta;
    private Cursor c,k, l,p;
    static TextView []tv = new TextView[10];
    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    private Databasehelper db2;
    private ScrollView scroll;
    String datex = "";
    String infox = "";
    String infocontrol = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myadapter);
        setTitle("Verinin üstüne basarak 'DUZENLE'");

        rv = (RecyclerView) findViewById(R.id.basitRV);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setBackgroundColor(Color.parseColor("#FFE2E1E3"));

        dataveriler = new ArrayList<>();
        dataveriler2 = new ArrayList<>();
        dataveriler3 = new ArrayList<>();
        dataveriler4 = new ArrayList<>();

        adapter = new Islembildirim(this, dataveriler,dataveriler2,dataveriler3,dataveriler4);



        rv.setAdapter(adapter);

        veriYukle();

    }

    public void veriYukle(){

        dataveriler.clear();
        dataveriler2.clear();
        dataveriler3.clear();
        dataveriler4.clear();

        db5 = new Databasehelper(this);
        db6= new Databasehelper(this);
        db7= new Databasehelper(this);
        db8= new Databasehelper(this);



        new Database_dao().veriler(db7);
        new Database_dao().veriler(db6);
        new Database_dao().veriler(db5);
        new Database_dao().veriler(db8);
        final SQLiteDatabase dbm = db5.getReadableDatabase();
        final SQLiteDatabase dbm2 = db6.getReadableDatabase();
        final SQLiteDatabase dbm3 = db7.getReadableDatabase();
        final SQLiteDatabase dbm4 = db8.getReadableDatabase();

        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);

        c = dbm.rawQuery("SELECT * FROM bildirim", null);
        c.moveToFirst();


        if (c.getCount() <= 0) {
            c.close();
            //Islemselect.setText("Öncelikle Gelir & gider giriniz..");
        } else {
            c.moveToLast();
            do {

                dataveriler.add("" + c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")));
                dataveriler2.add("" + c.getString(c.getColumnIndex("informationx")));
                dataveriler4.add("" + c.getInt(c.getColumnIndex("processid")));

            } while (c.moveToPrevious());





        }


        adapter.notifyDataSetChanged();
    }
}
