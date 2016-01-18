package com.example.roberto.deltahacksandroidapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PatientActivity extends AppCompatActivity implements PatientDataFields  {

    private RequestQueue mQueue;
    private JSONObject mJSON;

    public ProfileFragment createFragment(){
        return new ProfileFragment();
    }

    private void pullFromServer(JSONObject json){
        // Request a string response from the provided URL.
        String url = getText(R.string.node_server_pull_events).toString();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.POST,url,
                json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("reponse", "recieved");
                        sendNotification(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("error", "recieved");
            }
        });

        mQueue.add(jsObjRequest);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_patient);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        JSONObject data = new JSONObject();

        if (getIntent().getExtras() != null){
            try {
                data = new JSONObject(getIntent().getExtras().getString(PATIENT_INFO));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        /*
        if (findViewById(R.id.patient_fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            ProfileFragment frag = createFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.patient_fragment_container,frag).commit();
        }

*/
        TextView PatientTitle,PatientAddress,PatientNumber;

        PatientTitle = (TextView) findViewById(R.id.Patient_fragment_name);
        PatientAddress = (TextView) findViewById(R.id.Patient_fragment_address);
        PatientNumber = (TextView) findViewById(R.id.Patient_fragment_number);

        try {
            String name;
            name = data.getString(PATIENT_FIRST_NAME_2) + " " + data.get(PATIENT_LAST_NAME_2);

            PatientTitle.setText(name);
            PatientAddress.setText(data.getString(PATIENT_ADDRESS));
            PatientNumber.setText(data.getString(PATIENT_PHONE_NUMBER));

            if (mJSON == null) {
                mJSON = new JSONObject();
                mJSON.put(PATIENT_FIRST_NAME_2, data.getString(PATIENT_FIRST_NAME_2));
                mJSON.put(PATIENT_LAST_NAME_2, data.getString(PATIENT_LAST_NAME_2));
                mJSON.put(EVENT_TOKEN, data.getString(EVENT_TOKEN));
            }
            pullFromServer(mJSON);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        pullFromServer(mJSON);
    }

    private void sendNotification(JSONObject json) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        try {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_stat_ic_notification)
                    //.setContentTitle(json.getString(EVENT_TITLE))
                    .setContentTitle("MemNote")
                    .setContentText(json.getString(EVENT_DETAILS))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
