package mike.mytin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Collections;

public class DayFrag extends Fragment {
    User mainUser;
    ArrayList<TextView> times;
    CalendarDate date;
    boolean[] busy;

    View view;
    LinearLayout layout;
    MainActivity main;
    LinearLayout toggleHolder;
    ArrayList<ToggleButton> toggles;
    ArrayList<ArrayList<Event>> events;


    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.dayfrag, container, false);
        layout = (LinearLayout) view.findViewById(R.id.times);
        TextView date = (TextView) view.findViewById(R.id.thisdate);
        main = (MainActivity) getActivity();
        toggleHolder = (LinearLayout) view.findViewById(R.id.friendToggleHolder);
        mainUser = main.getMainUser();

        createFakeData(mainUser);

        createFAB(view);
        toggles = new ArrayList<>();
        createFriendToggles();
        createTimeSlots(layout);
        shadeEvents(view, mainUser);
        findEarliestSlot(view);

        date.setText(main.getChosenMonth() + "/" + main.getChosenDay() + "/" + main.getChosenYear());

        Button button = (Button) view.findViewById(R.id.update_users);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvents();
            }
        });
        return view;
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroyView(){super.onDestroyView(); Log.d("ODV", "DayFrag::Destroyed View");}

    @Override
    public void onDestroy(){super.onDestroy(); Log.d("Destroy", "DayFrag::onDestroy called");}

    private void createFAB(View view){
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.dayfragfab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment eventDetailFrag = new Event_details();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(android.R.id.content, eventDetailFrag);
                transaction.addToBackStack("Event Detail Stack").commit();
            }
        });
    }

    private void createFriendToggles(){
        int id = 0;
        for(User u:mainUser.getFriends()){
            final ToggleButton newSwitch = new ToggleButton(main);
            newSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    updateEvents();
                }
            });
            String name = u.getName();
            newSwitch.setText(name);
            newSwitch.setTextOn(name);
            newSwitch.setTextOff(name);
            newSwitch.setId(id);
            id++;
            toggles.add(newSwitch);
            toggleHolder.addView(newSwitch);
        }
    }

    private void updateEvents(){
        ArrayList<User> usersOn = new ArrayList<>();
        usersOn.add(mainUser);
        int i = 0;
        for(ToggleButton tb:toggles){
            if(tb.isChecked()){
                usersOn.add(mainUser.getFriends().get(i));
            }
            i++;
        }
        clearEvents();
        for(User u:usersOn){
            shadeEvents(view,u);
        }
        findEarliestSlot(view);
    }

    private void clearEvents(){
        for(TextView tv:times){
            String[] words = tv.getText().toString().split(" ");
            String time = words[0] + " ";
            tv.setText(time);
            tv.setBackgroundColor(0x00000000);
        }
        for(boolean b:busy){
            b = false;
        }
        for(ArrayList<Event> e:events){
            e.clear();
        }
    }

    private void createTimeSlots(LinearLayout layout){

        // set all of the slots as open
        busy = new boolean[96];
        for(Boolean a:busy){
            a = false;
        }

        times = new ArrayList<>(96);
        String s;
        String amOrPm = "AM ";

        // for every currentHour create 4 different slots (one every 15 minutes)
        for(int i = 0; i < 24; i++){
            if(i%12 == 0)
                s = "12";
            else
                s = Integer.toString(i%12);
            TextView hour = new TextView(main);
            hour.setText(s + ":00" + amOrPm);
            hour.setPadding(10,10,0,0);
            hour.setTextSize(20);
            layout.addView(hour);
            TextView quarter = new TextView(main);
            quarter.setText(s + ":15" + amOrPm);
            quarter.setPadding(50,10,0,0);
            layout.addView(quarter);
            TextView halfhour = new TextView(main);
            halfhour.setText(s + ":30" + amOrPm);
            halfhour.setPadding(50,10,0,0);
            layout.addView(halfhour);
            TextView thirdquarter = new TextView(main);
            thirdquarter.setText(s + ":45" + amOrPm);
            thirdquarter.setPadding(50,10,0,0);
            layout.addView(thirdquarter);

            times.add(hour);
            times.add(quarter);
            times.add(halfhour);
            times.add(thirdquarter);
            if(i == 11)
                amOrPm = "PM ";
        }
    }

    public void eventClick(View v){
        int id = v.getId();
        Log.d("DF","Event " + id + " clicked");
        for(Event e:(events.get(id))){
            Log.d("DF",e.getTitle());
        }
        Log.d("DF","" + busy[id]);

        Fragment eventView = new View_Event();
        Bundle b = new Bundle();
        b.putInt("eventIndex",0);
        eventView.setArguments(b);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(android.R.id.content, eventView);
        transaction.addToBackStack("Event View Stack").commit();


    }

    //Null exception error line 235
    private void shadeEvents(View v, User user){
/*
        //Remember to make sure has user has date before getting
        CalendarDate dt = user.getDates().get(Collections.binarySearch(user.getDates(), date));
        ArrayList<Event> e = dt.getSchedule();
        for(Event ev:e){
            int startTime = ev.getStartTime();

            int slot = startTime/60 * 4 + (startTime%60)/15;

            // how many slots the event should take   //if the duration goes more than halfway into another slot, just fill it in
            int duration = ev.getDuration()/15 + ev.getDuration()%15/8;

            // TODO: have text change to the event name
            for(int i = 0; i < duration; i++){
                times.get(slot).setBackgroundColor(0x99ff4081);
                events.get(slot).add(ev);
                // just display title for just the first slot
                if(i == 0) {
                    times.get(slot).setText(times.get(slot).getText().toString() + ev.getTitle());
                }
                busy[slot] = true;
                slot++;
            }
        }
       */
    }


    private void findEarliestSlot(View v){
        TextView tv = (TextView) v.findViewById(R.id.earliestTime);

        for(int i = 0; i < 98; i++){
            if(!busy[i]){
                tv.setText("The earliest time available is: " + times.get(i).getText().toString());
                return;
            }
        }
        tv.setText("No time slots available");
    }
    public void createFakeData(User mainUser){

        /*
        String friendNames[] = {"Charles X", "Bruce Wayne", "Tom Brady", "Ash Ketchum", "Mario Superstar"};
        if(mainUser.getFriends()!=null && mainUser.getFriends().isEmpty()) {
            for (int i = 0; i < friendNames.length; i++) {
                mainUser.addFriend(new User(friendNames[i]));
            }
        }
        */

        date = main.getThisDate();
        ArrayList<Event> sched = date.getSchedule();
        if(sched.isEmpty())
        {
            for(int i=0; i<3; i++)
            {
                sched.add(new Event((int) (Math.random() * 1100), 60 + (int)(Math.random() * 240), mainUser.getName().substring(0,2)+"Event " + i, ""));
            }
        }

        for (User u: mainUser.getFriends())
        {

            int index = Collections.binarySearch(u.getDates(), date);
            ArrayList<Event> uSched;
            if(index<0) {
                u.addDate(new CalendarDate(date.getMonth(), date.getDay(), date.getYear()));
                index = Collections.binarySearch(u.getDates(), date);
            }
            uSched = u.getDates().get(index).getSchedule();
            if (uSched.isEmpty()) {
                for (int i = 0; i < 3; i++) {
                    uSched.add(new Event((int) (Math.random() * 1100), 60 + (int) (Math.random() * 240), u.getName().substring(0, 2) + "Event " + i, ""));
                }
            }

        }

    }
}
