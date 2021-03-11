package com.lilexample.androidintents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DestinationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        TextView values = (TextView)findViewById(R.id.txtValues);

        // extract any data passed by the caller
        Intent callingIntent = getIntent();
        if (callingIntent != null) {
            String str = callingIntent.getStringExtra("StringData");
            Integer int1 = callingIntent.getIntExtra("IntData", -1);

            String data = str + "\n" + Integer.toString(int1);
            values.setText(data);
        }
    }
}
