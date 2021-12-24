package com.example.clu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.passwordEditText);
        String password = editText.getText().toString();
        editText = (EditText) findViewById(R.id.usernameEditText);
        String username = editText.getText().toString();
        Thread thread = new Thread(() -> {
            try  {
                //Your code goes here
                sendInfo(username, password, intent);
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

    public void sendInfo(String user, String pass, Intent intent) {
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
            runOnUiThread(() -> showDialog("LOGIN SUCCESS"));
            intent.putExtra(EXTRA_MESSAGE, " Welcome "+user+"!");
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