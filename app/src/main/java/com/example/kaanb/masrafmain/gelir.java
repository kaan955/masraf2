package com.example.kaanb.masrafmain;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class gelir extends AppCompatActivity {

    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt;
    private Button tarihpicker,kayıtgelirbtn,deletegelirbtn;
    private ImageView infoerror,tariherror,tutarerror;
    private Spinner myspinner;
    private ArrayList<String>etiketler = new ArrayList<>();
    private ArrayAdapter<String> etiketadaptoru;
    private ConstraintLayout constraintana;
    private CheckBox monthrepeat;
    static int currentday,currentmonth,currentyear,my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;
    static String myrepeat ="NO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gelir);

        init();
       addID();

       // constraintana.setBackgroundColor(Color.parseColor("#FF726C6C"));

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

                datepicker = new DatePickerDialog(gelir.this, new DatePickerDialog.OnDateSetListener() {
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


                        if ( monthrepeat.isChecked() )
                        {
                            myrepeat ="YES";
                        }
                        else {
                            myrepeat = "NO";
                        }



                if(my_year > 0 && !pricescontrol.equals("") &&  !control.equals("")) {
                    String pricesx = pricetxt.getText().toString();
                    prices = Double.parseDouble(pricesx);
                    s = informationtext.getText().toString();
                    spin = myspinner.getSelectedItem().toString();
                    new Database_dao().adding(db,"gelir",s,my_day,my_month,my_year,prices,myrepeat,spin,"devam",1);
                    Intent intent = new Intent(gelir.this, masrafmain.class);
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
                Intent intent = new Intent(gelir.this, masrafmain.class);
                startActivity(intent);
            }
        });

    }




    void init()
    {
        myspinner = new Spinner(this);
        tarihtext = new TextView(this);
        kayıtgelirbtn = new Button(this);
        pricetxt = new TextView(this);
        informationtext= new TextView(this);
        db = new Databasehelper(this);
        deletegelirbtn = new Button(this);
        infoerror = new ImageView(this);
        tariherror = new ImageView(this);
        tutarerror = new ImageView(this);
        monthrepeat = new CheckBox(this);
        constraintana = new ConstraintLayout(this);
    }
    void addID()
    {
        myspinner = findViewById(R.id.spinnerlabel);
        tarihtext = findViewById(R.id.tarihpicker);
        kayıtgelirbtn = findViewById(R.id.kayıtgelirbtn);
        informationtext = findViewById(R.id.informationtext);
        pricetxt = findViewById(R.id.pricetxt);
        deletegelirbtn = findViewById(R.id.deletegelirbtn);
        infoerror = findViewById(R.id.infoerror);
        tariherror = findViewById(R.id.tariherror);
        tutarerror = findViewById(R.id.tutarerror);
        monthrepeat = findViewById(R.id.monthrepeat);
        constraintana = findViewById(R.id.constraintana);
    }
}
