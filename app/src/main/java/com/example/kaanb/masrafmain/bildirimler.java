package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class bildirimler extends AppCompatActivity {


    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    static int my_day = 0, my_month = 0, my_year = 0;
    private Databasehelper db2;
    String datex = "";
    String infox = "";
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bildirimler);

        db2 = new Databasehelper(this);

        uyarı = new TextView(this);
        uyarı = findViewById(R.id.uyarı);



        alarminfotxt = new EditText(this);
        alarminfotxt = findViewById(R.id.alarminfotxt);

        tarihset = new TextView(this);
        tarihset = findViewById(R.id.tarihset);

        tarihtxt = new TextView(this);
        tarihtxt = findViewById(R.id.alarmdatetxt);

        kayıtbtn = new Button(this);
        kayıtbtn= findViewById(R.id.kayıtbutton);

        iptalbtn = new Button(this);
        iptalbtn= findViewById(R.id.iptalbutton);


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
                        tarihtxt.setText("" + my_day + " / "  +(month+1) + " / " +  year);
                    }
                },day,month,year);
                datepicker.show();
            }
        });




        kayıtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String infocontrol = "";
                infocontrol=alarminfotxt.getText().toString();


                if(my_day > 0  && my_month >0 && my_year > 0 && !infocontrol.equals("")) {
                    new Database_dao().addingalarm(db2, "Alarmdeneme", my_day, my_month, my_year);
                    Intent intent = new Intent(bildirimler.this, masrafmain.class);
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
