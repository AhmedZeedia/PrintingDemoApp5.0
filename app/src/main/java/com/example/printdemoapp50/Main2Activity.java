package com.example.printdemoapp50;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;

public class Main2Activity extends AppCompatActivity {
    public static final String IDENTIFIER_BUNDLE_KEY     = "IDENTIFIER_BUNDLE_KEY";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

    }

}
