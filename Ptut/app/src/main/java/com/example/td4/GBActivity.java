package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class GBActivity extends Present {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gb);
        final ImageButton button = findViewById(R.id.button1);
        button.setOnClickListener(v -> change());

        Button play = findViewById(R.id.playButton);
        play.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), GBGame.class));
        });
    }

}
