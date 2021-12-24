package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class infoActivity extends present {

    private Button playI;
    private ImageView backButtonI;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        backButtonI = findViewById(R.id.backButtonI);
        backButtonI.setOnClickListener(v -> change());
        playI=findViewById(R.id.startI);
        playI.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }





}
