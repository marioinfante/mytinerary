package mike.mytin;

/**
 * Created by Mike on 3/8/2017.
 */

public class FriendRequest {

    private String from;
    private String to;

    public FriendRequest()
    {
        from = "";
        to = "";
    }

    public FriendRequest(String from, String to)
    {
        this.from = from;
        this.to = to;
    }

    public String getSender()
    {
        return from;
    }

    public void setSender(String from)
    {
        this.from = from;
    }

    public void setReciever(String to)
    {
        this.to = to;
    }

    public String getReciever()
    {
        return to;
    }
}
