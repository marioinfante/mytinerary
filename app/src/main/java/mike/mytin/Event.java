package mike.mytin;

public class Event implements Comparable<Event> {

	public static final int MAX_TIME = 1440;

	private int startTime;					//time of day in minutes from 0 to 1440
	private int duration;					//duration of event
	private String description;				//description of event
	private String title;					//event title

    public Event(){}

	public Event(int time, int duration, String title, String description) {
		this.startTime = time;
		this.duration = duration;
		this.title = title;
		this.description = description;
	}

	public int getStartTime()
	{
		return startTime;
	}

	public void setStartTime(int time)
	{
		this.startTime = time;
	}
	
	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int dur)
	{
		this.duration = dur;
	}
	
	public int getEndTime()
	{
		return startTime+duration;
	}
	
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	@Override
	public int compareTo(Event e)
	{
		if(this.startTime > e.getStartTime())
			return 1;
		if(this.startTime < e.getStartTime())
			return -1;
		return 0;
	}
}
