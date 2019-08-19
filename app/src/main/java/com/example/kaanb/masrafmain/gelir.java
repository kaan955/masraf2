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

public class gelir extends AppCompatActivity {

    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt;
    private Button tarihpicker,kayıtgelirbtn;
    private Spinner myspinner;
    private RadioGroup radio;
    private AppCompatRadioButton radiobut;
    private ArrayList<String>etiketler = new ArrayList<>();
    private ArrayAdapter<String> etiketadaptoru;
     static int day,month,year;
     static int my_day,my_month,my_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gelir);

        myspinner = new Spinner(this);
        myspinner = findViewById(R.id.spinnerlabel);

        radio = new RadioGroup(this);
        radio = findViewById(R.id.radiogroup);

        tarihtext = new TextView(this);
        tarihtext = findViewById(R.id.tarihpicker);

        kayıtgelirbtn = new Button(this);
        kayıtgelirbtn = findViewById(R.id.kayıtgelirbtn);

        informationtext= new TextView(this);
        informationtext = findViewById(R.id.informationtext);


        pricetxt = new TextView(this);
        pricetxt = findViewById(R.id.pricetxt);

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

                datepicker = new DatePickerDialog(gelir.this, new DatePickerDialog.OnDateSetListener() {
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
                double prices;
                String spin;
                String pricesx=pricetxt.getText().toString();
                prices = Double.parseDouble(pricesx);
                String radiostring;

                int radiox = radio.getCheckedRadioButtonId();

                radiobut = findViewById(radiox);

                radiostring = radiobut.getText().toString();

                s = informationtext.getText().toString();
                spin= myspinner.getSelectedItem().toString();

                new Database_dao().adding(db,"Type",s,my_day,my_month,my_year,prices,radiostring,spin,"devam",4);

                Intent intent = new Intent(gelir.this,masrafmain.class);
                startActivity(intent);



        }

        });
    }
}
