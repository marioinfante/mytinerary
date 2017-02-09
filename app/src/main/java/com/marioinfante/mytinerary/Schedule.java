package com.marioinfante.mytinerary;

import java.util.ArrayList;

/**
 * Created by Simon on 2/8/2017.
 */

public class Schedule {

    private ArrayList<Event> events;
    private int numEvents;

    Schedule(ArrayList<Event> events){
        this.events = events;
    }

    public void addEvent(Event e){
        events.add(e);
    }

}
