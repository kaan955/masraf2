package com.example.kaanb.masrafmain.Edit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaanb.masrafmain.Database.Database_dao;
import com.example.kaanb.masrafmain.Database.Databasehelper;
import com.example.kaanb.masrafmain.Mainthings.masrafmain;
import com.example.kaanb.masrafmain.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;




public class BasitRVAdapter extends RecyclerView.Adapter<BasitRVAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<String> fromanotherlist,fromanotherlist2,fromanotherlist3,fromanotherlist4;
    private Databasehelper db5,db6,db7,db8;
    private Cursor c,k,z,p;
    static TextView []tv = new TextView[10];

    private TextView Islemselect;
    private int counter = 0;
    private Databasehelper db;
    private TextView tarihtext,informationtext,pricetxt,taksittxt;
    private Button tarihpicker,kayıtgelirbtn,deletegelirbtn;
    private ImageView infoerror,tariherror,tutarerror;
    private Spinner myspinner;
    private CheckBox monthrepeat;
    static int currentday,currentmonth,currentyear,my_day = 0,my_month = 0,my_year = 0;
    static double prices = 0.0;
    static String myrepeat ="NO";
    static int topcounter = 0;
    private LinearLayout linearlay;
    private ConstraintLayout constraintana;
    private ScrollView scroll;
    private RecyclerView rv;


    public BasitRVAdapter(Context mContext, List<String> text1,List<String> text2,List<String> text3,List<String> text4) {
        this.mContext = mContext;
        this.fromanotherlist = text1;
        this.fromanotherlist2 = text2;
        this.fromanotherlist3 = text3;
        this.fromanotherlist4 = text4;


    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        public TextView satirYazi,infom,ucretim;
        public CardView satirCardView;
        public ImageView imageViewNoktaResim;

        public CardViewTasarimNesneleriniTutucu(View view) {
            super(view);
            satirYazi =  view.findViewById(R.id.satirYazi);
            satirCardView =  view.findViewById(R.id.satirCardView);
            infom =  view.findViewById(R.id.infom);
            ucretim =  view.findViewById(R.id.ucretim);


        }
    }

    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_islemler, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }


    @Override
    public void onBindViewHolder(final CardViewTasarimNesneleriniTutucu holder, final int position) {
        final String gelenveri = fromanotherlist.get(position);
        final String gelenveri2 = fromanotherlist2.get(position);
        final String gelenveri3 = fromanotherlist3.get(position);
        final String gelenveri4 = fromanotherlist4.get(position);

        holder.satirYazi.setText(gelenveri.toString());
        holder.infom.setText(gelenveri2.toString());
        holder.ucretim.setText(gelenveri3.toString());

        db5 = new Databasehelper(mContext);
        db6= new Databasehelper(mContext);
        db7= new Databasehelper(mContext);
        db8= new Databasehelper(mContext);



        new Database_dao().veriler(db7);
        new Database_dao().veriler(db6);
        new Database_dao().veriler(db5);
        new Database_dao().veriler(db8);
        final SQLiteDatabase dbm = db5.getReadableDatabase();
        final SQLiteDatabase dbm2 = db6.getReadableDatabase();
        final SQLiteDatabase dbm3 = db7.getReadableDatabase();
        final SQLiteDatabase dbm4 = db8.getReadableDatabase();




        holder.satirCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                p = dbm4.rawQuery("SELECT * FROM holder WHERE processid=" + gelenveri4.toString(), null);
                p.moveToFirst();

                if (p.getString(p.getColumnIndex("type")).equals("gelir")) {


                    final View tasarim = LayoutInflater.from(mContext).inflate(R.layout.editdialog, null, false);

                    myspinner = tasarim.findViewById(R.id.spinnerlabel);
                    tarihtext = tasarim.findViewById(R.id.tarihpicker);
                    kayıtgelirbtn = tasarim.findViewById(R.id.kayıtgelirbtn);
                    informationtext = tasarim.findViewById(R.id.informationtext);
                    pricetxt = tasarim.findViewById(R.id.pricetxt);
                    deletegelirbtn = tasarim.findViewById(R.id.deletegelirbtn);
                    infoerror = tasarim.findViewById(R.id.infoerror);
                    tariherror = tasarim.findViewById(R.id.tariherror);
                    tutarerror = tasarim.findViewById(R.id.tutarerror);
                    monthrepeat = tasarim.findViewById(R.id.monthrepeat);
                    constraintana = tasarim.findViewById(R.id.constraintana);
                    constraintana.setBackgroundColor(Color.parseColor("#D0141414"));


                    k = dbm2.rawQuery("SELECT * FROM holder", null);
                    k.moveToFirst();
                    z = dbm3.rawQuery("SELECT * FROM holder WHERE processid=" + gelenveri4.toString(), null);
                    z.moveToFirst();

                    my_day = z.getInt(z.getColumnIndex("day"));
                            my_month = z.getInt(z.getColumnIndex("month"));
                    my_year = z.getInt(z.getColumnIndex("year"));

                    informationtext.setText("" + z.getString(z.getColumnIndex("info")));
                    tarihtext.setText("Tarih: " + z.getInt(z.getColumnIndex("day")) + "." +
                            z.getInt(z.getColumnIndex("month")) + "." +
                            z.getInt(z.getColumnIndex("year")));
                    pricetxt.setText("" + z.getInt(z.getColumnIndex("price")));
                    final ArrayList<String> etiketler = new ArrayList<>();
                    final ArrayAdapter<String> etiketadaptoru;
                    etiketler.add("Diğer");
                    etiketler.add("Maaş");
                    etiketler.add("Satış");
                    etiketler.add("Faiz");
                    etiketler.add("Kira");
                    etiketler.add("Ek iş");
                    etiketadaptoru = new ArrayAdapter<String>(mContext.getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, etiketler);
                    myspinner.setAdapter(etiketadaptoru);

                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) view).setTextColor(Color.parseColor("#FCE4EC"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    String getselected = ("" + z.getString(z.getColumnIndex("label")));
                    myspinner.setSelection(etiketadaptoru.getPosition(getselected));
                    String s = z.getString(z.getColumnIndex("repeat"));
                    if (z.getString(z.getColumnIndex("repeat")).equals("YES")) {
                        monthrepeat.setChecked(true);
                    }
                    tarihtext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            currentday = c.get(Calendar.DAY_OF_MONTH);
                            currentmonth = c.get(Calendar.MONTH);
                            currentyear = c.get(Calendar.YEAR);
                            DatePickerDialog datepicker;
                            datepicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    my_day = dayOfMonth;
                                    my_month = month + 1;
                                    my_year = year;
                                    tarihtext.setText("Tarih: " + my_day + " / " + my_month + " / " + my_year);
                                }
                            }, currentyear, currentmonth, currentday);
                            datepicker.show();
                        }
                    });
                    kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s, spin, control, datecontrol, pricescontrol = pricetxt.getText().toString();
                            control = informationtext.getText().toString();
                            datecontrol = tarihtext.getText().toString();
                            if (monthrepeat.isChecked()) {
                                myrepeat = "YES";
                            } else {
                                myrepeat = "NO";
                            }
                            if (!datecontrol.equals("") && !pricescontrol.equals("") && !control.equals("")) {
                                String pricesx = pricetxt.getText().toString();
                                prices = Double.parseDouble(pricesx);
                                s = informationtext.getText().toString();
                                spin = myspinner.getSelectedItem().toString();
                                ContentValues cv = new ContentValues();
                                cv.put("info", "" + s);
                                cv.put("day", "" + my_day);
                                cv.put("month", "" + my_month);
                                cv.put("year", "" + my_year);
                                cv.put("price", "" + prices);
                                cv.put("repeat", "" + myrepeat);
                                cv.put("label", "" + spin);
                                dbm.update("holder", cv, "processid=" + gelenveri4.toString(), null);
                                dbm.close();
                                Context context = v.getContext();
                                Intent intent = new Intent(context, masrafmain.class);
                                context.startActivity(intent);
                                Toast.makeText(mContext.getApplicationContext(), "Düzenleme Başarılı!", Toast.LENGTH_LONG).show();
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (datecontrol.equals("") && pricescontrol.equals("") && control.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (!datecontrol.equals("") && pricescontrol.equals("") && !control.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (datecontrol.equals("") && !control.equals("") && !pricescontrol.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (!pricescontrol.equals("") && control.equals("") && !datecontrol.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (!control.equals("") && pricescontrol.equals("") && datecontrol.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (control.equals("") && pricescontrol.equals("") && !datecontrol.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (control.equals("") && !pricescontrol.equals("") && datecontrol.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.GONE);
                            } else {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    deletegelirbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, masrafmain.class);
                            context.startActivity(intent);
                            Toast.makeText(mContext.getApplicationContext(), "Silindi!", Toast.LENGTH_LONG).show();
                            dbm2.delete("holder", "processid=" + gelenveri4.toString(), null);
                            dbm2.close();
                        }
                    });


                    AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                    //alert.setMessage("Deneme alarm");
                    alert.setView(myspinner);


                    alert.setView(tasarim);
                    alert.create().show();


                } else {
                    final View tasarim = LayoutInflater.from(mContext).inflate(R.layout.gideredit, null, false);
                    myspinner = tasarim.findViewById(R.id.spinnerlabel);
                    tarihtext = tasarim.findViewById(R.id.tarihpicker);
                    kayıtgelirbtn = tasarim.findViewById(R.id.kayıtgelirbtn);
                    informationtext = tasarim.findViewById(R.id.informationtext);
                    pricetxt = tasarim.findViewById(R.id.pricetxt);
                    deletegelirbtn = tasarim.findViewById(R.id.deletegelirbtn);
                    infoerror = tasarim.findViewById(R.id.infoerror);
                    tariherror = tasarim.findViewById(R.id.tariherror);
                    tutarerror = tasarim.findViewById(R.id.tutarerror);
                    monthrepeat = tasarim.findViewById(R.id.monthrepeat);
                    taksittxt = tasarim.findViewById(R.id.taksittxt);
                    constraintana = tasarim.findViewById(R.id.constraintana);
                    constraintana.setBackgroundColor(Color.parseColor("#D0141414"));

                    k = dbm2.rawQuery("SELECT * FROM holder", null);
                    k.moveToFirst();
                    z = dbm3.rawQuery("SELECT * FROM holder WHERE processid=" + gelenveri4.toString(), null);
                    z.moveToFirst();

                    my_day = z.getInt(z.getColumnIndex("day"));
                    my_month = z.getInt(z.getColumnIndex("month"));
                    my_year = z.getInt(z.getColumnIndex("year"));
                    informationtext.setText("" + z.getString(z.getColumnIndex("info")));
                    tarihtext.setText("Tarih: " + z.getInt(z.getColumnIndex("day")) + "." +
                            z.getInt(z.getColumnIndex("month")) + "." +
                            z.getInt(z.getColumnIndex("year")));
                    pricetxt.setText("" + z.getInt(z.getColumnIndex("price")));
                    final ArrayList<String> etiketler = new ArrayList<>();
                    final ArrayAdapter<String> etiketadaptoru;
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
                    etiketadaptoru = new ArrayAdapter<String>(mContext.getApplicationContext(), android.R.layout.simple_list_item_1, android.R.id.text1, etiketler);
                    myspinner.setAdapter(etiketadaptoru);

                    myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            ((TextView) view).setTextColor(Color.parseColor("#FCE4EC"));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    int selection = 0;
                    int finder = 0;
                    String getselected = ("" + z.getString(z.getColumnIndex("label")));
                    if (z.getString(z.getColumnIndex("repeat")).equals("YES")) {
                        monthrepeat.setChecked(true);
                    }
                    myspinner.setSelection(etiketadaptoru.getPosition(getselected));

                    int c = z.getInt(z.getColumnIndex("taksit"));
                    taksittxt.setText("" + z.getInt(z.getColumnIndex("taksit")));
                    tarihtext.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            currentday = c.get(Calendar.DAY_OF_MONTH);
                            currentmonth = c.get(Calendar.MONTH);
                            currentyear = c.get(Calendar.YEAR);
                            DatePickerDialog datepicker;
                            datepicker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    my_day = dayOfMonth;
                                    my_month = month + 1;
                                    my_year = year;
                                    tarihtext.setText("Tarih: " + my_day + " / " + my_month + " / " + my_year);
                                }
                            }, currentyear, currentmonth, currentday);
                            datepicker.show();
                        }
                    });
                    kayıtgelirbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s, spin, control, datecontrol, pricescontrol = pricetxt.getText().toString();
                            control = informationtext.getText().toString();
                            datecontrol = tarihtext.getText().toString();
                            if (monthrepeat.isChecked()) {
                                myrepeat = "YES";
                            } else {
                                myrepeat = "NO";
                            }
                            if (!datecontrol.equals("Tarih Girmek İçin Tıklayınız") && !pricescontrol.equals("") && !control.equals("")) {
                                String pricesx = pricetxt.getText().toString();
                                prices = Double.parseDouble(pricesx);
                                s = informationtext.getText().toString();
                                spin = myspinner.getSelectedItem().toString();
                                ContentValues cv = new ContentValues();
                                cv.put("info", "" + s); //These Fields should be your String values of actual column names
                                cv.put("day", "" + my_day);
                                cv.put("month", "" + my_month);
                                cv.put("year", "" + my_year);
                                cv.put("price", "" + prices);
                                cv.put("repeat", "" + myrepeat);
                                cv.put("label", "" + spin);
                                dbm.update("holder", cv, "processid=" + gelenveri4.toString(), null);
                                dbm.close();
                                Context context = v.getContext();
                                Intent intent = new Intent(context, masrafmain.class);
                                context.startActivity(intent);
                                Toast.makeText(mContext.getApplicationContext(), "Düzenleme başarılı", Toast.LENGTH_LONG).show();
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (datecontrol.equals("Tarih Girmek İçin Tıklayınız") && pricescontrol.equals("") && control.equals("")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (!datecontrol.equals("Tarih Girmek İçin Tıklayınız") && pricescontrol.equals("") && !control.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (datecontrol.equals("Tarih Girmek İçin Tıklayınız") && !control.equals("") && !pricescontrol.equals("")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (!pricescontrol.equals("") && control.equals("") && !datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.GONE);
                            } else if (!control.equals("") && pricescontrol.equals("") && datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                infoerror.setVisibility(View.GONE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (control.equals("") && pricescontrol.equals("") && !datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.GONE);
                                tutarerror.setVisibility(View.VISIBLE);
                            } else if (control.equals("") && !pricescontrol.equals("") && datecontrol.equals("Tarih Girmek İçin Tıklayınız")) {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.GONE);
                            } else {
                                infoerror.setVisibility(View.VISIBLE);
                                tariherror.setVisibility(View.VISIBLE);
                                tutarerror.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    deletegelirbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, masrafmain.class);
                            context.startActivity(intent);
                            Toast.makeText(mContext.getApplicationContext(), "Silindi!", Toast.LENGTH_LONG).show();
                            dbm2.delete("holder", "processid=" + gelenveri4.toString(), null);
                            dbm2.close();
                        }
                    });
                    android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                    //alert.setMessage("Deneme alarm");
                    alert.setView(myspinner);
                    alert.setView(tasarim);
                    alert.create().show();
                }
            }





        });
}










    @Override
    public int getItemCount() {
        return fromanotherlist.size();
    }

}


