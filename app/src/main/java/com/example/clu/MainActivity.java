package com.example.clu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.clu.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the user taps the Send button
     */
    public void sendMessage(View view){
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.passwordEditText);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.usernameEditText);
        String username = editText.getText().toString();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    //Your code goes here
                    sendInfo(username, password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        intent.putExtra(EXTRA_MESSAGE, username + " - " + password);
        startActivity(intent);

    }

    public void sendInfo(String user, String pass) {
        String text="";
        BufferedReader reader=null;
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
                //Log.d("STR",line);
                sb.append(line + "\n");
            }
            text=sb.toString();
        }
        catch(MalformedURLException e)
        {
            System.out.println(e+""+e.getMessage());
        }
        catch(IOException e)
        {
            System.out.println(e+""+e.getMessage());
        }
        finally {

        }
        // Show response on activity
        System.out.println(text);

    }

}