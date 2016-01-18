package com.example.roberto.deltahacksandroidapp;

/**
 * Created by Roberto on 2016-01-16.
 */
public class Patient {
    String mFirstName,mLastName,mAddress,mPhoneNumber,mContactName,mContactNumber,mCaregiverID,mGender;

    public Patient(String fname,String lname, String address, String phoneNumber, String contactName,
                   String contactNumber, String caregiverID,String gender){
        mFirstName = fname;
        mLastName = lname;
        mAddress = address;
        mPhoneNumber = phoneNumber;
        mContactName = contactName;
        mContactNumber = contactNumber;
        mCaregiverID = caregiverID;
        mGender = gender;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String mName) {
        this.mFirstName = mName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String mName) {
        this.mLastName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getContactName() {
        return mContactName;
    }

    public void setContactName(String mContactName) {
        this.mContactName = mContactName;
    }

    public String getContactNumber() {
        return mContactNumber;
    }

    public void setContactNumber(String mContactNumber) {
        this.mContactNumber = mContactNumber;
    }

    public String getCaregiverID() {
        return mCaregiverID;
    }

    public void setCaregiverID(String mCaregiverID) {
        this.mCaregiverID = mCaregiverID;
    }
    public String getGender() { return mGender; }
    public void setGender(String gender) { mGender = gender; }
}
