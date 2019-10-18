package com.example.kaanb.masrafmain;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class bildirimler extends AppCompatActivity {


    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private ConstraintLayout constraint;
    private ImageView tariherror,infoerror;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    static int my_day = 0, my_month = 0, my_year = 0;
    private Databasehelper db2,db3;
    String datex = "";
    String infox = "";
    int counter = 0;
    String infocontrol = "";


    public void init()
    {
        db2 = new Databasehelper(this);
        db3 = new Databasehelper(this);

        uyarı = new TextView(this);
        uyarı = findViewById(R.id.uyarı);

        alarminfotxt = new EditText(this);
        alarminfotxt = findViewById(R.id.alarminfotxt);

        tarihset = new TextView(this);
        tarihset = findViewById(R.id.tarihset);

        tarihtxt = new TextView(this);
        tarihtxt = findViewById(R.id.alarmdatetxt);

        kayıtbtn = new Button(this);
        kayıtbtn= findViewById(R.id.kayıtbutton);

        iptalbtn = new Button(this);
        iptalbtn= findViewById(R.id.iptalbutton);


        tariherror = new ImageView(this);
        tariherror = findViewById(R.id.tariherror);

        infoerror = new ImageView(this);
        infoerror = findViewById(R.id.infoerror);

        constraint = new ConstraintLayout(this);
        constraint = findViewById(R.id.constraint);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bildirimler);

        init();

        constraint.setBackgroundColor(Color.parseColor("#D0141414"));


        Calendar c = Calendar.getInstance();
        final int currentday = c.get(Calendar.DAY_OF_MONTH);
        final int currentmonth = c.get(Calendar.MONTH);
        final int currentyear = c.get(Calendar.YEAR);
        tarihtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatePickerDialog datepicker;

                datepicker = new DatePickerDialog(bildirimler.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        my_day = day;
                        my_month = month + 1;
                         my_year = year;
                        tarihtxt.setText("" + my_day + " / "  +(my_month) + " / " +  my_year);
                    }
                },currentyear,currentmonth,currentday);
                datepicker.show();
            }
        });

        kayıtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                infocontrol = alarminfotxt.getText().toString();
                if (my_year > 0 && !infocontrol.equals("")) {

                    new Database_dao().addingalarm(db2, infocontrol, my_day, my_month, my_year);
                    ///////////////


                    final Calendar e = Calendar.getInstance();
                    int mYear = e.get(Calendar.YEAR);
                    int mMonth = e.get(Calendar.MONTH);
                    int mDay = e.get(Calendar.DAY_OF_MONTH);


                    new Database_dao().veriler(db3);

                    SQLiteDatabase dbm3 = db3.getReadableDatabase();
                    Cursor g = dbm3.rawQuery("SELECT * FROM bildirim", null);


                    g.moveToLast();

                    /// bildirim

                    if (g.getCount() <= 0) {
                        g.close();

                    } else {


                        int checkprocessid = g.getInt(g.getColumnIndex("processid"));
                        int checkprocessid3 = g.getInt(g.getColumnIndex("processid")) + 1000;
                        int checkprocessid7 = g.getInt(g.getColumnIndex("processid")) + 10000;

                        int checkmonth = g.getInt(g.getColumnIndex("month"));
                        int checkday = g.getInt(g.getColumnIndex("day"));
                        int checkyear = g.getInt(g.getColumnIndex("year"));
                        String checkinfo = g.getString(g.getColumnIndex("informationx"));


                        if ((mYear == g.getInt(g.getColumnIndex("year"))) && (((mMonth + 1) == g.getInt(g.getColumnIndex("month"))) || ((mMonth + 2) == g.getInt(g.getColumnIndex("month"))))) {

                            if (((mMonth + 1) == g.getInt(g.getColumnIndex("month"))) && (mDay <= g.getInt(g.getColumnIndex("day")))) {

                                int day_counter = (g.getInt(g.getColumnIndex("day"))) - mDay;


                                int mycounterweek = 7, mycounter3 = 3, mycounterson = 0;
                                int myalarmday7 = 0, myalarmday3 = 0, myalarmdayson = 0;
                                myalarmday7 = mDay + (day_counter - mycounterweek);
                                myalarmday3 = mDay + (day_counter - mycounter3);
                                myalarmdayson = mDay;

                                bildirim(checkprocessid, checkmonth, checkyear, myalarmdayson, 0, checkinfo);
                                bildirim(checkprocessid3, checkmonth, checkyear, myalarmday3, 3, checkinfo);
                                bildirim(checkprocessid7, checkmonth, checkyear, myalarmday7, 7, checkinfo);

                            }

                        } else if (((mMonth + 2) == g.getInt(g.getColumnIndex("month")))) {
                            int res = e.getActualMaximum(Calendar.DATE);
                            int day_counter = (g.getInt(g.getColumnIndex("day"))) + (res - mDay);


                            int mycounterweek = 7, mycounter3 = 3, mycounterson = 0;
                            int myalarmday7 = 0, myalarmday3 = 0, myalarmdayson = 0;
                            myalarmday7 = mDay + (day_counter - mycounterweek);
                            myalarmday3 = mDay + (day_counter - mycounter3);
                            myalarmdayson = mDay;

                            bildirim(checkprocessid, checkmonth, checkyear, myalarmdayson, 0, checkinfo);
                            bildirim(checkprocessid3, checkmonth, checkyear, myalarmday3, 3, checkinfo);
                            bildirim(checkprocessid7, checkmonth, checkyear, myalarmday7, 7, checkinfo);

                        }

                }









                    /////////
                    Intent intent = new Intent(bildirimler.this, masrafmain.class);
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
                Intent intent = new Intent(bildirimler.this, masrafmain.class);
                startActivity(intent);
            }
        });

    }


    private void bildirim(int id,int ayson,int yilson,int gunson,int kalan,String aciklama) {

        NotificationCompat.Builder builder;

        String messagekalan = "";
        NotificationManager bildirimYoneticisi =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        Intent ıntent = new Intent(this,masrafmain.class);

        PendingIntent gidilecekIntent = PendingIntent.getActivity(this,id,ıntent,PendingIntent.FLAG_UPDATE_CURRENT);

        if(kalan == 0)
        {
            messagekalan = "bugün son gün !";
        }
        else if(kalan ==3)
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
                new Intent(bildirimler.this,Bildirimyakalayici.class);


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


        calendar.set(2019, 9, 18, 19, 59, 0);

        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {


            reminderDateTimeInMilliseconds = calendar.getTimeInMillis();


            alarmManager.set(AlarmManager.RTC_WAKEUP,reminderDateTimeInMilliseconds,gidilecekBroadcast);

        }


    }




}
