package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
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
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class editislemler extends AppCompatActivity {
    private Databasehelper db5;
    private TextView Islemselect;
    private int counter = 0;
    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt;
    private Button tarihpicker,kayıtgelirbtn,deletegelirbtn;
    private ImageView infoerror,tariherror,tutarerror;
    private Spinner myspinner;
    private ArrayList<String> etiketler = new ArrayList<>();
    private ArrayAdapter<String> etiketadaptoru;
    private CheckBox monthrepeat;
    static int currentday,currentmonth,currentyear,my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;
    static String myrepeat ="NO";
    private LinearLayout linear,linear2,linear3;
    private Cursor c;
    static TextView []tv = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editislemler);
        init();
        db5 = new Databasehelper(this);
        new Database_dao().veriler(db5);
        SQLiteDatabase dbm = db5.getReadableDatabase();
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
                tv3.setSingleLine();
                //tv3.setBackgroundResource(R.drawable.grentxtbar);
                counter = c.getInt(c.getColumnIndex("processid"));
                tv.setTag(counter);


                tv.setText(c.getInt(c.getColumnIndex("day")) + "." +
                        c.getInt(c.getColumnIndex("month")) + "." +
                        c.getInt(c.getColumnIndex("year")) + "\n");
                tv2.setText(c.getString(c.getColumnIndex("info")) +"\n");
                tv3.setText("₺" +String.format ("%.2f", c.getDouble(c.getColumnIndex("price"))) + "\n" );
                //tv.setId(c.getInt(c.getColumnIndex("processid")));

              //  final int myh = c.getInt(c.getColumnIndex("processid"));



                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    View tasarim = getLayoutInflater().inflate(R.layout.editdialog,null );
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

                        pricetxt.setText("Kaan" + tv.getTag() );

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
                        //etiketadaptoru = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,etiketler);
                        myspinner.setAdapter(etiketadaptoru);

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
                                        tarihtext.setText("Tarih: " + my_day + " / "  +my_month + " / " +  my_year);
                                    }
                                },currentyear,currentmonth,currentday);
                                datepicker.show();

                            }
                        });



                        kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String s,spin,control,pricescontrol=pricetxt.getText().toString();
                                control = informationtext.getText().toString();

                                monthrepeat.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if ( monthrepeat.isChecked() )
                                        {
                                            myrepeat ="YES";
                                        }
                                        else {
                                            myrepeat = "NO";
                                        }
                                    }
                                });


                                if(my_year > 0 && !pricescontrol.equals("") &&  !control.equals("")) {
                                    String pricesx = pricetxt.getText().toString();
                                    prices = Double.parseDouble(pricesx);
                                    s = informationtext.getText().toString();
                                    spin = myspinner.getSelectedItem().toString();
                                    new Database_dao().adding(db,"gelir",s,my_day,my_month,my_year,prices,myrepeat,spin,"devam",1);
                                    Intent intent = new Intent(editislemler.this, masrafmain.class);
                                    startActivity(intent);
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.GONE);
                                    tutarerror.setVisibility(View.GONE);
                                }
                                else if(my_year == 0 && pricescontrol.equals("") && control.equals(""))
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.VISIBLE);
                                    tutarerror.setVisibility(View.VISIBLE);
                                }
                                else if (my_year > 0 && pricescontrol.equals("") && !control.equals(""))
                                {
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.GONE);
                                    tutarerror.setVisibility(View.VISIBLE);
                                }
                                else if (my_year == 0 && !control.equals("") && !pricescontrol.equals(""))
                                {
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.VISIBLE);
                                    tutarerror.setVisibility(View.GONE);

                                }
                                else if(!pricescontrol.equals("") && control.equals("") && my_year>0)
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.GONE);
                                    tutarerror.setVisibility(View.GONE);
                                }
                                else if(!control.equals("") && pricescontrol.equals("") && my_year == 0)
                                {
                                    infoerror.setVisibility(View.GONE);
                                    tariherror.setVisibility(View.VISIBLE);
                                    tutarerror.setVisibility(View.VISIBLE);
                                }
                                else if(control.equals("") &&pricescontrol.equals("") && my_year > 0 )
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.GONE);
                                    tutarerror.setVisibility(View.VISIBLE);

                                }
                                else if(control.equals("") && !pricescontrol.equals("") && my_year ==0)
                                {
                                    infoerror.setVisibility(View.VISIBLE);
                                    tariherror.setVisibility(View.VISIBLE);
                                    tutarerror.setVisibility(View.GONE);
                                }
                                else
                                {
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
                            }
                        });


                        AlertDialog.Builder alert = new AlertDialog.Builder(editislemler.this);
                        alert.setMessage("Deneme alarm");
                        alert.setView(tasarim);

                        alert.create().show();

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


