package com.lolodev.fancydialog;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.lolodev.fancydialogslib.FancyBottomDialog;
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
//                FancyProgressDialog.getInstance()
//                        .setPrimaryColor(Color.WHITE)
//                        .setPrimaryColor(Color.CYAN)
//                        .setIndeterminateColor(Color.RED)
//                        .show(getSupportFragmentManager(), "");

                FancyBottomDialog fancyBottomDialog = FancyBottomDialog.newInstance(getBaseContext())
                        .setBackgroundColor(Color.WHITE)
                        .setBackgroundNavigationBar(Color.RED)
                        .setButtonPositiveTxtColor(Color.BLUE)
                        .setButtonNegativeTxtColor(Color.GREEN)
                        .setIsCancelable(true)
                        .setTitle("Title")
                        .setIcon(R.drawable.dialog_log_in_out, Color.RED)
                        .setBackgroundRoundCorners(true)
                        .setMessage("Message")
                        .setPositiveButton("OK", new FancyBottomDialog.OnPositiveClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setNegativeButton("NO", new FancyBottomDialog.OnNegativeClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                fancyBottomDialog.show(getSupportFragmentManager(), "");
            }
        });
    }
}
