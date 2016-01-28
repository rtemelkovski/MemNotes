package com.example.roberto.deltahacksandroidapp;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Roberto on 2016-01-16.
 */
public class PatientJsonObject extends JSONObject implements PatientDataFields {
    public PatientJsonObject(String token, Patient patient){
        try {
            put(PATIENT_TOKEN,token);
            put(PATIENT_FIRST_NAME,patient.getFirstName());
            put(PATIENT_LAST_NAME,patient.getLastName());
            put(PATIENT_ADDRESS,patient.getAddress());
            put(PATIENT_EMERGENCY_CONTACT,patient.getContactName());
            put(PATIENT_EMERGENCY_CONTACT_NUMBER, patient.getContactNumber());
            put(PATIENT_PHONE_NUMBER,patient.getPhoneNumber());
            put(CAREGIVER_ID,patient.getCaregiverID());
            put(PATIENT_GENDER,patient.getGender());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PatientJsonObject(String token, String fname, String lname){
        try{
            put(PATIENT_TOKEN,token);
            put(PATIENT_FIRST_NAME,fname);
            put(PATIENT_LAST_NAME,lname);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
