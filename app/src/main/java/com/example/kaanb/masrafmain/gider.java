package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class gider extends AppCompatActivity {

    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt,uyarı,taksittxt;
    private Button tarihpicker,kayıtgelirbtn,deletegiderbutton;
    private ImageView infoerror,tariherror,tutarerror;
    private Spinner myspinner;
    private ArrayList<String>etiketler = new ArrayList<>();
    private ArrayAdapter<String> etiketadaptoru;
    static int currentday,currentmonth,currentyear,taksit=1;
    static int my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gider);

         init();
        addID();

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
        etiketadaptoru = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,etiketler);
        myspinner.setAdapter(etiketadaptoru);



        tarihtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                currentday = c.get(Calendar.DAY_OF_MONTH);
                currentmonth = c.get(Calendar.MONTH);
                currentyear = c.get(Calendar.YEAR);

                DatePickerDialog datepicker;
                datepicker = new DatePickerDialog(gider.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        my_day = day;
                        my_month = month + 1;
                        my_year = year;
                        tarihtext.setText("Tarih: " + my_day + " / "  +(my_month) + " / " +  my_year);
                    }
                },currentyear,currentmonth,currentday);
                datepicker.show();
            }
        });

        kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s,spin,control;

                String pricescontrol = pricetxt.getText().toString();
                control = informationtext.getText().toString();
                String taksitx = taksittxt.getText().toString();
                if(taksitx.equals("") || taksitx.equals("0"))
                {
                    taksitx = "1";
                }


                if(my_year > 0 && !pricescontrol.equals("") &&  !control.equals("")) {

                    String pricesx=pricetxt.getText().toString();
                    taksit = Integer.parseInt(taksitx);
                    prices = Double.parseDouble(pricesx);



                    s = informationtext.getText().toString();
                    spin= myspinner.getSelectedItem().toString();
                    new Database_dao().adding(db,"Type",s,my_day,my_month,my_year,prices,"radiostring",spin,"devam",taksit);
                    Intent intent = new Intent(gider.this, masrafmain.class);
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


        deletegiderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gider.this, masrafmain.class);
                startActivity(intent);
            }
        });


    }


    void init()
    {
        myspinner = new Spinner(this);
        tarihtext = new TextView(this);
        uyarı = new TextView(this);
        kayıtgelirbtn = new Button(this);
        informationtext= new TextView(this);
        pricetxt = new TextView(this);
        taksittxt = new TextView(this);
        db = new Databasehelper(this);
        deletegiderbutton = new Button(this);
        infoerror = new ImageView(this);
        tariherror = new ImageView(this);
        tutarerror = new ImageView(this);



    }

    void addID()
    {
        myspinner = findViewById(R.id.spinnerlabel);
        tarihtext = findViewById(R.id.tarihpicker);
        uyarı = findViewById(R.id.uyarı);
        kayıtgelirbtn = findViewById(R.id.kayıtgelirbtn);
        informationtext = findViewById(R.id.informationtext);
        pricetxt = findViewById(R.id.pricetxt);
        taksittxt = findViewById(R.id.taksittxt);
        deletegiderbutton = findViewById(R.id.deletegelirbtn);
        infoerror = findViewById(R.id.infoerror);
        tariherror = findViewById(R.id.tariherror);
        tutarerror = findViewById(R.id.tutarerror);
    }


}
