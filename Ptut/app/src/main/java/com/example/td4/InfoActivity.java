package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class InfoActivity extends Present {

    private Button play;
    private ImageView backButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);

        backButton = findViewById(R.id.backButtonT);
        backButton.setOnClickListener(v -> change());
        play=findViewById(R.id.startT);
        play.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }





}
