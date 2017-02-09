package com.marioinfante.mytinerary;

/**
 * Created by Simon on 2/8/2017.
 */

public class Event {

    // each event needs to have these
    private Date date;
    private int time;
    private String title;

    // each event can have this
    private String location;
    private String description;

    Event(Date date, int time, String title){
        this.date = date;
        this.time = time;
        this.title = title;
        this.location = "";
        this.description = "";
    }

    public void setLocation(String loc){
        this.location = loc;
    }

    public void setDescription(String des){
        this.description = des;
    }

    public String toString(){
        return "STUB";
    }

}
