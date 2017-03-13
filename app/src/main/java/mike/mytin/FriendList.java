package mike.mytin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mike on 3/8/2017.
 */

public class FriendList {

    List<User> friends;

    public FriendList()
    {
        friends = new ArrayList<>();
    }

    public FriendList(List<User> l)
    {
        friends = l;
    }

    public FriendList(FriendList f)
    {
        this.friends = f.getFriends();
    }

    public boolean addFriend(User u)
    {
        if(Collections.binarySearch(friends, u)>=0)
            return false;
        else
        {
            addFriendNoCheck(u);
            return true;
        }
    }

    public void addFriendNoCheck(User u)
    {
        friends.add(u);
        Collections.sort(friends);
    }

    public boolean removeFriend(User u)
    {
        int index = Collections.binarySearch(friends, u);
        if(index<=0)
            return false;
        else
        {
            friends.remove(index);
            return true;
        }
    }

    public List<User> getFriends()
    {
        return friends;
    }

    public void setFriends(List<User> f)
    {
        this.friends = f;
    }

    public int contains(String username)
    {
        return Collections.binarySearch(friends, new User(username));
    }

    public User find(String username)
    {
        int index = contains(username);
        if(index>=0)
            return friends.get(index);
        else
            return null;
    }

    public FriendList getMutualFriends(FriendList f)
    {
        HashMap<String, String> hm = new HashMap<>(friends.size()*2);
        FriendList mutual = new FriendList();
        for(User u: friends)
        {
            String s = u.getUsername();
            hm.put(s,s);
        }

        for(User u: f.getFriends())
        {
            if(hm.containsKey(u.getUsername()))
                mutual.addFriendNoCheck(u);
        }

        return mutual;
    }

}
