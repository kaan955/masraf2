package com.example.kaanb.masrafmain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class editbildirim extends AppCompatActivity
{
    private TextView tarihtxt,tarihset,alarminfotxt,uyarı;
    private ImageView tariherror,infoerror;
    private Button kayıtbtn,iptalbtn;
    static int day, month, year;
    static int my_day = 0, my_month = 0, my_year = 0;
    private Databasehelper db2;
    String datex = "";
    String infox = "";
    int counter = 0;
    String infocontrol = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editbildirim);








    }
}
