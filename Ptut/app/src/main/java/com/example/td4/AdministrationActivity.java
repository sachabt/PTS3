package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class AdministrationActivity extends Present {


    private Button playA;
    private ImageView backButtonA;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.administration);

        //Initialise les boutons de la page
        backButtonA = findViewById(R.id.backButtonT);
        backButtonA.setOnClickListener(v -> change());
        playA=findViewById(R.id.startT);
        playA.setOnClickListener(v -> {
            Intent adminGame=new Intent(getApplicationContext(),AdminGame.class);
            startActivity(adminGame);
        });
    }
}
