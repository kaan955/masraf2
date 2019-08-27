package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class bildirimler extends AppCompatActivity {

    private TextView tarihtxt;
    static int day, month, year;
    static int my_day = 0, my_month = 0, my_year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bildirimler);



        tarihtxt = new TextView(this);
        tarihtxt = findViewById(R.id.alarmdatetxt);

        tarihtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                day = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year = c.get(Calendar.YEAR);


                DatePickerDialog datepicker;

                datepicker = new DatePickerDialog(bildirimler.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        my_day = dayOfMonth;
                        my_month = month + 1;
                        my_year = year;
                        tarihtxt.setText("Tarih: " + my_day + " / "  +(month+1) + " / " +  year);
                    }
                },day,month,year);
                datepicker.show();




            }
        });


    }

}
