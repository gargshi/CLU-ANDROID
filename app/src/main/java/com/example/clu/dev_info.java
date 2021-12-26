package com.example.clu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dev_info extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_info);
        TextView[] a = new TextView[16];
        a[0]=findViewById(R.id.os_version_tb);a[0].setText(System.getProperty("os.version")+ "(" + android.os.Build.VERSION.INCREMENTAL + ")");
        a[1]=findViewById(R.id.os_api_level_tb);a[1].setText(Integer.toString(android.os.Build.VERSION.SDK_INT));
        a[2]=findViewById(R.id.device_tb);a[2].setText(android.os.Build.DEVICE);
        a[3]=findViewById(R.id.model_product_tb);a[3].setText(android.os.Build.MODEL+ " ("+ android.os.Build.PRODUCT + ")");
        a[4]=findViewById(R.id.release_tb);a[4].setText(android.os.Build.VERSION.RELEASE);
        a[5]=findViewById(R.id.brand_tb);a[5].setText(android.os.Build.BRAND);
        a[6]=findViewById(R.id.display_tb);a[6].setText(android.os.Build.DISPLAY);
        a[7]=findViewById(R.id.cpu_abi_tb);a[7].setText(android.os.Build.CPU_ABI.toString());
        a[8]=findViewById(R.id.cpu_abi_2_tb);a[8].setText(android.os.Build.CPU_ABI2.toString());
        a[9]=findViewById(R.id.unknown_tb);a[9].setText(android.os.Build.UNKNOWN.toString());
        a[10]=findViewById(R.id.hardware_tb);a[10].setText(android.os.Build.HARDWARE.toString());
        a[11]=findViewById(R.id.build_id_tab);a[11].setText(android.os.Build.ID.toString());
        a[12]=findViewById(R.id.manufacturer_tab);a[12].setText(android.os.Build.MANUFACTURER.toString());
        a[13]=findViewById(R.id.serial_tab);a[13].setText(android.os.Build.SERIAL.toString());
        a[14]=findViewById(R.id.user_tab);a[14].setText(android.os.Build.USER.toString());
        a[15]=findViewById(R.id.host_tab);a[15].setText(android.os.Build.HOST.toString());
    }
    public void back_to_main_menu(View v)
    {
        startActivity(new Intent(this, DisplayMessageActivity.class));

    }
    private String getDeviceSuperInfo() {
        String s = "INFO:-";
        try {
            s += "\n OS Version: "      + System.getProperty("os.version")+ "(" + android.os.Build.VERSION.INCREMENTAL + ")";
            s += "\n OS API Level: "    + android.os.Build.VERSION.SDK_INT;
            s += "\n Device: "          + android.os.Build.DEVICE;
            s += "\n Model (and Product): " + android.os.Build.MODEL+ " ("+ android.os.Build.PRODUCT + ")";
            s += "\n RELEASE: "         + android.os.Build.VERSION.RELEASE;
            s += "\n BRAND: "           + android.os.Build.BRAND;
            s += "\n DISPLAY: "         + android.os.Build.DISPLAY;
            s += "\n CPU_ABI: "         + android.os.Build.CPU_ABI;
            s += "\n CPU_ABI2: "        + android.os.Build.CPU_ABI2;
            s += "\n UNKNOWN: "         + android.os.Build.UNKNOWN;
            s += "\n HARDWARE: "        + android.os.Build.HARDWARE;
            s += "\n Build ID: "        + android.os.Build.ID;
            s += "\n MANUFACTURER: "    + android.os.Build.MANUFACTURER;
            s += "\n SERIAL: "          + android.os.Build.SERIAL;
            s += "\n USER: "            + android.os.Build.USER;
            s += "\n HOST: "            + android.os.Build.HOST;
        } catch (Exception e) {
            s="Some Error";
        }
    return s;
    }//end getDeviceSuperInfo
}