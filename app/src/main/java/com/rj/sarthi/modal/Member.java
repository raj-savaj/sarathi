package com.rj.sarthi.modal;

public class Member {
    private int SR_No;
    private String Name;
    private String Ac_No;
    private int Member_No;
    private String Start_Date;
    private int Install_Amt;
    private int Month;
    private int Payable;
    private int Paid;
    private String MobileNo;

    public int getSR_No() {
        return SR_No;
    }
    public void setSR_No(int SR_No) {
        this.SR_No = SR_No;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getAc_No() {
        return Ac_No;
    }
    public void setAc_No(String ac_No) {
        Ac_No = ac_No;
    }

    public int getMember_No() {
        return Member_No;
    }
    public void setMember_No(int member_No) {
        Member_No = member_No;
    }

    public String getStart_Date() {
        return Start_Date;
    }
    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    public int getInstall_Amt() {
        return Install_Amt;
    }
    public void setInstall_Amt(int install_Amt) {
        Install_Amt = install_Amt;
    }

    public int getMonth() {
        return Month;
    }
    public void setMonth(int month) {
        Month = month;
    }

    public int getPayable() {
        return Payable;
    }
    public void setPayable(int payable) {
        Payable = payable;
    }

    public int getPaid() {
        return Paid;
    }
    public void setPaid(int paid) {
        Paid = paid;
    }

    public String getMobileNo() {
        return MobileNo;
    }
    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }
}
