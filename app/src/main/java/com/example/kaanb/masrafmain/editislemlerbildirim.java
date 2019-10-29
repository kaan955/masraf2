package com.example.kaanb.masrafmain;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class editislemlerbildirim extends AppCompatActivity {
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
        setContentView(R.layout.editbildirimislemler);
        setTitle("Verinin üstüne basarak 'DUZENLE'");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        init();

        constraintana.setBackgroundColor(Color.parseColor("#D0141414"));
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
            c.moveToLast();
            do {

                final TextView tv = new TextView(this);
                final TextView tv2 = new TextView(this);
                final TextView tv3 = new TextView(this);



                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setTextColor(Color.parseColor("#bdbdbd"));
                tv2.setLayoutParams(lp);
                tv2.setTextColor(Color.parseColor("#bdbdbd"));
                tv3.setLayoutParams(lp);
                tv3.setTextColor(Color.parseColor("#bdbdbd"));

                counter = c.getInt(c.getColumnIndex("processid"));
                tv.setTag(counter);


                tv.setText("" + c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText("" + c.getString(c.getColumnIndex("informationx")) +"\n");

                if( c.getString(c.getColumnIndex("repeat")).equals("NO"))
                {
                    tv3.setText("Değil" + "\n");
                }
                else if(c.getString(c.getColumnIndex("repeat")).equals("YES"))
                {
                    tv3.setText("Tekrarlı" +"\n");
                }





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
                        monthrepeat = tasarim.findViewById(R.id.monthrepeat);


                        k = dbm2.rawQuery("SELECT * FROM bildirim", null);
                            k.moveToFirst();



                            l = dbm3.rawQuery("SELECT * FROM bildirim WHERE processid=" + tv.getTag(), null);
                            l.moveToFirst();

                        final int checkprocessid = l.getInt(l.getColumnIndex("processid"));
                        final int checkprocessid3 = l.getInt(l.getColumnIndex("processid"))+10000;
                        final int checkprocessid7 = l.getInt(l.getColumnIndex("processid"))+100000;

                        int checkmonth = l.getInt(l.getColumnIndex("month"));
                        int checkday = l.getInt(l.getColumnIndex("day"));
                        int checkyear = l.getInt(l.getColumnIndex("year"));
                          final String checkinfo = l.getString(l.getColumnIndex("informationx"));


                                String deneme = l.getString(l.getColumnIndex("informationx"));
                                final int processid = l.getInt(l.getColumnIndex("processid"));

                            alarminfotxt.setText("" + l.getString(l.getColumnIndex("informationx")));
                            tarihset.setText("" + l.getInt(l.getColumnIndex("day")) + "." +
                                    l.getInt(l.getColumnIndex("month")) + "." +
                                    l.getInt(l.getColumnIndex("year")));
                        if (l.getString(l.getColumnIndex("repeat")).equals("YES")) {
                            monthrepeat.setChecked(true);
                        }

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
                                String datecontrol ="";
                                datecontrol = tarihset.getText().toString();

                                if (monthrepeat.isChecked()) {
                                    myrepeat = "YES";
                                } else {
                                    myrepeat = "NO";
                                }

                                infocontrol=alarminfotxt.getText().toString();
                                if(!datecontrol.equals("") && !infocontrol.equals("")) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("informationx", "" + infocontrol);
                                    cv.put("day", "" + my_day);
                                    cv.put("month", "" + my_month);
                                    cv.put("year", "" + my_year);
                                    cv.put("repeat",""+ myrepeat);


                                    dbm3.update("bildirim", cv, "processid=" + tv.getTag(), null);
                                    dbm3.close();


                                    ///////////////////


                                    bildirim(checkprocessid, 1, 1, 1, 0, infocontrol);

                                    ///////////




                                    dbm3.close();
                                    Intent intent = new Intent(editislemlerbildirim.this, masrafmain.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Kayıt başarılı",Toast.LENGTH_SHORT).show();
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.GONE);
                                }
                                else if(infocontrol.equals("") && !datecontrol.equals(""))
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.GONE);

                                }
                                else if(!infocontrol.equals("") && !datecontrol.equals(""))
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
                                    Toast.makeText(getApplicationContext(),"Silindi!",Toast.LENGTH_SHORT).show();
                                    dbm2.delete("bildirim", "processid=" + tv.getTag(), null);



                                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                    Intent myIntent = new Intent(getApplicationContext(), Bildirimyakalayici.class);
                                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                            getApplicationContext(), checkprocessid, myIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
                                    PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                                            getApplicationContext(), checkprocessid3, myIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);
                                    PendingIntent pendingIntent7 = PendingIntent.getBroadcast(
                                            getApplicationContext(), checkprocessid7, myIntent,
                                            PendingIntent.FLAG_UPDATE_CURRENT);

                                    alarmManager.cancel(pendingIntent);
                                    alarmManager.cancel(pendingIntent3);
                                    alarmManager.cancel(pendingIntent7);


                                    dbm2.close();


                                }
                            });


                            AlertDialog.Builder alert = new AlertDialog.Builder(editislemlerbildirim.this);
                            alert.setView(tasarim);
                            alert.create().show();


                    }


                });




                tv2.setOnClickListener(new View.OnClickListener() {
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
                        monthrepeat = tasarim.findViewById(R.id.monthrepeat);


                        k = dbm2.rawQuery("SELECT * FROM bildirim", null);
                        k.moveToFirst();



                        l = dbm3.rawQuery("SELECT * FROM bildirim WHERE processid=" + tv.getTag(), null);
                        l.moveToFirst();

                        final int checkprocessid = l.getInt(l.getColumnIndex("processid"));
                        final int checkprocessid3 = l.getInt(l.getColumnIndex("processid"))+10000;
                        final int checkprocessid7 = l.getInt(l.getColumnIndex("processid"))+100000;

                        int checkmonth = l.getInt(l.getColumnIndex("month"));
                        int checkday = l.getInt(l.getColumnIndex("day"));
                        int checkyear = l.getInt(l.getColumnIndex("year"));
                        final String checkinfo = l.getString(l.getColumnIndex("informationx"));


                        String deneme = l.getString(l.getColumnIndex("informationx"));
                        final int processid = l.getInt(l.getColumnIndex("processid"));

                        alarminfotxt.setText("" + l.getString(l.getColumnIndex("informationx")));
                        tarihset.setText("" + l.getInt(l.getColumnIndex("day")) + "." +
                                l.getInt(l.getColumnIndex("month")) + "." +
                                l.getInt(l.getColumnIndex("year")));
                        if (l.getString(l.getColumnIndex("repeat")).equals("YES")) {
                            monthrepeat.setChecked(true);
                        }

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

                                if (monthrepeat.isChecked()) {
                                    myrepeat = "YES";
                                } else {
                                    myrepeat = "NO";
                                }

                                infocontrol=alarminfotxt.getText().toString();
                                if(my_year > 0 && !infocontrol.equals("")) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("informationx", "" + infocontrol);
                                    cv.put("day", "" + my_day);
                                    cv.put("month", "" + my_month);
                                    cv.put("year", "" + my_year);
                                    cv.put("repeat",""+ myrepeat);


                                    dbm3.update("bildirim", cv, "processid=" + tv.getTag(), null);
                                    dbm3.close();


                                    ///////////////////


                                    bildirim(checkprocessid, 1, 1, 1, 0, infocontrol);

                                    ///////////




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



                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent myIntent = new Intent(getApplicationContext(), Bildirimyakalayici.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid3, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                PendingIntent pendingIntent7 = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid7, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

                                alarmManager.cancel(pendingIntent);
                                alarmManager.cancel(pendingIntent3);
                                alarmManager.cancel(pendingIntent7);


                                dbm2.close();


                            }
                        });


                        AlertDialog.Builder alert = new AlertDialog.Builder(editislemlerbildirim.this);
                        alert.setView(tasarim);
                        alert.create().show();


                    }


                });

                tv3.setOnClickListener(new View.OnClickListener() {
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
                        monthrepeat = tasarim.findViewById(R.id.monthrepeat);


                        k = dbm2.rawQuery("SELECT * FROM bildirim", null);
                        k.moveToFirst();



                        l = dbm3.rawQuery("SELECT * FROM bildirim WHERE processid=" + tv.getTag(), null);
                        l.moveToFirst();

                        final int checkprocessid = l.getInt(l.getColumnIndex("processid"));
                        final int checkprocessid3 = l.getInt(l.getColumnIndex("processid"))+10000;
                        final int checkprocessid7 = l.getInt(l.getColumnIndex("processid"))+100000;

                        int checkmonth = l.getInt(l.getColumnIndex("month"));
                        int checkday = l.getInt(l.getColumnIndex("day"));
                        int checkyear = l.getInt(l.getColumnIndex("year"));
                        final String checkinfo = l.getString(l.getColumnIndex("informationx"));


                        String deneme = l.getString(l.getColumnIndex("informationx"));
                        final int processid = l.getInt(l.getColumnIndex("processid"));

                        alarminfotxt.setText("" + l.getString(l.getColumnIndex("informationx")));
                        tarihset.setText("" + l.getInt(l.getColumnIndex("day")) + "." +
                                l.getInt(l.getColumnIndex("month")) + "." +
                                l.getInt(l.getColumnIndex("year")));
                        if (l.getString(l.getColumnIndex("repeat")).equals("YES")) {
                            monthrepeat.setChecked(true);
                        }

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

                                if (monthrepeat.isChecked()) {
                                    myrepeat = "YES";
                                } else {
                                    myrepeat = "NO";
                                }

                                infocontrol=alarminfotxt.getText().toString();
                                if(my_year > 0 && !infocontrol.equals("")) {
                                    ContentValues cv = new ContentValues();
                                    cv.put("informationx", "" + infocontrol);
                                    cv.put("day", "" + my_day);
                                    cv.put("month", "" + my_month);
                                    cv.put("year", "" + my_year);
                                    cv.put("repeat",""+ myrepeat);


                                    dbm3.update("bildirim", cv, "processid=" + tv.getTag(), null);
                                    dbm3.close();


                                    ///////////////////


                                    bildirim(checkprocessid, 1, 1, 1, 0, infocontrol);

                                    ///////////




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



                                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                Intent myIntent = new Intent(getApplicationContext(), Bildirimyakalayici.class);
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid3, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);
                                PendingIntent pendingIntent7 = PendingIntent.getBroadcast(
                                        getApplicationContext(), checkprocessid7, myIntent,
                                        PendingIntent.FLAG_UPDATE_CURRENT);

                                alarmManager.cancel(pendingIntent);
                                alarmManager.cancel(pendingIntent3);
                                alarmManager.cancel(pendingIntent7);


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
                linear3.addView(tv3);

            } while (c.moveToPrevious());
        }




    }

    void init() {
        linear = new LinearLayout(this);
        linear = findViewById(R.id.linear);
        linear2 = new LinearLayout(this);
        linear2 = findViewById(R.id.linear2);
        Islemselect = new TextView(this);
        Islemselect = findViewById(R.id.Islemselect);

        constraintana = new ConstraintLayout(this);
        constraintana = findViewById(R.id.constraintana);

        scroll = new ScrollView(this);
        scroll = findViewById(R.id.scroll);

        consta = new ConstraintLayout(this);
        consta = findViewById(R.id.consta);
        linear3= new LinearLayout(this);
        linear3 = findViewById(R.id.linear3);

    }


    private void bildirim(int id,int ayson,int yilson,int gunson,int kalan,String aciklama) {

        NotificationCompat.Builder builder;

        String messagekalan = "";
        NotificationManager bildirimYoneticisi =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(this,editislemlerbildirim.class);

        PendingIntent gidilecekIntent = PendingIntent.getActivity(this,id,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(kalan == 0)
        {
            messagekalan = "bugün son gün !";
        }
        else if(kalan == 3)
        {
            messagekalan = "Son 3 gün!";
        }
        else if(kalan == 7)
        {
            messagekalan = "7 gün kaldı.";
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // For Oreo

            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";

            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = bildirimYoneticisi.getNotificationChannel(kanalId);

            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                kanal.setDescription(kanalTanım);
                bildirimYoneticisi.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(this, kanalId);

            builder.setContentTitle("Bildirim")  // gerekli
                    .setContentText("" + aciklama +", " + messagekalan)  // gerekli
                    .setSmallIcon(R.drawable.grennadd) // gerekli
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setContentIntent(gidilecekIntent);

        } else {

            builder = new NotificationCompat.Builder(this);

            builder.setContentTitle("Bildirim")  // gerekli
                    .setContentText("" + aciklama +", " + messagekalan)  // gerekli
                    .setSmallIcon(R.drawable.grennadd) // gerekli
                    .setContentIntent(gidilecekIntent)
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setPriority(Notification.PRIORITY_HIGH);
        }


        Intent broadcastIntent =
                new Intent(editislemlerbildirim.this,Bildirimyakalayici.class);


        broadcastIntent.putExtra("bildirimNesnesi",builder.build());

        PendingIntent gidilecekBroadcast = PendingIntent.getBroadcast(this,id,broadcastIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager =
                (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Date currentTime = Calendar.getInstance().getTime();
        long reminderDateTimeInMilliseconds=0000;

        Random r=new Random(); //random sınıfı
        int result = r.nextInt(59-1) + 1;


        calendar.set(2019, 9, 19, 21, 34, 0);

        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {


            reminderDateTimeInMilliseconds = calendar.getTimeInMillis();


            alarmManager.set(AlarmManager.RTC_WAKEUP,reminderDateTimeInMilliseconds,gidilecekBroadcast);

        }


    }

}