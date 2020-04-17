package com.lolodev.fancydialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.lolodev.fancydialogslib.FancyProgressDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatButton openDialog = findViewById(R.id.open_dialog);
        openDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FancyProgressDialog.getInstance()
                        .setPrimaryColor(Color.WHITE)
                        .setPrimaryColor(Color.CYAN)
                        .setIndeterminateColor(Color.RED)
                        .show(getSupportFragmentManager(), "");
            }
        });
    }
}
