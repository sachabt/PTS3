package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class DroitActivity extends Present{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.droit);

        Button playButton = this.findViewById(R.id.playButton);

        playButton.setOnClickListener(v -> {
            Intent droitGame = new Intent(getApplicationContext(), DroitGame.class);
            startActivity(droitGame);
        });
    }
}
