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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class gider extends AppCompatActivity {

    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt,uyarı,taksittxt;
    private Button tarihpicker,kayıtgelirbtn;
    private Spinner myspinner;
    private RadioGroup radio;
    private AppCompatRadioButton radiobut;
    private ArrayList<String>etiketler = new ArrayList<>();
    private ArrayAdapter<String> etiketadaptoru;
    static int day,month,year,taksit=1;
    static int my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gider);

        myspinner = new Spinner(this);
        myspinner = findViewById(R.id.spinnerlabel);

        radio = new RadioGroup(this);
        radio = findViewById(R.id.radiogroup);

        tarihtext = new TextView(this);
        tarihtext = findViewById(R.id.tarihpicker);

        uyarı = new TextView(this);
        uyarı = findViewById(R.id.uyarı);

        kayıtgelirbtn = new Button(this);
        kayıtgelirbtn = findViewById(R.id.kayıtgelirbtn);

        informationtext= new TextView(this);
        informationtext = findViewById(R.id.informationtext);


        pricetxt = new TextView(this);
        pricetxt = findViewById(R.id.pricetxt);

        taksittxt = new TextView(this);
        taksittxt = findViewById(R.id.taksittxt);

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
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);


                DatePickerDialog datepicker;

                datepicker = new DatePickerDialog(gider.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        my_day = dayOfMonth;
                        my_month = month + 1;
                        my_year = year;
                        tarihtext.setText("Tarih: " + my_day + " / "  +(month+1) + " / " +  year);
                    }
                },day,month,year);
                datepicker.show();




            }
        });


        db = new Databasehelper(this);

        kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s;
                //double prices;
                String spin;
                String control;
                Double controlprice;
                String radiostring;

                String pricescontrol=pricetxt.getText().toString();
                controlprice = prices = Double.parseDouble(pricescontrol);
                control = informationtext.getText().toString();


                // new Database_dao().adding(db,"Type",s,my_day,my_month,my_year,prices,radiostring,spin,"devam",4);


                if(my_day > 0  && my_month >0 && my_year > 0 && controlprice>0.0 &&  (control != null)) {
                    int radiox = radio.getCheckedRadioButtonId();
                    String pricesx=pricetxt.getText().toString();
                    String taksitx = taksittxt.getText().toString();

                    taksit = Integer.parseInt(taksitx);
                    prices = Double.parseDouble(pricesx);
                    radiobut = findViewById(radiox);
                    radiostring = radiobut.getText().toString();
                    s = informationtext.getText().toString();
                    spin= myspinner.getSelectedItem().toString();
                    new Database_dao().adding(db,"Type",s,my_day,my_month,my_year,prices,radiostring,spin,"devam",taksit);
                    Intent intent = new Intent(gider.this, masrafmain.class);
                    startActivity(intent);
                    uyarı.setVisibility(uyarı.GONE);
                }
                else
                {
                    uyarı.setVisibility(uyarı.VISIBLE);
                }



            }

        });
    }
}
