package com.example.clu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.clu.MESSAGE";
    public Intent intent=null;
    SharedPreferences sp= null;
    SharedPreferences.Editor e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        sp=getSharedPreferences("PREFLIST", Context.MODE_PRIVATE);
        e1=sp.edit();
        Boolean res=sp.getBoolean("Dark_Mode", true);
        String u=sp.getString("USER_LOGGED_IN","");
        if(!u.isEmpty())
        {
            intent.putExtra(EXTRA_MESSAGE, " Welcome "+u+"!");
            startActivity(intent);
        }
        System.out.println(sp.getAll());
        if (res)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view) {
        // Do something in response to button

        EditText editText = (EditText) findViewById(R.id.passwordEditText);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.usernameEditText);
        String username = editText.getText().toString();
        Thread thread = new Thread(() -> {
            try  {
                //Your code goes here
                sendInfo(username, password, intent, e1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    public void switchScreen(View view)
    {
        Intent intent = new Intent(this, SignupActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, "Received output: "+ user + " - " + pass);
        startActivity(intent);
    }

    public void sendInfo(String user, String pass, Intent intent,SharedPreferences.Editor e1) {
        String text="";
        BufferedReader reader;
        String data = "user=" + user + "&pass=" + pass;
        try
        {
            System.out.println(data);
            URL url = new URL("https://heterogeneous-snow.000webhostapp.com/username-check.php");
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
            text=sb.toString();
        } catch(Exception e)
        {
            System.out.println(e+""+e.getMessage());
        } finally {
            System.out.println("End of session");
        }
        // Show response on activity
        System.out.println(text);
        if(text.contains("user found"))
        {
            intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(EXTRA_MESSAGE, user);
            e1.putString("USER_LOGGED_IN",user).commit();
            startActivity(intent);
        }
        else
        {
            runOnUiThread(() -> showDialog("LOGIN FAILED. Check your credentials"));
        }

    }
    public void showDialog(String msg)
    {
        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.create();
        ad.setTitle("Alert!");
        ad.setMessage(msg).setCancelable(false);
        ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        ad.show();
    }
}