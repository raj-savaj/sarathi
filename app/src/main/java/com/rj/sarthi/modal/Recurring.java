package com.rj.sarthi.modal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Recurring {

    private String Name;
    private String AC_No;
    private String C_Paid;
    private int receipt_no;
    private int Payable;
    private String date;

    public Recurring(String AC_No, String c_Paid, int receipt_no, String date) {
        this.AC_No = AC_No;
        C_Paid = c_Paid;
        this.receipt_no = receipt_no;
        this.date = date;
    }

    public String getAC_No() {
        return AC_No;
    }

    public void setAC_No(String AC_No) {
        this.AC_No = AC_No;
    }

    public String getC_Paid() {
        return C_Paid;
    }

    public void setC_Paid(String c_Paid) {
        C_Paid = c_Paid;
    }

    public int getReceipt_no() {
        return receipt_no;
    }

    public void setReceipt_no(int receipt_no) {
        this.receipt_no = receipt_no;
    }

    /*public String getDate() {
        return date;
    }*/

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {

        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
            return newFormat.format(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPayable() {
        return Payable;
    }

    public void setPayable(int payable) {
        Payable = payable;
    }
}
