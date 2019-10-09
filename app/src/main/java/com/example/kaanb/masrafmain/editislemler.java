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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
    private LinearLayout linear,linear2,linear3;
    private Cursor c,k,z,p;
    static TextView []tv = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editislemler);
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

        DecimalFormat df = new DecimalFormat("0");
        df.setMaximumFractionDigits(340);

         c = dbm.rawQuery("SELECT * FROM holder", null);
        c.moveToFirst();


        if (c.getCount() <= 0) {
            c.close();
            Islemselect.setText("Öncelikle Gelir & gider giriniz..");
        } else {
            c.moveToLast();
            do {

               final TextView tv = new TextView(this);
                TextView tv2 = new TextView(this);
                TextView tv3 = new TextView(this);


                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(lp);
                tv2.setLayoutParams(lp);
                tv3.setLayoutParams(lp);

                counter = c.getInt(c.getColumnIndex("processid"));
                tv.setTag(counter);


                tv.setText(c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText(c.getString(c.getColumnIndex("info")) +"\n");
                tv3.setText("₺" +String.format ("%.2f", c.getDouble(c.getColumnIndex("price"))) + "\n");



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
                                    String s, spin, control, pricescontrol = pricetxt.getText().toString();
                                    control = informationtext.getText().toString();


                                    if (monthrepeat.isChecked()) {
                                        myrepeat = "YES";
                                    } else {
                                        myrepeat = "NO";
                                    }


                                    if (my_year > 0 && !pricescontrol.equals("") && !control.equals("")) {
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
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (my_year == 0 && pricescontrol.equals("") && control.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (my_year > 0 && pricescontrol.equals("") && !control.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (my_year == 0 && !control.equals("") && !pricescontrol.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);

                                    } else if (!pricescontrol.equals("") && control.equals("") && my_year > 0) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!control.equals("") && pricescontrol.equals("") && my_year == 0) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && pricescontrol.equals("") && my_year > 0) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);

                                    } else if (control.equals("") && !pricescontrol.equals("") && my_year == 0) {
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
                                    String s, spin, control, pricescontrol = pricetxt.getText().toString();
                                    control = informationtext.getText().toString();


                                    if (monthrepeat.isChecked()) {
                                        myrepeat = "YES";
                                    } else {
                                        myrepeat = "NO";
                                    }


                                    if (my_year > 0 && !pricescontrol.equals("") && !control.equals("")) {
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
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (my_year == 0 && pricescontrol.equals("") && control.equals("")) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (my_year > 0 && pricescontrol.equals("") && !control.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (my_year == 0 && !control.equals("") && !pricescontrol.equals("")) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.GONE);

                                    } else if (!pricescontrol.equals("") && control.equals("") && my_year > 0) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.GONE);
                                    } else if (!control.equals("") && pricescontrol.equals("") && my_year == 0) {
                                        infoerror.setVisibility(View.GONE);
                                        tariherror.setVisibility(View.VISIBLE);
                                        tutarerror.setVisibility(View.VISIBLE);
                                    } else if (control.equals("") && pricescontrol.equals("") && my_year > 0) {
                                        infoerror.setVisibility(View.VISIBLE);
                                        tariherror.setVisibility(View.GONE);
                                        tutarerror.setVisibility(View.VISIBLE);

                                    } else if (control.equals("") && !pricescontrol.equals("") && my_year == 0) {
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


            }
            while (c.moveToPrevious());
            //Islemselect.setText("" + datex + infox + pricex+ idx);
        }




    }
    void init()
    {
        myspinner = new Spinner(this);
        linear = new LinearLayout(this);
        linear = findViewById(R.id.linear);
        linear2 = new LinearLayout(this);
        linear2 = findViewById(R.id.linear2);
        linear3 = new LinearLayout(this);
        linear3 = findViewById(R.id.linear3);
        Islemselect = new TextView(this);
        Islemselect = findViewById(R.id.Islemselect);



    }
}


