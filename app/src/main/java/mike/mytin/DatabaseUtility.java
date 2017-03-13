package mike.mytin;

/**
 * Created by Omar on 2/15/2017.
 */


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseUtility
{
    private DatabaseReference mDatabase;

    public DatabaseUtility()
    {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public DatabaseReference getDataBaseRef()
    {
        return mDatabase;
    }

    public void read(String path)
    {

    }

    public void write(String path, Object data)
    {

    }

}
