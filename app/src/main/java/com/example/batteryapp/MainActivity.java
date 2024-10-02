package com.example.batteryapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.batteryapp.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private BatteryBroadcastReceiver batteryBroadcastReceiver=new BatteryBroadcastReceiver();
    private IntentFilter intentFilter =new IntentFilter();
    private ActivityMainBinding binding;
    TextView level,voltage,health,batteryType,chargingSource,temperature,chargingStatus;

    public static final String TAG="BatteryBroadcastReceiver";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        level = binding.textLevel;
        voltage = binding.textVoltage;
        health = binding.textHealth;
        batteryType = binding.textType;
        chargingSource=binding.textChargingSource;
        temperature=binding.textTemperature;
        chargingStatus=binding.textChargingStatus;
        readFromFile();

    }

    @Override
    protected void onPause() {
        unregisterReceiver(batteryBroadcastReceiver);
        Log.d(TAG, "BroadcastReceiver desregistrado satisfactoriamente.");
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(batteryBroadcastReceiver, intentFilter);
        Log.d(TAG, "BroadcastReceiver registrado satisfactoriamente.");
        super.onResume();
    }

    private void readFromFile(){
        try {
            FileInputStream fileInputStream = openFileInput("datos.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String text="";
            while ((text = bufferedReader.readLine()) != null) {
                stringBuilder.append(text).append("\n");
            }
            String[] data = stringBuilder.toString().split("\n");
            Log.v("textAAAA",data[4]);
            level.setText(data[0]);
            voltage.setText(data[1]);
            health.setText(data[2]);
            batteryType.setText(data[3]);
            chargingSource.setText(data[4]);
            temperature.setText(data[5]);
            chargingStatus.setText(data[6]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}