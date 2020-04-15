package com.lolodev.fancydialog;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FancyDialog fancyDialog=FancyDialog.newInstance();
        fancyDialog.setBackgroundColor(Color.BLACK);
        fancyDialog.setIsCancelable(true);
        fancyDialog.show(getSupportFragmentManager(),"");
    }
}
