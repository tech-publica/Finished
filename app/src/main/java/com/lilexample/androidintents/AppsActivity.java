package com.lilexample.androidintents;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.provider.AlarmClock;

public class AppsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int NOTIFY_ID = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);

        findViewById(R.id.btnSetAlarm).setOnClickListener(this);
        findViewById(R.id.btnShowMapLoc).setOnClickListener(this);
        findViewById(R.id.btnStartPhoneCall).setOnClickListener(this);
        findViewById(R.id.btnSendAnEmail).setOnClickListener(this);
        findViewById(R.id.btnPendIntent).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel","Android Intents",importance);
            channel.setDescription("Test Notification Channel");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onClick(View v) {
        int btnClick = v.getId();

        if (btnClick == R.id.btnSetAlarm) {
            final String message = "Time to wake up!";
            final int hour = 6;
            final int minutes = 30;

            // Create an intent to tell the system to set an alarm
            // NOTE: your app needs to have the Set Alarm permission
            Intent i = new Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, message)
                    .putExtra(AlarmClock.EXTRA_HOUR, hour)
                    .putExtra(AlarmClock.EXTRA_MINUTES, minutes)
                    .putExtra(AlarmClock.EXTRA_VIBRATE, true);

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        else if (btnClick == R.id.btnShowMapLoc) {
            // Locations can be specified using latlongs, queries, addresses, etc.
            String location = "geo:37.4220,-122.0841";
//            String location = "geo:0,0?q=37.4220,-122.0841(GooglePlex)";
//            String location = "geo:0,0?q=20+W+34th+St+10001";
//            String location = "geo:47.6205,-122.3493?q=restaurants";

            // Parse the location using the Uri class
            Uri geoLocUri = Uri.parse(location);

            // Pass the Uri directly to the Intent constructor
            Intent i = new Intent(Intent.ACTION_VIEW, geoLocUri);

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        else if (btnClick == R.id.btnSendAnEmail) {
            String[] addresses = { "test@example.com" };
            String[] ccs = { "someone@example.com" };
            String subject = "This is a test";
            String message = "This is a test email message!";

            Intent i = new Intent(Intent.ACTION_SENDTO);

            // Use setData to ensure that only email apps respond
            i.setData(Uri.parse("mailto:"));

            i.putExtra(Intent.EXTRA_EMAIL, addresses);
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_CC, ccs);
            i.putExtra(Intent.EXTRA_TEXT, message);

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        else if (btnClick == R.id.btnStartPhoneCall) {
            String phoneNumber = "1-800-555-1212";

            // Build the Uri for the phone number
            Uri numUri = Uri.parse("tel:" + phoneNumber);

            // Your application needs the CALL_PHONE permission for this intent
            Intent i = new Intent(Intent.ACTION_DIAL);
//            Intent i = new Intent(Intent.ACTION_CALL); // Actually makes the call
            // Set the Uri as the intent data
            i.setData(numUri);

            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        else if (btnClick == R.id.btnWebSearch) {
            String queryStr = "Eiffel Tower";

            // Create an intent to fire off a web search
            Intent i = new Intent(Intent.ACTION_WEB_SEARCH);

            i.putExtra(SearchManager.QUERY, queryStr);
            if (i.resolveActivity(getPackageManager()) != null) {
                startActivity(i);
            }
        }
        else if (btnClick == R.id.btnPendIntent) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "my_channel");

            // Create the intent that will be fired when the user taps the notification
            Intent intent = new Intent(this, DestinationActivity.class);
            // Wrap it up in a PendingIntent
            PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFY_ID, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            builder.setSmallIcon(R.drawable.ic_launcher_foreground);
            builder.setContentTitle("Sample Notification");
            builder.setContentText("This is a sample notification");
            builder.setAutoCancel(true);
            builder.setSubText("Tap to view");
            builder.setContentIntent(pendingIntent);

            Notification notification = builder.build();
            NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            mgr.notify(NOTIFY_ID, notification);
        }
    }
}

