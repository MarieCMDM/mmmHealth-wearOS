package com.example.mmmmhealth_wear;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mmmmhealth_wear.databinding.ActivityMainBinding;

import java.sql.SQLException;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;

    Button btnReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mTextView = binding.text;
        btnReport = findViewById(R.id.btnRecord);
        int SYS = 121;
        int DIA = 71;
        int ECG = 51;
        int Spo2 = 91;

        //Connect wearable to DB
        try {
            DbUtility.connectionToDb();
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }


        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    DbUtility.submitData1("1",99,99,"' '", SYS, DIA, ECG, Spo2);
                    Toast.makeText(getApplicationContext(), "Reported", Toast.LENGTH_SHORT).show();
                } catch (SQLException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "An error occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}