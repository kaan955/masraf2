package com.example.kaanb.masrafmain;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class masrafmain extends AppCompatActivity {

private Button gelir,gider;
private TextView ser,lastprocessshow,lastprocesswriter2,lasttenshow,lastprocesswriter;
    private Databasehelper db;
    int lastprocesscounter = 0;



    public void init()
    {
        gelir = new Button(this);
        gider = new Button(this);
        ser = new TextView(this);
        lastprocessshow = new TextView(this);
        lasttenshow = new TextView(this);
        lastprocesswriter =  new TextView(this);
        lastprocesswriter2 =  new TextView(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masrafmain);
        init();


        final TextView ser = findViewById(R.id.editText2);
        Button gelir = findViewById(R.id.gelir);
        gider = findViewById(R.id.gider);
        lastprocessshow = findViewById(R.id.lastprocessshow);
        lasttenshow = findViewById(R.id.lasttenshow);
        lastprocesswriter = findViewById(R.id.lastprocesswriter);
        lastprocesswriter2 = findViewById(R.id.lastprocesswriter2);
        db = new Databasehelper(this);


       gelir.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ser.setText("Kaan");

               Intent intent = new Intent(masrafmain.this,gelir.class);
                startActivity(intent);

               new Database_dao().veriler(db);

               SQLiteDatabase dbm = db.getReadableDatabase();
               Cursor c = dbm.rawQuery("SELECT * FROM holder", null);
              c.moveToLast();
               do
               {
                   if(lastprocesscounter == 0) {
                       lastprocesswriter.setText("" + c.getString(c.getColumnIndex("info")));
                       lastprocesscounter++;
                   }
                   else if(lastprocesscounter == 1)
                   {
                       lastprocesswriter2.setText("" + c.getString(c.getColumnIndex("info")));
                       lastprocesscounter++;
                   }

               }
               while (c.moveToPrevious());


                /*
              ArrayList<Mydatabase> gelenler = new Database_dao().veriler(db);
               for(Mydatabase k:gelenler)
               {
                   int x = k.getProcessid();

                   //String y=k.getType();

                   //veriyaz.setText("" + x + y + k.getPricetype() + k.getYear());

                   //lastprocessshow.setText(""+k.getProcessid()+" + " +k.getType()+" + " + k.getInfo() +"Burası gün:" +k.getDay()+k.getMonth()+k.getYear()+"price:"+k.getPrice()+"+"+k.getRepeat()+k.getLabel()+k.getPricetype()+k.getTaksit());

                  if(lastprocesscounter == 0) {
                      lastprocesswriter.setText("Tarih: " + k.getDay() + "." + k.getMonth() + "." + k.getYear() + "\n Açıklama: " + k.getInfo() + "\n Ücret: " + k.getPrice() +
                              "\nEtiket: " + k.getLabel());
                      lastprocesscounter++;

                  }
                  else if(lastprocesscounter ==1)
                  {
                      lastprocesswriter2.setText("Tarih: " + k.getDay() + "." + k.getMonth() + "." + k.getYear() + "\n Açıklama: " + k.getInfo() + "\n Ücret: " + k.getPrice() +
                              "\nEtiket: " + k.getLabel());
                      lastprocesscounter++;

                  }
                   //veriyaz.setText("Buraya kadar soru yok");


               }
*/
           }



       });






       gider.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(masrafmain.this,gider.class);
               startActivity(intent);
           }
       });


    }








}
