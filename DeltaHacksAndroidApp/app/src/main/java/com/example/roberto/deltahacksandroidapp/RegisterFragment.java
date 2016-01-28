package com.example.roberto.deltahacksandroidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class RegisterFragment extends Fragment implements PatientDataFields{
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "RegisterFragment";
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "on create");

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG, "on create");
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    Log.d(TAG,"token sent");
                } else {
                    Log.d(TAG, "token not sent");
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);

        final EditText firstNameEdit, lastNameEdit, phoneNumberEdit, caregiverIDEdit, passwordEdit,
                addressEdit,emergencyNameEdit,emergencyNameNumber;

        final RadioGroup group = (RadioGroup) v.findViewById(R.id.RegisterGenderChoice);

        Button signUpButton = (Button) v.findViewById(R.id.SignUpFragmentButton);
        firstNameEdit = (EditText) v.findViewById(R.id.RegisterFirstNameEnter);
        lastNameEdit = (EditText) v.findViewById(R.id.RegisterLastNameEnter);
        phoneNumberEdit = (EditText) v.findViewById(R.id.RegisterPhoneNumberEnter);
        caregiverIDEdit = (EditText) v.findViewById(R.id.RegisterEmergencyCaregiverIDEnter);
        addressEdit = (EditText) v.findViewById(R.id.RegisterRegisterAddressEnter);
        emergencyNameEdit = (EditText) v.findViewById(R.id.RegisterEmergencyContactNameEnter);
        emergencyNameNumber = (EditText) v.findViewById(R.id.RegisterEmergencyContactNumberEnter);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPlayServices() ) {
                    int selectedId = group.getCheckedRadioButtonId();
                    // Start IntentService to register this application with GCM.
                    Intent intent = new Intent(getActivity(), RegistrationIntentService.class);
                    // add all the other shenanigans
                    intent.putExtra(PATIENT_FIRST_NAME,firstNameEdit.getText().toString());
                    intent.putExtra(PATIENT_LAST_NAME,lastNameEdit.getText().toString());
                    intent.putExtra(PATIENT_PHONE_NUMBER,phoneNumberEdit.getText().toString());
                    intent.putExtra(PATIENT_ADDRESS,addressEdit.getText().toString());
                    intent.putExtra(CAREGIVER_ID,caregiverIDEdit.getText().toString());
                    intent.putExtra(PATIENT_EMERGENCY_CONTACT,emergencyNameEdit.getText().toString());
                    intent.putExtra(PATIENT_EMERGENCY_CONTACT_NUMBER, emergencyNameNumber.getText().toString());
                    if (group.getCheckedRadioButtonId() == R.id.radioMale)
                        intent.putExtra(PATIENT_GENDER,"Male");
                    else if (group.getCheckedRadioButtonId() == R.id.radioFemale){
                        intent.putExtra(PATIENT_GENDER,"Female");
                    }

                    if (intent.getExtras().size() == 8) {
                        getActivity().startService(intent);
                    }
                }
            }
        });

        return v;
    }


    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    public boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(getActivity(), resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                getActivity().finish();
            }
            return false;
        }
        return true;
    }

}
