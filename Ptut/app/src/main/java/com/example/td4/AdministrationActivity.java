package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class AdministrationActivity extends present{


    private Button play;
    private ImageView backButtonA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administration);

        backButtonA = findViewById(R.id.backButtonA);
        backButtonA.setOnClickListener(v -> change());
        play=findViewById(R.id.start);
        play.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }
}
