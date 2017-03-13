package mike.mytin;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnDaySelectedListener{

    //this is the main user that is used throughout this application
    private FragmentManager fm = getFragmentManager();
    private User mainUser = new User("EMPTY_USER");
    private int chosenYear;
    private int chosenMonth;
    private int chosenDay;
    OnDaySelectedListener daySelectedListener;
    private CalendarDate thisDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the navigation bar in the main activity
        createNavBar();
        getLoginInfo();
        TextView welcome = (TextView) findViewById(R.id.welcome_message);
        welcome.setText("Welcome " + mainUser.getName());
        startCalendar();
    }

    @Override
    public void onBackPressed() {
        //check drawer whether its open or closed
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        //check if there are fragments over the main content
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home){

        } else if (id == R.id.nav_friends) {

        } else if (id == R.id.nav_search) {
            Fragment searchFrag = new SearchFrag();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.content_frame, searchFrag);
            transaction.addToBackStack("Search View Stack").commit();
        } else if (id == R.id.nav_manage) {
            //INPUT SETTINGS PAGE
        }

        //close the drawer once an item is selected
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public User getMainUser(){
        return mainUser;
    }

    private void createNavBar(){
        setContentView(R.layout.nav_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getLoginInfo(){
        Intent intent = getIntent();
        this.mainUser =  intent.getParcelableExtra("userTag");
    }

    private void startCalendar(){
        try {
            daySelectedListener = (OnDaySelectedListener) this;
        } catch (ClassCastException e) {
            throw new ClassCastException(this.toString() + " must implement OnDaySelectedListener");
        }

        CalendarView calendarView= (CalendarView) findViewById(R.id.calendarview);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                daySelectedListener.onDaySelected(year,month,dayOfMonth);
            }
        });

    }

    @Override
    public void onDaySelected(int year, int month, int dayOfMonth){
        this.chosenYear = year;
        this.chosenMonth = month + 1;
        this.chosenDay = dayOfMonth;

        List<CalendarDate> dates = mainUser.getDates();
        thisDate = new CalendarDate(this.chosenMonth, this.chosenDay, this.chosenYear);

        int index = Collections.binarySearch(dates, thisDate);

        if(mainUser.getFriends()!=null && mainUser.getFriends().isEmpty())
            createInitFakeUserData(mainUser);

        if(index >= 0)
            thisDate = dates.get(index);
        else
        {
            mainUser.addDate(thisDate);
            for(User u: mainUser.getFriends())
                u.addDate(new CalendarDate(thisDate.getMonth(), thisDate.getDay(), thisDate.getYear()));
        }

        Fragment dayFrag = new DayFrag();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, dayFrag);
        transaction.addToBackStack("Daily View Stack").commit();
    }

    public int getChosenDay(){
        return chosenDay;
    }

    public int getChosenMonth(){
        return chosenMonth;
    }

    public int getChosenYear(){
        return chosenYear;
    }

    private void createSearchActivity(){
        Fragment searchFrag = new SearchFrag();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, searchFrag);
        transaction.addToBackStack("Search View Stack").commit();
    }

    public CalendarDate getThisDate(){
        return thisDate;
    }

    public void createInitFakeUserData(User user)
    {
        String friendNames[] = {"Charles X", "Bruce Wayne", "Tom Brady", "Ash Ketchum", "Mario Superstar"};
        for (int i=0; i<friendNames.length; i++)
        {
            user.addFriend(new User(friendNames[i]));
        }
        Log.d("fakedata", "Data created:" + user.getFriends().get(3).getName());


    }
}
