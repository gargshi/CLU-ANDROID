package com.example.clu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {

    private static final String TAG = "custom";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Intent intent= getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView tx=(TextView) findViewById(R.id.display_text_view);
        tx.setText(message);

    }
    public void view_dev_info(View v)
    {
        System.out.println("Device info");
        startActivity(new Intent(this, dev_info.class));

    }
    public void view_settings(View v)
    {
        System.out.println("Settings");
        startActivity(new Intent(this, Settings.class));

    }
    public void logout(View v)
    {
        //Intent i=new Intent(this, MainActivity.class);
        startActivity(new Intent(this, MainActivity.class));
    }

}