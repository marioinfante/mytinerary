package mike.mytin;

/**
 * Created by Mike on 3/6/2017.
 */

public class DataManipulator {

    private DatabaseUtility dbu;

    public DataManipulator()
    {
        dbu = new DatabaseUtility();
    }

    public DatabaseUtility getDBU()
    {
        return dbu;
    }

}
