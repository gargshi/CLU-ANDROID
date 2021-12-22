package com.example.clu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
    }

    public void switchScreen(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.putExtra(EXTRA_MESSAGE, "Received output: "+ user + " - " + pass);
        startActivity(intent);
    }

    public void signup(View view)
    {
        EditText e1=(EditText) findViewById(R.id.susername);
        String user=e1.getText().toString();
        EditText e2=(EditText) findViewById(R.id.spassword);
        String pass=e2.getText().toString();
        EditText e3=(EditText) findViewById(R.id.srepassword);
        String repass=e3.getText().toString();
        if(pass.equals(repass))
        {
            Thread thread = new Thread(() -> {
                try  {
                    //Your code goes here
                    sendInfo(user, pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        else
        {
            System.out.println("Password not match");
        }
    }

    public void sendInfo(String user,String pass)//Intent intent)
    {
        String text="";
        BufferedReader reader;
        String data = "user=" + user + "&pass=" + pass;
        try
        {
            System.out.println(data);
            URL url = new URL("https://heterogeneous-snow.000webhostapp.com/insert_user.php");
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

        //intent.putExtra(EXTRA_MESSAGE, "Received output: "+user + " - " + pass);
        //startActivity(intent);

    }
}