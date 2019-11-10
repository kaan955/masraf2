package com.example.kaanb.masrafmain.Edit;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import com.example.kaanb.masrafmain.R;
import com.example.kaanb.masrafmain.Mainthings.masrafmain;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class editislemler extends AppCompatActivity {
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
    static int topcounter = 0;
    private LinearLayout linear,linear2,linear3,linear4,linear5,linear6;
    private ConstraintLayout constraintana;
    private ScrollView scroll;


    private Cursor c,k,z,p;
    static TextView []tv = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editislemler);
        setTitle("Verinin üstüne basarak 'DUZENLE'");
        init();
        db5 = new Databasehelper(this);
        db6= new Databasehelper(this);
        db7= new Databasehelper(this);
        db8= new Databasehelper(this);

        constraintana.setBackgroundColor(Color.parseColor("#D0141414"));

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

        c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToFirst();


        if (c.getCount() <= 0) {
            c.close();
            //Islemselect.setText("Öncelikle Gelir & gider giriniz..");
        } else {
            c.moveToLast();
            do {
                topcounter++;


                final TextView tv = new TextView(this);
                final TextView tv2 = new TextView(this);


                counter = c.getInt(c.getColumnIndex("processid"));

                ConstraintLayout.LayoutParams clpcontactUs = new ConstraintLayout.LayoutParams(
                        750, 100);
                ConstraintLayout.LayoutParams clpcontactUs2 = new ConstraintLayout.LayoutParams(
                        750, 100);
                tv.setLayoutParams(clpcontactUs);
                tv2.setLayoutParams(clpcontactUs2);





                tv.setText("" + c.getString(c.getColumnIndex("info")));
                tv2.setText("" + c.getString(c.getColumnIndex("info")));


                tv.setTextSize(25);
                tv2.setTextSize(25);
                tv.setTag(counter);
                tv.setBackgroundResource(R.drawable.backgroundpicture);
                tv.setId(View.generateViewId());
                tv2.setId(View.generateViewId());
                constraintana.addView(tv);
                constraintana.addView(tv2);



                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintana);



                constraintSet.connect(tv.getId(), ConstraintSet.TOP, constraintana.getId(), ConstraintSet.TOP, topcounter*100);
                constraintSet.connect(tv.getId(), ConstraintSet.LEFT, constraintana.getId(), ConstraintSet.LEFT, 20);
                constraintSet.connect(tv2.getId(), ConstraintSet.TOP, constraintana.getId(), ConstraintSet.TOP, topcounter*100);
                constraintSet.connect(tv2.getId(), ConstraintSet.RIGHT,constraintana.getId(), ConstraintSet.RIGHT, 0);
                constraintSet.applyTo(constraintana);











                final TextView tv3 = new TextView(this);
                final TextView tv4 = new TextView(this);
                final TextView tv5 = new TextView(this);
                final TextView tv6 = new TextView(this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv.setTextColor(Color.parseColor("#bdbdbd"));
                tv2.setLayoutParams(lp);
                tv2.setTextColor(Color.parseColor("#bdbdbd"));
                tv3.setLayoutParams(lp);
                tv3.setTextColor(Color.parseColor("#bdbdbd"));
                tv4.setLayoutParams(lp);
                tv4.setTextColor(Color.parseColor("#bdbdbd"));
                tv5.setLayoutParams(lp);
                tv5.setTextColor(Color.parseColor("#bdbdbd"));
                tv6.setLayoutParams(lp);
                tv6.setTextColor(Color.parseColor("#bdbdbd"));
                counter = c.getInt(c.getColumnIndex("processid"));
                tv.setTag(counter);
                tv.setText(c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText(c.getString(c.getColumnIndex("info")) +"\n");
                tv3.setText("₺" +String.format ("%.2f", c.getDouble(c.getColumnIndex("price"))) + "\n");
                if(c.getString(c.getColumnIndex("type")).equals("gelir"))
                {
                    tv4.setText("Gelir"  +"\n");
                }
                else if(c.getString(c.getColumnIndex("type")).equals("gider"))
                {
                    tv4.setText("Gider" +"\n");
                }
                tv5.setText("" +  c.getString(c.getColumnIndex("label"))+"\n");
                if( c.getString(c.getColumnIndex("repeat")).equals("NO"))
                {
                    tv6.setText("Değil" + "\n");
                }
                else if(c.getString(c.getColumnIndex("repeat")).equals("YES"))
                {
                    tv6.setText("Tekrarlı" +"\n");
                }
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        p = dbm4.rawQuery("SELECT * FROM holder WHERE processid=" + tv.getTag(), null);
                        p.moveToFirst();
                        if (p.getString(p.getColumnIndex("type")).equals("gelir")) {
                            View tasarim = getLayoutInflater().inflate(R.layout.editdialog, null);
                            myspinner = tasarim.findViewById(R.id.spinnerlabel);
                            tarihtext = tasarim.findViewById(R.id.tarihpicker);
                            kayıtgelirbtn = tasarim.findViewById(R.id.kayıtgelirbtn);
                            informationtext = tasarim.findViewById(R.id.informationtext);
                            pricetxt = tasarim.findViewById(R.id.pricetxt);
                            deletegelirbtn = tasarim.findViewById(R.id.deletegelirbtn);
                            infoerror = tasarim.findViewById(R.id.infoerror);
                            tariherror = tasarim.findViewById(R.id.tariherror);
                            tutarerror = tasarim.findViewById(R.id.tutarerror);
                            monthrepeat = tasarim.findViewById(R.id.monthrepeat);
                            k = dbm2.rawQuery("SELECT * FROM holder", null);
                            k.moveToFirst();
                            z = dbm3.rawQuery("SELECT * FROM holder WHERE processid=" + tv.getTag(), null);
                            z.moveToFirst();
                            informationtext.setText("" + z.getString(z.getColumnIndex("info")));
                            tarihtext.setText("Tarih: " + z.getInt(z.getColumnIndex("day")) + "." +
                                    z.getInt(z.getColumnIndex("month")) + "." +
                                    z.getInt(z.getColumnIndex("year")));
                            pricetxt.setText("" + z.getInt(z.getColumnIndex("price")));
                            final ArrayList<String> etiketler = new ArrayList<>();
                            final ArrayAdapter<String> etiketadaptoru;
                            etiketler.add("Diğer");
                            etiketler.add("Maaş");
                            etiketler.add("Satış");
                            etiketler.add("Faiz");
                            etiketler.add("Kira");
                            etiketler.add("Ek iş");
                            etiketadaptoru = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, etiketler);
                            myspinner.setAdapter(etiketadaptoru);
                            String getselected = ("" + z.getString(z.getColumnIndex("label")));
                            myspinner.setSelection(etiketadaptoru.getPosition(getselected));
                            String s = z.getString(z.getColumnIndex("repeat"));
                            if (z.getString(z.getColumnIndex("repeat")).equals("YES")) {
                                monthrepeat.setChecked(true);
                            }
                            tarihtext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar c = Calendar.getInstance();
                                    currentday = c.get(Calendar.DAY_OF_MONTH);
                                    currentmonth = c.get(Calendar.MONTH);
                                    currentyear = c.get(Calendar.YEAR);
                                    DatePickerDialog datepicker;
                                    datepicker = new DatePickerDialog(editislemler.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            my_day = dayOfMonth;
                                            my_month = month + 1;
                                            my_year = year;
                                            tarihtext.setText("Tarih: " + my_day + " / " + my_month + " / " + my_year);
                                        }
                                    }, currentyear, currentmonth, currentday);
                                    datepicker.show();
                                }
                            });
                            kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String s, spin, control,datecontrol, pricescontrol = pricetxt.getText().toString();
                                    control = informationtext.getText().toString();
                                    datecontrol = tarihtext.getText().toString();
                                    if (monthrepeat.isChecked()) {
                                        myrepeat = "YES";
                                    } else {
                                        myrepeat = "NO";
                                    }
                                    if (!datecontrol.equals("") && !pricescontrol.equals("") && !control.equals("")) {
                                        String pricesx = pricetxt.getText().toString();
                                        prices = Double.parseDouble(pricesx);
                                        s = informationtext.getText().toString();
                                        spin = myspinner.getSelectedItem().toString();
                                        ContentValues cv = new ContentValues();
                                        cv.put("info", "" + s);
                                        cv.put("day", "" + my_day);
                                        cv.put("month", "" + my_month);
                                        cv.put("year", "" + my_year);
                                        cv.put("price", "" + prices);
                                        cv.put("repeat", "" + myrepeat);
                                        cv.put("label", "" + spin);
                                        dbm.update("holder", cv, "processid=" + tv.getTag(), null);
                                        dbm.close();
                                        Intent intent = new Intent(editislemler.this, masrafmain.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Düzenleme Başarılı!",Toast.LENGTH_LONG).show();
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (datecontrol.equals("") && pricescontrol.equals("") && control.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (!datecontrol.equals("") && pricescontrol.equals("") && !control.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (datecontrol.equals("") && !control.equals("") && !pricescontrol.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!pricescontrol.equals("") && control.equals("") &&!datecontrol.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!control.equals("") && pricescontrol.equals("") && datecontrol.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && pricescontrol.equals("") && !datecontrol.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && !pricescontrol.equals("") && datecontrol.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            deletegelirbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(editislemler.this, masrafmain.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Silindi!",Toast.LENGTH_LONG).show();
                                    dbm2.delete("holder", "processid=" + tv.getTag(), null);
                                    dbm2.close();
                                }
                            });
                            AlertDialog.Builder alert = new AlertDialog.Builder(editislemler.this);
                            //alert.setMessage("Deneme alarm");
                            alert.setView(myspinner);
                            alert.setView(tasarim);
                            alert.create().show();
                        } else {
                            View tasarim = getLayoutInflater().inflate(R.layout.gideredit, null);
                            myspinner = tasarim.findViewById(R.id.spinnerlabel);
                            tarihtext = tasarim.findViewById(R.id.tarihpicker);
                            kayıtgelirbtn = tasarim.findViewById(R.id.kayıtgelirbtn);
                            informationtext = tasarim.findViewById(R.id.informationtext);
                            pricetxt = tasarim.findViewById(R.id.pricetxt);
                            deletegelirbtn = tasarim.findViewById(R.id.deletegelirbtn);
                            infoerror = tasarim.findViewById(R.id.infoerror);
                            tariherror = tasarim.findViewById(R.id.tariherror);
                            tutarerror = tasarim.findViewById(R.id.tutarerror);
                            monthrepeat = tasarim.findViewById(R.id.monthrepeat);
                            taksittxt = tasarim.findViewById(R.id.taksittxt);
                            k = dbm2.rawQuery("SELECT * FROM holder", null);
                            k.moveToFirst();
                            z = dbm3.rawQuery("SELECT * FROM holder WHERE processid=" + tv.getTag(), null);
                            z.moveToFirst();
                            informationtext.setText("" + z.getString(z.getColumnIndex("info")));
                            tarihtext.setText("Tarih: " + z.getInt(z.getColumnIndex("day")) + "." +
                                    z.getInt(z.getColumnIndex("month")) + "." +
                                    z.getInt(z.getColumnIndex("year")));
                            pricetxt.setText("" + z.getInt(z.getColumnIndex("price")));
                            final ArrayList<String> etiketler = new ArrayList<>();
                            final ArrayAdapter<String> etiketadaptoru;
                            etiketler.add("Diğer");
                            etiketler.add("Maaş");
                            etiketler.add("Yemek");
                            etiketler.add("Eğlence");
                            etiketler.add("Yol");
                            etiketler.add("Araba");
                            etiketler.add("Sağlık");
                            etiketler.add("Giyim");
                            etiketler.add("Eğitim");
                            etiketler.add("Sigara");
                            etiketler.add("Ev");
                            etiketler.add("Fatura");
                            etiketler.add("Market");
                            etiketler.add("Hobiler");
                            etiketler.add("Telefon");
                            etiketadaptoru = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, etiketler);
                            myspinner.setAdapter(etiketadaptoru);
                            int selection = 0;
                            int finder = 0;
                            String getselected = ("" + z.getString(z.getColumnIndex("label")));
                            if (z.getString(z.getColumnIndex("repeat")).equals("YES")) {
                                monthrepeat.setChecked(true);
                            }
                            myspinner.setSelection(etiketadaptoru.getPosition(getselected));
                            taksittxt.setText("" + z.getInt(z.getColumnIndex("taksit")));
                            tarihtext.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Calendar c = Calendar.getInstance();
                                    currentday = c.get(Calendar.DAY_OF_MONTH);
                                    currentmonth = c.get(Calendar.MONTH);
                                    currentyear = c.get(Calendar.YEAR);
                                    DatePickerDialog datepicker;
                                    datepicker = new DatePickerDialog(editislemler.this, new DatePickerDialog.OnDateSetListener() {
                                        @Override
                                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                            my_day = dayOfMonth;
                                            my_month = month + 1;
                                            my_year = year;
                                            tarihtext.setText("Tarih: " + my_day + " / " + my_month + " / " + my_year);
                                        }
                                    }, currentyear, currentmonth, currentday);
                                    datepicker.show();
                                }
                            });
                            kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String s, spin, control,datecontrol, pricescontrol = pricetxt.getText().toString();
                                    control = informationtext.getText().toString();
                                    datecontrol = tarihtext.getText().toString();
                                    if (monthrepeat.isChecked()) {
                                        myrepeat = "YES";
                                    } else {
                                        myrepeat = "NO";
                                    }
                                    if (!datecontrol.equals("Tarih Girmek İçin Tıklayınız") && !pricescontrol.equals("") && !control.equals("")) {
                                        String pricesx = pricetxt.getText().toString();
                                        prices = Double.parseDouble(pricesx);
                                        s = informationtext.getText().toString();
                                        spin = myspinner.getSelectedItem().toString();
                                        ContentValues cv = new ContentValues();
                                        cv.put("info", "" + s); //These Fields should be your String values of actual column names
                                        cv.put("day", "" + my_day);
                                        cv.put("month", "" + my_month);
                                        cv.put("year", "" + my_year);
                                        cv.put("price", "" + prices);
                                        cv.put("repeat", "" + myrepeat);
                                        cv.put("label", "" + spin);
                                        dbm.update("holder", cv, "processid=" + tv.getTag(), null);
                                        dbm.close();
                                        Intent intent = new Intent(editislemler.this, masrafmain.class);
                                        startActivity(intent);
                                        Toast.makeText(getApplicationContext(),"Düzenleme başarılı",Toast.LENGTH_LONG).show();
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (datecontrol.equals("Tarih Girmek İçin Tıklayınız") && pricescontrol.equals("") && control.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (!datecontrol.equals("Tarih Girmek İçin Tıklayınız") && pricescontrol.equals("") && !control.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (datecontrol.equals("Tarih Girmek İçin Tıklayınız") && !control.equals("") && !pricescontrol.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!pricescontrol.equals("") && control.equals("") && !datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!control.equals("") && pricescontrol.equals("") && datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && pricescontrol.equals("") && !datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && !pricescontrol.equals("") && datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    }
                                }
                            });
                            deletegelirbtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(editislemler.this, masrafmain.class);
                                    startActivity(intent);
                                    Toast.makeText(getApplicationContext(),"Silindi!",Toast.LENGTH_LONG).show();
                                    dbm2.delete("holder", "processid=" + tv.getTag(), null);
                                    dbm2.close();
                                }
                            });
                            AlertDialog.Builder alert = new AlertDialog.Builder(editislemler.this);
                            //alert.setMessage("Deneme alarm");
                            alert.setView(myspinner);
                            alert.setView(tasarim);
                            alert.create().show();
                        }
                    }
                });
                linear.addView(tv);
                linear2.addView(tv2);
                linear3.addView(tv3);
                linear4.addView(tv4);
                linear5.addView(tv5);
                linear6.addView(tv6);

            }
            while (c.moveToPrevious());
            //Islemselect.setText("" + datex + infox + pricex+ idx);
        }





    }
    void init()
    {
        myspinner = new Spinner(this);

        scroll = new ScrollView(this);
        scroll = findViewById(R.id.scroll);

        constraintana = new ConstraintLayout(this);
        constraintana = findViewById(R.id.constraintana);







    }
}
