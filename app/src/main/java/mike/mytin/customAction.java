package mike.mytin;

/**
 * Created by Mike on 3/6/2017.
 */

public abstract class customAction {

    protected Object custData;

    public customAction(){
        custData = new Object();
    }
    public customAction(Object custData)
    {
        this.custData = custData;
    }

    public void setCustData(Object custData)
    {
        this.custData = custData;
    }

    public abstract void execute();
}
