package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class DroitActivity extends Present{

    private Button playA;
    private ImageView backButtonA;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.droit);

        //Initialise les boutons de la page
        backButtonA = this.findViewById(R.id.backButtonT2);
        backButtonA.setOnClickListener(v ->
        {Intent map=new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(map);});
        playA=findViewById(R.id.playButton);
        playA.setOnClickListener(v -> {
            Intent DroitGame=new Intent(getApplicationContext(),DroitGame.class);
            startActivity(DroitGame);
        });
    }
}
