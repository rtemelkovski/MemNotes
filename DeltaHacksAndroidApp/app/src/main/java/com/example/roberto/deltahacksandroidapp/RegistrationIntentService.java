/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.roberto.deltahacksandroidapp;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class RegistrationIntentService extends IntentService implements PatientDataFields {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};

    private RequestQueue mQueue;
    private Patient mPatient;
    private String mToken;
    private Intent mGoToLogin;


    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            // gets token for the gcm server
            InstanceID instanceID = InstanceID.getInstance(this);
            mToken = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + mToken);
            mQueue = Volley.newRequestQueue(this);
            mGoToLogin = new Intent(this,PatientActivity.class);

            if (intent.getExtras().size() > 1) {
                mPatient = new Patient(intent.getStringExtra(PATIENT_FIRST_NAME),
                        intent.getStringExtra(PATIENT_LAST_NAME),
                        intent.getStringExtra(PATIENT_ADDRESS),
                        intent.getStringExtra(PATIENT_PHONE_NUMBER),
                        intent.getStringExtra(PATIENT_EMERGENCY_CONTACT),
                        intent.getStringExtra(PATIENT_EMERGENCY_CONTACT_NUMBER),
                        intent.getStringExtra(CAREGIVER_ID),
                        intent.getStringExtra(PATIENT_GENDER)
                        );

                PatientJsonObject data = new PatientJsonObject(mToken, mPatient);
                sendRegistrationToServer(data);

                // Subscribe to topic channels
                subscribeTopics(mToken);

                // stores a boolean that indicates whether the generated token has been
                // sent to your server. If the boolean is false, send the token to your server,
                // otherwise your server should have already received the token.
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            }else{
                String[] name = intent.getStringExtra(PATIENT_FULL_NAME).split(" ");
                String fname = name[0];
                String lname = name[1];

                Log.d("fname", fname);
                Log.d("lname",lname);

                PatientJsonObject json = new PatientJsonObject(mToken, fname, lname);
                sendLoginToServer(json);
                subscribeTopics(mToken);
                sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            // If an exception happens while fetching the new token or updating our registration data
            // on a third-party server, this ensures that we'll attempt the update at a later time.
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
        mGoToLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param json The json object you will send new token.
     */
    private void sendRegistrationToServer(PatientJsonObject json) {
        // Request a string response from the provided URL.
        String url = getText(R.string.node_server_sign_up).toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("reponse", "recieved");
                        mGoToLogin.putExtra(PATIENT_INFO, response.toString());
                        startActivity(mGoToLogin);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("error", "recieved");
                     }
                 });

        mQueue.add(jsObjRequest);
    }

    private void sendLoginToServer(PatientJsonObject json){
        String url = getText(R.string.node_server_log_in).toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Login", "recieved");
                        mGoToLogin.putExtra(PATIENT_INFO,response.toString());
                        startActivity(mGoToLogin);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error login", "recieved");
            }
        });

        mQueue.add(jsObjRequest);

    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

}
