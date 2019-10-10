package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class editislemlerbildirim extends AppCompatActivity {

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
    private Cursor c,k, l,p;
    static TextView []tv = new TextView[10];
    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    private Databasehelper db2;
    String datex = "";
    String infox = "";
    String infocontrol = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editbildirimislemler);
        init();
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



        c = dbm.rawQuery("SELECT * FROM bildirim", null);
        c.moveToFirst();


        if (c.getCount() <= 0) {
            c.close();
            Islemselect.setText("Öncelikle bir bildirim giriniz..");
        } else {
            c.moveToFirst();
            do {

                final TextView tv = new TextView(this);
                TextView tv2 = new TextView(this);



                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv2.setLayoutParams(lp);

                counter = c.getInt(c.getColumnIndex("processid"));
                tv.setTag(counter);


                tv.setText("" + c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText("" + c.getString(c.getColumnIndex("informationx")) +"\n");




                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p = dbm4.rawQuery("SELECT * FROM bildirim WHERE processid=" + tv.getTag(), null);
                        p.moveToFirst();


                            View tasarim = getLayoutInflater().inflate(R.layout.editbildirim, null);
                        uyarı = tasarim.findViewById(R.id.uyarı);
                        alarminfotxt = tasarim.findViewById(R.id.alarminfotxt);
                        tarihset = tasarim.findViewById(R.id.tarihset);
                        tarihtxt = tasarim.findViewById(R.id.alarmdatetxt);
                        kayıtbtn= tasarim.findViewById(R.id.kayıtbutton);
                        iptalbtn= tasarim.findViewById(R.id.iptalbutton);
                        tariherror = tasarim.findViewById(R.id.tariherror);
                        infoerror = tasarim.findViewById(R.id.infoerror);


                        k = dbm2.rawQuery("SELECT * FROM bildirim", null);
                            k.moveToFirst();

                            l = dbm3.rawQuery("SELECT * FROM bildirim WHERE processid=" + tv.getTag(), null);
                            l.moveToFirst();
                                String deneme = l.getString(l.getColumnIndex("informationx"));
                            alarminfotxt.setText("" + l.getString(l.getColumnIndex("informationx")));
                            tarihset.setText("" + l.getInt(l.getColumnIndex("day")) + "." +
                                    l.getInt(l.getColumnIndex("month")) + "." +
                                    l.getInt(l.getColumnIndex("year")));

                        tarihset.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Calendar c = Calendar.getInstance();
                                    currentday = c.get(Calendar.DAY_OF_MONTH);
                                    currentmonth = c.get(Calendar.MONTH);
                                    currentyear = c.get(Calendar.YEAR);


                                    DatePickerDialog datepicker;

                                    datepicker = new DatePickerDialog(editislemlerbildirim.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                                            my_day = dayOfMonth;
                                            my_month = month + 1;
                                            my_year = year;
                                            tarihset.setText("" + my_day + " / " + my_month + " / " + my_year);
                                        }
                                    }, currentyear, currentmonth, currentday);
                                    datepicker.show();

                                }
                            });


                        kayıtbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                infocontrol=alarminfotxt.getText().toString();
                                if(my_year > 0 && !infocontrol.equals("")) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("informationx", "" + infocontrol);
                                    cv.put("day", "" + my_day);
                                    cv.put("month", "" + my_month);
                                    cv.put("year", "" + my_year);


                                    dbm3.update("bildirim", cv, "processid=" + tv.getTag(), null);
                                    dbm3.close();
                                    Intent intent = new Intent(editislemlerbildirim.this, masrafmain.class);
                                    startActivity(intent);
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.GONE);
                                }
                                else if(infocontrol.equals("") && my_year > 0)
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.GONE);

                                }
                                else if(!infocontrol.equals("") && my_year == 0)
                                {
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.VISIBLE);
                                }


                            }
                        });

                        iptalbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(editislemlerbildirim.this, masrafmain.class);
                                    startActivity(intent);
                                    dbm2.delete("bildirim", "processid=" + tv.getTag(), null);
                                    dbm2.close();
                                }
                            });


                            AlertDialog.Builder alert = new AlertDialog.Builder(editislemlerbildirim.this);
                            alert.setView(tasarim);
                            alert.create().show();


                    }


                });
                linear.addView(tv);
                linear2.addView(tv2);

            }
            while (c.moveToPrevious());
        }




    }

    void init() {
        linear = new LinearLayout(this);
        linear = findViewById(R.id.linear);
        linear2 = new LinearLayout(this);
        linear2 = findViewById(R.id.linear2);
        Islemselect = new TextView(this);
        Islemselect = findViewById(R.id.Islemselect);

    }

}