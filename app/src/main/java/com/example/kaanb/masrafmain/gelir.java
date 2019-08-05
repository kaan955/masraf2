package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class gelir extends AppCompatActivity {

    private Databasehelper db;
    private TextView tarihtext,veriyaz,informationtext,pricetxt;
    private Button tarihpicker,kayıtgelirbtn;
     static int day,month,year;
     static int my_day,my_month,my_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gelir);

        veriyaz = new TextView(this);
        veriyaz = findViewById(R.id.veriyaz);

        tarihtext = new TextView(this);
        tarihtext = findViewById(R.id.tarihpicker);

        kayıtgelirbtn = new Button(this);
        kayıtgelirbtn = findViewById(R.id.kayıtgelirbtn);

        informationtext= new TextView(this);
        informationtext = findViewById(R.id.informationtext);

        pricetxt = new TextView(this);
        pricetxt = findViewById(R.id.pricetxt);


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

        veriyaz.setText("Kaangirmedi");

        db = new Databasehelper(this);

        kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s;
                double prices;

                String pricesx=pricetxt.getText().toString();
                prices = Double.parseDouble(pricesx);
                s = informationtext.getText().toString();

                 new Database_dao().adding(db,232,"Type",s,my_day,my_month,my_year,prices,"deeneeme","budatamam","devam",4);


                Intent intent = new Intent(gelir.this,masrafmain.class);
                startActivity(intent);

        }

        });
    }
}
