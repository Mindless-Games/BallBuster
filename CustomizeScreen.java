package com.example.andrew.ballbuster;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class CustomizeScreen extends Activity {

    ArrayList<Button> buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customize_screen);

        final Button button2 = findViewById(R.id.cusButton2);
        final Button button3 = findViewById(R.id.cusButton3);
        final Button button1 = findViewById(R.id.cusButton);
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);


        for(Button b : buttonList) {
            b.setAlpha(.5f);
        }

        button1.setAlpha(0);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public void setColor() {

    }

    public void getColor() {

    }
}
