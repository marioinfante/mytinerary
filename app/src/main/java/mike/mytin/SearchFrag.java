package mike.mytin;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Senghai on 3/8/2017.
 */

public class SearchFrag extends Fragment {
    private ArrayList<User> fakeUsers;
    private MainActivity main;
    private LinearLayout layout;
    private View view;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        view = inflater.inflate(R.layout.search, container, false);
        layout = (LinearLayout) view.findViewById(R.id.search_output);
        main = (MainActivity) getActivity();
        User mainUser = main.getMainUser();
        fakeUsers = new ArrayList<User>();
        createFakeData();
        displayAllUsers();


        //Button pressed
        //grab text from input text
        //If user found, display user button
        //else display Snackbar "No User Found"

        //User button creates new frag page
        //display that friend's accessible info with "Add Friend" button (maybe request?)
        //get friends and check if person is already in friends list

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

    private void createFakeData(){
        fakeUsers.add(new User("Mike Rousso"));
        fakeUsers.add(new User("Keith Long"));
        fakeUsers.add(new User("Danny Seflinger"));
    }

    private void displayAllUsers(){
        for(int i = 0; i < fakeUsers.size(); i++){
            User user = fakeUsers.get(i);
            Button userButton = new Button(main);
            userButton.setText(user.getName());
            userButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Fragment userInfoFrag = new UserInfoFrag();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, userInfoFrag);
                    transaction.addToBackStack("User Info View Stack").commit();
                }
            });
            layout.addView(userButton);
        }
    }
}
