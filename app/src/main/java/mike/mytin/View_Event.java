package mike.mytin;

import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class View_Event extends Fragment {

    private class timeSet{
        public int hour;
        public int min;
        public timeSet()
        {
            hour = -1;
            min = -1;
        }
    }

    private EditText title;
    private TextView start;
    private TextView end;
    private EditText descript;
    private Button done;
    private Button cEndTime;
    private Button cStartTime;
    final Calendar c = Calendar.getInstance();
    int currentHour = c.get(Calendar.HOUR_OF_DAY);
    int currentMinute = c.get(Calendar.MINUTE);
    private timeSet et, st;
    private Bundle args;
    private int eventIndex;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = inflater.inflate(R.layout.activity_event_details, container, false);

        title = (EditText)v.findViewById(R.id.titleText);
        title.setEnabled(false);
        start = (TextView)v.findViewById(R.id.startTimeText);
        end = (TextView)v.findViewById(R.id.endTimeText);
        descript = (EditText)v.findViewById(R.id.descriptionText);
        descript.setEnabled(false);
        if(descript.getText().toString().isEmpty())
            descript.setHint("");
        cEndTime = (Button)v.findViewById(R.id.setEndTime);
        cEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectEndTime(v);
            }
        });
        cEndTime.setEnabled(false);
        cStartTime = (Button)v.findViewById(R.id.setStartTime);
        cStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStartTime(v);
            }
        });
        cStartTime.setEnabled(false);
        done = (Button)v.findViewById(R.id.buttonDone);
        done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                editPressed(v);
            }
        });
        done.setText("EDIT");
        et = new timeSet();
        st = new timeSet();
        args = getArguments();
        setDisplay();

        Button del = (Button)v.findViewById(R.id.buttonDelete);
        del.setVisibility(View.VISIBLE);
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getThisDate().getSchedule().remove(eventIndex);
                getFragmentManager().popBackStack();
            }
        });

        return v;
    }

    public void setDisplay()
    {
        if(args!=null)
        {
            eventIndex = args.getInt("eventIndex");
            Event e = ((MainActivity)getActivity()).getThisDate().getSchedule().get(eventIndex);
            title.setText(e.getTitle());
            descript.setText(e.getDescription());
            int hourOfDay = e.getStartTime()/60;
            st.hour = hourOfDay;
            int minuteOfDay = e.getStartTime()%60;
            st.min = minuteOfDay;
            start.setText( (hourOfDay>9&&hourOfDay<13?"":" ") + (hourOfDay>12?hourOfDay%12:(hourOfDay==0?12:hourOfDay)) + ":" + (minuteOfDay >9? minuteOfDay :"0"+ minuteOfDay) + (hourOfDay>11?" PM":" AM"));;
            hourOfDay = e.getEndTime()/60;
            et.hour = hourOfDay;
            minuteOfDay = e.getEndTime()%60;
            et.min = minuteOfDay;
            end.setText( (hourOfDay>9&&hourOfDay<13?"":" ") + (hourOfDay>12?hourOfDay%12:(hourOfDay==0?12:hourOfDay)) + ":" + (minuteOfDay >9? minuteOfDay :"0"+ minuteOfDay) + (hourOfDay>11?" PM":" AM"));;

        }
    }

    public void editPressed(View view)
    {
        //Set everything clickable
        title.setEnabled(true);
        cStartTime.setEnabled(true);
        cEndTime.setEnabled(true);
        descript.setEnabled(true);
        if(descript.getText().toString().isEmpty())
        {
            descript.setHint("Description");
        }
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donePressed(v);
            }
        });
        done.setText("SAVE");
    }

    public void donePressed(View view)
    {
        //perform checks to make sure correct data was entered
        String titlestr = title.getText().toString();
        String startStr = start.getText().toString();
        String endStr = end.getText().toString();
        int endInt = -1, startInt = -1;
        if(!startStr.isEmpty())
        {
            startInt = st.hour*60 + st.min;
        }
        if(!endStr.isEmpty())
        {
            endInt = et.hour*60 + et.min;
        }
        String desc = descript.getText().toString();


        if(titlestr.isEmpty())
        {
            Snackbar.make(view, "Please enter a title", Snackbar.LENGTH_SHORT).show();
            title.requestFocus();
        }

        else if(startStr.isEmpty())
        {
            Snackbar.make(view, "Please enter a start time", Snackbar.LENGTH_SHORT).show();
            cStartTime.requestFocus();
        }

        else if(endStr.isEmpty())
        {
            Snackbar.make(view, "Please enter an end time", Snackbar.LENGTH_SHORT).show();
            cEndTime.requestFocus();
        }

        else if(endInt<=startInt)
        {
            Snackbar.make(view, "End time must be after start time", Snackbar.LENGTH_SHORT).show();
            cEndTime.requestFocus();
        }

        //alert user about inputting correct/necessary info
        //Toast.makeText(this,"Please enter info", Toast.LENGTH_SHORT).show();

        else
        {
            //do something with this event
            MainActivity main = (MainActivity) getActivity();
            Event e = main.getThisDate().getSchedule().get(eventIndex);
            e.setTitle(titlestr);
            e.setDescription(desc);
            e.setStartTime(startInt);
            e.setDuration(endInt - startInt);
            getFragmentManager().popBackStack();
        }

    }

    public void selectEndTime(View view)
    {
        chooseTime(end, et);
    }

    public void selectStartTime(View view)
    {
        chooseTime(start, st);
    }

    public void chooseTime(final TextView etxt, final timeSet tS)
    {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow((null == getActivity().getCurrentFocus()) ? null : getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        int currHour = currentHour;
        int currMin = currentMinute;
        if(tS.hour!=-1)
            currHour = tS.hour;
        if(tS.min!=-1)
            currMin = tS.min;

        TimePickerDialog timeChooser = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute)
            {
                tS.hour = hourOfDay;
                tS.min = minute;
                etxt.setText( (hourOfDay>9&&hourOfDay<13?" ":"  ") + (hourOfDay>12?hourOfDay%12:(hourOfDay==0?12:hourOfDay)) + ":" + (minute>9?minute:"0"+minute) + (hourOfDay>11?" PM":" AM"));
            }
        }, currHour, currMin, false);
        timeChooser.show();
    }

}
