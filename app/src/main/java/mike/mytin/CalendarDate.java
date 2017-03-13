package mike.mytin;

import java.util.ArrayList;
import java.util.Collections;

public class CalendarDate implements Comparable<CalendarDate>{

	private int day;				//day of the month
	private int month;				//month of the year
	private int year;		
	// private final int currentYear = 2017;
	private ArrayList<Event> schedule;		//sorted from lowest to highest (earliest time to latest) with Collections.sort

	public CalendarDate(){
        //default constructor for database retrieval
    }

	public CalendarDate(int month, int day, int year){
		setYear(year);
		setMonth(month);
		setDay(day);
		schedule = new ArrayList<Event>(10);
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		if(day<1 || day>31 || (month%2==0 && day>30) || (month==2 && ((isLeapYear(year) && day>29) || (!isLeapYear(year) && day>28) )))
			throw new IllegalArgumentException("Invalid day (" + day + ")");
		else
			this.day = day;
	}

	public int getMonth()
	{
		return month;
	}

	public void setMonth(int month)
	{
		if(month<1 || month>12)
			throw new IllegalArgumentException("Invalid month (" + month +")" );
		else
			this.month = month;

	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		/*if(year - currentYear >100 || year - currentYear < -100)
			throw new IllegalArgumentException("Year must be within 100 years of " + currentYear);
		else*/
			this.year = year;
	}
	
	public void addEvent(Event e)
	{
		schedule.add(e);
		Collections.sort(schedule);
	}
	
	public boolean removeEvent(Event e)
	{
		int index = containsEvent(e);		//check if event is in schedule
		if(index>=0){
			schedule.remove(index);
			return true;
		}
		return false;
	}
	
	public int containsEvent(Event e)
	{
		return Collections.binarySearch(schedule, e);
	}
	
	public ArrayList<Event> getSchedule()
	{
		return schedule;
	}
	
	public void setSchedule(ArrayList<Event> sched)
	{
		schedule = sched;
	}
	
	private boolean isLeapYear(int year)
	{
		if(year%4!=0)
			return false;
		else if(year%100!=0)
			return true;
		else if(year%400!=0)
			return false;
		else 
			return true;
	}
	
	private int dow(int month, int day, int year) //returns number representing the day of the week (1-7 = Sun-Sat)
    {
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if (month < 3)
        	year-=1;
        return (year + year/4 - year/100 + year/400 + t[month-1] + day) % 7 + 1;
    }
	
	@Override
	public int compareTo(CalendarDate d)
	{
		if(this.year > d.getYear())
			return 1;
		if(this.year < d.getYear())
			return -1;
		if(this.month > d.getMonth())
			return 1;
		if(this.month < d.getMonth())
			return -1;
		if(this.day > d.getDay())
			return 1;
		if(this.day < d.getDay())
			return -1;
		return 0;
	}

}
