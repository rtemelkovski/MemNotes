package com.example.roberto.deltahacksandroidapp;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;

public class ProfileFragment extends ListFragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private eventAdapter mRemindAdapter;
    private ArrayList<Reminder> mReminders;

    public ProfileFragment() {
        mReminders = new ArrayList<Reminder>();
        mReminders.add(new Reminder("Take Pills","Don't forget to take your pills"));
        mReminders.add(new Reminder("Visit Kids","Tell them hi"));
    }

    public static ProfileFragment newInstance(int columnCount) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        mRemindAdapter = new eventAdapter(mReminders);
        setListAdapter(mRemindAdapter);
    }


    private class eventAdapter extends ArrayAdapter<Reminder> {

        public eventAdapter(ArrayList<Reminder> events){ super(getActivity(), 0, events); }

        @Override
        public View getView(final int position, View convertView,ViewGroup parent){
            // if you didn't get a view, inflate one
            if (convertView == null){
                convertView = getActivity().getLayoutInflater().inflate(R.layout.fragment_item_list,null);
            }
            Reminder reminder = getItem(position);

            TextView titleText = (TextView) convertView.findViewById(R.id.Reminder_List_Entry_Title);
            TextView detailsText = (TextView) convertView.findViewById(R.id.Reminder_List_Entry_Details);

            titleText.setText(reminder.getTitle());
            detailsText.setText(reminder.getDescription());
            return convertView;
        }
    }
}
