package com.example.td4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BUActivity extends Present {




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bu);

        Button playButton = this.findViewById(R.id.playButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BUGame.class);
                startActivity(intent);
            }
        });
    }
}