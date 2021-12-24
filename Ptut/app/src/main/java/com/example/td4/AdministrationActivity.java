package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class AdministrationActivity extends present{


    private Button playA;
    private ImageView backButtonA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administration);

        backButtonA = findViewById(R.id.backButtonI);
        backButtonA.setOnClickListener(v -> change());
        playA=findViewById(R.id.startI);
        playA.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }
}
