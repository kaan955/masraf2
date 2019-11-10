package com.example.kaanb.masrafmain.Edit;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaanb.masrafmain.Database.Database_dao;
import com.example.kaanb.masrafmain.Database.Databasehelper;
import com.example.kaanb.masrafmain.Mainthings.Bildirimyakalayici;
import com.example.kaanb.masrafmain.Mainthings.masrafmain;
import com.example.kaanb.masrafmain.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class Islembildirim extends RecyclerView.Adapter<Islembildirim.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<String> fromanotherlist,fromanotherlist2,fromanotherlist3,fromanotherlist4;
    private Databasehelper db5,db6,db7,db8;
    private Cursor c,k,z,p,l;
    static TextView []tv = new TextView[10];
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
    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    private Databasehelper db2;
    private ScrollView scroll;
    String datex = "";
    String infox = "";
    String infocontrol = "";


    public Islembildirim(Context mContext, List<String> text1,List<String> text2,List<String> text3,List<String> text4) {
        this.mContext = mContext;
        this.fromanotherlist = text1;
        this.fromanotherlist2 = text2;
        this.fromanotherlist3 = text3;
        this.fromanotherlist4 = text4;


    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView satirYazi,infom,ucretim;
        public CardView satirCardView;
        public ImageView imageViewNoktaResim;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            satirYazi =  view.findViewById(R.id.satirYazi);
            satirCardView =  view.findViewById(R.id.satirCardView);
            infom =  view.findViewById(R.id.infom);
            ucretim =  view.findViewById(R.id.ucretim);


        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_islemler, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final String gelenveri = fromanotherlist.get(position);
        final String gelenveri2 = fromanotherlist2.get(position);
       // final String gelenveri3 = fromanotherlist3.get(position);
        final String gelenveri4 = fromanotherlist4.get(position);

        holder.satirYazi.setText(gelenveri.toString());
        holder.infom.setText(gelenveri2.toString());
       // holder.ucretim.setText(gelenveri3.toString());

        db5 = new Databasehelper(mContext);
        db6= new Databasehelper(mContext);
        db7= new Databasehelper(mContext);
        db8= new Databasehelper(mContext);



        new Database_dao().veriler(db7);
        new Database_dao().veriler(db6);
        new Database_dao().veriler(db5);
        new Database_dao().veriler(db8);
        final SQLiteDatabase dbm = db5.getReadableDatabase();
        final SQLiteDatabase dbm2 = db6.getReadableDatabase();
        final SQLiteDatabase dbm3 = db7.getReadableDatabase();
        final SQLiteDatabase dbm4 = db8.getReadableDatabase();




        holder.satirCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p = dbm4.rawQuery("SELECT * FROM bildirim WHERE processid=" + gelenveri4.toString(), null);
                p.moveToFirst();




                    final View tasarim = LayoutInflater.from(mContext).inflate(R.layout.editbildirim, null, false);

                    uyarı = tasarim.findViewById(R.id.uyarı);
                    alarminfotxt = tasarim.findViewById(R.id.alarminfotxt);
                    tarihset = tasarim.findViewById(R.id.tarihset);
                    tarihtxt = tasarim.findViewById(R.id.alarmdatetxt);
                    kayıtbtn = tasarim.findViewById(R.id.kayıtbutton);
                    iptalbtn = tasarim.findViewById(R.id.iptalbutton);
                    tariherror = tasarim.findViewById(R.id.tariherror);
                    infoerror = tasarim.findViewById(R.id.infoerror);
                    monthrepeat = tasarim.findViewById(R.id.monthrepeat);
                constraintana = tasarim.findViewById(R.id.constraintana);
                constraintana.setBackgroundColor(Color.parseColor("#D0141414"));


                    k = dbm2.rawQuery("SELECT * FROM bildirim", null);
                    k.moveToFirst();


                    l = dbm3.rawQuery("SELECT * FROM bildirim WHERE processid=" + gelenveri4.toString(), null);
                    l.moveToFirst();

                    final int checkprocessid = l.getInt(l.getColumnIndex("processid"));
                    final int checkprocessid3 = l.getInt(l.getColumnIndex("processid")) + 10000;
                    final int checkprocessid7 = l.getInt(l.getColumnIndex("processid")) + 100000;

                    int checkmonth = l.getInt(l.getColumnIndex("month"));
                    int checkday = l.getInt(l.getColumnIndex("day"));
                    int checkyear = l.getInt(l.getColumnIndex("year"));
                    final String checkinfo = l.getString(l.getColumnIndex("informationx"));


                    String deneme = l.getString(l.getColumnIndex("informationx"));
                    final int processid = l.getInt(l.getColumnIndex("processid"));
                    my_day = l.getInt(l.getColumnIndex("day"));
                       my_month = l.getInt(l.getColumnIndex("month"));
                     my_year = l.getInt(l.getColumnIndex("year"));
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

                            datepicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
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
                            String datecontrol = "";
                            datecontrol = tarihset.getText().toString();

                            if (monthrepeat.isChecked()) {
                                myrepeat = "YES";
                            } else {
                                myrepeat = "NO";
                            }

                            infocontrol = alarminfotxt.getText().toString();
                            if (!datecontrol.equals("") && !infocontrol.equals("")) {
                                ContentValues cv = new ContentValues();
                                cv.put("informationx", "" + infocontrol);
                                cv.put("day", "" + my_day);
                                cv.put("month", "" + my_month);
                                cv.put("year", "" + my_year);
                                cv.put("repeat", "" + myrepeat);


                                dbm3.update("bildirim", cv, "processid=" + gelenveri4.toString(), null);
                                dbm3.close();


                                ///////////////////


                                bildirim(checkprocessid, 1, 1, 1, 0, infocontrol);

                                ///////////


                                dbm3.close();
                                Intent intent = new Intent(mContext, masrafmain.class);
                                mContext.startActivity(intent);
                                Toast.makeText(mContext.getApplicationContext(), "Kayıt başarılı", Toast.LENGTH_SHORT).show();
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.GONE);
                            } else if (infocontrol.equals("") && !datecontrol.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.GONE);

                            } else if (!infocontrol.equals("") && !datecontrol.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.VISIBLE);
                            } else {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                            }


                        }
                    });

                    iptalbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext, masrafmain.class);
                            mContext.startActivity(intent);
                            Toast.makeText(mContext.getApplicationContext(), "Silindi!", Toast.LENGTH_SHORT).show();
                            dbm2.delete("bildirim", "processid=" + gelenveri4.toString(), null);


                            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                            Intent myIntent = new Intent(mContext.getApplicationContext(), Bildirimyakalayici.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                    mContext.getApplicationContext(), checkprocessid, myIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(
                                    mContext.getApplicationContext(), checkprocessid3, myIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            PendingIntent pendingIntent7 = PendingIntent.getBroadcast(
                                    mContext.getApplicationContext(), checkprocessid7, myIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);

                            alarmManager.cancel(pendingIntent);
                            alarmManager.cancel(pendingIntent3);
                            alarmManager.cancel(pendingIntent7);


                            dbm2.close();


                        }
                    });


                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                    alert.setView(tasarim);
                    alert.create().show();

                }



            });

    }

    private void bildirim(int id,int ayson,int yilson,int gunson,int kalan,String aciklama) {

        NotificationCompat.Builder builder;

        String messagekalan = "";
        NotificationManager bildirimYoneticisi =
                (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        View v = new View(mContext);
        Context context = v.getContext();
        Intent intent = new Intent(context, masrafmain.class);
        context.startActivity(intent);

        PendingIntent gidilecekIntent = PendingIntent.getActivity(mContext,id,intent,PendingIntent.FLAG_UPDATE_CURRENT);

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

            builder = new NotificationCompat.Builder(mContext);

            builder.setContentTitle("Bildirim")  // gerekli
                    .setContentText("" + aciklama +", " + messagekalan)  // gerekli
                    .setSmallIcon(R.drawable.grennadd) // gerekli
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setContentIntent(gidilecekIntent);

        } else {

            builder = new NotificationCompat.Builder(mContext);

            builder.setContentTitle("Bildirim")  // gerekli
                    .setContentText("" + aciklama +", " + messagekalan)  // gerekli
                    .setSmallIcon(R.drawable.grennadd) // gerekli
                    .setContentIntent(gidilecekIntent)
                    .setAutoCancel(true)  // Bildirim tıklandıktan sonra kaybolur."
                    .setPriority(Notification.PRIORITY_HIGH);
        }


        Intent broadcastIntent =
                new Intent(mContext,Bildirimyakalayici.class);


        broadcastIntent.putExtra("bildirimNesnesi",builder.build());

        PendingIntent gidilecekBroadcast = PendingIntent.getBroadcast(mContext,id,broadcastIntent
                ,PendingIntent.FLAG_UPDATE_CURRENT);


        AlarmManager alarmManager =
                (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        Date currentTime = Calendar.getInstance().getTime();
        long reminderDateTimeInMilliseconds=0000;

        Random r=new Random(); //random sınıfı
        int result = r.nextInt(59-1) + 1;


        calendar.set(2019, 9, 19, 21, result, 0);

        if (System.currentTimeMillis() < calendar.getTimeInMillis()) {


            reminderDateTimeInMilliseconds = calendar.getTimeInMillis();


            alarmManager.set(AlarmManager.RTC_WAKEUP,reminderDateTimeInMilliseconds,gidilecekBroadcast);

        }


    }




    @Override
    public int getItemCount() {
        return fromanotherlist.size();
    }

}


