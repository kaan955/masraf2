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

    public Mydatabase() {
    }

    public Mydatabase(int processid) {
        this.processid = processid;
        /*
        , String type, String info, int month, int day, int year, double price, String repeat, String label, String pricetype, int taksit

        this.type = type;
        this.info = info;
        this.month = month;
        this.day = day;
        this.year = year;
        this.price = price;
        this.repeat = repeat;
        this.label = label;
        this.pricetype = pricetype;
        this.taksit = taksit;
        */
    }



    public int getProcessid() {
        return processid;
    }

    /*
    public void setProcessid(int processid) {
        this.processid = processid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public void setTaksit(int taksit) {
        this.taksit = taksit;
    }*/

}
