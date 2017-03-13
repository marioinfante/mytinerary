package mike.mytin;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements Parcelable, Comparable<User> {
    private List<User> userFriends = new ArrayList<User>();
    private List<CalendarDate> dates = new ArrayList<CalendarDate>();
    private String name;
    private String username;

    public User(){
        //default constructor to allow database getting
    }

    public User(String username){
        this.name = name;
        name = "user " + username;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){ this.name = name; }

    public String getUsername() { return username; }

    public void setUsername(String username){ this.username = username; }

    public void addFriend(User friend) { userFriends.add(friend); }

    public List<User> getFriends(){
        return userFriends;
    }

    public void setFriends(List<User> friends){
        this.userFriends = friends;
    }

    public void addDate(CalendarDate date){
        dates.add(date);
        Collections.sort(dates);
    }

    public List<CalendarDate> getDates(){
        return dates;
    }

    public void setDates(List<CalendarDate> dates)
    {
        this.dates = dates;
    }

    @Override
    public int compareTo(@NonNull User o) {
        return this.username.compareTo(o.getUsername());
    }


    //Parcel Part
    public User(Parcel in) {
        String[] data = new String[2];

        in.readStringArray(data);
        this.name = data[0];
        this.username = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeStringArray(new String[]{this.name, this.username});
    }

    public static final Creator<User> CREATOR = new Creator<User>(){
        @Override
        public User createFromParcel (Parcel source) {
            return new User(source);
        }
        @Override
        public User[] newArray(int size){
            return new User[size];
        }
    };
}
