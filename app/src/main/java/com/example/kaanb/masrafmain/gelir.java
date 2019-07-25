package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    private TextView tarihtext,veriyaz;
    private Button tarihpicker,kayıtgelirbtn;
     static int day,month,year;


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
                        tarihtext.setText("Tarih: " + day + " / "  +(month+1) + " / " +  year);
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

               // new Database_dao().adding(db,222);
               new Database_dao().adding(db,252);






            ArrayList<Mydatabase>gelenler = new Database_dao().veriler(db);
            for(Mydatabase k:gelenler)
            {
                int x = k.getProcessid();
                    int y = 5;
                veriyaz.setText("" + x);
                //veriyaz.setText(""+y + "\n");
            }
        }

        });
    }
}
