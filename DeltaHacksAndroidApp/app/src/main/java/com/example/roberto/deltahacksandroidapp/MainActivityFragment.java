package com.example.roberto.deltahacksandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements PatientDataFields {

    private static final String TAG = "MainActivityFragment";
    private EditText mNameInput;
    private Button mLogInButton, mSignUpButton;
    public OnLoginSelectedListener mCallback;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mNameInput = (EditText) v.findViewById(R.id.LoginFragmentNameEnter);
        mLogInButton = (Button) v.findViewById(R.id.LoginFragmentButton);
        mSignUpButton = (Button) v.findViewById(R.id.SignUpFragmentButton);

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mNameInput.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    mNameInput.setError("This item cannot be empty");
                }else {
                    // send get to node to get schedule
                    Toast.makeText(getActivity(), "all good", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    intent.putExtra(PATIENT_FULL_NAME,name);
                    getActivity().startService(intent);
                }
            }
        });

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onSignUpPressed();
            }
        });

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnLoginSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    // Container Activity must implement this interface
    public interface OnLoginSelectedListener {
        public void onSignUpPressed();
    }
}
