package com.example.clu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DisplayMessageActivity extends AppCompatActivity {
    String uname="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        SharedPreferences sp = getSharedPreferences("PREFLIST", MODE_PRIVATE);
        String message = getIntent().getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView tx = findViewById(R.id.display_text_view);
        uname=sp.getString("USER_LOGGED_IN","");
        System.out.println("uname :"+uname);
        tx.setText("Welcome " + uname + "!");
    }

    public void view_dev_info(View v) {
        System.out.println("Device info");
        runOnUiThread(() -> startActivity(new Intent(this, dev_info.class)));

    }

    public void view_settings(View v) {
        System.out.println("Settings");
        runOnUiThread(() -> startActivity(new Intent(this, Settings.class)));

    }

    public void logout(View v) {
        //Intent i=new Intent(this, MainActivity.class);
        Thread thread = new Thread(() -> {
            try {
                //Your code goes here
                logout_site(uname);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    public void logout_site(String user) {
        String text = "";
        BufferedReader reader;
        String data = "user=" + user;
        try {
            System.out.println(data);
            URL url = new URL("https://heterogeneous-snow.000webhostapp.com/username-logout.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            text = sb.toString();
        } catch (Exception e) {
            System.out.println(e + "" + e.getMessage());
        } finally {
            System.out.println("End of session");
        }
        // Show response on activity
        System.out.println(text);
        if (text.contains("user logged out")) {
            SharedPreferences sp = getSharedPreferences("PREFLIST", MODE_PRIVATE);
            SharedPreferences.Editor e = sp.edit();
            e.putString("USER_LOGGED_IN", "").commit();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            System.out.println("Some Issue in Logging out");
        }
    }
}