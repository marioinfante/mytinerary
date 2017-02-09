package com.marioinfante.mytinerary;

/**
 * Created by Simon on 2/8/2017.
 */

public class Event {

    private Date date;
    private int time;
    private String title;
    private String location;

    Event(Date date, int time, String title){
        this.date = date;
        this.time = time;
        this.title = title;
    }

    public void setLocation(String loc){
        this.location = loc;
    }

    public String toString(){

        return "STUB";
    }

}
