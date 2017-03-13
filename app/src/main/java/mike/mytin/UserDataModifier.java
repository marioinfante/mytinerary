
package mike.mytin;

/**
 * Created by Mike on 3/6/2017.
 */

public class UserDataModifier extends DataManipulator {

    public UserDataModifier()
    {
        super();
    }

    public void writeNewUser(User user)
    {
        getDBU().getDataBaseRef().child("users").child(user.getUsername()).setValue(user);
    }
}
