package com.example.clu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch btntoggledark = findViewById(R.id.mode_switch);
        btntoggledark.setChecked(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES);
        btntoggledark.setOnClickListener(
                view ->
                {
                    SharedPreferences sp= getSharedPreferences("PREFLIST", Context.MODE_PRIVATE);
                    SharedPreferences.Editor e=sp.edit();
                    e.putBoolean("Dark_Mode",btntoggledark.isChecked()).commit();
                    if(btntoggledark.isChecked())
                    {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    else
                    {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
        );
        btntoggledark.setOnCheckedChangeListener(
                (compoundButton, b) ->
                {
                    SharedPreferences sp= getSharedPreferences("PREFLIST",MODE_PRIVATE);
                    SharedPreferences.Editor e=sp.edit();
                    if(b)
                    {
                        e.putBoolean("Dark_Mode",true).commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    else
                    {
                        e.putBoolean("Dark_Mode",false).commit();
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
        );

    }
}