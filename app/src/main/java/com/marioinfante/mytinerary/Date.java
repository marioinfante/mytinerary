package com.marioinfante.mytinerary;

/**
 * Created by Simon on 2/8/2017.
 */
public class Date {

    private int day;
    private int month;
    private int year;

    Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toString(){
        String result = "";
        result = result + day + "/" + month + "/" + year;
        return result;
    }
}
