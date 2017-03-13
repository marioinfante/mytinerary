package mike.mytin;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Omar on 3/10/2017.
 */

public class UserInfoFrag extends Fragment {
    private View view;
    private User mainUser;
    private MainActivity main;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.userinfofrag, container, false);
        main = (MainActivity) getActivity();
        mainUser = main.getMainUser();

        Button button = (Button) view.findViewById(R.id.addfriendbutton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });

        return view;
    }


    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDestroyView(){super.onDestroyView(); Log.d("ODV", "DayFrag::Destroyed View");}

    @Override
    public void onDestroy(){super.onDestroy(); Log.d("Destroy", "DayFrag::onDestroy called");}

    public void addFriend(){
        //check if already in mainUser's friend list
        //if so, display this user is already your friend Snackbar
        //else, add friend to friend's list and display "This user has been added to your friend's list" Snackbar
    }

}
