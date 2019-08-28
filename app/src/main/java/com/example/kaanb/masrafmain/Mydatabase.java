package com.example.kaanb.masrafmain;

public class Mydatabase {

    private int processid;
    private String type;
    private String info;
    private int month;
    private int day;
    private int year;
    private double price;
    private String repeat;
    private String label;
    private String pricetype;
    private int taksit;

    //
    private int bmonth,bday,byear;
    private String binformation;

    public Mydatabase() {
    }
        public Mydatabase(String typex,String infox, int month,int day,int year,double price,String repeat,String label,String pricetype,int taksit)
        {
       // this.processid = processidx;

        this.type = typex;

        this.info = infox;
       this.month = month;
        this.day = day;
        this.year = year;
         this.price = price;
        this.repeat = repeat;
        this.label = label;
        this.pricetype = pricetype;
       this.taksit = taksit;


    }

    public int getBmonth() {
        return bmonth;
    }

    public void setBmonth(int bmonth) {
        this.bmonth = bmonth;
    }

    public int getBday() {
        return bday;
    }

    public void setBday(int bday) {
        this.bday = bday;
    }

    public int getByear() {
        return byear;
    }

    public void setByear(int byear) {
        this.byear = byear;
    }

    public String getBinformation() {
        return binformation;
    }

    public void setBinformation(String binformation) {
        this.binformation = binformation;
    }

    public  Mydatabase(String informationx, int month, int day, int year)
    {
        this.binformation = informationx;
        this.bday = day;
        this.bmonth = month;
        this.byear = year;
    }


    public int getProcessid() {
        return processid;
    }
    public void setProcessid(int processidx) {
        this.processid = processidx;
    }

    public String getType() {
        return type;
    }

    public void setType(String typex) {
        this.type = typex;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String infox) {
        this.info = infox;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }
    public int getTaksit() {
        return taksit;
    }

    public void setTaksit(int taksitx) {
        this.taksit = taksitx;
    }

}
