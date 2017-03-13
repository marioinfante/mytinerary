package mike.mytin;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Mike on 3/9/2017.
 */

public class UserFinder extends DataManipulator {

    private FriendList foundList;
    customAction ca;

    public UserFinder()
    {
        super();
        foundList = new FriendList();
        ca = new customAction() {
            @Override
            public void execute() {

            }

            public boolean found()
            {
                return false;
            }
        };
    }

    public boolean exists(final String username)
    {
        getDBU().getDataBaseRef().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               dataSnapshot.hasChild(username);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return false;
    }

    public User find(String username)
    {
        return null;
    }


}
